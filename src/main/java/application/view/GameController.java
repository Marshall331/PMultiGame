package application.view;

import application.control.Game;
import application.control.MainMenu;
import application.control.SettingsMenu;
import application.tools.AlertUtilities;
import application.tools.Animations;
import application.tools.ConfigurationSave;
import application.tools.StageManagement;
import application.tools.Style;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import model.game;
import model.gameConfiguration;
import model.player;
import javafx.scene.paint.Color;
import javafx.scene.paint.RadialGradient;
import javafx.scene.paint.Stop;

/**
 * Controller JavaFX de la scène du jeu.
 */
public class GameController {

	// Stage de la fenêtre précédente
	private Stage primaryStage;

	// Scène de la fenêtre précédente
	public Scene scene;

	private game game;

	@FXML
	BorderPane borderpane;
	@FXML
	public GridPane scoreBoard;
	@FXML
	public GridPane boardGame;
	@FXML
	public Line topLine;
	@FXML
	public Line midLine;
	@FXML
	public Label labScrPlayer1;
	@FXML
	public Label labScrPlayer2;
	@FXML
	public Label labGoal;
	@FXML
	public Rectangle paddle1;
	@FXML
	public Rectangle paddle2;
	@FXML
	public Circle balle;
	@FXML
	public Button menuButton;
	@FXML
	public Button settingsButton;

	public gameConfiguration conf;
	public SettingsMenu settingsMenu;

	public player player1;
	public player player2;

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
		this.conf = ConfigurationSave.chargerConfiguration();

