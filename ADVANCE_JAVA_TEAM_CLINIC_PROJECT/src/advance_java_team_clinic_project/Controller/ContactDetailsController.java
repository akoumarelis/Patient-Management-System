/*
 *  Project for TEI OF CRETE lesson
 *  Plan Driven and Agile Programming
 *  TP4129 - TP4187 - TP4145
 */
package advance_java_team_clinic_project.Controller;

import advance_java_team_clinic_project.Model.ContactDetailsModel;
import advance_java_team_clinic_project.classes.LoggedInUserClass;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

/**
 * FXML Controller class
 *
 * @author Chris
 */
public class ContactDetailsController implements Initializable {

    @FXML
    private Button submitBtn;
    @FXML
    private TextField email;
    @FXML
    private TextField relativeTelephoneNumber;
    @FXML
    private TextField telephoneNumber;
    @FXML
    private TextField cellphoneNumber;
    /**
     * Initializes the controller class.
     */
    private static ContactDetailsModel ak = new ContactDetailsModel();
    private ResultSet rs;
    LoggedInUserClass user = LoggedInUserClass.getInstance();

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        addTextLimiter(email);
        addTextLimiter(relativeTelephoneNumber);

        

    }

    public void setData(Integer userContactID) throws SQLException {
        ak.getObject();
        rs = ak.fetchContactInfoData(userContactID);
        if (rs.next()) {
            telephoneNumber.setText(String.valueOf(rs.getString("tel_number")));
            cellphoneNumber.setText(String.valueOf(rs.getString("cel_number")));
            email.setText(rs.getString("email"));
            relativeTelephoneNumber.setText(String.valueOf(rs.getString("relative_tel_number")));
        }
        
        submitBtn.setOnMouseClicked((MouseEvent event) -> {
            try {
                ak.updateContactDetails(userContactID, Integer.parseInt(telephoneNumber.getText()), Integer.parseInt(cellphoneNumber.getText()), email.getText(), Integer.parseInt(relativeTelephoneNumber.getText()));
            } catch (SQLException ex) {
                Logger.getLogger(ContactDetailsController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }

    /**
     *
     * @param tf
     */
    public static void addTextLimiter(final TextField tf) {
        tf.textProperty().addListener((final ObservableValue<? extends String> ov, final String oldValue, final String newValue) -> {
            if (tf.getText().length() > 10) {
                String s = tf.getText().substring(0, 10);
                tf.setText(s);
            }
        });
    }

}
