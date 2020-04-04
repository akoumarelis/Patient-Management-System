/*
 *  Project for TEI OF CRETE lesson
 *  Plan Driven and Agile Programming
 *  TP4129 - TP4187 - TP4145
 */
package advance_java_team_clinic_project.Controller;

import advance_java_team_clinic_project.Model.AdmissionModel;
import advance_java_team_clinic_project.Model.TestsModel;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.UnaryOperator;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.TextFormatter.Change;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.converter.IntegerStringConverter;

/**
 * FXML Controller class
 *
 * @author Chris
 */
public class PaymentWindowController implements Initializable {

    @FXML
    private Button submitBtn;
    @FXML
    private TextField amountInput;
    @FXML
    private AnchorPane paymentPane;

    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        UnaryOperator<Change> integerFilter = change -> {
        String newText = change.getControlNewText();
            if (newText.matches("-?([1-9][0-9]*)?")) { return change;}
            return null;
        };
        amountInput.setTextFormatter(
            new TextFormatter<Integer>(new IntegerStringConverter(), 0, integerFilter));
    }    
    
    /**
     *
     * @param type
     * @param ID
     */
    public void setPayment(Integer type, Integer ID){
        //type 1 -> tests
        //type 2 -> admissions
        
       submitBtn.setOnMouseClicked((MouseEvent event) -> {
            if(type == 1){
                TestsModel test = new TestsModel();
                test.updateIsPaid(ID, Integer.valueOf(amountInput.getText()));
                 Stage stage = (Stage) paymentPane.getScene().getWindow();
                paymentPane.fireEvent(new WindowEvent(stage, WindowEvent.WINDOW_CLOSE_REQUEST));
            }else if(type == 2){
                AdmissionModel admission = new AdmissionModel();
                admission.updateIsPaid(ID,Integer.valueOf(amountInput.getText()));
                 Stage stage = (Stage) paymentPane.getScene().getWindow();
                paymentPane.fireEvent(new WindowEvent(stage, WindowEvent.WINDOW_CLOSE_REQUEST));
            }     
        });
        
        
    }
}
