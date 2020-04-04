/*
 *  Project for TEI OF CRETE lesson
 *  Plan Driven and Agile Programming
 *  TP4129 - TP4187 - TP4145
 */
package advance_java_team_clinic_project.Controller;

import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 *
 * @author Chris
 */
public abstract class StageRedirect {

    /**
     * Set new Stage on a JavaFX Scene.
     *
     * @param path
     * @param currentStage
     * @throws IOException
     */
    void setNewStage(String path, Stage currentStage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource(path));
        Scene scene = new Scene(root);
        currentStage.setScene(scene);
    }

    /**
     * Load a pane on a specific JavaFX scene.
     *
     * @param path
     * @param pane
     */
    void loadUIonSamePane(String path, BorderPane pane) {
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource(path));
        } catch (IOException ex) {

        }
        pane.setCenter(root);
    }
      
}
