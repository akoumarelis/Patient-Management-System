/*
 *  Project for TEI OF CRETE lesson
 *  Plan Driven and Agile Programming
 *  TP4129 - TP4187 - TP4145
 */
package advance_java_team_clinic_project.Controller;

import advance_java_team_clinic_project.Model.AppointmentsModel;
import advance_java_team_clinic_project.Model.CustomComboModel;
import advance_java_team_clinic_project.classes.CustomComboClass;
import advance_java_team_clinic_project.classes.RecordsClass;
import advance_java_team_clinic_project.classes.LoggedInUserClass;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
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
import javafx.stage.StageStyle;
import javafx.util.Callback;
import javafx.util.StringConverter;

/**
 * FXML Controller class
 *
 * @author Tasos
 */
public class AppointmentRecordsController extends StageRedirect implements Initializable {

    private AppointmentsModel ak = new AppointmentsModel();
    ;
    private ResultSet rs;
    private ObservableList data;
    @FXML
    private TableView<RecordsClass> recordsTable = new TableView<>();

    TableColumn editCol = new TableColumn("EDIT");
    TableColumn idCol = new TableColumn("CODE");
    TableColumn appDateCol = new TableColumn("APP DATE");
    TableColumn hourCol = new TableColumn("HOUR");
    TableColumn commentsCol = new TableColumn("COMMENTS");
    TableColumn createdDateCol = new TableColumn("CREATED");
    TableColumn updatedDateCol = new TableColumn("UPDATED");
    TableColumn patientCol = new TableColumn("PATIENT");
    TableColumn doctorCol = new TableColumn("DOCTOR");
    TableColumn updatedByCol = new TableColumn("UPDATED BY");
    TableColumn createdByCol = new TableColumn("CREATED BY");
    TableColumn deleteCol = new TableColumn("DELETE");
    @FXML
    private Text textHead;
    LoggedInUserClass user = LoggedInUserClass.getInstance();
    @FXML
    private AnchorPane recordsPane;
    @FXML
    private ComboBox doctorComboBox;
    @FXML
    private ComboBox patientComboBox;
    @FXML
    private Text doctorHeader;
    @FXML
    private Text patientHeader;

    private CustomComboModel ed = new CustomComboModel();
    ObservableList<CustomComboClass> customCombo = FXCollections.observableArrayList();
    private Integer patientID = null;
    private Integer doctorID = null;
    @FXML
    private DatePicker createdFromDate;
    @FXML
    private DatePicker createdToDate;
    @FXML
    private Button searchBtn;
    @FXML
    private ComboBox appointmentsComboBox;
    @FXML
    private Text appointmentsHeader;
    @FXML
    private Button clearBtn;
    @FXML
    private DatePicker appDate;

    private Integer appComboID = 1;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        if (user.getRoleID() == 3) {
            doctorComboBox.setVisible(false);
            patientComboBox.setVisible(false);
            doctorHeader.setVisible(false);
            patientHeader.setVisible(false);
        } else {
            doctorComboBox.setVisible(true);
            patientComboBox.setVisible(true);
            doctorHeader.setVisible(true);
            patientHeader.setVisible(true);
        }

        if (user.getRoleID() == 2 || user.getRoleID() == 4) {
            doctorHeader.setVisible(true);
            doctorComboBox.setVisible(true);
        }

        if (user.getRoleID() == 4) {
            appointmentsComboBox.setVisible(true);
            appointmentsHeader.setVisible(true);
        } else {
            appointmentsHeader.setVisible(false);
            appointmentsComboBox.setVisible(false);
        }

        customCombo.addAll(new CustomComboClass(2, "New Appointments"), new CustomComboClass(1, "All Appointments"));
        appointmentsComboBox.setItems(FXCollections.observableArrayList(customCombo));
        appointmentsComboBox.setValue(appointmentsComboBox.getItems().get(1));
        clearBtn.setOnMouseClicked((MouseEvent event) -> {
            doctorComboBox.setValue(null);
            doctorID = null;
            patientComboBox.setValue(null);
            patientID = null;
            appointmentsComboBox.setValue(appointmentsComboBox.getItems().get(1));
            appComboID = 1;
            createdToDate.setValue(null);
            createdFromDate.setValue(null);
            appDate.setValue(null);
            handleSearchAction();
        });

        recordsTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        recordsTable.setId("tables");

        editCol.setCellValueFactory(new PropertyValueFactory<>("edit"));
        idCol.setCellValueFactory(new PropertyValueFactory<>("app_code"));
        appDateCol.setCellValueFactory(new PropertyValueFactory<>("app_date"));
        hourCol.setCellValueFactory(new PropertyValueFactory<>("hour"));
        commentsCol.setCellValueFactory(new PropertyValueFactory<>("comments"));
        createdDateCol.setCellValueFactory(new PropertyValueFactory<>("created"));
        updatedDateCol.setCellValueFactory(new PropertyValueFactory<>("updated"));
        patientCol.setCellValueFactory(new PropertyValueFactory<>("patient"));
        doctorCol.setCellValueFactory(new PropertyValueFactory<>("doctor"));
        updatedByCol.setCellValueFactory(new PropertyValueFactory<>("updated_by"));
        createdByCol.setCellValueFactory(new PropertyValueFactory<>("created_by"));
        deleteCol.setCellValueFactory(new PropertyValueFactory<>("delete"));

        recordsTable.getColumns().addAll(editCol, idCol, appDateCol, hourCol, commentsCol, createdDateCol, updatedDateCol, patientCol, doctorCol, updatedByCol, createdByCol);

