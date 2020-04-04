/*
 *  Project for TEI OF CRETE lesson
 *  Plan Driven and Agile Programming
 *  TP4129 - TP4187 - TP4145
 */
package advance_java_team_clinic_project.Model;

import advance_java_team_clinic_project.classes.CustomComboClass;
import advance_java_team_clinic_project.classes.LoggedInUserClass;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;

/**
 *
 * @author User
 */
public class CustomComboModel {

    private Statement stmt;
    private String sql;
    private ResultSet rs;
    private DatabaseConnectionModel object;
    LoggedInUserClass user = LoggedInUserClass.getInstance();
    public Integer roleId;
    Alert alert = new Alert(Alert.AlertType.INFORMATION);

    public void getObject() throws SQLException {
        object = DatabaseConnectionModel.getInstance();
    }

    /**
     * @param tableName
     * @return
     * @throws SQLException
     */
    public ObservableList<CustomComboClass> FetchData(String tableName) throws SQLException {
        ObservableList<CustomComboClass> customCombo = FXCollections.observableArrayList();
        getObject();
        stmt = object.connection.createStatement();
        sql = "select id,description from " + tableName;
        rs = stmt.executeQuery(sql);
        while (rs.next()) {
            customCombo.add(new CustomComboClass(rs.getInt("id"), rs.getString("description")));
        }
        return customCombo;
    }
    
    public ObservableList<CustomComboClass> FetchUserFilterData(Integer roleID) throws SQLException {
        ObservableList<CustomComboClass> customCombo = FXCollections.observableArrayList();
        getObject();
        stmt = object.connection.createStatement();
        sql = "select nvl(a.SURNAME,a.username) || ' ' || a.FIRSTNAME description, a.id from pm_users a where a.role_id = "+ roleID;
        rs = stmt.executeQuery(sql);
        while (rs.next()) {
            customCombo.add(new CustomComboClass(rs.getInt("id"), rs.getString("description")));
        }
        return customCombo;
    }

}
