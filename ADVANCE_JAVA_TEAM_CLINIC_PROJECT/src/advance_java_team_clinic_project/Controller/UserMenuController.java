/*
 *  Project for TEI OF CRETE lesson
 *  Plan Driven and Agile Programming
 *  TP4129 - TP4187 - TP4145
 */
package advance_java_team_clinic_project.Controller;

import advance_java_team_clinic_project.classes.LoggedInUserClass;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ToggleButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * FXML Controller class
 *
 * @author Chris
 */
public class UserMenuController extends StageRedirect implements Initializable {

    @FXML
    private VBox mainVBox;
    @FXML
    private TextFlow usernameTextFlow;
    @FXML
    private Text usernameText;
    @FXML
    private ToggleButton profileBtn;
    @FXML
    private ToggleButton editProfileBtn;
    @FXML
    private ToggleButton enterNewUserBtn;
    @FXML
    private ToggleButton searchUserBtn;
    @FXML
    private ToggleButton parametricsBtn;
    @FXML
    private ToggleButton logoutBtn;

    LoggedInUserClass loggedInUser = LoggedInUserClass.getInstance();
    @FXML
    private VBox buttonsVBox;
    @FXML
    private ToggleButton appointmentsBtn;
    @FXML
    private ToggleButton recordsBtn;
    @FXML
    private ToggleButton makeAnAppointmentBtn;
    @FXML
    private ToggleButton testsBtn;
    @FXML
    private BorderPane userBorderPane;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Doctor, Admin, Clinic, Receptionist
        // Profile, Edit, Profile, Logout

        usernameText.setText(usernameText.getText() + loggedInUser.getUsername());
        usernameTextFlow.setTextAlignment(TextAlignment.CENTER);

