/*
 *  Project for TEI OF CRETE lesson
 *  Plan Driven and Agile Programming
 *  TP4129 - TP4187 - TP4145
 */
package advance_java_team_clinic_project.Controller;

import advance_java_team_clinic_project.classes.LoggedInUserClass;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

/**
 * FXML Controller class
 *
 * @author Chris
 */
public class ProfileController implements Initializable {

    LoggedInUserClass user = LoggedInUserClass.getInstance();
    @FXML
    private Text firstNameText;
    @FXML
    private Text lastNameText;
    @FXML
    private Text usernameText;
    @FXML
    private AnchorPane profilePane;
    @FXML
    private Text roleText;

    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        if(user.getFirstName() == null){firstNameText.setText(firstNameText.getText() + "");}
        else{firstNameText.setText(firstNameText.getText() + " " + user.getFirstName());}
        
        if(user.getSurname() == null){lastNameText.setText(lastNameText.getText() + "");}
        else{lastNameText.setText(lastNameText.getText() + " " + user.getSurname());}
               
        
        usernameText.setText(usernameText.getText() + " " + user.getUsername());

        
        
        switch (user.getRoleID()) {
            case 1:
                roleText.setText(roleText.getText() + "ADMIN");
                break;
            case 2:
                roleText.setText(roleText.getText() + "DOCTOR");
                break;
            case 3:
                roleText.setText(roleText.getText() + "PATIENT");
                break;
            case 4:
                roleText.setText(roleText.getText() + "RECEPTION");
                break;
            case 5:
                roleText.setText(roleText.getText() + "CLINIC");
                break;
        }

    }

}
