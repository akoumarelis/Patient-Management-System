/*
 *  Project for TEI OF CRETE lesson
 *  Plan Driven and Agile Programming
 *  TP4129 - TP4187 - TP4145
 */
package advance_java_team_clinic_project.Controller;

import advance_java_team_clinic_project.classes.CustomComboClass;
import advance_java_team_clinic_project.Model.RegisterAndLoginModel;
import advance_java_team_clinic_project.Model.CustomComboModel;
import java.io.IOException;
import java.net.URL;
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
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * FXML Controller class
 *
 * @author Tasos
 */
public class PasswordRecoveryController extends StageRedirect implements Initializable {

    @FXML
    private Pane RecoveryPane;
    @FXML
    private Button sumbitBtn;
    @FXML
    private ImageView backBtn;
    @FXML
    private TextField UsernameText;
    @FXML
    private ComboBox ComboQuest;
    @FXML
    private TextField AnswerPass;
    private String username, answer;
    private int idCombo;
    Alert alert = new Alert(Alert.AlertType.INFORMATION);
    private ObservableList<CustomComboClass> customCombo = FXCollections.observableArrayList();
    private static final CustomComboModel ed = new CustomComboModel();
    private static Stage stage;
    private static final RegisterAndLoginModel ak = new RegisterAndLoginModel();
    private static final PasswordRecoveryPopUpController op = new PasswordRecoveryPopUpController();

    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            setComboValues();
            sumbitBtn.setOnMouseClicked((MouseEvent event) -> {
                try {
                    stage = new Stage();
                    stage = (Stage) sumbitBtn.getScene().getWindow();
                    username = UsernameText.getText(); //Pernei to username ws String
                    answer = AnswerPass.getText(); //Pernei to answer ws String
                    if (ak.recoveryPassword(username, idCombo, answer) > 0) { //An tas stoixeia pou edwse einai sosta me tin basi (username, id tis erotisis, answer)
                        Parent root = FXMLLoader.load(getClass().getResource("../View/PasswordRecoveryPopUpView.fxml"));
                        Stage checkContact = new Stage();
                        Scene scene = new Scene(root);
                        checkContact.setTitle("Password Recovery");
                        checkContact.setScene(scene);
                        checkContact.setResizable(false);
                        checkContact.show();
                        op.SetId(ak.recoveryPassword(username, idCombo, answer)); //Kalame aftin tin sinartisi i opoia mas epistrefei afto to id gia na to dosoume epeite stin basi gia na kserei gia pio xristi allazoume id
                    } else {
                        alert.setHeaderText(null);
                        alert.initStyle(StageStyle.UTILITY);
                        alert.setTitle("Wrong answers");
                        alert.setContentText("Wrong answers.");
                        alert.showAndWait();
                    }
                    
                } catch (IOException ex) {
                    Logger.getLogger(EditProfileController.class.getName()).log(Level.SEVERE, null, ex);
                }
            } //Molis patisei to sumbit button pernei to username, id tou question kai to answer tou xristi, epeita anigei to pop up gia allagi kodikou
            );
            setComboEventListeners();
        } catch (SQLException ex) {
            Logger.getLogger(PasswordRecoveryController.class.getName()).log(Level.SEVERE, null, ex);
        }
        backBtn.setOnMouseClicked((MouseEvent event) -> {
            try {
                Stage currentStage = (Stage) RecoveryPane.getScene().getWindow();
                setNewStage("../View/LoginWindowView.fxml", currentStage);
            } catch (IOException ex) {
                Logger.getLogger(PasswordRecoveryController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }

    public Stage GetStage() { //Pername to stage sto pop parathiro gia na kleinei otan allaksei o kodikos
        return stage;
    }

    private void setComboValues() throws SQLException {     //Gemizei to combobox me tis erotiseis apo tin basi
        customCombo = ed.FetchData("PM_QUESTIONS");
        ComboQuest.setItems(FXCollections.observableArrayList(customCombo));
    }

    private void setComboEventListeners() {      //Listener gia na paroume to id tis erotiseis molis tin epileksei o xristis
        ComboQuest.valueProperty().addListener((obs, oldval, newval) -> {
            if (newval != null) {
                CustomComboClass coQuest1 = (CustomComboClass) ComboQuest.getSelectionModel().getSelectedItem();
                idCombo = coQuest1.getId();
            }
        });
    }

}
