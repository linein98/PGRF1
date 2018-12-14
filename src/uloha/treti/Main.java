package uloha.treti;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Inicializace GUI
 * https://www.youtube.com/watch?v=Mk3qkQROb_k
 * https://github.com/linein98/PGRF1
 *
 * @author Tomas Brabec
 */
public class Main extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("view/window.fxml"));
        stage.setTitle("PRGF1 - Úloha třetí");
        Scene scene = new Scene(root, 1024, 888);
        stage.setScene(scene);
        stage.setMinWidth(1024);
        stage.setMinHeight(888);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
