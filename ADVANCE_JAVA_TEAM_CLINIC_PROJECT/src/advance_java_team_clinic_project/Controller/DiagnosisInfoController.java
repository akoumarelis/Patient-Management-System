/*
 *  Project for TEI OF CRETE lesson
 *  Plan Driven and Agile Programming
 *  TP4129 - TP4187 - TP4145
 */
package advance_java_team_clinic_project.Controller;

import advance_java_team_clinic_project.Model.DiagnosisInfoModel;
import advance_java_team_clinic_project.classes.LoggedInUserClass;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author Chris
 */
public class DiagnosisInfoController implements Initializable {

    @FXML
    private TextField createdByInput;
    @FXML
    private TextField updatedByInput;
    @FXML
    private Button backBtn;
    @FXML
    private Button admissionInfoBtn;
    @FXML
    private TextArea medicineText;
    @FXML
    private Button testsBtn;
    @FXML
    private TextArea commentsText;
    @FXML
    private TextField doctorInput;
    @FXML
    private TextField patientInput;
    @FXML
    private Button createDiagnose;
    @FXML
    private Button updateDiagnose;
    @FXML
    private TextField createdInput;
    @FXML
    private TextField updatedInput;
    @FXML
    private TextField patientTypeInput;

    private Integer appInfoId, admissionID;
    LoggedInUserClass user = LoggedInUserClass.getInstance();
    @FXML
    private AnchorPane diagnosisPanel;

    private DiagnosisInfoModel diagInfoModel = new DiagnosisInfoModel();

