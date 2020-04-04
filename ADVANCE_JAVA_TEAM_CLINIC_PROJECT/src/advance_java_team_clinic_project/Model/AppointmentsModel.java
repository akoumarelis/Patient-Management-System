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
import javafx.scene.control.Alert;

/**
 *
 * @author Tasos
 */
public class AppointmentsModel {

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
     * Fetches Data from Database.
     *
     * @param roleID
     * @param userID
     * @return
     * @throws SQLException
     */
    public ResultSet fetchBasicInfoData(Integer roleID, Integer userID, Integer doctorID, Integer patientID, String createdFrom, String createdTo, String appDate, Integer appComboID) throws SQLException {
        getObject();
        stmt = object.connection.createStatement();
        sql = " select a.id, "
                + "    to_char(a.app_date,'dd/mm/yyyy') app_date, "
                + " a.app_hour, "
                + "    a.comments, "
                + "    a.app_code, "
                + "    to_char(a.created,'dd/mm/yyyy') created, "
                + "    to_char(a.updated,'dd/mm/yyyy') updated, "
                + "    b.Surname || ' ' || b.firstname patient, "
                + "    c.Surname || ' ' || c.firstname doctor, "
                + "    d.Surname || ' ' || d.firstname updated_by, "
                + "    e.Surname || ' ' || e.firstname created_by "
                + " from pm_appointment_info a, pm_users b, pm_users c, pm_users d, pm_users e "
                + " where a.patient_id = b.id "
                + "        and a.doctor_id = c.id(+) "
                + "        and a.updated_by = d.id "
                + "        and a.created_by = e.id "
                + " and nvl(a.doctor_id,-1) = nvl(" + doctorID + ",nvl(a.doctor_id,-1)) "
                + " and a.patient_id = nvl(" + patientID + ",a.patient_id) "
                + " and a.created between nvl(to_date('" + createdFrom + "','DD/MM/YYYY'),to_Date('01/01/1900','MM/DD/YYYY')) and nvl(to_date('" + createdTo + "','DD/MM/YYYY')+1,to_Date('01/01/2100','MM/DD/YYYY')) "
                // + "/* and nvl(a.app_date,to_date('01/01/1990','DD/MM/YYYY')) = nvl(to_date('" + appDate + "','DD/MM/YYYY'),nvl(a.app_date,to_date('01/01/1990','DD/MM/YYYY'))) */ "
                + " and (('" + appDate + "' = '01/01/1900' ) or (to_date('" + appDate + "','DD/MM/YYYY') != to_Date('01/01/1900','DD/MM/YYYY') and a.app_date = to_date('" + appDate + "','DD/MM/YYYY') ))"
                + " and ((" + appComboID + " = 1) or (" + appComboID + " = 2 and app_date is null)) "
                + "        and " + roleID + " = 1 " // Admin
                + " union "
                + " select a.id, "
                + "    to_char(a.app_date,'dd/mm/yyyy') app_date, "
                + " a.app_hour, "
                + "    a.comments, "
                + "    a.app_code, "
                + "    to_char(a.created,'dd/mm/yyyy') created, "
                + "    to_char(a.updated,'dd/mm/yyyy') updated, "
                + "    b.Surname || ' ' || b.firstname patient, "
                + "    c.Surname || ' ' || c.firstname doctor, "
                + "    d.Surname || ' ' || d.firstname updated_by, "
                + "    e.Surname || ' ' || e.firstname created_by "
                + " from pm_appointment_info a, pm_users b, pm_users c, pm_users d, pm_users e "
                + " where a.patient_id = b.id "
                + "        and a.doctor_id = c.id "
                + "        and a.updated_by = d.id "
                + "        and a.created_by = e.id "
                + " and nvl(a.doctor_id,-1) = nvl(" + doctorID + ",nvl(a.doctor_id,-1)) "
                + " and a.patient_id = nvl(" + patientID + ",a.patient_id) "
                + " and a.created between nvl(to_date('" + createdFrom + "','DD/MM/YYYY'),to_Date('01/01/1900','MM/DD/YYYY')) and nvl(to_date('" + createdTo + "','DD/MM/YYYY')+1,to_Date('01/01/2100','MM/DD/YYYY')) "
                // + "/* and nvl(a.app_date,to_date('01/01/1990','DD/MM/YYYY')) = nvl(to_date('" + appDate + "','DD/MM/YYYY'),nvl(a.app_date,to_date('01/01/1990','DD/MM/YYYY'))) */ "
                + " and (('" + appDate + "' = '01/01/1900' ) or (to_date('" + appDate + "','DD/MM/YYYY') != to_Date('01/01/1900','DD/MM/YYYY') and a.app_date = to_date('" + appDate + "','DD/MM/YYYY') ))"
                + " and ((" + appComboID + " = 1) or (" + appComboID + " = 2 and app_date is null)) "
                + "       and a.doctor_id = " + userID
                + "       and " + roleID + " = 2 " // Doctor
                + " union "
                + " select a.id, "
                + "    to_char(a.app_date,'dd/mm/yyyy') app_date, "
                + " a.app_hour, "
                + "    a.comments, "
                + "    a.app_code, "
                + "    to_char(a.created,'dd/mm/yyyy') created, "
                + "    to_char(a.updated,'dd/mm/yyyy') updated, "
                + "    b.Surname || ' ' || b.firstname patient, "
                + "    c.Surname || ' ' || c.firstname doctor, "
                + "    d.Surname || ' ' || d.firstname updated_by, "
                + "    e.Surname || ' ' || e.firstname created_by "
                + " from pm_appointment_info a, pm_users b, pm_users c, pm_users d, pm_users e "
                + " where a.patient_id = b.id "
                + "        and a.doctor_id = c.id(+) "
                + "        and a.updated_by = d.id "
                + "        and a.created_by = e.id "
                + "       and a.patient_id = " + userID
                + " and nvl(a.doctor_id,-1) = nvl(" + doctorID + ",nvl(a.doctor_id,-1)) "
                + " and a.patient_id = nvl(" + patientID + ",a.patient_id) "
                + " and a.created between nvl(to_date('" + createdFrom + "','DD/MM/YYYY'),to_Date('01/01/1900','MM/DD/YYYY')) and nvl(to_date('" + createdTo + "','DD/MM/YYYY')+1,to_Date('01/01/2100','MM/DD/YYYY')) "
                // + "/* and nvl(a.app_date,to_date('01/01/1990','DD/MM/YYYY')) = nvl(to_date('" + appDate + "','DD/MM/YYYY'),nvl(a.app_date,to_date('01/01/1990','DD/MM/YYYY'))) */ "
                + " and (('" + appDate + "' = '01/01/1900' ) or (to_date('" + appDate + "','DD/MM/YYYY') != to_Date('01/01/1900','DD/MM/YYYY') and a.app_date = to_date('" + appDate + "','DD/MM/YYYY') ))"
                + " and ((" + appComboID + " = 1) or (" + appComboID + " = 2 and app_date is null)) "
                + "       and " + roleID + " = 3 " // Patient
                + " union "
                + " select a.id, "
                + "    to_char(a.app_date,'dd/mm/yyyy') app_date, "
                + " a.app_hour, "
                + "    a.comments, "
                + "    a.app_code, "
                + "    to_char(a.created,'dd/mm/yyyy') created, "
                + "    to_char(a.updated,'dd/mm/yyyy') updated, "
                + "    b.Surname || ' ' || b.firstname patient, "
                + "    c.Surname || ' ' || c.firstname doctor, "
                + "    d.Surname || ' ' || d.firstname updated_by, "
                + "    e.Surname || ' ' || e.firstname created_by "
                + " from pm_appointment_info a, pm_users b, pm_users c, pm_users d, pm_users e "
                + " where a.patient_id = b.id "
                + "       and a.doctor_id = c.id(+) "
                + "       and a.updated_by = d.id "
                + "       and a.created_by = e.id "
                + " and nvl(a.doctor_id,-1) = nvl(" + doctorID + ",nvl(a.doctor_id,-1)) "
                + " and a.patient_id = nvl(" + patientID + ",a.patient_id) "
                + " and a.created between nvl(to_date('" + createdFrom + "','DD/MM/YYYY'),to_Date('01/01/1900','MM/DD/YYYY')) and nvl(to_date('" + createdTo + "','DD/MM/YYYY')+1,to_Date('01/01/2100','MM/DD/YYYY')) "
                // + "/* and nvl(a.app_date,to_date('01/01/1990','DD/MM/YYYY')) = nvl(to_date('" + appDate + "','DD/MM/YYYY'),nvl(a.app_date,to_date('01/01/1990','DD/MM/YYYY'))) */ "
                + " and (('" + appDate + "' = '01/01/1900' ) or (to_date('" + appDate + "','DD/MM/YYYY') != to_Date('01/01/1900','DD/MM/YYYY') and a.app_date = to_date('" + appDate + "','DD/MM/YYYY') ))"
                + " and ((" + appComboID + " = 1) or (" + appComboID + " = 2 and app_date is null)) "
                + "       and " + roleID + " = 4"; // Reception
        rs = stmt.executeQuery(sql);
        return rs;
    }

