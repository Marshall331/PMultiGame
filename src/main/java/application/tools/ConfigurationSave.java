package application.tools;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import model.gameConfiguration;

public class ConfigurationSave {

    public static void sauvegarderConfiguration(gameConfiguration config) {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("config.ser"))) {
            out.writeObject(config);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static gameConfiguration chargerConfiguration() {
        gameConfiguration config = null;
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream("config.ser"))) {
            config = (gameConfiguration) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return config;
    }
}
