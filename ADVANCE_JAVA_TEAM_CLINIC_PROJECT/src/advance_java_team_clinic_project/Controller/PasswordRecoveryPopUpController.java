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
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * FXML Controller class
 *
 * @author Tasos
 */
public class PasswordRecoveryPopUpController extends StageRedirect implements Initializable {

    
    
    @FXML
    private Pane RecoveryPop;
    @FXML
    private PasswordField passwordInput;
    @FXML
    private PasswordField passwordRepeatInput;
    @FXML
    private Button submitBtn;
    private String pass1, pass2;
    private int Id;
    Alert alert = new Alert(Alert.AlertType.INFORMATION);
    private static final RegisterAndLoginModel ak = new RegisterAndLoginModel();
    private static final PasswordRecoveryController po = new PasswordRecoveryController();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        submitBtn.setOnMouseClicked(new EventHandler<MouseEvent>() { //Molis patisei to sumbit button pernei to username, id tou question kai to answer tou xristi, epeita anigei to pop up gia allagi kodikou
            @Override
            public void handle(MouseEvent event) {
                pass1 = passwordInput.getText(); //Pernei to username ws String
                pass2 = passwordRepeatInput.getText(); //Pernei to answer ws String
                ak.getObject();
                
                if (!pass1.equals(null) && !pass1.equals("") && !pass2.equals(null) && !pass2.equals("")) { //An ta password den einai kena
                    if (pass1.equals(pass2)) { try {
                        //An ta passwrod einai idia
                        ak.changePassword(pass1, Id); //Kalame to query stin basi pou allazei to password tou sigekrimenou xristi me afto to id
                        po.GetStage().close();
                        Stage currentStage = (Stage) RecoveryPop.getScene().getWindow();
                        setNewStage("../View/LoginWindowView.fxml", currentStage);
                        currentStage.show();
                        } catch (IOException ex) {
                            Logger.getLogger(PasswordRecoveryPopUpController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    } else {
                    alert.setHeaderText(null);
                    alert.initStyle(StageStyle.UTILITY);
                    alert.setTitle("Wrong passwords.");
                    alert.setContentText("Wrong passwords.");
                    alert.showAndWait();                  
                }
                } else {
                    alert.setHeaderText(null);
                    alert.initStyle(StageStyle.UTILITY);
                    alert.setTitle("Null textfields.");
                    alert.setContentText("Null textfields.");
                    alert.showAndWait();
                   
                }

            }
        });

    }

    public void SetId(Integer Id) {
        this.Id = Id;

    }

}
