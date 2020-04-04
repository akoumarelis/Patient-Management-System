/*
 *  Project for TEI OF CRETE lesson
 *  Plan Driven and Agile Programming
 *  TP4129 - TP4187 - TP4145
 */
package advance_java_team_clinic_project.Controller;

import advance_java_team_clinic_project.Model.AppointmentsModel;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author Tasos
 */
public class NewAppointmentController implements Initializable {

    @FXML
    private Button submitBtn;
    @FXML
    private TextArea reasonText;
    private Alert alert = new Alert(Alert.AlertType.INFORMATION);
    private AppointmentsModel ap = new AppointmentsModel();
    @FXML
    private AnchorPane appointmentPane;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        submitBtn.setOnMouseClicked((MouseEvent event) -> {
            ap.makeAppointment(reasonText.getText());
            reasonText.clear();
            alert.setTitle("Success");
            alert.setContentText("Appointment successfully submitted");
            alert.showAndWait();
        });
    }

}