    public ResultSet fetchBasicInfoData(Integer appID) throws SQLException {
        getObject();
        stmt = object.connection.createStatement();
        System.out.println("APPID: " + appID);
        sql = "select a.id, "
                + " to_char(a.app_date,'dd/mm/yyyy') app_date, "
                + " a.comments, "
                + " a.app_hour, " 
                + " a.app_code, "
                + " to_char(a.created,'dd/mm/yyyy') created, "
                + " to_char(a.updated,'dd/mm/yyyy') updated, "
                + " b.Surname || ' ' || b.firstname patient, "
                + " c.Surname || ' ' || c.firstname doctor, "
                + " d.Surname || ' ' || d.firstname updated_by, "
                + " e.Surname || ' ' || e.firstname created_by, "
                + " nvl(f.id,-1) diagnosis, "
                + " a.doctor_id doctor_id "
                + " from pm_appointment_info a, pm_users b, pm_users c, pm_users d, pm_users e , pm_diagnosis f "
                + " where a.patient_id = b.id "
                + " and a.doctor_id = c.id(+) "
                + " and a.updated_by = d.id "
                + " and a.created_by = e.id "
                + " and a.id = f.app_info_id(+) "
                + " and a.id = " + appID;
        rs = stmt.executeQuery(sql);
        return rs;
    }
    