        buttonsVBox.getChildren().clear();
        switch (loggedInUser.getRoleID()) {
            case 1:
                buttonsVBox.getChildren().addAll(profileBtn, editProfileBtn, enterNewUserBtn, searchUserBtn, parametricsBtn, testsBtn, appointmentsBtn, logoutBtn);
                loadUIonSamePane("../View/ProfileView.fxml", userBorderPane);
                profileBtn.setSelected(true);
                break;
            case 2:
                buttonsVBox.getChildren().addAll(profileBtn, editProfileBtn, appointmentsBtn, logoutBtn);
                loadUIonSamePane("../View/ProfileView.fxml", userBorderPane);
                profileBtn.setSelected(true);
                buttonsVBox.setSpacing(30);
                break;
            case 3:
                buttonsVBox.getChildren().addAll(profileBtn,editProfileBtn,recordsBtn, testsBtn, makeAnAppointmentBtn, logoutBtn);
                loadUIonSamePane("../View/ProfileView.fxml", userBorderPane);
                profileBtn.setSelected(true);
                buttonsVBox.setSpacing(10);
                break;
            case 4:
                buttonsVBox.getChildren().addAll(profileBtn, editProfileBtn, searchUserBtn, appointmentsBtn, testsBtn, logoutBtn);
                loadUIonSamePane("../View/ProfileView.fxml", userBorderPane);
                profileBtn.setSelected(true);
                buttonsVBox.setSpacing(10);
                break;
            case 5:
                buttonsVBox.getChildren().addAll(profileBtn, editProfileBtn, testsBtn, logoutBtn);
                loadUIonSamePane("../View/ProfileView.fxml", userBorderPane);
                profileBtn.setSelected(true);
                buttonsVBox.setSpacing(30);
                break;

        }
        setMouseControllers();
    }

    public void setMouseControllers() {
        profileBtn.setOnMouseClicked((MouseEvent event) -> {
            loadUIonSamePane("../View/ProfileView.fxml", userBorderPane);
            clearSelectedButtons();
            profileBtn.setSelected(true);
        });

        parametricsBtn.setOnMouseClicked((MouseEvent event) -> {
            loadUIonSamePane("../View/ParametricsView.fxml", userBorderPane);
            clearSelectedButtons();
            parametricsBtn.setSelected(true);
        });

        searchUserBtn.setOnMouseClicked((MouseEvent event) -> {
            clearSelectedButtons();
            searchUserBtn.setSelected(true);
            FXMLLoader loader = new FXMLLoader(UserMenuController.this.getClass().getResource("../View/SearchUserWindowView.fxml"));
            Parent root = null;
            try {
                root = (Parent) loader.load();
            } catch (IOException ex) {
                Logger.getLogger(UserMenuController.class.getName()).log(Level.SEVERE, null, ex);
            }
            SearchUserWindowController searchController = loader.getController();
            searchController.setResults("");
            userBorderPane.setCenter(root);
        });

        editProfileBtn.setOnMouseClicked((MouseEvent event) -> {
            clearSelectedButtons();
            editProfileBtn.setSelected(true);
            FXMLLoader loader = new FXMLLoader(UserMenuController.this.getClass().getResource("../View/EditProfileView.fxml"));
            Parent root = null;
            try {
                root = (Parent) loader.load();
            } catch (IOException ex) {
                Logger.getLogger(UserMenuController.class.getName()).log(Level.SEVERE, null, ex);
            }
            EditProfileController editController = loader.getController();
            editController.myInit(loggedInUser.getId());
            userBorderPane.setCenter(root);
        });

        logoutBtn.setOnMouseClicked((MouseEvent event) -> {
            Stage currentStage = (Stage) userBorderPane.getScene().getWindow();

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("");
            alert.setHeaderText("Do you want to Logout?");
            alert.initStyle(StageStyle.UTILITY);

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                try {
                    setNewStage("../View/LoginWindowView.fxml", currentStage);
                } catch (IOException ex) {
                    Logger.getLogger(UserMenuController.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                clearSelectedButtons();
            }
        });

        makeAnAppointmentBtn.setOnMouseClicked((MouseEvent event) -> {
            loadUIonSamePane("../View/NewAppointmentView.fxml", userBorderPane);
            clearSelectedButtons();
            makeAnAppointmentBtn.setSelected(true);
        });

        recordsBtn.setOnMouseClicked((MouseEvent event) -> {
            loadUIonSamePane("../View/AppointmentRecordsView.fxml", userBorderPane);
            clearSelectedButtons();
            recordsBtn.setSelected(true);
        });

        testsBtn.setOnMouseClicked((MouseEvent event) -> {

            try {
                clearSelectedButtons();
                testsBtn.setSelected(true);

                FXMLLoader loader = new FXMLLoader(UserMenuController.this.getClass().getResource("../View/TestsTableView.fxml"));
                Parent root = (Parent) loader.load();
                TestsTableController allTests = loader.getController();
                allTests.setTestID(-1);
                userBorderPane.setCenter(root);
            } catch (IOException ex) {
                Logger.getLogger(UserMenuController.class.getName()).log(Level.SEVERE, null, ex);
            }

        });
        
        enterNewUserBtn.setOnMouseClicked((MouseEvent event) -> {

            try {
                clearSelectedButtons();
                enterNewUserBtn.setSelected(true);

                FXMLLoader loader = new FXMLLoader(UserMenuController.this.getClass().getResource("../View/SignUpWindowView.fxml"));
                Parent root = (Parent) loader.load();
                SignUpWindowController signUpController = loader.getController();
                signUpController.setRegisterPage(true);
                userBorderPane.setCenter(root);
            } catch (IOException ex) {
                Logger.getLogger(UserMenuController.class.getName()).log(Level.SEVERE, null, ex);
            }

        });

        appointmentsBtn.setOnMouseClicked((MouseEvent event) -> {
            loadUIonSamePane("../View/AppointmentRecordsView.fxml", userBorderPane);
            clearSelectedButtons();
            appointmentsBtn.setSelected(true);
        });
    }

    public void clearSelectedButtons() {
        profileBtn.setSelected(false);
        editProfileBtn.setSelected(false);
        enterNewUserBtn.setSelected(false);
        searchUserBtn.setSelected(false);
        parametricsBtn.setSelected(false);
        logoutBtn.setSelected(false);
        appointmentsBtn.setSelected(false);
        recordsBtn.setSelected(false);
        makeAnAppointmentBtn.setSelected(false);
        testsBtn.setSelected(false);
    }

}
