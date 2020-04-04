/*
 *  Project for TEI OF CRETE lesson
 *  Plan Driven and Agile Programming
 *  TP4129 - TP4187 - TP4145
 */
package advance_java_team_clinic_project.Model;

import advance_java_team_clinic_project.classes.LoggedInUserClass;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javafx.scene.control.Alert;
import javafx.stage.StageStyle;

/**
 *
 * @author Tasos
 */
public class AddressDetailsModel {

    private Statement stmt;
    private String sql, sql_users;
    private ResultSet rs;
    private DatabaseConnectionModel object;
    
    LoggedInUserClass loggedInUser = LoggedInUserClass.getInstance();

    public void getObject() throws SQLException {
        object = DatabaseConnectionModel.getInstance();
    }

    /**
     * Returns userID's information from data.
     *
     * @param address_id
     * @return
     * @throws SQLException
     */
    public ResultSet fetchAddressInfoData(Integer address_id) throws SQLException {
        stmt = object.connection.createStatement();
        sql = "select id, "
                + "address,"
                + "city, "
                + "county, "
                + "postal_code "
                + "from pm_address_details "
                + "where  id = " + address_id;
        rs = stmt.executeQuery(sql);
        return rs;
    }

    public void updateAddressDetails(Integer address_id, String address, String city, String county, int postal_code) throws SQLException {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        alert.initStyle(StageStyle.UTILITY);
        stmt = object.connection.createStatement();
        sql_users = "update pm_address_details set address=\'" + address + "\',city=\'" + city + "\',county=\'" + county + "\',postal_code=" + postal_code +  ",updated_by=" + loggedInUser.getId() + "where id =" + address_id;
        rs = stmt.executeQuery(sql_users);
        alert.setTitle("Update");
        alert.setContentText("Update submitted");
        alert.showAndWait();
    }
}
