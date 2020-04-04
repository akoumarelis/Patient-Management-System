/*
 *  Project for TEI OF CRETE lesson
 *  Plan Driven and Agile Programming
 *  TP4129 - TP4187 - TP4145
 */
package advance_java_team_clinic_project.Main;

import java.sql.SQLException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author Beast
 */
public class Main extends Application {

    public static void main(String[] args) throws SQLException {
        //launch javafx 
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        //JavaFx Run
        Parent root = FXMLLoader.load(getClass().getResource("../View/LoginWindowView.fxml"));

        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setWidth(1200);
        primaryStage.setHeight(680);
        primaryStage.setResizable(true);
        primaryStage.show();
    }

}
