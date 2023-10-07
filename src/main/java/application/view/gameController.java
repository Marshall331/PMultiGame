package application.view;

import application.control.Game;
import application.control.MainMenu;
import application.control.SettingsMenu;
import application.tools.AlertUtilities;
import application.tools.Utilities;
import javafx.animation.ScaleTransition;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import model.game;
import model.gameConfiguration;
import model.player;
import javafx.util.Duration; // Assurez-vous d'importer Duration de javafx.util

/**
 * Controller JavaFX de la scène du jeu.
 */
public class GameController {

	// Stage de la fenêtre précédente
	private Stage primaryStage;

	// Scène de la fenêtre précédente
	private Scene scene;

	private game game;

	@FXML
	private GridPane board;
	@FXML
	private Label labScrPlayer1;
	@FXML
	private Label labScrPlayer2;
	@FXML
	private Rectangle paddle1;
	@FXML
	private Rectangle paddle2;
	@FXML
	private Circle balle;
	@FXML
	private Button menuButton;
	@FXML
	private Button settingsButton;

	private gameConfiguration conf;

	private player player1;
	private player player2;

	/**
	 * Affichage de la fenêtre.
	 */
	public void displayDialog() {
		this.primaryStage.show();
	}

	/**
	 * Initialisation du contrôleur de vue GameController.
	 *
	 * @param _containingStage Stage qui contient la fenêtre précédente.
	 */
	public void initContext(Stage _containingStage, Scene _scene) {
		this.primaryStage = _containingStage;
		this.scene = _scene;

		initValues();
		initViewItems();
		createGame();
	}

	private void initValues() {
		gameConfiguration _conf = Utilities.chargerConfiguration();
		this.conf = _conf;

		this.player1 = new player(paddle1, this.conf.player1isComputer, this.conf.player1MaxSpeed,
				this.conf.player1isSpeedLimited, this.conf.player1MouseControl);
		this.player2 = new player(paddle2, this.conf.player2isComputer, this.conf.player2MaxSpeed,
				this.conf.player2isSpeedLimited, this.conf.player2MouseControl);
	}

	private void initViewItems() {
		this.primaryStage.setOnCloseRequest(e -> this.closeWindow(e));

		ScaleTransition scaleTransition = new ScaleTransition(Duration.millis(200), settingsButton);
		scaleTransition.setToX(1.1); // horizontal zoom
		scaleTransition.setToY(1.1); // vertical zoom

		settingsButton.setOnMouseEntered(event -> {
			scaleTransition.playFromStart();
			this.settingsButton.setStyle("-fx-background-color: white;");
		});

		Image image = new Image(
				MultiplayerMenuController.class.getResource("images/SettingsIcon.png").toExternalForm());
		ImageView imageView = new ImageView(image);
		imageView.setFitWidth(35);
		imageView.setFitHeight(35);
		settingsButton.setGraphic(imageView);

		settingsButton.setOnMouseExited(event -> {
			scaleTransition.stop();
			settingsButton.setScaleX(1);
			settingsButton.setScaleY(1);
		});
	}

	private void createGame() {
		setControls(player1.mouseControl, conf.isSoloGame);
		game = new game(player1, player2, balle, conf);

		// Mises à jour automatique des scores
		labScrPlayer1.textProperty().bind(Bindings.convert(Bindings.concat(game.scorePlayer2)));
		labScrPlayer2.textProperty().bind(Bindings.convert(Bindings.concat(game.scorePlayer1)));

		// Lancement du jeu
		game.start();
	}

	private void setControls(boolean _isMouseControl, boolean _isSoloGame) {
		// System.out.println(this.conf.player1MouseControl);
		// scene.setOnMouseMoved(event -> {
		// double x = event.getSceneX();
		// double y = event.getSceneY();
		// System.out.println("Coordonnées X : " + x + ", Y : " + y);
		// });
		if (_isMouseControl && _isSoloGame) {
			board.setOnMouseEntered(event -> {
				board.setCursor(Cursor.NONE);
			});
			board.setOnMouseMoved(event -> {
				// Calculez la position Y souhaitée pour le centre de la raquette
				double desiredRacketY = event.getY() - game.HEIGHT;
				double maxY = game.paddleMaxY;
				double minY = game.paddleMinY;

				if (desiredRacketY <= minY) {
					desiredRacketY = minY;
				} else if (desiredRacketY >= maxY) {
					desiredRacketY = maxY;
				}

				player1.mouseMove = desiredRacketY;
			});
			board.setOnMouseExited(event -> {
				board.setCursor(Cursor.DEFAULT);
			});
		} else if (!_isMouseControl && _isSoloGame) {
			scene.setOnKeyPressed(event -> {
				switch (event.getCode()) {
					case Z:
						player1.vel = -player1.maxSpeed;
						break;
					case S:
						player1.vel = player1.maxSpeed;
						break;
					default:
						event.consume();
						break;
				}
			});
			scene.setOnKeyReleased(event -> {
				switch (event.getCode()) {
					case Z:
						player1.vel = 0;
						break;
					case S:
						player1.vel = 0;
						break;
					default:
						event.consume();
						break;
				}
			});
		} else {
			scene.setOnKeyPressed(event -> {
				switch (event.getCode()) {
					case Z:
						player1.vel = -player1.maxSpeed;
						break;
					case S:
						player1.vel = player1.maxSpeed;
						break;
					case P:
						player2.vel = -player2.maxSpeed;
						break;
					case L:
						player2.vel = player2.maxSpeed;
						break;
					default:
						event.consume();
						break;
				}
			});
			scene.setOnKeyReleased(event -> {
				switch (event.getCode()) {
					case Z:
						player1.vel = 0;
						break;
					case S:
						player1.vel = 0;
						break;
					case P:
						player2.vel = 0;
						break;
					case L:
						player2.vel = 0;
						break;
					default:
						event.consume();
						break;
				}
			});
		}
	}

	/*
	 * Action menu retour. Retourne à la fenêtre précédente.
	 */
	@FXML
	private void doMenu() {
		this.primaryStage.close();
		game.stop();
		MainMenu mM = new MainMenu();
		mM.inGame = true;
		mM.start(primaryStage);
	}

	@FXML
	private void doSettings() {
		game.stop();
		disableButtons(true);
		this.conf.scr1 = Integer.parseInt(this.labScrPlayer1.getText());
		this.conf.scr2 = Integer.parseInt(this.labScrPlayer2.getText());
		System.out.println("SCR1 : " + this.conf.scr1);
		System.out.println("SCR2 : " + this.conf.scr2);
		Utilities.sauvegarderConfiguration(conf);
		SettingsMenu sM = new SettingsMenu(primaryStage, true);
		disableButtons(false);
		updateGameSettings();
	}

	private void updateGameSettings() {
		this.conf = Utilities.chargerConfiguration();
		Game g = new Game(this.primaryStage);
	}

	private void disableButtons(boolean _disable) {
		menuButton.setDisable(_disable);
		settingsButton.setDisable(_disable);
	}

	/*
	 * Méthode de fermeture de la fenêtre par la croix.
	 *
	 * @param e Evénement associé (inutilisé pour le moment)
	 *
	 */
	private void closeWindow(WindowEvent _e) {
		game.stop();
		if (AlertUtilities.confirmYesCancel(this.primaryStage, "Quitter l'application",
				"Etes vous sur de vouloir quitter le jeu ?", null, AlertType.CONFIRMATION)) {
			this.primaryStage.close();
		}
		_e.consume();
		game.start();
	}
}