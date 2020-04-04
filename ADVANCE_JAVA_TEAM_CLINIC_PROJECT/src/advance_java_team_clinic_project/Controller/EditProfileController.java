/*
 *  Project for TEI OF CRETE lesson
 *  Plan Driven and Agile Programming
 *  TP4129 - TP4187 - TP4145
 */
package advance_java_team_clinic_project.Controller;

import advance_java_team_clinic_project.classes.CustomComboClass;
import advance_java_team_clinic_project.Model.EditProfileModel;
import advance_java_team_clinic_project.Model.CustomComboModel;
import advance_java_team_clinic_project.classes.LoggedInUserClass;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
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
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.RowConstraints;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 * FXML Controller class
 *
 * @author Chris
 */
public class EditProfileController extends StageRedirect implements Initializable {

    @FXML
    private AnchorPane editProfilePane;
    private static EditProfileModel ak = new EditProfileModel();
    private static final CustomComboModel ed = new CustomComboModel();
    private ResultSet rs;
    LoggedInUserClass user = LoggedInUserClass.getInstance();

    @FXML
    private Button usernamebtn;
    @FXML
    private TextField name;
    @FXML
    private TextField surname;
    @FXML
    private Button insurancebtn;
    @FXML
    private TextField code;
    @FXML
    private TextField amka;
    @FXML
    private TextField ama;
    @FXML
    private TextField fathersName;
    @FXML
    private TextField mothersName;
    @FXML
    private DatePicker dateOfBirth;
    @FXML
    private TextField placeOfBirth;
    @FXML
    private TextField profession;
    @FXML
    private Button passwordbtn;
    @FXML
    private Button addressbtn;
    @FXML
    private Button contactbtn;
    @FXML
    private ComboBox comboRole;
    ObservableList<CustomComboClass> customCombo = FXCollections.observableArrayList();
    private Integer genderId, ecoStatusId, nationalityId, roleId;
    @FXML
    private ComboBox comboGender;
    @FXML
    private ComboBox comboEcoStatus;
    @FXML
    private ComboBox comboNationality;
    @FXML
    private Button cancelBtn;
    @FXML
    private Button submitBtn;
    
