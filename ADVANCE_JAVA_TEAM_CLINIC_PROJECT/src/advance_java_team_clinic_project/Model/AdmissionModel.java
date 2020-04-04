/*
 *  Project for TEI OF CRETE lesson
 *  Plan Driven and Agile Programming
 *  TP4129 - TP4187 - TP4145
 */
package advance_java_team_clinic_project.Model;

import advance_java_team_clinic_project.classes.LoggedInUserClass;
import advance_java_team_clinic_project.Model.DAO.AdmissionDao;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.Alert;

/**
 *
 * @author Chris
 */
public class AdmissionModel implements AdmissionDao{

    private Statement stmt;
    private String sql;
    private ResultSet rs;
    private Integer ak;
    private LoggedInUserClass user = LoggedInUserClass.getInstance();
    private Alert alert = new Alert(Alert.AlertType.INFORMATION);
    private DatabaseConnectionModel object;
    LoggedInUserClass loggedInUser = LoggedInUserClass.getInstance();

    /**
     * Get the database connection
     */
    @Override
    public void getObject() {
        try {
            object = DatabaseConnectionModel.getInstance();
        } catch (SQLException ex) {
            Logger.getLogger(InsuranceDetailsModel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     *
     * @param ID
     * @return
     */
    @Override
    public ResultSet fetchAdmissionData(Integer ID) {
        try {
            getObject();
            stmt = object.connection.createStatement();
            sql = "select a.id, a.diag_id, to_char(a.admission_date, 'yyyy/MM/dd') admission_date, nvl(to_char(a.discharge_date, 'yyyy/MM/dd'),'-1') discharge_date, "
                    + " a.cost_per_day, a.room, a.bed, b.SURNAME || ' ' || b.FIRSTNAME created_by,  "
                    + " to_char(a.CREATED,'dd/mm/yyyy') created ,  "
                    + " c.SURNAME || ' ' || c.FIRSTNAME updated_by,"
                    + " a.total_cost,  "
                    + " decode(a.is_paid, 1, 'Yes' , 0 , 'No', 'Uknown') is_paid, "
                    + " a.paid_amount ,"
                    + " a.is_paid is_paid_value, "
                    + " to_char(a.UPDATED,'dd/mm/yyyy') updated ,"
                    + " doc.SURNAME || ' ' || doc.FIRSTNAME doctor,"
                    + " pat.SURNAME || ' ' || pat.FIRSTNAME patient"
                    + " from pm_addmissions a, pm_users b, pm_users c, pm_diagnosis di, pm_appointment_info ap, pm_users doc, pm_users pat where "
                    + " a.id=   " + ID + "   and a.updated_by = c.id and a.created_by = b.id and a.diag_id = di.id and di.app_info_id = ap.id and ap.doctor_id = doc.id and ap.patient_id = pat.id ";
            rs = stmt.executeQuery(sql);
        } catch (SQLException ex) {
            Logger.getLogger(InsuranceDetailsModel.class.getName()).log(Level.SEVERE, null, ex);
        }
        return rs;
    }

    /**
     *
     * @param diagID
     * @param costPerDay
     * @param room
     * @param bed
     * @param ad_date
     * @return
     */
    @Override
    public boolean createAdmissionData(Integer diagID, Integer costPerDay, String room, String bed, String ad_date) {
        try {
            getObject();
            stmt = object.connection.createStatement();
            sql = "insert into pm_addmissions (diag_id, cost_per_day, room, bed,is_paid, admission_date,created_by, updated_by) "
                    + "values (" + diagID + "," + costPerDay + ",'" + room + "','" + bed + "',0,to_date('" + ad_date + "','dd/mm/yyyy'), " + user.getId() + ", " + user.getId() + ")";
            rs = stmt.executeQuery(sql);
        } catch (SQLException ex) {
            Logger.getLogger(AdmissionModel.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        return true;
    }

    /**
     *
     * @param ID
     * @param costPerDay
     * @param room
     * @param bed
     * @param ad_date
     * @param dis_date
     * @return
     */
    @Override
    public boolean updateAdmissionData(Integer ID, Integer costPerDay, String room, String bed, String ad_date, String dis_date) {
        try {
            getObject();
            stmt = object.connection.createStatement();
            sql = "update pm_addmissions set "
                    + " cost_per_day = " + costPerDay + " , "
                    + " room = '" + room + "', "
                    + " bed ='" + bed + "', "
                    + " admission_date = to_date('" + ad_date + "','dd/mm/yyyy'), "
                    + " discharge_date = to_date('" + dis_date + "','dd/mm/yyyy') "
                    + " where id = " + ID;
            rs = stmt.executeQuery(sql);
        } catch (SQLException ex) {
            Logger.getLogger(AdmissionModel.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        return true;
    }

    /**
     *
     * @param ID
     * @param amount
     */
    @Override
    public void updateIsPaid(Integer ID, Integer amount) {
        try {
            getObject();
            stmt = object.connection.createStatement();
            sql = "update pm_addmissions set paid_amount = "+ amount +",updated_by="+loggedInUser.getId()+" where id = " + ID;
            rs = stmt.executeQuery(sql);
        } catch (SQLException ex) {
            Logger.getLogger(AdmissionModel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
