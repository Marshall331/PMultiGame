package application.view;

import application.control.menuDifficulte;
import application.tools.AlertUtilities;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.GridPane;
import javafx.scene.robot.Robot;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import model.game;
import model.player;

/**
 * Controller JavaFX de la scène du jeu.
 */
public class gameController {

	// Stage de la fenêtre précédente
	private Stage primaryStage;

	// Scène de la fenêtre précédente
	private Scene scene;

	private game game;

	private static final int WIDTH = 1680 / 2;
	private static final int HEIGHT = 972 / 2;
	private static final int PADDLE_WIDTH = 30;
	private static final int PADDLE_HEIGHT = 160;
	private static final int BALL_RADIUS = 20;
	private static final double PADDLE_SPEED = 7;
	private static final double BALL_SPEED = 5;
	private double paddle1Velocity;
	private double paddle2Velocity;
	private boolean ballCollidedWithPaddle;

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

	private player player1;
	private player player2;

	/**
	 * Initialisation du contrôleur de vue GameController.
	 *
	 * @param _containingStage Stage qui contient la fenêtre précédente.
	 */
	public void initContext(Stage _containingStage, Scene scene) {
		this.primaryStage = _containingStage;
		this.scene = scene;
		this.primaryStage.setOnCloseRequest(e -> this.closeWindow(e));

		player1 = new player(paddle1, false, 5, false);
		player2 = new player(paddle2, true, 5, true);
		game = new game(player1, player2, balle);

		// Mises à jour automatique des scores
		labScrPlayer1.textProperty().bind(Bindings.convert(Bindings.concat(game.scorePlayer2)));
		labScrPlayer2.textProperty().bind(Bindings.convert(Bindings.concat(game.scorePlayer1)));
		// Configuration des évenements du clavier
		setControls(true, player2.isComputer);

		// Lancement du jeu
		game.start();
	}

	/**
	 * Affichage de la fenêtre.
	 */
	public void displayDialog() {
		this.primaryStage.show();
	}

	private void resetPoint() {
		balle.setTranslateX(0);
		balle.setTranslateY(0);
		paddle1.setTranslateY(0);
		paddle2.setTranslateY(0);
	}

	private void setControls(boolean isMouseControl, boolean isSoloGame) {

		if (isMouseControl && isSoloGame) {
			board.setOnMouseEntered(event -> {
				board.setCursor(Cursor.NONE);
			});
			board.setOnMouseMoved(event -> {
				// Calculez la position Y souhaitée pour le centre de la raquette
				double desiredRacketY = event.getY() - HEIGHT;
				double maxY = -HEIGHT + PADDLE_HEIGHT / 2;
				double minY = HEIGHT - PADDLE_HEIGHT / 2 + 10;

				if (desiredRacketY <= maxY) {
					desiredRacketY = maxY + 1;
				} else if (desiredRacketY >= minY) {
					desiredRacketY = minY - 1;
				}

				player1.mouseMove = desiredRacketY;
			});
			board.setOnMouseExited(event -> {
				board.setCursor(Cursor.DEFAULT);
			});
		} else if (!isMouseControl && isSoloGame) {
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
	private void doRetour() {
		this.primaryStage.close();
		game.stop();
		menuDifficulte mD = new menuDifficulte(primaryStage);
	}

	/*
	 * Méthode de fermeture de la fenêtre par la croix.
	 *
	 * @param e Evénement associé (inutilisé pour le moment)
	 *
	 */
	private void closeWindow(WindowEvent e) {
		if (AlertUtilities.confirmYesCancel(this.primaryStage, "Quitter l'application",
				"Etes vous sur de vouloir quitter le jeu ?", null, AlertType.CONFIRMATION)) {
			this.primaryStage.close();
		}
		e.consume();
	}
}
