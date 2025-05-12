package ok.ubahn.UI;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import ok.ubahn.Controller.RouteController;
import ok.ubahn.Model.Graph;
import ok.ubahn.Util.CSVLoader;

/**
 * JavaFX App
 */

public class MainApp extends Application {

    private static Scene scene;


    @Override
    public void start(Stage stage) {
        try {


            scene = new Scene(loadFXML("Main"), 750, 750);
            stage.setScene(scene);
            stage.setTitle("Vienna U-Bahn Route Finder");
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();  // Print the stack trace to diagnose initialization errors
        }
    }

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApp.class.getResource("/ok/ubahn/" + fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch(args);
    }

}