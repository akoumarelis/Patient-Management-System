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
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author Chris
 */
public class ParametricsModel {

    LoggedInUserClass user = LoggedInUserClass.getInstance();

    private Statement stmt;
    private ResultSet rs;
    private String sql;
    private DatabaseConnectionModel object;

    private StringProperty id = new SimpleStringProperty();
    private StringProperty description = new SimpleStringProperty();
    private StringProperty created = new SimpleStringProperty();
    private StringProperty createdby = new SimpleStringProperty();
    private StringProperty updated = new SimpleStringProperty();
    private StringProperty updatedby = new SimpleStringProperty();

    /**
     *
     * @return
     */
    public StringProperty descriptionProperty() {
        return description;
    }

    /**
     *
     * @return
     */
    public StringProperty idProperty() {
        return id;
    }

    /**
     *
     * @return
     */
    public StringProperty createdProperty() {
        return created;
    }

    /**
     *
     * @return
     */
    public StringProperty createdbyProperty() {
        return createdby;
    }

    /**
     *
     * @return
     */
    public StringProperty updatedProperty() {
        return updated;
    }

    /**
     *
     * @return
     */
    public StringProperty updatedbyProperty() {
        return updatedby;
    }

    /**
     *
     * @param tableName
     * @return
     */
    public ResultSet getDesc(String tableName) {
        try {
            object = DatabaseConnectionModel.getInstance();
            stmt = object.connection.createStatement();
            sql = "select a.id, a.description, b.SURNAME || ' ' || b.FIRSTNAME createdBy, c.SURNAME || ' ' || c.FIRSTNAME updatedby, a.created,"
                    + " a.updated from " + tableName + " a , pm_users b, pm_users c where a.created_by = b.id and a.updated_by = c.id";
            rs = stmt.executeQuery(sql);
        } catch (SQLException ex) {
            Logger.getLogger(TestsModel.class.getName()).log(Level.SEVERE, null, ex);
        }
        return rs;
    }

    /**
     *
     * @param tableName
     * @param desc
     */
    public void createComponent(String tableName, String desc) {
        try {
            object = DatabaseConnectionModel.getInstance();
            stmt = object.connection.createStatement();
            sql = "insert into " + tableName + " (description,created_by,updated_by) values ('" + desc + "'," + user.getId() + "," + user.getId() + ")";
            rs = stmt.executeQuery(sql);
        } catch (SQLException ex) {
            Logger.getLogger(TestsModel.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    /**
     *
     * @param tableName
     * @param desc
     * @param id
     */
    public void editComponent(String tableName, String desc, Integer id) {
        try {
            object = DatabaseConnectionModel.getInstance();
            stmt = object.connection.createStatement();
            sql = "update " + tableName + " set description = '" + desc + "' , updated_by = " + user.getId() + "  where id = " + id;
            rs = stmt.executeQuery(sql);
        } catch (SQLException ex) {
            Logger.getLogger(TestsModel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     *
     * @param tableName
     * @param id
     */
    public void deleteComponent(String tableName, Integer id) {
        try {
            object = DatabaseConnectionModel.getInstance();
            stmt = object.connection.createStatement();
            sql = "delete from " + tableName + " where id = " + id;
            rs = stmt.executeQuery(sql);
        } catch (SQLException ex) {
            Logger.getLogger(TestsModel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public ParametricsModel() {
    }
;
}
