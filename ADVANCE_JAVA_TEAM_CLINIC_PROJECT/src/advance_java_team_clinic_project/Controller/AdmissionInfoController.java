/*
 *  Project for TEI OF CRETE lesson
 *  Plan Driven and Agile Programming
 *  TP4129 - TP4187 - TP4145
 */
package advance_java_team_clinic_project.Controller;

import advance_java_team_clinic_project.Model.AdmissionModel;
import advance_java_team_clinic_project.Model.DiagnosisInfoModel;
import advance_java_team_clinic_project.classes.LoggedInUserClass;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;

/**
 * FXML Controller class
 *
 * @author Chris
 */
public class AdmissionInfoController implements Initializable {

    @FXML
    private DatePicker admissionDateInput;
    @FXML
    private DatePicker dischargeDateInput;
    @FXML
    private TextField roomInput;
    @FXML
    private TextField bedInput;
    @FXML
    private TextField costPerDayinput;
    @FXML
    private TextField totalCostInput;
    @FXML
    private TextField createdByInput;
    @FXML
    private TextField updatedByInput;
    @FXML
    private TextField createdDateInput;
    @FXML
    private TextField updatedDateInput;
    @FXML
    private AnchorPane admissionInfoPane;
    @FXML
    private TextField doctorsName;
    @FXML
    private TextField patientsName;
    @FXML
    private Button backBtn;
    @FXML
    private Button createBtn;
    @FXML
    private Button updateBtn;
    @FXML
    private Text isPaidLayout;
    @FXML
    private TextField isPaidValue;
    @FXML
    private Text disDateLayout;
    @FXML
    private Button payBtn;
    @FXML
    private Text paidAmountLayout;
    @FXML
    private TextField paidAmountValue;

