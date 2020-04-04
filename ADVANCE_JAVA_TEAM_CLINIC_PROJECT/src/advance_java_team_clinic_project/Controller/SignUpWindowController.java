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
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *
 * @author Chris
 */
public class SignUpWindowController extends StageRedirect implements Initializable {

    @FXML
    private TextField registerUsername;
    @FXML
    private PasswordField registerPassword;
    @FXML
    private PasswordField confirmPassword;
    @FXML
    private Pane signUpPane;
    @FXML
    private Button registerBtn;
    @FXML
    private ComboBox FirstQuestionCombo;
    @FXML
    private ComboBox SecondQuestionCombo;
    @FXML
    private TextField FirstAnswerQuestion;
    @FXML
    private TextField SecondAnswerQuestion;

    ObservableList<CustomComboClass> customCombo = FXCollections.observableArrayList();
    private static final CustomComboModel ed = new CustomComboModel();

    private String passWord, confirmPassWord, userName, answer1, answer2;
    private int question1 = -1, question2 = -1;
    Alert alert = new Alert(Alert.AlertType.INFORMATION);
    private static RegisterAndLoginModel ak;
    @FXML
    private ImageView backBtn;
    @FXML
    private Text registerHeader;
    @FXML
    private ImageView registerIcon;

    @Override
    @SuppressWarnings("empty-statement")
    public void initialize(URL location, ResourceBundle resources) {
        
    }
    
    public void setRegisterPage(boolean loggedIn){
        alert.setHeaderText(null);
        alert.initStyle(StageStyle.UTILITY);

        if(loggedIn){
            registerHeader.setText("Create New User");
            backBtn.setVisible(false);
            registerIcon.setVisible(false);
        }else{
            registerHeader.setText("Sign Up");
        }
        
        registerBtn.setOnAction((ActionEvent e) -> {
            passWord = registerPassword.getText();
            confirmPassWord = confirmPassword.getText();
            userName = registerUsername.getText();
            answer1 = FirstAnswerQuestion.getText();
            answer2 = SecondAnswerQuestion.getText();
            /**/
            Stage currentStage = (Stage) signUpPane.getScene().getWindow();
            Parent root;
            Scene scene;
            /**/
            if (!userName.equals(null) && !userName.equals("")) {
                ak = new RegisterAndLoginModel();
                if (!passWord.equals(null) && !passWord.equals("") && !confirmPassWord.equals(null) && !confirmPassWord.equals("")) {
                    if (passWord.equals(confirmPassWord)) {
                        if (!answer1.equals(null) && !answer1.equals("") && !answer2.equals(null) && !answer2.equals("") && (question1 > 0) && (question2 > 0)) {
                            if (question1 != question2) {
                                try {
                                    ak.getObject();
                                        if (ak.registerQuery(userName, passWord, question1, question2, answer1, answer2) == true) {
                                            if(loggedIn){
                                                FXMLLoader loader = new FXMLLoader(SignUpWindowController.this.getClass().getResource("../View/SearchUserWindowView.fxml"));
                                                root = (Parent) loader.load();
                                                SearchUserWindowController searchController = loader.getController();
                                                searchController.setResults(""); 
                                            }else{
                                                root = FXMLLoader.load(SignUpWindowController.this.getClass().getResource("../View/LoginWindowView.fxml"));
                                                scene = new Scene(root);
                                                currentStage.setScene(scene);
                                            }
                                            
                                        };
                                    
                                } catch (IOException ex) {
                                    Logger.getLogger(SignUpWindowController.class.getName()).log(Level.SEVERE, null, ex);
                                }
                            } else {
                                alert.setTitle("Same questions.");
                                alert.setContentText("You must choose different questions.");
                                alert.showAndWait();
                            }
                        } else {
                            alert.setTitle("Questions or answers are empty.");
                            alert.setContentText("Questions must be picked and answered for password recovery.");
                            alert.showAndWait();
                        }
                    } else {
                        alert.setTitle("Incorrect password");
                        alert.setContentText("Passwords do not match!");
                        alert.showAndWait();
                    }
                } else {
                    alert.setTitle("Incorrect password");
                    alert.setContentText("Please enter valid passwords!");
                    alert.showAndWait();
                }
            } else {
                alert.setTitle("Incorrect username");
                alert.setContentText("Please enter a valid username!");
                alert.showAndWait();
            }
        });

        backBtn.setOnMouseClicked((MouseEvent event) -> {
            Stage currentStage = (Stage) signUpPane.getScene().getWindow();
            try {
                setNewStage("../View/LoginWindowView.fxml", currentStage);
            } catch (IOException ex) {
                Logger.getLogger(SignUpWindowController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        try {
            setComboValues();
            setComboEventListeners();
        } catch (SQLException ex) {
            Logger.getLogger(SignUpWindowController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void setComboValues() throws SQLException {
        customCombo = ed.FetchData("PM_QUESTIONS");
        FirstQuestionCombo.setItems(FXCollections.observableArrayList(customCombo));
        SecondQuestionCombo.setItems(FXCollections.observableArrayList(customCombo));
    }

    private void setComboEventListeners() {
        FirstQuestionCombo.valueProperty().addListener((obs, oldval, newval) -> {
            if (newval != null) {
                CustomComboClass coQuest1 = (CustomComboClass) FirstQuestionCombo.getSelectionModel().getSelectedItem();
                question1 = coQuest1.getId();
                System.out.println(question1);
            }
        });

        SecondQuestionCombo.valueProperty().addListener((obs, oldval, newval) -> {
            if (newval != null) {
                CustomComboClass coQuest2 = (CustomComboClass) SecondQuestionCombo.getSelectionModel().getSelectedItem();
                question2 = coQuest2.getId();
                System.out.println(question2);
            }
        });
    }
}