		setItemsSize();
		setupPlayers();
		initViewItems();
		createGame();
	}

	private void setItemsSize() {

		double borderWidth = primaryStage.getWidth() - primaryStage.getScene().getWidth() + 1;
		double titleBarHeight = primaryStage.getHeight() - primaryStage.getScene().getHeight();

		this.primaryStage.setWidth(this.conf.WIDTH * 2 + borderWidth);
		this.primaryStage.setHeight(this.conf.HEIGHT * 2 + 78 + titleBarHeight);
		this.borderpane.setPrefSize(this.conf.WIDTH * 2, this.conf.HEIGHT * 2 + 78);
		this.scoreBoard.setPrefSize(this.conf.WIDTH * 2, 78);
		this.boardGame.setPrefSize(this.conf.WIDTH * 2, this.conf.HEIGHT * 2);
		this.borderpane.setMinSize(this.conf.WIDTH * 2, this.conf.HEIGHT * 2 + 78);
		this.scoreBoard.setMinSize(this.conf.WIDTH * 2, 78);
		this.boardGame.setMinSize(this.conf.WIDTH * 2, this.conf.HEIGHT * 2);
		this.borderpane.setMaxSize(this.conf.WIDTH * 2, this.conf.HEIGHT * 2 + 78);
		this.scoreBoard.setMaxSize(this.conf.WIDTH * 2, 78);
		this.boardGame.setMaxSize(this.conf.WIDTH * 2, this.conf.HEIGHT * 2);
		this.midLine.setEndY(this.conf.midLineY);
		this.paddle1.setHeight(this.conf.player1PaddleSize);
		this.paddle2.setHeight(this.conf.player2PaddleSize);
		this.paddle1.setWidth(this.conf.PADDLE_WIDTH);
		this.paddle2.setWidth(this.conf.PADDLE_WIDTH);
		this.balle.setRadius(this.conf.ballSize);
		// this.labGoal.setStyle("-fx-text-fill: " + this.conf.ballColor + ";");
		this.labGoal.setOpacity(0);

		Style.setBallColor(this.balle, this.conf.ballColor);
		Style.setPlayerColor(paddle1, this.conf.player1Color);
		Style.setPlayerColor(paddle2, this.conf.player2Color);

		this.menuButton.setPrefWidth(this.conf.menuButtonWidth);
		this.menuButton.setStyle("-fx-font-size: " + this.conf.menuButtonFontSize + "px; -fx-font-weight: bold;");

		// this.labScrPlayer1.setStyle("-fx-font-size: " + this.conf.labScoreFontSize +
		// "px; -fx-font-weight: bold;"
		// + "-fx-border-color: red; -fx-border-width: 4px;");
		// this.labScrPlayer2.setStyle("-fx-font-size: " + this.conf.labScoreFontSize +
		// "px; -fx-font-weight: bold;"
		// + "-fx-border-color: red; -fx-border-width: 4px;");
		this.labScrPlayer1.setStyle("-fx-font-size: " + this.conf.labScoreFontSize + "px; -fx-font-weight: bold;");
		this.labScrPlayer2.setStyle("-fx-font-size: " + this.conf.labScoreFontSize + "px; -fx-font-weight: bold;");
		this.labScrPlayer1.setPrefWidth(this.conf.labScoreWidth);
		this.labScrPlayer2.setPrefWidth(this.conf.labScoreWidth);

		GridPane.setMargin(this.labScrPlayer1,
				new Insets(0, this.conf.labScoreWidth + this.conf.labScoreMargin, 0, 0));
		GridPane.setMargin(this.labScrPlayer2,
				new Insets(0, 0, 0, this.conf.labScoreWidth + this.conf.labScoreMargin));

		GridPane.setMargin(this.settingsButton, new Insets(0, this.conf.settingsButtonRightMargin, 0, 0));

	}

	private void setupPlayers() {
		this.player1 = new player(1, paddle1, this.conf.isPlayer1Computer, this.conf.player1MaxSpeed,
				this.conf.isPlayer1MouseControl);
		this.player2 = new player(2, paddle2, this.conf.isPlayer2Computer, this.conf.player2MaxSpeed,
				this.conf.isPlayer2MouseControl);
	}

	private void initViewItems() {
		this.primaryStage.setOnCloseRequest(e -> this.closeWindow(e));

		Animations.setAnimatedIcon(settingsButton, 1.15, "grey");
	}

	private void createGame() {
		setControls(player1.mouseControl, conf.isSoloGame);
		game = new game(this, player1, player2, balle, conf);

		// Mises à jour automatique des scores
		labScrPlayer1.textProperty().bind(Bindings.convert(Bindings.concat(game.scorePlayer2)));
		labScrPlayer2.textProperty().bind(Bindings.convert(Bindings.concat(game.scorePlayer1)));

		// Lancement du jeu
		game.start();
	}

	private void setControls(boolean _isMouseControl, boolean _isSoloGame) {
		if (!this.conf.isPlayer1Computer) {
			if (this.conf.isPlayer1MouseControl) {
				this.setMouseControl(player1);
			} else {
				this.setKeyboardControl(player1);
			}
		}
		if (!this.conf.isPlayer2Computer) {
			if (this.conf.isPlayer2MouseControl) {
				this.setMouseControl(player2);
			} else {
				this.setKeyboardControl(player2);
			}
		}
		// if (_isMouseControl && _isSoloGame) {
		// board.setOnMouseEntered(event -> {
		// board.setCursor(Cursor.NONE);
		// });
		// board.setOnMouseMoved(event -> {
		// // Calculez la position Y souhaitée pour le centre de la raquette
		// double desiredRacketY = event.getY() - game.HEIGHT;
		// double maxY = game.paddleMaxY;
		// double minY = game.paddleMinY;

		// if (desiredRacketY <= minY) {
		// desiredRacketY = minY;
		// } else if (desiredRacketY >= maxY) {
		// desiredRacketY = maxY;
		// }

		// player1.mouseMove = desiredRacketY;
		// });
		// board.setOnMouseExited(event -> {
		// board.setCursor(Cursor.DEFAULT);
		// });
		// } else if (!_isMouseControl && _isSoloGame) {
		// scene.setOnKeyPressed(event -> {
		// switch (event.getCode()) {
		// case Z:
		// player1.vel = -player1.maxSpeed;
		// break;
		// case S:
		// player1.vel = player1.maxSpeed;
		// break;
		// default:
		// event.consume();
		// break;
		// }
		// });
		// scene.setOnKeyReleased(event -> {
		// switch (event.getCode()) {
		// case Z:
		// player1.vel = 0;
		// break;
		// case S:
		// player1.vel = 0;
		// break;
		// default:
		// event.consume();
		// break;
		// }
		// });
		// } else {
		// scene.setOnKeyPressed(event -> {
		// switch (event.getCode()) {
		// case Z:
		// player1.vel = -player1.maxSpeed;
		// break;
		// case S:
		// player1.vel = player1.maxSpeed;
		// break;
		// case P:
		// player2.vel = -player2.maxSpeed;
		// break;
		// case L:
		// player2.vel = player2.maxSpeed;
		// break;
		// default:
		// event.consume();
		// break;
		// }
		// });
		// scene.setOnKeyReleased(event -> {
		// switch (event.getCode()) {
		// case Z:
		// player1.vel = 0;
		// break;
		// case S:
		// player1.vel = 0;
		// break;
		// case P:
		// player2.vel = 0;
		// break;
		// case L:
		// player2.vel = 0;
		// break;
		// default:
		// event.consume();
		// break;
		// }
		// });
		// }
	}

	private void setMouseControl(player _player) {
		boardGame.setOnMouseEntered(event -> {
			boardGame.setCursor(Cursor.NONE);
		});
		boardGame.setOnMouseMoved(event -> {
			double desiredRacketY = event.getY() - game.HEIGHT;
			double maxY = _player.paddleMaxY;
			double minY = _player.paddleMinY;
			System.out.println("X : " + event.getX() + "Y : " + event.getY());
			if (desiredRacketY <= minY) {
				desiredRacketY = minY;
			} else if (desiredRacketY >= maxY) {
				desiredRacketY = maxY;
			}

			_player.mouseMove = desiredRacketY;
		});
		boardGame.setOnMouseExited(event -> {
			boardGame.setCursor(Cursor.DEFAULT);
		});
	}

	private void setKeyboardControl(player _Player) {
		if (_Player.id == 1) {
			scene.setOnKeyPressed(event -> {
				switch (event.getCode()) {
					case Z:
						_Player.vel = -_Player.maxSpeed;
						break;
					case S:
						_Player.vel = _Player.maxSpeed;
						break;
					default:
						event.consume();
						break;
				}
			});
			scene.setOnKeyReleased(event -> {
				switch (event.getCode()) {
					case Z:
						_Player.vel = 0;
						break;
					case S:
						_Player.vel = 0;
						break;
					default:
						event.consume();
						break;
				}
			});
		} else {
			scene.setOnKeyPressed(event -> {
				switch (event.getCode()) {
					case P:
						_Player.vel = -_Player.maxSpeed;
						break;
					case L:
						_Player.vel = _Player.maxSpeed;
						break;
					default:
						event.consume();
						break;
				}
			});
			scene.setOnKeyReleased(event -> {
				switch (event.getCode()) {
					case P:
						_Player.vel = 0;
						break;
					case L:
						_Player.vel = 0;
						break;
					default:
						event.consume();
						break;
				}
			});
		}
	}

	@FXML
	private void doReset() {
		this.conf.resetScore();
		ConfigurationSave.sauvegarderConfiguration(conf);
		Game game = new Game(primaryStage);
	}

	/*
	 * Action menu retour. Retourne à la fenêtre précédente.
	 */
	@FXML
	private void doMenu() {
		game.stop();
		if (this.settingsMenu != null) {
			this.settingsMenu.closeMenu();
		}

		this.primaryStage.close();
		MainMenu mM = new MainMenu();
		mM.inGame = true;
		mM.start(primaryStage);
	}

	@FXML
	private void doSettings() {
		game.stop();
		StageManagement.disableItems(this.primaryStage.getScene(), true);
		this.conf.scr1 = Integer.parseInt(this.labScrPlayer1.getText());
		this.conf.scr2 = Integer.parseInt(this.labScrPlayer2.getText());
		ConfigurationSave.sauvegarderConfiguration(conf);
		this.settingsMenu = new SettingsMenu(primaryStage, true);
		this.settingsMenu.startMenu();
		updateGameSettings();
	}

	private void updateGameSettings() {
		this.conf = ConfigurationSave.chargerConfiguration();
		if (this.conf.isConfHasChanged) {
			Game g = new Game(this.primaryStage);
		} else {
			StageManagement.disableItems(this.primaryStage.getScene(), false);
			this.game.start();
		}
	}

	/*
	 * Méthode de fermeture de la fenêtre par la croix.
	 *
	 * @param e Evénement associé
	 *
	 */
	private void closeWindow(WindowEvent _e) {
		this.game.stop();
		if (AlertUtilities.confirmYesCancel(this.primaryStage, "Quitter l'application",
				"Etes vous sur de vouloir quitter le jeu ?", null, AlertType.CONFIRMATION)) {
			if (this.settingsMenu != null) {
				this.settingsMenu.closeMenu();
			}
			this.primaryStage.close();
		}
		this.game.start();
		_e.consume();
	}
}