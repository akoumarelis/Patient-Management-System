/*
 *  Project for TEI OF CRETE lesson
 *  Plan Driven and Agile Programming
 *  TP4129 - TP4187 - TP4145
 */
package advance_java_team_clinic_project.Controller;

import advance_java_team_clinic_project.Model.AppointmentsModel;
import advance_java_team_clinic_project.Model.CustomComboModel;
import advance_java_team_clinic_project.classes.CustomComboClass;
import advance_java_team_clinic_project.classes.LoggedInUserClass;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author Chris
 */
public class AppointmentRecordInfoController implements Initializable {

    @FXML
    private DatePicker appDateInput;
    @FXML
    private TextField appCodeInput;
    @FXML
    private TextField createdInput;
    @FXML
    private TextArea commentsTextArea;

    private AppointmentsModel ak;
    private ResultSet rs;
    private Integer id;
    @FXML
    private Button backBtn;
    @FXML
    private Button diagnoseInfoBtn;
    @FXML
    private AnchorPane idRecordPane;

    LoggedInUserClass user = LoggedInUserClass.getInstance();
    private TextField doctorInput;
    @FXML
    private TextField patientInput;
    private Integer diagID = -1;

    private Statement stmt;
    private String sql;
    @FXML
    private ComboBox doctorComboBox;
    
    private CustomComboModel ed = new CustomComboModel();
    ObservableList<CustomComboClass> customCombo = FXCollections.observableArrayList();
    @FXML
    private TextField hourInput;
    @FXML
    private Button updateBtn;

    private Alert alert = new Alert(Alert.AlertType.INFORMATION);
    private Alert failAlert = new Alert(Alert.AlertType.ERROR);
    private Integer doctorID;
    
    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        if(user.getRoleID() == 3){doctorComboBox.setDisable(true);}
        if(user.getRoleID() == 1 || user.getRoleID() == 4){
            appDateInput.setEditable(true);
            updateBtn.setVisible(true);
        }else{
            updateBtn.setVisible(false);
        }
        
       
        
        
        appDateInput.setEditable(false);
        appCodeInput.setEditable(false);
        createdInput.setEditable(false);
        commentsTextArea.setEditable(false);
        patientInput.setEditable(false);

        backBtn.setOnMouseClicked((MouseEvent event) -> {
            try {
                FXMLLoader loader = new FXMLLoader(AppointmentRecordInfoController.this.getClass().getResource("../View/AppointmentRecordsView.fxml"));
                Parent root = (Parent) loader.load();
                idRecordPane.getChildren().clear();
                idRecordPane.getChildren().add(root);
            } catch (IOException ex) {
                Logger.getLogger(AppointmentRecordInfoController.class.getName()).log(Level.SEVERE, null, ex);
            }

        });
        
        
    }

    /**
     *
     * @param appID
     */
    public void setID(String appID) {

        try {
            ak = new AppointmentsModel();
            rs = ak.fetchBasicInfoData(Integer.parseInt(appID));
            ed.getObject();
            customCombo = ed.FetchUserFilterData(2);
            doctorComboBox.setItems(FXCollections.observableArrayList(customCombo));
            if (rs.next()) {
                doctorComboBox.setValue(rs.getString("doctor"));
                doctorID = rs.getInt("doctor_id");
                patientInput.setText(rs.getString("patient"));
                if(rs.getString("app_date") != null){
                   appDateInput.setValue(LOCAL_DATE(rs.getString("app_date"))); 
                }else{
                    appDateInput.setValue(null);
                }
                String app_hour = rs.getString("app_hour");
                if(app_hour == null){
                    
                }else{
                     hourInput.setText(app_hour);
                }
                appCodeInput.setText(rs.getString("app_code"));
                createdInput.setText(rs.getString("created"));
                commentsTextArea.setText(rs.getString("comments"));
                diagID = rs.getInt("diagnosis");
            }
        } catch (SQLException ex) {
            Logger.getLogger(AppointmentRecordInfoController.class.getName()).log(Level.SEVERE, null, ex);
        }

        if (diagID == -1) {
            if (user.getRoleID() == 3 || user.getRoleID() == 4) {
                diagnoseInfoBtn.setVisible(false);
            } else {
                if (user.getRoleID() != 3 && user.getRoleID() != 4 && user.getRoleID() != 5) {
                    diagnoseInfoBtn.setVisible(true);
                    diagnoseInfoBtn.setText("Create diagnose");
                }
            }
        } else {
               diagnoseInfoBtn.setVisible(true);
                 diagnoseInfoBtn.setText("Diagnose Info");           
        }
        
        doctorComboBox.valueProperty().addListener((obs, oldval, newval) -> {
            if (newval != null) {
                CustomComboClass coDoctor = (CustomComboClass) doctorComboBox.getSelectionModel().getSelectedItem();
                doctorID = coDoctor.getId();            
            }
        });
        
        updateBtn.setOnMouseClicked((MouseEvent event) -> {
            if (ak.updateAppointmentData(appID, appDateInput.getValue().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")), doctorID, hourInput.getText(), commentsTextArea.getText())) {
                alert.setTitle("Update");
                alert.setContentText("Update submitted");
                alert.showAndWait();
                try {
                    ak.fetchBasicInfoData(Integer.valueOf(appID));
                } catch (SQLException ex) {
                    Logger.getLogger(AppointmentRecordInfoController.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                alert.setTitle("Update");
                alert.setContentText("Update failed");
                alert.showAndWait();
            }
        });

        diagnoseInfoBtn.setOnMouseClicked((MouseEvent event) -> {
           
               
                    FXMLLoader loader = new FXMLLoader(AppointmentRecordInfoController.this.getClass().getResource("../View/DiagnosisInfoView.fxml"));
                    Parent root = null;
            try {
                root = (Parent) loader.load();
            } catch (IOException ex) {
                Logger.getLogger(AppointmentRecordInfoController.class.getName()).log(Level.SEVERE, null, ex);
            }
                    DiagnosisInfoController diagnosisID = loader.getController();
                    diagnosisID.setDiagnosisID(appID, diagID);
                    idRecordPane.getChildren().clear();
                    idRecordPane.getChildren().add(root);
       
        });

    }
    
    public static final LocalDate LOCAL_DATE(String dateString) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate localDate = LocalDate.parse(dateString, formatter);
        return localDate;
    }

    /**
     *
     * @return
     */
    public Integer getID() {
        return this.id;
    }

}