    private String app_id = null;
    private Alert alert = new Alert(Alert.AlertType.INFORMATION);
    private AdmissionModel admissionModel = new AdmissionModel();
    private DiagnosisInfoModel diagnosisInfoModel = new DiagnosisInfoModel();
    private ResultSet rs;
    private Integer isPaid;
    private LoggedInUserClass loggedInUser = LoggedInUserClass.getInstance();

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        alert.setHeaderText(null);
        alert.initStyle(StageStyle.UTILITY);
        payBtn.setVisible(false);
    }

    /**
     *
     * @param diagID
     * @param ID
     */
    public void setAdmissionID(Integer diagID, Integer ID) {
        //checks if the user is not a patient
        if (loggedInUser.getRoleID() != 3) {
            //if it doesn't exist, show createBtn - if not show updateBtn
            if (ID == -1) {
                createBtn.setVisible(true);
                updateBtn.setVisible(false);
                dischargeDateInput.setVisible(false);
                disDateLayout.setVisible(false);
                isPaidValue.setVisible(false);
                isPaidLayout.setVisible(false);
                paidAmountLayout.setVisible(false);
                payBtn.setVisible(false);
                paidAmountValue.setVisible(false);
            } else {
                updateBtn.setVisible(true);
                createBtn.setVisible(false);
            }
        } else if (loggedInUser.getRoleID() == 3) {
            createBtn.setVisible(false);
            updateBtn.setVisible(false);
            setDisDate(admissionDateInput);
            setDisDate(dischargeDateInput);
            roomInput.setEditable(false);
            bedInput.setEditable(false);
            costPerDayinput.setEditable(false);
        }

        //Fetch Appointment ID for page redirection
        fetchAppID(diagID);
        //Fetch admission data from database
        fetchAdmissionData(ID);

        backBtn.setOnMouseClicked((MouseEvent event) -> {
            try {
                FXMLLoader loader = new FXMLLoader(AdmissionInfoController.this.getClass().getResource("../View/DiagnosisInfoView.fxml"));
                Parent root = (Parent) loader.load();
                DiagnosisInfoController diagnosisID = loader.getController();
                diagnosisID.setDiagnosisID(app_id, diagID);
                admissionInfoPane.getChildren().clear();
                admissionInfoPane.getChildren().add(root);
            } catch (IOException ex) {
                Logger.getLogger(AppointmentRecordInfoController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        createBtn.setOnMouseClicked((MouseEvent event) -> { 
            Integer cost = null;
            if(costPerDayinput.getText().equals("")){
                cost = null;
            }else{
                Integer.valueOf(costPerDayinput.getText());
            }
            if (admissionModel.createAdmissionData(diagID, cost, roomInput.getText(), bedInput.getText(), admissionDateInput.getValue().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")))) {
                alert.setTitle("Create");
                alert.setContentText("Creation submitted");
                alert.showAndWait();
                fetchAdmissionData(ID);
            } else {
                alert.setTitle("Create");
                alert.setContentText("Creation failed");
                alert.showAndWait();
            }
        });

        updateBtn.setOnMouseClicked((MouseEvent event) -> {
            if (admissionModel.updateAdmissionData(ID, Integer.valueOf(costPerDayinput.getText()), roomInput.getText(), bedInput.getText(), admissionDateInput.getValue().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")), dischargeDateInput.getValue().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")))) {
                alert.setTitle("Update");
                alert.setContentText("Update submitted");
                alert.showAndWait();
                fetchAdmissionData(ID);
            } else {
                alert.setTitle("Update");
                alert.setContentText("Update failed");
                alert.showAndWait();
            }
        });

        payBtn.setOnMouseClicked((MouseEvent event) -> {
            FXMLLoader loader = new FXMLLoader(AdmissionInfoController.this.getClass().getResource("../View/PaymentWindowView.fxml"));
            Parent root = null;
            try {
                root = (Parent) loader.load();
            } catch (IOException ex) {
                Logger.getLogger(AdmissionInfoController.class.getName()).log(Level.SEVERE, null, ex);
            }
            Stage paymentStage = new Stage();
            Scene scene = new Scene(root);
            paymentStage.setTitle("Enter amount ");
            paymentStage.setScene(scene);
            paymentStage.setResizable(false);
            paymentStage.show();
            PaymentWindowController pay = loader.getController();
            pay.setPayment(2, ID);
            paymentStage.setOnCloseRequest((WindowEvent eventl) -> {
                fetchAdmissionData(ID);
            });
        });
    }

    /**
     *
     * @param dateString
     * @return
     */
    public static final LocalDate LOCAL_DATE(String dateString) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        LocalDate dateTime = LocalDate.parse(dateString, formatter);
        return dateTime;
    }

    private void setDisDate(DatePicker lDate) {
        lDate.setEditable(false);
        lDate.setOnMouseClicked(e -> {
            if (!lDate.isEditable()) {
                lDate.hide();
            }
        });
    }

    private void fetchAppID(Integer ID) {
        rs = diagnosisInfoModel.fetchDiagnoseInfoData(ID);
        try {
            if (rs.next()) {
                app_id = rs.getString("APP_INFO_ID");
            }
        } catch (SQLException ex) {
            Logger.getLogger(AdmissionInfoController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void fetchAdmissionData(Integer ID) {
        try {
            ResultSet rs = admissionModel.fetchAdmissionData(ID);
            if (rs.next()) {
                roomInput.setText(rs.getString("room"));
                bedInput.setText(rs.getString("bed"));
                costPerDayinput.setText(rs.getString("cost_per_day"));
                createdByInput.setText(rs.getString("created_by"));
                updatedByInput.setText(rs.getString("updated_by"));
                doctorsName.setText(rs.getString("doctor"));
                patientsName.setText(rs.getString("patient"));
                totalCostInput.setText(rs.getString("total_cost"));
                isPaidValue.setText(rs.getString("is_paid"));
                paidAmountValue.setText(rs.getString("paid_amount"));
                isPaid = rs.getInt("is_paid_value");
                admissionDateInput.setValue(LOCAL_DATE(rs.getString("admission_date")));
                if (!rs.getString("discharge_date").equals("-1")) {
                    dischargeDateInput.setValue(LOCAL_DATE(rs.getString("discharge_date")));
                    isPaidValue.setVisible(true);
                    isPaidLayout.setVisible(true);
                    paidAmountLayout.setVisible(true);
                    paidAmountValue.setVisible(true);
                    if (loggedInUser.getRoleID() == 3 && isPaid != 1) {
                        payBtn.setVisible(true);
                    } else {
                        payBtn.setVisible(false);
                    }
                } else {
                    isPaidValue.setVisible(false);
                    isPaidLayout.setVisible(false);
                    paidAmountLayout.setVisible(false);
                    payBtn.setVisible(false);
                    paidAmountValue.setVisible(false);
                }
                createdDateInput.setText(rs.getString("created"));
                updatedDateInput.setText(rs.getString("updated"));
            }
            dischargeDateInput.setVisible(true);
            disDateLayout.setVisible(true);
        } catch (SQLException ex) {
            Logger.getLogger(AdmissionInfoController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