    public boolean updateAppointmentData(String ID, String appDate, Integer doctorID, String hour, String comments) {
        try {
            getObject();
            stmt = object.connection.createStatement();
            sql = "update pm_appointment_info set "
                    + " doctor_id = " + doctorID + " , "
                    + " app_hour = '" + hour+ "' , "
                    + " comments = '" + comments + "' ,"
                    + " app_date = to_date('" + appDate + "','dd/mm/yyyy'), "
                    + " updated_by = " + user.getId() 
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
     * @param appID
     * @throws SQLException 
     */
    public boolean deleteAppointment(Integer appID) throws SQLException{
        try {
            getObject();
            stmt = object.connection.createStatement();
            sql = "delete from pm_appointment_info where id =" + appID;
            rs = stmt.executeQuery(sql);
        }catch (SQLException ex) {
            Logger.getLogger(AppointmentsModel.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        return true;
    }
    
    /**
     *
     * @param comments
     */
    public void makeAppointment(String comments) {

        try {
            getObject();
            stmt = object.connection.createStatement();
            sql = "insert into PM_APPOINTMENT_INFO (patient_id,comments,created_by,updated_by) values (" + user.getId() + " , '" + comments + "' , " + user.getId() + " , " + user.getId() + " )";
            rs = stmt.executeQuery(sql);
        } catch (SQLException ex) {
            Logger.getLogger(AppointmentsModel.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
