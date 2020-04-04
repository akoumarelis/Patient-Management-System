/*
 *  Project for TEI OF CRETE lesson
 *  Plan Driven and Agile Programming
 *  TP4129 - TP4187 - TP4145
 */
package advance_java_team_clinic_project.Controller;

import advance_java_team_clinic_project.Model.ParametricsModel;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Callback;

/**
 * FXML Controller class
 *
 * @author Tasos
 */
public class ParametricsTableController extends StageRedirect implements Initializable {

    @FXML
    private AnchorPane ParametricsPanel;
    @FXML
    private Button backBtn;
    @FXML
    private Button createBtn;

    @FXML
    private TableView<ParametricsModel> parametricsTable;

    @FXML
    private Text DescriptionLayout;

    private ObservableList data;

    TableColumn idCol = new TableColumn("CODE");
    TableColumn descrCol = new TableColumn("DESCRIPTION");
    TableColumn createdDateCol = new TableColumn("CREATED");
    TableColumn updatedDateCol = new TableColumn("UPDATED");
    TableColumn updatedByCol = new TableColumn("UPDATED BY");
    TableColumn createdByCol = new TableColumn("CREATED BY");

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        parametricsTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        idCol.setCellValueFactory(new PropertyValueFactory<>("code"));
        descrCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        createdDateCol.setCellValueFactory(new PropertyValueFactory<>("created"));
        updatedDateCol.setCellValueFactory(new PropertyValueFactory<>("updated"));
        updatedByCol.setCellValueFactory(new PropertyValueFactory<>("updatedby"));
        createdByCol.setCellValueFactory(new PropertyValueFactory<>("createdby"));

        backBtn.setOnMouseClicked((MouseEvent event) -> {
            try {
                FXMLLoader loader = new FXMLLoader(ParametricsTableController.this.getClass().getResource("../View/ParametricsView.fxml"));
                Parent root = (Parent) loader.load();
                ParametricsPanel.getChildren().clear();
                ParametricsPanel.getChildren().add(root);
            } catch (IOException ex) {
                Logger.getLogger(EditProfileController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        parametricsTable.getColumns().addAll(idCol, descrCol, createdDateCol, createdByCol, updatedDateCol, updatedByCol);

    }

    /**
     *
     * @param tableName
     * @param lName
     */
    public void setWindow(String tableName, String lName) {
        ParametricsModel pm = new ParametricsModel();
        ResultSet rs = null;
        rs = pm.getDesc(tableName);
        System.out.println(lName);
        DescriptionLayout.setText(lName);
        try {
            data = FXCollections.observableArrayList(fillParametricsTable(rs));
        } catch (SQLException ex) {
            Logger.getLogger(ParametricsTableController.class.getName()).log(Level.SEVERE, null, ex);
        }

        createBtn.setOnMouseClicked((MouseEvent event) -> {
            try {
                FXMLLoader loader = new FXMLLoader(ParametricsTableController.this.getClass().getResource("../View/ComponentView.fxml"));
                Parent root = (Parent) loader.load();
                Stage createB = new Stage();
                Scene scene = new Scene(root);
                createB.setTitle("Enter New Description ");
                createB.setScene(scene);
                createB.setResizable(false);
                createB.setOnCloseRequest((WindowEvent event1) -> {
                    setWindow(tableName, lName);
                });
                createB.show();

                ComponentsController createComp = loader.getController();
                createComp.createComponent(tableName);
            } catch (IOException ex) {
                Logger.getLogger(EditProfileController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        Callback<TableColumn<ParametricsModel, Void>, TableCell<ParametricsModel, Void>> cellFactory = (TableColumn<ParametricsModel, Void> param) -> {
            TableCell<ParametricsModel, Void> cell = new TableCell<ParametricsModel, Void>() {
                private Button btn = new Button();

                @Override
                public void updateItem(Void item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);
                    } else {
                        ParametricsModel data = new ParametricsModel();
                        Integer id;
                        data = getTableView().getItems().get(getIndex());
                        id = Integer.valueOf(data.idProperty().getValue());
                        String desc = data.descriptionProperty().getValue();
                        btn.setText("EDIT");
                        btn.setOnMouseClicked((MouseEvent event) -> {

                            try {
//                                    Parent root = FXMLLoader.load(getClass().getResource("../View/AdminNewComponent.fxml"));
                                Stage createB = new Stage();

                                FXMLLoader loader = new FXMLLoader(ParametricsTableController.this.getClass().getResource("../View/ComponentView.fxml"));
                                Parent root = (Parent) loader.load();
                                Scene scene = new Scene(root);
                                createB.setTitle("EDIT");
                                createB.setScene(scene);
                                createB.setResizable(false);
                                createB.setOnCloseRequest((WindowEvent event1) -> {
                                    setWindow(tableName, lName);
                                });
                                createB.show();

                                ComponentsController editComp = loader.getController();
                                editComp.editComponent(tableName, id, desc);
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
        idCol.setCellFactory(cellFactory);

        parametricsTable.setItems(data);
    }

    /**
     *
     * @param rs
     * @return
     * @throws SQLException
     */
    public ArrayList fillParametricsTable(ResultSet rs) throws SQLException {

        ArrayList<ParametricsModel> data = new ArrayList();

        while (rs.next()) {
            ParametricsModel pm = new ParametricsModel();
            pm.idProperty().set(rs.getString("id"));
            pm.descriptionProperty().set(rs.getString("description"));
            pm.createdProperty().set(rs.getString("created"));
            pm.createdbyProperty().set(rs.getString("createdby"));
            pm.updatedProperty().set(rs.getString("updated"));
            pm.updatedbyProperty().set(rs.getString("updatedby"));
            data.add(pm);
        }
        return data;
    }

}
