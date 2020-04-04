/*
 *  Project for TEI OF CRETE lesson
 *  Plan Driven and Agile Programming
 *  TP4129 - TP4187 - TP4145
 */
package advance_java_team_clinic_project.Controller;

import advance_java_team_clinic_project.Model.RegisterAndLoginModel;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Chris
 */
public class LoginWindowController extends StageRedirect implements Initializable {

    @FXML
    private Button registerBtn;
    @FXML
    private AnchorPane leftPane;
    @FXML
    private AnchorPane rightPane;
    @FXML
    private Button loginBtn;
    @FXML
    private Button forgotBtn;
    @FXML
    private Pane loginPane;
    private static RegisterAndLoginModel ak;
    @FXML
    private TextField userNameTxtField;
    @FXML
    private PasswordField passWordField;

    String userNameGiven;
    String passWordGiven;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        registerBtn.setOnAction((ActionEvent e) -> {
            try {
                handleRegisterAction(e);
            } catch (IOException ex) {
                Logger.getLogger(LoginWindowController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        loginBtn.setOnAction((ActionEvent e) -> {
            try {
                handleLoginAction(e);
            } catch (IOException ex) {
                Logger.getLogger(LoginWindowController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        
        forgotBtn.setOnAction((ActionEvent e) -> {
            try {
                handleRecoveryAction(e);
            } catch (IOException ex) {
                Logger.getLogger(LoginWindowController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

    }

    /**
     * Function that triggers when the user presses the login button. Connects
     * with database and checks if the user exists or not.
     *
     * @param event
     * @throws IOException
     */
    private void handleLoginAction(ActionEvent event) throws IOException {
        ak = new RegisterAndLoginModel();
        userNameGiven = userNameTxtField.getText();
        passWordGiven = passWordField.getText();
        /**/
        Stage currentStage = (Stage) loginPane.getScene().getWindow();
        if (ak.loginQuery(userNameGiven, passWordGiven) == true) {
            setNewStage("../View/UserMenuView.fxml", currentStage);
        }
    }

    /**
     * Function that triggers when the user presses the register button. Returns
     * a new page with the sign up selection.
     *
     * @param event
     * @throws IOException
     */
    @SuppressWarnings("empty-statement")
    private void handleRegisterAction(ActionEvent event) throws IOException {
        Stage currentStage = (Stage) loginPane.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(LoginWindowController.this.getClass().getResource("../View/SignUpWindowView.fxml"));
        Parent root = (Parent)loader.load();
        Scene scene = new Scene(root);
        currentStage.setScene(scene);
        SignUpWindowController registerController = loader.getController();
        registerController.setRegisterPage(false);
    }
        /**
     * Function that triggers when the user presses the Forgot Password button. Returns
     * a new page with the recovery password panel.
     *
     * @param event
     * @throws IOException
    
    
    */

   @SuppressWarnings("empty-statement")
   private void handleRecoveryAction(ActionEvent event) throws IOException {
       Stage currentStage = (Stage) loginPane.getScene().getWindow();
       setNewStage("../View/PasswordRecoveryView.fxml", currentStage);
       currentStage.show();

   }
    
    
    
}
