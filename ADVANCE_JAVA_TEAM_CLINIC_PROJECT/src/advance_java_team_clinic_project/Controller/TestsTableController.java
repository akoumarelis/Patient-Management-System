/*
 *  Project for TEI OF CRETE lesson
 *  Plan Driven and Agile Programming
 *  TP4129 - TP4187 - TP4145
 */
package advance_java_team_clinic_project.Controller;

import advance_java_team_clinic_project.Model.CustomComboModel;
import advance_java_team_clinic_project.Model.TestsModel;
import advance_java_team_clinic_project.classes.CustomComboClass;
import advance_java_team_clinic_project.classes.LoggedInUserClass;
import advance_java_team_clinic_project.classes.RecordsClass;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;

/**
 * FXML Controller class
 *
 * @author Chris
 */
public class TestsTableController implements Initializable {

    private ObservableList data;
    private CustomComboModel ed = new CustomComboModel();

    @FXML
    private TableView<TestsModel> testsTable = new TableView<>();

    
    TableColumn idCol = new TableColumn("Edit");
    TableColumn descriptionCol = new TableColumn("Description");
    TableColumn isCompletedCol = new TableColumn("Completed");
    TableColumn costCol = new TableColumn("Cost");
    TableColumn isPaidCol = new TableColumn("Paid");
    TableColumn resultsCol = new TableColumn("Results");
    TableColumn statusCol = new TableColumn("Status");
    TableColumn createdCol = new TableColumn("Created");
    TableColumn createdByCol = new TableColumn("Created By");
    TableColumn updatedCol = new TableColumn("Updated");
    TableColumn updatedByCol = new TableColumn("Updated By");
    TableColumn patientCol = new TableColumn("Patient");
    TableColumn doctorCol = new TableColumn("Doctor");
    TableColumn deleteCol = new TableColumn("Delete");

    @FXML
    private Button backBtn;
    @FXML
    private AnchorPane testsPane;

    LoggedInUserClass user = LoggedInUserClass.getInstance();
    TestsModel tests = new TestsModel();
    @FXML
    private ComboBox patientComboBox;

    ObservableList<CustomComboClass> customCombo = FXCollections.observableArrayList();
    @FXML
    private ComboBox doctorComboBox;
    @FXML
    private DatePicker createdFromDate;
    @FXML
    private DatePicker createdToDate;
    @FXML
    private ComboBox paidComboBox;
    @FXML
    private ComboBox completedComboBox;
    @FXML
    private Button searchBtn;
    @FXML
    private Text doctorHeader;
    @FXML
    private Text patientHeader;
    
    private Integer patientID = null;
    private Integer doctorID = null;
    private Integer completedAllID = null;
    private Integer paidAllID = null;
    
    @FXML
    private Button clearBtn;
    
    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        testsTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        testsTable.setId("tables");
        //For All
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        descriptionCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        isCompletedCol.setCellValueFactory(new PropertyValueFactory<>("is_completed"));
        costCol.setCellValueFactory(new PropertyValueFactory<>("cost"));
        isPaidCol.setCellValueFactory(new PropertyValueFactory<>("is_paid"));
        resultsCol.setCellValueFactory(new PropertyValueFactory<>("results"));
        statusCol.setCellValueFactory(new PropertyValueFactory<>("status_id"));
        doctorCol.setCellValueFactory(new PropertyValueFactory<>("doctor"));
        createdCol.setCellValueFactory(new PropertyValueFactory<>("created"));
        createdByCol.setCellValueFactory(new PropertyValueFactory<>("created_by"));
        updatedCol.setCellValueFactory(new PropertyValueFactory<>("updated"));
        updatedByCol.setCellValueFactory(new PropertyValueFactory<>("updated_by"));
        patientCol.setCellValueFactory(new PropertyValueFactory<>("patient"));
        deleteCol.setCellValueFactory(new PropertyValueFactory<>("delete"));

        if(user.getRoleID() == 3){
           doctorComboBox.setVisible(false);
           patientComboBox.setVisible(false);
           doctorHeader.setVisible(false);
           patientHeader.setVisible(false);
        }else{
           doctorComboBox.setVisible(true);
           patientComboBox.setVisible(true);
           doctorHeader.setVisible(true);
           patientHeader.setVisible(true);
        }
                
       customCombo.addAll(new CustomComboClass(1,"Yes"), new CustomComboClass(0,"No"), new CustomComboClass(-1,"All"));
       completedComboBox.setItems(FXCollections.observableArrayList(customCombo));
       paidComboBox.setItems(FXCollections.observableArrayList(customCombo));
      
