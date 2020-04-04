/*
 *  Project for TEI OF CRETE lesson
 *  Plan Driven and Agile Programming
 *  TP4129 - TP4187 - TP4145
 */
package advance_java_team_clinic_project.Controller;

import advance_java_team_clinic_project.Model.AddressDetailsModel;
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
public class AddressDetailsController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private Button submitBtn;
    @FXML
    private TextField address;
    @FXML
    private TextField city;
    @FXML
    private TextField county;
    @FXML
    private TextField postalCode;

    private static AddressDetailsModel ak = new AddressDetailsModel();
    private ResultSet rs;
    LoggedInUserClass user = LoggedInUserClass.getInstance();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        addTextLimiter(postalCode);
    }

    public void setData(Integer userAddressID) throws SQLException {
        ak.getObject();
        rs = ak.fetchAddressInfoData(userAddressID);
        if (rs.next()) {
            address.setText(rs.getString("address"));
            city.setText(rs.getString("city"));
            county.setText(rs.getString("county"));
            postalCode.setText(String.valueOf(rs.getInt("postal_code")));
        }
        
        submitBtn.setOnMouseClicked((MouseEvent event) -> {
            try {
                ak.updateAddressDetails(userAddressID, address.getText(), city.getText(), county.getText(), Integer.parseInt(postalCode.getText()));
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
            if (tf.getText().length() > 5) {
                String s = tf.getText().substring(0, 5);
                tf.setText(s);
            }
        });
    }

}