    private Integer address_ID = null;
    private Integer contact_ID = null;
    private Integer insurance_ID = null;
    @FXML
    private HBox patientsBtns;
    @FXML
    private GridPane gridPane;


    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }
    
    public void myInit(Integer userID){
        try {
            setData(userID);
        } catch (SQLException ex) {
            Logger.getLogger(EditProfileController.class.getName()).log(Level.SEVERE, null, ex);
        }
        if(userID!=user.getId()){
            insurancebtn.setDisable(true);
            contactbtn.setDisable(true);
            passwordbtn.setDisable(true);
            usernamebtn.setDisable(true);
            addressbtn.setDisable(true);
        }else{
            insurancebtn.setDisable(false);
            contactbtn.setDisable(false);
            passwordbtn.setDisable(false);
            usernamebtn.setDisable(false);
            addressbtn.setDisable(false);
        }
        
        if(user.getRoleID() != 3 && user.getId() == userID){
            gridPane.getRowConstraints().get(4).setMaxHeight(0); //renove one grid Row
            patientsBtns.setVisible(false); // Hide the Address-Insurance-Contact
        }
               
        if(user.getRoleID() == 1 && userID != user.getId()){
            contactbtn.setDisable(false);
            addressbtn.setDisable(false);
            insurancebtn.setDisable(false);
            comboRole.setDisable(false);
        }else{
            comboRole.setDisable(true);
        }
        
        usernamebtn.setOnMouseClicked((MouseEvent event) -> {
            try {
                FXMLLoader loader = new FXMLLoader(EditProfileController.this.getClass().getResource("../View/UsernameWindowView.fxml"));
                Parent root = null;
                root = (Parent)loader.load();
                Stage checkUsername = new Stage();
                Scene scene = new Scene(root);
                checkUsername.setTitle("Enter New Username");
                checkUsername.setScene(scene);
                checkUsername.setResizable(false);
                UsernameWindowController usernameController = loader.getController();
                usernameController.myInit(userID);
                checkUsername.setOnCloseRequest((WindowEvent event1) -> {myInit(userID);});
                checkUsername.show();
            } catch (IOException ex) {
                Logger.getLogger(EditProfileController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        passwordbtn.setOnMouseClicked((MouseEvent event) -> {
            try {
                FXMLLoader loader = new FXMLLoader(EditProfileController.this.getClass().getResource("../View/PasswordWindowView.fxml"));
                Parent root = null;
                root = (Parent)loader.load();
                Stage checkPassword = new Stage();
                Scene scene = new Scene(root);
                checkPassword.setTitle("Enter New Password");
                checkPassword.setScene(scene);
                checkPassword.setResizable(false);
                PasswordWindowController passwordController = loader.getController();
                passwordController.myInit(userID);
                checkPassword.show();
            } catch (IOException ex) {
                Logger.getLogger(EditProfileController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        cancelBtn.setOnMouseClicked((MouseEvent event) -> {
            
            Stage currentStage = (Stage) editProfilePane.getScene().getWindow();
            try {
                setNewStage("../View/UserMenuView.fxml", currentStage);
            } catch (IOException ex) {
                Logger.getLogger(EditProfileController.class.getName()).log(Level.SEVERE, null, ex);
            }
              
        });

        submitBtn.setOnMouseClicked((MouseEvent event) -> {
            try {
                ak = new EditProfileModel();
                ak.getObject();
                ak.updateBasicInfoData(userID, roleId, surname.getText(), name.getText(), amka.getText(), ama.getText(), dateOfBirth.getValue().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")), fathersName.getText(), mothersName.getText(), genderId, ecoStatusId, nationalityId, profession.getText(), placeOfBirth.getText()/*, memberId*/);
                LoggedInUserClass user1 = LoggedInUserClass.getInstance();
                rs = ak.fetchBasicInfoData(user1.getId());
                if (rs.next()) {
                    code.setText(rs.getString("global_code"));
                }
            } catch (SQLException ex) {
                Logger.getLogger(EditProfileController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }

    /**
     * Returns the user's data from the database and shows it on the screen.
     *
     * @throws SQLException
     */
    private void setData(Integer userID) throws SQLException {
        ak.getObject();
        rs = ak.fetchBasicInfoData(userID);
        if (rs.next()) {
            address_ID = rs.getInt("address_id");
            contact_ID = rs.getInt("contact_id");
            usernamebtn.setText(rs.getString("username"));
            name.setText(rs.getString("firstname"));
            surname.setText(rs.getString("surname"));
            code.setText(rs.getString("global_code"));
            amka.setText(rs.getString("amka"));
            ama.setText(rs.getString("ama"));
            fathersName.setText(rs.getString("fathers_name"));
            mothersName.setText(rs.getString("mothers_name"));
            dateOfBirth.setValue(LOCAL_DATE(rs.getString("date_of_birth")));
            dateOfBirth.setPromptText("dd-MM-yyyy");
            profession.setText(rs.getString("profession"));
            genderId = rs.getInt("gender_id");
            ecoStatusId = rs.getInt("eco_status_id");
            nationalityId = rs.getInt("nationality_id");
            roleId = rs.getInt("role_id");
            placeOfBirth.setText(rs.getString("place_of_birth"));
            setComboValues();
            setComboEventListeners();
        }
        
        addressbtn.setOnMouseClicked((MouseEvent event) -> {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("../View/AddressDetailsView.fxml"));
                Parent root = (Parent)loader.load();
                Stage checkAddress = new Stage();
                Scene scene = new Scene(root);
                checkAddress.setTitle("Address Details");
                checkAddress.setScene(scene);
                checkAddress.setResizable(false);
                AddressDetailsController addressController = loader.getController();
                try {
                    addressController.setData(address_ID);
                } catch (SQLException ex) {
                    Logger.getLogger(EditProfileController.class.getName()).log(Level.SEVERE, null, ex);
                }
                checkAddress.show();
            } catch (IOException ex) {
                Logger.getLogger(EditProfileController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        
        contactbtn.setOnMouseClicked((MouseEvent event) -> {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("../View/ContactDetailsView.fxml"));
                Parent root = (Parent)loader.load();
                Stage checkContact = new Stage();
                Scene scene = new Scene(root);
                checkContact.setTitle("Contact Details");
                checkContact.setScene(scene);
                checkContact.setResizable(false);
                ContactDetailsController contactController = loader.getController();
                try {
                    contactController.setData(contact_ID);
                } catch (SQLException ex) {
                    Logger.getLogger(EditProfileController.class.getName()).log(Level.SEVERE, null, ex);
                }
                checkContact.show();
            } catch (IOException ex) {
                Logger.getLogger(EditProfileController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        
        insurancebtn.setOnMouseClicked((MouseEvent event) -> {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("../View/InsuranceDetailsView.fxml"));
                Parent root = (Parent)loader.load();
                Stage checkInsurance = new Stage();
                Scene scene = new Scene(root);
                checkInsurance.setTitle("Insurance Details");
                checkInsurance.setScene(scene);
                checkInsurance.setResizable(false);
                InsuranceDetailsController insuranceController = loader.getController();
                try {
                    insuranceController.setData(userID);
                } catch (SQLException ex) {
                    Logger.getLogger(EditProfileController.class.getName()).log(Level.SEVERE, null, ex);
                }
                checkInsurance.show();
            } catch (IOException ex) {
                Logger.getLogger(EditProfileController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        
    }

    /**
     * Returns the ComboBox Values from the database
     *
     * @throws SQLException
     */
    private void setComboValues() throws SQLException {
        ed.getObject();

        customCombo = ed.FetchData("PM_ROLES");
        comboRole.setItems(FXCollections.observableArrayList(customCombo));
        InitiateComboList(roleId, comboRole);
        
        customCombo = ed.FetchData("PM_NATIONALITIES");
        comboNationality.setItems(FXCollections.observableArrayList(customCombo));
        InitiateComboList(nationalityId, comboNationality);
        customCombo = ed.FetchData("PM_ECO_STATUS");
        comboEcoStatus.setItems(FXCollections.observableArrayList(customCombo));
        InitiateComboList(ecoStatusId, comboEcoStatus);
        customCombo = ed.FetchData("PM_GENDERS");
        comboGender.setItems(FXCollections.observableArrayList(customCombo));
        InitiateComboList(genderId, comboGender);
    }

    private void InitiateComboList(Integer lId, ComboBox lComboBox) {
        customCombo.stream().filter((r) -> (r.getId() == lId)).forEachOrdered((r) -> {
            lComboBox.setValue(r.getDescription());
        });
    }

    public static final LocalDate LOCAL_DATE(String dateString) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d-MM-yyyy", Locale.FRENCH);
        LocalDate localDate = LocalDate.parse(dateString, formatter);
        return localDate;
    }

    /**
     * Set the ComboBox Event Listeners
     */
    private void setComboEventListeners() {
        comboRole.valueProperty().addListener((obs, oldval, newval) -> {
            if (newval != null) {
                CustomComboClass coRole = (CustomComboClass) comboRole.getSelectionModel().getSelectedItem();
                roleId = coRole.getId();
            }
        });
        comboNationality.valueProperty().addListener((obs, oldval, newval) -> {
            if (newval != null) {
                CustomComboClass coNationality = (CustomComboClass) comboNationality.getSelectionModel().getSelectedItem();
                nationalityId = coNationality.getId();
            }
        });
        comboEcoStatus.valueProperty().addListener((obs, oldval, newval) -> {
            if (newval != null) {
                CustomComboClass coEcoStatus = (CustomComboClass) comboEcoStatus.getSelectionModel().getSelectedItem();
                ecoStatusId = coEcoStatus.getId();
            }
        });
        comboGender.valueProperty().addListener((obs, oldval, newval) -> {
            if (newval != null) {
                CustomComboClass coGender = (CustomComboClass) comboGender.getSelectionModel().getSelectedItem();
                genderId = coGender.getId();
            }
        });
    }
}
