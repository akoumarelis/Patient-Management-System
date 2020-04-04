/*
 *  Project for TEI OF CRETE lesson
 *  Plan Driven and Agile Programming
 *  TP4129 - TP4187 - TP4145
 */
package advance_java_team_clinic_project.Controller;

import advance_java_team_clinic_project.Model.DatabaseConnectionModel;
import advance_java_team_clinic_project.Model.EditProfileModel;
import advance_java_team_clinic_project.classes.LoggedInUserClass;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Chris
 */
public class PasswordWindowController implements Initializable {

    @FXML
    private PasswordField passwordInput;
    @FXML
    private PasswordField passwordRepeatInput;
    @FXML
    private PasswordField currentPassword;
    @FXML
    private Button submitBtn;

    private String hashPwd;
    LoggedInUserClass user = LoggedInUserClass.getInstance();
    private Statement stmt;

    private static final EditProfileModel ak = new EditProfileModel();
    private ResultSet rs;
    private DatabaseConnectionModel object;
    @FXML
    private AnchorPane passwordPane;
    private boolean passChanged;

    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        

    }
    
    public void myInit(Integer userID){
        try {
            object = DatabaseConnectionModel.getInstance();
            ak.getObject();
            stmt = object.connection.createStatement();
            rs = ak.getPassword(userID);
            if (rs.next()) {
                hashPwd = rs.getString("password");
            }

        } catch (SQLException ex) {
            Logger.getLogger(PasswordWindowController.class.getName()).log(Level.SEVERE, null, ex);
        }

        submitBtn.setDisable(true);
        passwordRepeatInput.addEventFilter(KeyEvent.KEY_RELEASED, newPasswordRepeatValidation());
        passwordInput.addEventFilter(KeyEvent.KEY_RELEASED, newPasswordValidation());

        submitBtn.setOnMouseClicked((MouseEvent event) -> {
            String password = currentPassword.getText();
            if(ak.updatePassword(userID, password,passwordRepeatInput.getText())){
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("");
                alert.setHeaderText(null);
                alert.setContentText("Password successfully changed!");
                alert.showAndWait();
                Stage s = (Stage) passwordPane.getScene().getWindow();
                s.close();
            }else{
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Wrong current password.");
                alert.showAndWait();
            }
        });
    }

    /**
     *
     * @return
     */
    public EventHandler<KeyEvent> newPasswordRepeatValidation() {
        return (KeyEvent e) -> {
            PasswordField txt_TextField = (PasswordField) e.getSource();
            if (txt_TextField.getText().equals(passwordInput.getText()) && !currentPassword.getText().isEmpty()) {
                submitBtn.setDisable(false);
            } else {
                submitBtn.setDisable(true);
            }
        };
    }

    /**
     *
     * @return
     */
    public EventHandler<KeyEvent> newPasswordValidation() {
        return (KeyEvent e) -> {
            PasswordField txt_TextField = (PasswordField) e.getSource();
            if (txt_TextField.getText().equals(passwordRepeatInput.getText()) && !currentPassword.getText().isEmpty()) {
                submitBtn.setDisable(false);
            } else {
                submitBtn.setDisable(true);
            }
        };
    }

    
}
