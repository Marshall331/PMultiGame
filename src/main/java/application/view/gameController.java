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
	private GridPane terrain;
	@FXML
	private Label labScrj1;
	@FXML
	private Label labScrj2;
	@FXML
	private Rectangle paddle1;
	@FXML
	private Rectangle paddle2;
	@FXML
	private Circle balle;

	private player j1;
	private player j2;

	/**
	 * Initialisation du contrôleur de vue GameController.
	 *
	 * @param _containingStage Stage qui contient la fenêtre précédente.
	 */
	public void initContext(Stage _containingStage, Scene scene) {
		this.primaryStage = _containingStage;
		this.scene = scene;
		this.primaryStage.setOnCloseRequest(e -> this.closeWindow(e));

		j1 = new player(paddle1, false);
		j2 = new player(paddle2, true);
		game = new game(j1, j2, balle);

		// Mises à jour automatique des scores
		labScrj1.textProperty().bind(Bindings.convert(Bindings.concat(game.scr1)));
		labScrj2.textProperty().bind(Bindings.convert(Bindings.concat(game.scr2)));
		// Configuration des évenements du clavier
		setKeyEvents();

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

	private void setKeyEvents() {
		// Mise en mouvement des raquettes lorsque les touches sont appuyés
		terrain.setOnKeyPressed(event -> {
			switch (event.getCode()) {
				case Z:
					j1.vel = -10;
					break;
				case S:
					j1.vel = 10;
					break;
				case P:
					j2.vel = -10;
					break;
				case L:
					j2.vel = 10;
					break;
				default:
					event.consume();
					break;
			}
		});

		// Arrêt du mouvement des raquettes lorsque les touches sont relachés
		terrain.setOnKeyReleased(event -> {
			switch (event.getCode()) {
				case Z:
					j1.vel = 0;
					break;
				case S:
					j1.vel = 0;
					break;
				case P:
					j2.vel = 0;
					break;
				case L:
					j2.vel = 0;
					break;
				default:
					event.consume();
					break;
			}
		});

		scene.setOnMouseMoved(event -> {
			scene.setCursor(Cursor.NONE);

			double pos = event.getY() - HEIGHT;
			if (pos - PADDLE_HEIGHT / 2 > j1.rect.getTranslateY()) {
				j1.vel = 5;
			}
			if (pos + PADDLE_HEIGHT / 2 < j1.rect.getTranslateY()) {
				j1.vel = -5;
			}
			if (pos > j1.rect.getTranslateY() + 160 && pos < j1.rect.getTranslateY() - 160) {
				j1.vel = 0;
			}
			System.out.println(event.getY());
			System.out.println(pos);

		});

		terrain.setOnMouseExited(event -> {
			// La souris a quitté le terrain du jeu, rétablissez le curseur par défaut
			scene.setCursor(Cursor.DEFAULT);
		});
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