    private ResultSet rs;
    @FXML
    private Button testCreate;
    @FXML
    private Button create_addmission;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        createdByInput.setEditable(false);
        updatedByInput.setEditable(false);
        doctorInput.setEditable(false);
        patientInput.setEditable(false);
        createdInput.setEditable(false);
        updatedInput.setEditable(false);
        patientTypeInput.setEditable(false);
        if (user.getRoleID() == 3) {
            medicineText.setEditable(false);
            commentsText.setEditable(false);
            createDiagnose.setVisible(false);
            updateDiagnose.setVisible(false);
        }
    }

    /**
     *
     * @param app_id
     * @param diagID
     */
    public void setDiagnosisID(String app_id, Integer diagID) {
        appInfoId = Integer.valueOf(app_id);
        admissionID = -1;
//        diagInfoModel.getObject();
        if(app_id.equals("-1")){
            rs = diagInfoModel.fetchDiagnoseInfoData(diagID);
            try {
                while(rs.next()){
                    appInfoId = Integer.valueOf(rs.getString("APP_INFO_ID"));
                }
            } catch (SQLException ex) {
                Logger.getLogger(DiagnosisInfoController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        if (diagID == -1) {
            testsBtn.setVisible(false);
            updateDiagnose.setVisible(false);
            admissionInfoBtn.setVisible(false);
            testCreate.setVisible(false);
            create_addmission.setVisible(false);
            createDiagnose.setVisible(false);
            if (user.getRoleID() != 3) {
                createDiagnose.setVisible(true);
            }
        } else {
            rs = diagInfoModel.fetchDiagnoseInfoData(diagID);
            try {
                while (rs.next()) {
                    commentsText.setText(rs.getString("comments"));
                    medicineText.setText(rs.getString("meds"));
                    patientTypeInput.setText(rs.getString("patient_type"));
                    createdInput.setText(rs.getString("created"));
                    createdByInput.setText(rs.getString("created_by"));
                    updatedInput.setText(rs.getString("updated"));
                    updatedByInput.setText(rs.getString("updated_by"));
                    admissionID = rs.getInt("admission_id");
                    doctorInput.setText(rs.getString("doctor"));
                    patientInput.setText(rs.getString("patient"));
                }
            } catch (SQLException ex) {
                Logger.getLogger(DiagnosisInfoController.class.getName()).log(Level.SEVERE, null, ex);
            }
            if (user.getRoleID() != 3) {
                if (admissionID == -1) {
                    create_addmission.setVisible(true);
                    admissionInfoBtn.setVisible(false);
                } else if (admissionID > 0) {
                    admissionInfoBtn.setVisible(true);
                    create_addmission.setVisible(false);
                }
                updateDiagnose.setVisible(true);
                testsBtn.setVisible(true);
                testCreate.setVisible(true);
            } else if (user.getRoleID() == 3) {
                if (admissionID == -1) {
                    admissionInfoBtn.setVisible(false);
                    create_addmission.setVisible(false);
                } else if (admissionID > 0) {
                    admissionInfoBtn.setVisible(true);
                    create_addmission.setVisible(false);
                    admissionInfoBtn.setText("Admission info");
                }
                testCreate.setVisible(false);
            }
            createDiagnose.setVisible(false);
        }
        backBtn.setOnMouseClicked((MouseEvent event) -> {
            try {
                FXMLLoader loader = new FXMLLoader(DiagnosisInfoController.this.getClass().getResource("../View/AppointmentRecordInfoView.fxml"));
                Parent root = (Parent) loader.load();
                AppointmentRecordInfoController idRecord = loader.getController();
                idRecord.setID(appInfoId.toString());
                diagnosisPanel.getChildren().clear();
                diagnosisPanel.getChildren().add(root);
            } catch (IOException ex) {
                Logger.getLogger(AppointmentRecordInfoController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        updateDiagnose.setOnMouseClicked((MouseEvent event) -> {
            diagInfoModel.updateDiagnoseDetails(diagID, commentsText.getText(), medicineText.getText());
        });
        create_addmission.setOnMouseClicked((MouseEvent event) -> {
            admissionRedirect(diagID);
        });
        admissionInfoBtn.setOnMouseClicked((MouseEvent event) -> {
            admissionRedirect(diagID);
        });
        createDiagnose.setOnMouseClicked((MouseEvent event) -> {
            diagInfoModel.createDiagnoseDetails(appInfoId, commentsText.getText(), medicineText.getText());
            Integer diag_ID = null;
            try {
                diag_ID = fetchDiagID(app_id);
            } catch (SQLException ex) {
                Logger.getLogger(DiagnosisInfoController.class.getName()).log(Level.SEVERE, null, ex);
            }
            setDiagnosisID(appInfoId.toString(),diag_ID);
            
            
        });

        /* TESTS CONTEXT */
        testCreate.setOnMouseClicked((MouseEvent event) -> {
            FXMLLoader loader = new FXMLLoader(DiagnosisInfoController.this.getClass().getResource("../View/TestsInfoView.fxml"));
            Parent root = null;
            try {
                root = (Parent) loader.load();
            } catch (IOException ex) {
                Logger.getLogger(DiagnosisInfoController.class.getName()).log(Level.SEVERE, null, ex);
            }
            TestsInfoController testID = loader.getController();
            testID.setTestIDView(true, -1,diagID);
            //Scene
            diagnosisPanel.getChildren().clear();
            diagnosisPanel.getChildren().add(root);
        });
        testsBtn.setOnMouseClicked((MouseEvent event) -> {
            try {
                FXMLLoader loader = new FXMLLoader(DiagnosisInfoController.this.getClass().getResource("../View/TestsTableView.fxml"));
                Parent root = (Parent) loader.load();
                TestsTableController testID = loader.getController();
                testID.setTestID(diagID);
                diagnosisPanel.getChildren().clear();
                diagnosisPanel.getChildren().add(root);
            } catch (IOException ex) {
                Logger.getLogger(AppointmentRecordInfoController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        /* END -- TESTS CONTEXT */
    }
    
    private Integer fetchDiagID(String app_id) throws SQLException{
        ResultSet rs = diagInfoModel.getDiagId(app_id);
        Integer dID = null;
        while(rs.next()){
                dID =  rs.getInt("id");
        }
        return dID;
    }
    
    private void admissionRedirect(Integer diagID) {
        try {
            FXMLLoader loader = new FXMLLoader(DiagnosisInfoController.this.getClass().getResource("../View/AdmissionInfoView.fxml"));
            Parent root = (Parent) loader.load();
            AdmissionInfoController admissionInfoID = loader.getController();
            admissionInfoID.setAdmissionID(diagID, admissionID);
            diagnosisPanel.getChildren().clear();
            diagnosisPanel.getChildren().add(root);
        } catch (IOException ex) {
            Logger.getLogger(AppointmentRecordInfoController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
