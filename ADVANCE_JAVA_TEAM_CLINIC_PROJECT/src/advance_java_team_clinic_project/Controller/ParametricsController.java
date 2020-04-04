/*
 *  Project for TEI OF CRETE lesson
 *  Plan Driven and Agile Programming
 *  TP4129 - TP4187 - TP4145
 */
package advance_java_team_clinic_project.Controller;

import advance_java_team_clinic_project.classes.LoggedInUserClass;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

/**
 *
 * @author Tasos
 */
public class ParametricsController extends StageRedirect implements Initializable {

    private ResultSet rs;
    LoggedInUserClass user = LoggedInUserClass.getInstance();
    @FXML
    private Button pm_roles;
    @FXML
    private Button pm_insurance_companies;
    @FXML
    private Button pm_genders;
    @FXML
    private Button pm_nationalities;
    @FXML
    private Button pm_eco_status;
    @FXML
    private Button pm_members;
    @FXML
    private AnchorPane AdminParametricsPanel;
    @FXML
    private Button pm_questions;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        pm_roles.setOnMouseClicked((MouseEvent event) -> {
            goToNextPage("pm_roles", "ROLES");

        });
        pm_insurance_companies.setOnMouseClicked((MouseEvent event) -> {
            goToNextPage("pm_insurance_companies", "INSURANCES");
        });
        pm_genders.setOnMouseClicked((MouseEvent event) -> {
            goToNextPage("pm_genders", "GENDERS");
        });
        pm_nationalities.setOnMouseClicked((MouseEvent event) -> {
            goToNextPage("pm_nationalities", "NATIONALITIES");
        });
        pm_eco_status.setOnMouseClicked((MouseEvent event) -> {
            goToNextPage("pm_eco_status", "ECO STATUS");
        });
        pm_members.setOnMouseClicked((MouseEvent event) -> {
            goToNextPage("pm_members", "MEMBERS");
        });

        pm_questions.setOnMouseClicked((MouseEvent event) -> {
            goToNextPage("pm_questions", "QUESTIONS");
        });

    }

    //lName = pm_roles -> ROLES
    private void goToNextPage(String tableName, String lName) {
        try {
            FXMLLoader loader = new FXMLLoader(ParametricsController.this.getClass().getResource("../View/ParametricsTableView.fxml"));
            Parent root = (Parent) loader.load();
            ParametricsTableController newWindow = loader.getController();
            newWindow.setWindow(tableName, lName);
            AdminParametricsPanel.getChildren().clear();
            AdminParametricsPanel.getChildren().add(root);
        } catch (IOException ex) {
            Logger.getLogger(ParametricsController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