        Callback<TableColumn<RecordsClass, String>, TableCell<RecordsClass, String>> cellFactory = (TableColumn<RecordsClass, String> param) -> {
            TableCell<RecordsClass, String> cell = new TableCell<RecordsClass, String>() {
                private Button btn = new Button();

                @Override
                public void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);
                    } else {
                        RecordsClass data = new RecordsClass();
                        data = getTableView().getItems().get(getIndex());
                        String appID = data.app_codeProperty().getValue().substring(4);
                        btn.setText("EDIT");
                        btn.setOnMouseClicked((MouseEvent event) -> {
                            try {
                                FXMLLoader loader = new FXMLLoader(AppointmentRecordsController.this.getClass().getResource("../View/AppointmentRecordInfoView.fxml"));
                                Parent root = (Parent) loader.load();
                                AppointmentRecordInfoController id = loader.getController();
                                id.setID(appID);
                                //Scene
                                recordsPane.getChildren().clear();
                                recordsPane.getChildren().add(root);
                            } catch (IOException ex) {
                                Logger.getLogger(AppointmentRecordsController.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        });
                        setGraphic(btn);
                    }
                }
            };
            return cell;
        };
        editCol.setCellFactory(cellFactory);

        Callback<TableColumn<RecordsClass, Void>, TableCell<RecordsClass, Void>> deleteFactory = (TableColumn<RecordsClass, Void> param) -> {
            TableCell<RecordsClass, Void> cell = new TableCell<RecordsClass, Void>() {
                private Button btn = new Button();

                @Override
                public void updateItem(Void item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);
                    } else {
                        RecordsClass rdata = new RecordsClass();
                        rdata = getTableView().getItems().get(getIndex());
                        Integer appID = Integer.valueOf(rdata.app_codeProperty().getValue().substring(4)); //APP-NUM -> (4) NUM
                        btn.setText("DELETE");
                        btn.setOnMouseClicked((MouseEvent event) -> {
                            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                            alert.setTitle("");
                            alert.setHeaderText("Do you want to delete test?");
                            alert.initStyle(StageStyle.UTILITY);

                            Optional<ButtonType> result = alert.showAndWait();
                            if (result.get() == ButtonType.OK) {
                                try {
                                    System.out.println("Deleting.. " + appID);
                                    if (ak.deleteAppointment(appID)) {
                                        System.out.println(appID + " deleted.");
                                        rs = ak.fetchBasicInfoData(user.getRoleID(), user.getId(), null, null, "01/01/1900", "01/01/2100", "01/01/1900", 1);
                                        data = FXCollections.observableArrayList(databaseRecords(rs));
                                        recordsTable.setItems(data);
                                    }
                                } catch (SQLException ex) {
                                    Logger.getLogger(AppointmentRecordsController.class.getName()).log(Level.SEVERE, null, ex);
                                }
                            }else{}
                            
                        });
                        setGraphic(btn);
                    }
                }
            };
            return cell;
        };

        switch (user.getRoleID()) {
            case 1:
                textHead.setText("APPOINTMENTS");
                break;
            case 2:
                textHead.setText("YOUR APPOINTMENTS");
                break;
            case 3:
                textHead.setText("YOUR RECORDS");
                break;
            case 4:
                textHead.setText("APPOINTMENTS");
                break;
            case 5:
                textHead.setText("APPOINTMENTS");
                break;
        }

        try {

            rs = ak.fetchBasicInfoData(user.getRoleID(), user.getId(), null, null, "01/01/1900", "01/01/2100", "01/01/1900", 1);
            data = FXCollections.observableArrayList(databaseRecords(rs));

            if (user.getRoleID() == 1) {
                deleteCol.setCellFactory(deleteFactory);
                recordsTable.getColumns().add(deleteCol);
            }

            ed.getObject();
            customCombo = ed.FetchUserFilterData(3);
            patientComboBox.setItems(FXCollections.observableArrayList(customCombo));

            customCombo = ed.FetchUserFilterData(2);
            doctorComboBox.setItems(FXCollections.observableArrayList(customCombo));

            setComboEventListeners();

            recordsTable.setItems(data);

        } catch (SQLException ex) {
            Logger.getLogger(AppointmentRecordsController.class.getName()).log(Level.SEVERE, null, ex);
        }

        searchBtn.setOnMouseClicked((MouseEvent event) -> {
            handleSearchAction();
//               System.out.println("App Date:" + appDate.getValue());
//               System.out.println("Created From:" + createdFromDate.getValue());
//               System.out.println("Created To:" + createdToDate.getValue());
//               System.out.println("Patient: " + patientID);   
//               System.out.println("Doctor: " +doctorID);
//               System.out.println("Doctor: " +appComboID);
        });

    }

    private void setComboEventListeners() {
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

        appointmentsComboBox.valueProperty().addListener((obs, oldval, newval) -> {
            if (newval != null) {
                CustomComboClass coAppointment = (CustomComboClass) appointmentsComboBox.getSelectionModel().getSelectedItem();
                appComboID = coAppointment.getId();
                System.out.println(coAppointment.getId());

            }
        });
    }

    /**
     * Returns data from a database
     *
     * @param rs
     * @return
     * @throws SQLException
     */
    private ArrayList databaseRecords(ResultSet rs) throws SQLException {
        ArrayList<RecordsClass> data = new ArrayList();

        while (rs.next()) {
            RecordsClass record = new RecordsClass();
            record.editProperty().set(rs.getString("app_code"));
            record.app_codeProperty().set(rs.getString("app_code"));
            record.app_dateProperty().set(rs.getString("app_date"));
            record.hourProperty().set(rs.getString("app_hour"));
            record.commentsProperty().set(rs.getString("comments"));
            record.createdProperty().set(rs.getString("created"));
            record.updatedProperty().set(rs.getString("updated"));
            record.patientProperty().set(rs.getString("patient"));
            record.doctorProperty().set(rs.getString("doctor"));
            record.updated_byProperty().set(rs.getString("updated_by"));
            record.created_byProperty().set(rs.getString("created_by"));
            data.add(record);
        }
        return data;
    }

    private void handleSearchAction() {
        try {
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
            if (appDate.getValue() != null) {
                app = appDate.getValue().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            } else {
                app = "01/01/1900";
            }

            rs = ak.fetchBasicInfoData(user.getRoleID(), user.getId(), doctorID, patientID,
                    createdFrom,
                    createdTo,
                    app, appComboID);
            data = FXCollections.observableArrayList(databaseRecords(rs));
            recordsTable.setItems(data);

        } catch (SQLException ex) {
            Logger.getLogger(AppointmentRecordsController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
