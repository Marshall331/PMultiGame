package application.view;

import application.control.menuDifficulte;
import application.tools.AlertUtilities;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.binding.Bindings;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.GridPane;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Duration;

/**
 * Controller JavaFX de la scène du jeu.
 */
public class gameController {

	// Stage de la fenêtre précédente
	private Stage primaryStage;

	// Scène de la fenêtre précédente
	private Scene scene;

	// Boucle du jeu
	Timeline jeu;

	// Score des joueurs
	private IntegerProperty scrj1 = new SimpleIntegerProperty(0);
	private IntegerProperty scrj2 = new SimpleIntegerProperty(0);

	private static final int WIDTH = 1680 / 2;
	private static final int HEIGHT = 972 / 2;
	private static final int PADDLE_WIDTH = 30;
	private static final int PADDLE_HEIGHT = 160;
	private static final int BALL_RADIUS = 30;
	private static final double PADDLE_SPEED = 1;
	private static final double BALL_SPEED = 1;

	private double paddle1Velocity;
	private double paddle2Velocity;
	private double ballVelocityX;
	private double ballVelocityY;
	public boolean ballCollidedWithPaddle;

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

	/**
	 * Initialisation du contrôleur de vue GameController.
	 *
	 * @param _containingStage Stage qui contient la fenêtre précédente.
	 */
	public void initContext(Stage _containingStage, Scene scene) {
		this.primaryStage = _containingStage;
		this.scene = scene;
		this.primaryStage.setOnCloseRequest(e -> this.closeWindow(e));

		// Mises à jour automatique des scores
		labScrj1.textProperty().bind(Bindings.convert(Bindings.concat(scrj1)));
		labScrj2.textProperty().bind(Bindings.convert(Bindings.concat(scrj2)));

		// Lancement du jeu
		this.jouer();
	}

	/**
	 * Affichage de la fenêtre.
	 */
	public void displayDialog() {
		this.primaryStage.show();
	}

	/**
	 * Boucle de jeu, gère les déplacements de la balle et des raquettes et met à
	 * jour les scores lors des buts.
	 */
	public void jouer() {

		paddle1Velocity = 0;
		paddle2Velocity = 0;
		ballVelocityX = BALL_SPEED;
		ballVelocityY = BALL_SPEED;
	    this.ballCollidedWithPaddle = false; // Variable de contrôle

		jeu = new Timeline(new KeyFrame(Duration.millis(16), new EventHandler<ActionEvent>() {
			public void handle(ActionEvent arg0) {

				// Déplacer les raquettes
				paddle1.setTranslateY(paddle1.getTranslateY() + paddle1Velocity);
				paddle2.setTranslateY(paddle2.getTranslateY() + paddle2Velocity);

				// Gérer les collisions avec les bords
				if (paddle1.getTranslateY() < -HEIGHT + PADDLE_HEIGHT / 2) {
					paddle1.setTranslateY(-HEIGHT + PADDLE_HEIGHT / 2);
				} else if (paddle1.getTranslateY() > HEIGHT - PADDLE_HEIGHT / 2) {
					paddle1.setTranslateY(HEIGHT - PADDLE_HEIGHT / 2);
				}
				if (paddle2.getTranslateY() < -HEIGHT + PADDLE_HEIGHT / 2) {
					paddle2.setTranslateY(-HEIGHT + PADDLE_HEIGHT / 2);
				} else if (paddle2.getTranslateY() > HEIGHT - PADDLE_HEIGHT / 2) {
					paddle2.setTranslateY(HEIGHT - PADDLE_HEIGHT / 2);
				}

				// Mouvement de la balle
				balle.setTranslateX(balle.getTranslateX() + ballVelocityX);
				balle.setTranslateY(balle.getTranslateY() + ballVelocityY);

				// Gérer les collisions de la balle avec les raquettes
	            if ((balle.getBoundsInParent().intersects(paddle1.getBoundsInParent())
	                    || balle.getBoundsInParent().intersects(paddle2.getBoundsInParent()))
	                    && !ballCollidedWithPaddle) {
	                ballVelocityX *= -1;
	                balle.setTranslateX(balle.getTranslateX() + ballVelocityX);
	                ballCollidedWithPaddle = true;
	            } else if (!balle.getBoundsInParent().intersects(paddle1.getBoundsInParent())
	                    && !balle.getBoundsInParent().intersects(paddle2.getBoundsInParent())) {
	                ballCollidedWithPaddle = false;
	            }

				if (balle.getTranslateX() < -WIDTH + BALL_RADIUS) {
					scrj1.set(scrj1.getValue() + 1);
					resetPoint();
				} else if (balle.getTranslateX() > WIDTH - BALL_RADIUS / 2) {
					scrj2.set(scrj2.getValue() + 1);
					resetPoint();
				}
				
				// Gère le rebond verticale
				if (balle.getTranslateY() <= -HEIGHT + BALL_RADIUS || balle.getTranslateY() >= HEIGHT - BALL_RADIUS) {
					ballVelocityY *= -1;
				}
			}
		}));
		jeu.setCycleCount(Timeline.INDEFINITE);
		jeu.play();
	}

	private void resetPoint() {
		balle.setTranslateX(0);
		balle.setTranslateY(0);
		paddle1.setTranslateY(0);
		paddle2.setTranslateY(0);
	}

	@FXML
	public void bougerPaddle(KeyEvent event) {
		if (event.getCode() == KeyCode.Z) {
			paddle1Velocity = -PADDLE_SPEED;
		} else if (event.getCode() == KeyCode.S) {
			paddle1Velocity = PADDLE_SPEED;
		} else if (event.getCode() == KeyCode.P) {
			paddle2Velocity = -PADDLE_SPEED;
		} else if (event.getCode() == KeyCode.L) {
			paddle2Velocity = PADDLE_SPEED;
		}
	}

	@FXML
	public void arreterPaddle(KeyEvent event) {
		if (event.getCode() == KeyCode.Z || event.getCode() == KeyCode.S) {
			paddle1Velocity = 0;
		} else if (event.getCode() == KeyCode.P || event.getCode() == KeyCode.L) {
			paddle2Velocity = 0;
		}
	}

	/*
	 * Action menu retour. Retourne à la fenêtre précédente.
	 */
	@FXML
	private void doRetour() {
		this.primaryStage.close();
		jeu.stop();
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
