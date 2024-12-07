package lingoquestpackage.lingoquest;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;

    /**
     * The entry point for the JavaFX application.
     * This method is called when the JavaFX application is launched.
     *
     * @param stage The primary stage (window) for the JavaFX application.
     * @throws Exception if the FXML file fails to load.
     */
    @Override
    public void start(Stage stage) throws Exception {
        //LanguageGame.getInstance().login("TTomacka", "Password1234");
        scene = new Scene(loadFXML("/lingoquestpackage/login"), 640, 480);
        stage.setScene(scene);
        stage.setTitle("LingoQuest");
        stage.show();
    }

    /**
     * Sets the root node of the scene to the specified FXML file.
     * This allows switching between different screens in the application.
     *
     * @param fxml The path to the FXML file to load as the new root.
     * @throws IOException if the FXML file cannot be loaded.
     */
    public static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }
    
    /**
     * Helper method to load an FXML file and return the root node.
     *
     * @param fxml The path to the FXML file to load (without the .fxml extension).
     * @return The root node loaded from the FXML file.
     * @throws IOException if the FXML file cannot be loaded.
     */
    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }
    
    /**
     * The main entry point for the application.
     * This method launches the JavaFX application.
     *
     * @param args Command-line arguments passed to the application.
     */
    public static void main(String[] args) {
        launch();
    }

}