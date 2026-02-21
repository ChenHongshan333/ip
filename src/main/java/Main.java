import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import mintty.Mintty;

/**
 * A GUI for Duke using FXML.
 */
public class Main extends Application {

    Path file = Paths.get("data", "mintty.txt");
    private Mintty mintty = new Mintty(file.toString());

    @Override
    public void start(Stage stage) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/view/MainWindow.fxml"));
            AnchorPane ap = fxmlLoader.load();
            Scene scene = new Scene(ap);
            stage.setScene(scene);
            fxmlLoader.<MainWindow>getController().setMintty(mintty); // inject the Mintty instance
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