       testsTable.getColumns().addAll(idCol, descriptionCol, isCompletedCol, costCol, isPaidCol, resultsCol, doctorCol, createdCol, createdByCol, updatedCol, updatedByCol, patientCol);
    }

    /**
     *
     * @param diagID
     */
    public void setTestID(Integer diagID) {
        if(diagID == -1) backBtn.setVisible(false);
        Callback<TableColumn<TestsModel, String>, TableCell<TestsModel, String>> cellFactory = (final TableColumn<TestsModel, String> param) -> {
            final TableCell<TestsModel, String> cell = new TableCell<TestsModel, String>() {
                final Button btn = new Button();

                @Override
                public void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);
                        setText(null);
                    } else {
                        TestsModel test = new TestsModel();
                        test = getTableView().getItems().get(getIndex());
                        String testID = test.idProperty().getValue();
                        btn.setText("EDIT");
                        btn.setStyle("-fx-pref-width: 200px;");
                        btn.setOnAction(event -> {
                            FXMLLoader loader = new FXMLLoader(TestsTableController.this.getClass().getResource("../View/TestsInfoView.fxml"));
                            Parent root = null;
                            try {
                                root = (Parent) loader.load();
                            } catch (IOException ex) {
                                Logger.getLogger(TestsTableController.class.getName()).log(Level.SEVERE, null, ex);
                            }
                            TestsInfoController id = loader.getController();
                            if(diagID == -1){
                                id.setTestIDView(false,Integer.valueOf(testID),-1);
                            }else{
                                id.setTestIDView(true,Integer.valueOf(testID),-1);
                            }
                            
                            //Scene
                            testsPane.getChildren().clear();
                            testsPane.getChildren().add(root);
                        });
                        setGraphic(btn);
                        setText(null);
                    }
                }
            };
            return cell;
        };
        
        Callback<TableColumn<TestsModel, Void>, TableCell<TestsModel, Void>>  deleteFactory = (TableColumn<TestsModel, Void> param) -> {
           TableCell<TestsModel, Void> cell = new TableCell<TestsModel, Void>() {
               private Button btn = new Button();
               @Override
               public void updateItem(Void item, boolean empty) {
                   super.updateItem(item, empty);
                   if (empty) {
                       setGraphic(null);
                   } else {
                       TestsModel test = new TestsModel();
                       test = getTableView().getItems().get(getIndex());
                       Integer testID = Integer.valueOf(test.idProperty().getValue());
                       btn.setText("DELETE");
                       btn.setOnMouseClicked((MouseEvent event) -> {
                            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                            alert.setTitle("");
                            alert.setHeaderText("Do you want to delete test?");
                            alert.initStyle(StageStyle.UTILITY);

                            Optional<ButtonType> result = alert.showAndWait();
                            if (result.get() == ButtonType.OK) {
                               if(tests.deleteTest(testID)){
                                    try {
                                        data = FXCollections.observableArrayList(databaseTests(tests.getTestByDiagID(diagID,doctorID,patientID,"01/01/1900","01/01/2100",completedAllID,paidAllID)));
                                        testsTable.setItems(data);
                                    } catch (SQLException ex) {
                                            Logger.getLogger(TestsTableController.class.getName()).log(Level.SEVERE, null, ex);
                                        }
                           }
                            } else {
                                
                            }

                          
                       });
                       setGraphic(btn);
                   }
               }
           };
           return cell;
       };
        
        idCol.setCellFactory(cellFactory);
        if(user.getRoleID() == 1){
            deleteCol.setCellFactory(deleteFactory);
            testsTable.getColumns().add(deleteCol);
        }
        backBtn.setOnMouseClicked((MouseEvent event) -> {
            try {
                FXMLLoader loader = new FXMLLoader(TestsTableController.this.getClass().getResource("../View/DiagnosisInfoView.fxml"));
                Parent root = (Parent) loader.load();
                DiagnosisInfoController diagnosisID = loader.getController();
                diagnosisID.setDiagnosisID("-1", diagID);
                testsPane.getChildren().clear();
                testsPane.getChildren().add(root);
            } catch (IOException ex) {
                Logger.getLogger(TestsTableController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        try {
            data = FXCollections.observableArrayList(databaseTests(tests.getTestByDiagID(diagID,doctorID,patientID,"01/01/1900","01/01/2100",completedAllID,paidAllID)));
            ed.getObject();
            
            customCombo = ed.FetchUserFilterData(3);
            patientComboBox.setItems(FXCollections.observableArrayList(customCombo));
            
            customCombo = ed.FetchUserFilterData(2);
            doctorComboBox.setItems(FXCollections.observableArrayList(customCombo));
            
            setComboEventListeners();
            
            testsTable.setItems(data);
        } catch (SQLException ex) {
            Logger.getLogger(TestsTableController.class.getName()).log(Level.SEVERE, null, ex);
        }

        
           searchBtn.setOnMouseClicked((MouseEvent event) -> {
             handleSearchAction(diagID);            
        });
           
        clearBtn.setOnMouseClicked((MouseEvent event) -> {
           doctorComboBox.setValue(null);
           doctorID = null;
           patientComboBox.setValue(null);
           patientID = null;
           completedComboBox.setValue(null);
           paidComboBox.setValue(null);
           paidComboBox.setValue(paidComboBox.getItems().get(2));
           completedComboBox.setValue(paidComboBox.getItems().get(2));
           paidAllID = null;
           completedAllID = null;
           createdToDate.setValue(null);
           createdFromDate.setValue(null);
           handleSearchAction(diagID);
        });   
        
    }
    
    private void setComboEventListeners(){
        patientComboBox.valueProperty().addListener((obs, oldval, newval) -> {
            if (newval != null) {
                CustomComboClass coPatient = (CustomComboClass) patientComboBox.getSelectionModel().getSelectedItem();
                patientID = coPatient.getId();
                System.out.println(coPatient.getId());
            }
        });
        
        doctorComboBox.valueProperty().addListener((obs, oldval, newval) -> {
            if (newval != null) {
                CustomComboClass coDoctor = (CustomComboClass) doctorComboBox.getSelectionModel().getSelectedItem();
                doctorID = coDoctor.getId();
                System.out.println(coDoctor.getId());
                
            }
        });
        
        paidComboBox.valueProperty().addListener((obs, oldval, newval) -> {
            if (newval != null) {
                CustomComboClass coPaid = (CustomComboClass) paidComboBox.getSelectionModel().getSelectedItem();
               if(coPaid.getId() == -1){
                   paidAllID = null;
               }else{
                   paidAllID = coPaid.getId();
               }
                
            }
        });
        
        completedComboBox.valueProperty().addListener((obs, oldval, newval) -> {
            if (newval != null) {
                CustomComboClass coCompleted = (CustomComboClass) completedComboBox.getSelectionModel().getSelectedItem();
                System.out.println(coCompleted.getId());
                if(coCompleted.getId() == -1){
                   completedAllID = null;
               }else{
                   completedAllID = coCompleted.getId();
               }
                
            }
        });
    }
    
    private void handleSearchAction(Integer diagID){
            String createdFrom, createdTo, app;
            if (createdFromDate.getValue() != null) {
                createdFrom = createdFromDate.getValue().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            } else {
                createdFrom = "01/01/1900";
            }
            if (createdToDate.getValue() != null) {
                createdTo = createdToDate.getValue().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            } else {
                createdTo = "01/01/2100";
            }
        try {
            data = FXCollections.observableArrayList(databaseTests(tests.getTestByDiagID(diagID,doctorID,patientID,createdFrom,createdTo,completedAllID,paidAllID)));
        } catch (SQLException ex) {
            Logger.getLogger(TestsTableController.class.getName()).log(Level.SEVERE, null, ex);
        }
            testsTable.setItems(data);

    }
    
    private ArrayList databaseTests(ResultSet rs) throws SQLException {
        ArrayList<TestsModel> data = new ArrayList();

        while (rs.next()) {
            TestsModel test = new TestsModel();
            test.idProperty().set(rs.getString("id"));
            //test.diag_idProperty().set(rs.getString("diag_id"));
            test.descriptionProperty().set(rs.getString("description"));
            test.is_completedProperty().set(rs.getString("is_completed"));
            test.costProperty().set(rs.getString("cost"));
            test.is_paidProperty().set(rs.getString("paid"));
            test.resultsProperty().set(rs.getString("results"));
            test.doctorProperty().set(rs.getString("doctor"));
                test.createdProperty().set(rs.getString("created"));
                test.created_byProperty().set(rs.getString("created_by"));
                test.updatedProperty().set(rs.getString("updated"));
                test.updated_byProperty().set(rs.getString("updated_by"));
                test.patientProperty().set(rs.getString("patient"));
            

            data.add(test);
        }
        return data;
    }
    
    

}