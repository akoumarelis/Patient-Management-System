/*
 *  Project for TEI OF CRETE lesson
 *  Plan Driven and Agile Programming
 *  TP4129 - TP4187 - TP4145
 */
package advance_java_team_clinic_project.Controller;

import advance_java_team_clinic_project.Model.TestsModel;
import advance_java_team_clinic_project.classes.CustomComboClass;
import advance_java_team_clinic_project.classes.LoggedInUserClass;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 * FXML Controller class
 *
 * @author Chris
 */
public class TestsInfoController implements Initializable {

    @FXML
    private AnchorPane testIDPane;
    @FXML
    private TextField costInput;
    @FXML
    private TextField resultsInput;
    @FXML
    private TextField isPaidInput;
    private TextField caseStatusInput;
    @FXML
    private TextField descriptionInput;
    @FXML
    private TextField createdByInput;
    @FXML
    private TextField updatedByInput;
    @FXML
    private TextField createdDate;
    @FXML
    private TextField updatedDate;
    @FXML
    private Button backBtn;

    TestsModel tests = new TestsModel();
    @FXML
    private ComboBox isCompleted;
    @FXML
    private Button updateBtn;
    @FXML
    private Button createBtn;
    @FXML
    private Button payBtn;
    @FXML
    private Text paidAmountLayout;
    @FXML
    private TextField paidAmountInput;
    @FXML
    private TextField doctorInput;
    @FXML
    private TextField patientInput;

    private LoggedInUserClass loggedInUser = LoggedInUserClass.getInstance();

    private Integer diagID = null;
    ObservableList<CustomComboClass> customCombo = FXCollections.observableArrayList();
    private Integer isPaidValue = 0;
    
    private Integer isCompletedID = null;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
     
       isCompleted.setEditable(false);
       customCombo.addAll(new CustomComboClass(1,"Yes"), new CustomComboClass(2,"No"));
       isCompleted.setItems(FXCollections.observableArrayList(customCombo));
       isCompleted.valueProperty().addListener((obs, oldval, newval) -> {
            if (newval != null) {
                CustomComboClass isCompletedCombo = (CustomComboClass) isCompleted.getSelectionModel().getSelectedItem();
                isCompletedID = isCompletedCombo.getId();
                System.out.println(isCompletedCombo.getId());
            }else{
                
            }
        });
    }

    public void setTestIDView(boolean fromDiag, Integer testID, Integer diagId) {
        System.out.println(testID);
        
       
        
        
        
        diagID = diagId;
        if (testID != -1) {
            ResultSet rs = tests.getTestByID(testID);

            try {
                if (rs.next()) {
                    diagID = rs.getInt("diag_id");
                    descriptionInput.setText(rs.getString("description"));
                    costInput.setText(rs.getString("cost"));
                    resultsInput.setText(rs.getString("results"));
                    isPaidInput.setText(rs.getString("Paid"));
                    paidAmountInput.setText(rs.getString("paid_amount"));
                    String isCompletedInput = rs.getString("is_completed");
                    if(isCompletedInput.equals("Yes")){
                        System.out.println("YES");
                        isCompleted.setValue(isCompleted.getItems().get(0));
                    }else{
                        isCompleted.setValue(isCompleted.getItems().get(1));
                    }
                    createdByInput.setText(rs.getString("createdby"));
                    updatedByInput.setText(rs.getString("updated_by"));
                    createdDate.setText(rs.getString("created"));
                    updatedDate.setText(rs.getString("updated"));
                    doctorInput.setText(rs.getString("doctor"));
                    patientInput.setText(rs.getString("patient"));
                    isPaidValue = rs.getInt("isPaidValue");
                }
            } catch (SQLException ex) {
                Logger.getLogger(TestsInfoController.class.getName()).log(Level.SEVERE, null, ex);
            }

            if (loggedInUser.getRoleID() == 3) {
                descriptionInput.setEditable(false);
                resultsInput.setEditable(false);
                costInput.setEditable(false);
                isCompleted.setEditable(false);
                updateBtn.setVisible(false);
                createBtn.setVisible(false);
                if (isPaidValue == 1) {
                    payBtn.setVisible(false);
                } else {
                    payBtn.setVisible(true);
                }
            } else if (loggedInUser.getRoleID() != 3) {
                updateBtn.setVisible(true);
                createBtn.setVisible(false);
                payBtn.setVisible(false);
            }
        } else if (testID == -1) {
            updateBtn.setVisible(false);
            payBtn.setVisible(false);
            createBtn.setVisible(true);
        }

        createBtn.setOnMouseClicked((MouseEvent event) -> {
            Integer tmpID = 0;
            tmpID = tests.createTest(diagId, descriptionInput.getText());
            if (tmpID == 0) {
                //alert that insertion failed
            } else if (tmpID != 0) {
                setTestIDView(fromDiag,tmpID,diagId);
            }
        });
        
       
        payBtn.setOnMouseClicked((MouseEvent event) -> {
            FXMLLoader loader = new FXMLLoader(TestsInfoController.this.getClass().getResource("../View/PaymentWindowView.fxml"));
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
            pay.setPayment(1, testID);
            paymentStage.setOnCloseRequest((WindowEvent eventl) -> {
                setTestIDView(fromDiag,testID,diagID);
            });
        });
        
        updateBtn.setOnMouseClicked((MouseEvent event) -> {
            tests.updateTest(testID, descriptionInput.getText(), isCompletedID, Integer.valueOf(costInput.getText()),resultsInput.getText());
            setTestIDView(fromDiag,testID,diagId);
        });

        backBtn.setOnMouseClicked((MouseEvent event) -> {
            try {
                FXMLLoader loader = new FXMLLoader(TestsInfoController.this.getClass().getResource("../View/TestsTableView.fxml"));
                Parent root = (Parent) loader.load();
                TestsTableController testID1 = loader.getController();
                if (fromDiag) {
                    testID1.setTestID(diagID);
                } else {
                    testID1.setTestID(-1);
                }
                testIDPane.getChildren().clear();
                testIDPane.getChildren().add(root);
            } catch (IOException ex) {
                Logger.getLogger(TestsTableController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }

}
