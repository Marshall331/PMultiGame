package application.view;

import java.text.DecimalFormat;

import application.tools.AlertUtilities;
import application.tools.ConfigurationSave;
import javafx.fxml.FXML;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Slider;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import model.gameConfiguration;

/**
 * Controller JavaFX de la vue des choix de la difficulté
 */
public class GameSpeedSettingsController {

    // Fenêtre physique ou est la scène contenant le fichier xml contrôlé par this
    private Stage primaryStage;

    public gameConfiguration conf;

    public boolean inGame;

    public double baseSpeedY;
    public double baseSpeedX;
    public double maxSpeed;
    public double acc;
    public double size;
    public String color;

    public boolean isSettingsChanged;

    /**
     * Initialisation du contrôleur de vue DailyBankMainFrameController.
     *
     * @param _containingStage Stage qui contient la fenêtre précédente.
     */
    public void initContext(Stage _containingStage, int _playerId) {
        this.primaryStage = _containingStage;

        this.conf = ConfigurationSave.chargerConfiguration();

        initGameSettings();
        itemsConfigure();
        setItemsByConf();
    }

    /**
     * Affichage de la fenêtre.
     */
    public void displayDialog() {
        this.primaryStage.showAndWait();
    }

    @FXML
    private Label labBaseSpeedY;

    @FXML
    private Slider sliderBaseSpeedY;

    @FXML
    private Label labBaseSpeedX;

    @FXML
    private Slider sliderBaseSpeedX;

    @FXML
    private Label labMaxSpeed;

    @FXML
    private Slider sliderMaxSpeed;

    @FXML
    private Label labAcc;

    @FXML
    private Slider sliderAcc;

    @FXML
    private Label labSize;

    @FXML
    private Slider sliderSize;

    @FXML
    private ColorPicker ballColor;

    private void initGameSettings() {
        this.baseSpeedY = this.conf.ballBaseSpeedY;
        this.baseSpeedX = this.conf.ballBaseSpeedX;
        this.maxSpeed = this.conf.ballMaxSpeed;
        this.acc = this.conf.ballAcc;
        this.size = this.conf.ballSize;
        this.color = this.conf.ballColor;
    }

    private void itemsConfigure() {

        sliderBaseSpeedY.valueProperty().addListener((observable, oldValue, newValue) -> {
            int size = newValue.intValue();
            this.labBaseSpeedY.setText(Integer.toString(size));
        });

        sliderBaseSpeedX.valueProperty().addListener((observable, oldValue, newValue) -> {
            int size = newValue.intValue();
            this.labBaseSpeedX.setText(Integer.toString(size));
        });

        sliderMaxSpeed.valueProperty().addListener((observable, oldValue, newValue) -> {
            int size = newValue.intValue();
            this.labMaxSpeed.setText(Integer.toString(size));
        });

        sliderAcc.valueProperty().addListener((observable, oldValue, newValue) -> {
            Double speed = newValue.doubleValue();
            DecimalFormat decimalFormat = new DecimalFormat("#.#"); // Format avec un seul chiffre après la virgule
            this.labAcc.setText(decimalFormat.format(speed));
        });

        sliderSize.valueProperty().addListener((observable, oldValue, newValue) -> {
            int size = newValue.intValue();
            this.labSize.setText(Integer.toString(size));
        });

        this.ballColor.setValue(Color.web(color));
    }

    private void setItemsByConf() {

        this.labBaseSpeedY.setText("" + (int) this.baseSpeedY);
        this.labBaseSpeedX.setText("" + (int) this.baseSpeedX);
        this.labMaxSpeed.setText("" + (int) this.maxSpeed);
        this.labAcc.setText("" + this.acc);
        this.labSize.setText("" + (int) this.size);

        this.sliderBaseSpeedY.setValue(this.baseSpeedY);
        this.sliderBaseSpeedX.setValue(this.baseSpeedX);
        this.sliderMaxSpeed.setValue(this.maxSpeed);
        this.sliderAcc.setValue(this.acc);
        this.sliderSize.setValue(this.size);

        this.ballColor.setValue(Color.web(color));
    }

    @FXML
    private void doValider() {

        // boolean allOK = true;
        this.baseSpeedY = this.sliderBaseSpeedY.getValue();
        this.baseSpeedX = this.sliderBaseSpeedX.getValue();
        this.maxSpeed = this.sliderMaxSpeed.getValue();
        this.acc = this.sliderAcc.getValue();
        this.size = this.sliderSize.getValue();
        this.color = this.ballColor.getValue().toString();

        this.conf.isConfHasChanged = true;

        this.checkSettingsChanged();
        // System.out.println("AVANT : " + this.conf.ballMaxSpeed);
        this.setNewSettings();
        ConfigurationSave.sauvegarderConfiguration(conf);
        this.conf = ConfigurationSave.chargerConfiguration();
        // System.out.println("APRES : " + this.conf.ballMaxSpeed);
        this.doRetour();
    }

    private void checkSettingsChanged() {
        // if (this.playerId == 1) {
        // if (this.isComputer != this.conf.isPlayer1Computer || this.mouseControl !=
        // this.conf.isPlayer1MouseControl
        // || this.maxSpeed != this.conf.player1MaxSpeed || this.paddleSize !=
        // this.conf.player1PaddleSize) {
        // this.conf.isConfHasChanged = true;
        // }
        // } else {
        // if (this.isComputer != this.conf.isPlayer2Computer || this.mouseControl !=
        // this.conf.isPlayer2MouseControl
        // || this.maxSpeed != this.conf.player2MaxSpeed || this.paddleSize !=
        // this.conf.player2PaddleSize) {
        // this.conf.isConfHasChanged = true;
        // }
        // }
    }

    /*
     * Action menu retour. Retourne à la fenêtre précédente.
     */
    @FXML
    private void doRetour() {
        this.primaryStage.close();
    }

    @FXML
    private void doReset() {
        if (AlertUtilities.confirmYesCancel(this.primaryStage, "Réinitialiser les paramètres ?",
                "Voulez vous vraiment réinitialiser les paramètres ?", null, AlertType.CONFIRMATION)) {
            this.resetPlayerSettings();
        }
    }

    private void setNewSettings() {
        this.conf.setNewGameSpeedSettings(baseSpeedY, baseSpeedX, maxSpeed, acc, size, color);
    }

    public void resetPlayerSettings() {
        this.baseSpeedY = 5;
        this.baseSpeedX = 5;
        this.maxSpeed = 30;
        this.acc = 1.1;
        this.size = 25;
        this.color = "#4000ff";
        this.setItemsByConf();
    }
}
