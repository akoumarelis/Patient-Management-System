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
public class TestsModel {

    LoggedInUserClass user = LoggedInUserClass.getInstance();
    private Statement stmt;
    private ResultSet rs;
    private String sql;
    private DatabaseConnectionModel object;

    private StringProperty id = new SimpleStringProperty();
    private StringProperty diag_id = new SimpleStringProperty();
    private StringProperty description = new SimpleStringProperty();
    private StringProperty is_completed = new SimpleStringProperty();
    private StringProperty cost = new SimpleStringProperty();
    private StringProperty is_paid = new SimpleStringProperty();
    private StringProperty status_id = new SimpleStringProperty();
    private StringProperty results = new SimpleStringProperty();
    private StringProperty created = new SimpleStringProperty();
    private StringProperty created_by = new SimpleStringProperty();
    private StringProperty updated = new SimpleStringProperty();
    private StringProperty updated_by = new SimpleStringProperty();
    private StringProperty patient = new SimpleStringProperty();
    private StringProperty doctor = new SimpleStringProperty();
    private LoggedInUserClass loggedInUser = LoggedInUserClass.getInstance();

    
    
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
    public StringProperty diag_idProperty() {
        return diag_id;
    }

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
    public StringProperty is_completedProperty() {
        return is_completed;
    }

    /**
     *
     * @return
     */
    public StringProperty costProperty() {
        return cost;
    }

    /**
     *
     * @return
     */
    public StringProperty is_paidProperty() {
        return is_paid;
    }

    /**
     *
     * @return
     */
    public StringProperty status_idProperty() {
        return status_id;
    }

    /**
     *
     * @return
     */
    public StringProperty resultsProperty() {
        return results;
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
    public StringProperty created_byProperty() {
        return created_by;
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
    public StringProperty updated_byProperty() {
        return updated_by;
    }

    /**
     *
     * @return
     */
    public StringProperty patientProperty() {
        return patient;
    }

    /**
     *
     * @return
     */
    public StringProperty doctorProperty() {
        return doctor;
    }

    public TestsModel() {
    }
    
     public void getObject() {
        try {
            object = DatabaseConnectionModel.getInstance();
        } catch (SQLException ex) {
            Logger.getLogger(InsuranceDetailsModel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     *
     * @param diagID
     * @return
     */
    public ResultSet getTestByDiagID(Integer diagID, Integer doctorID, Integer patientID, String fromDate, String toDate, Integer isCompleted, Integer isPaid) {
        try {
            object = DatabaseConnectionModel.getInstance();
            stmt = object.connection.createStatement();
            sql = "select a.id id, "
                    + " diag.id diag, "
                    + " a.description, "
                    + " a.cost, "
                    + " a.results, "
                    + " decode(a.is_paid,1,'Yes','No') Paid, "
                    + " decode(a.status_id,1,'Completed','In progresss') status,"
                    + " decode(a.is_completed,1,'Yes','No') is_completed, "
                    + "f.SURNAME || ' ' || f.FIRSTNAME doctor, "
                    + "a.created, "
                    + " cr.SURNAME || ' ' || cr.FIRSTNAME created_by, "
                    + " a.updated, "
                    + " up.SURNAME || ' ' || up.FIRSTNAME updated_by ,"
                    + " pat.SURNAME || ' ' || pat.FIRSTNAME patient"
                    + " from pm_diag_tests a, pm_diagnosis diag , pm_appointment_info ap,pm_users f , pm_users up, pm_users cr, pm_users pat "
                    + " where ap.patient_id = pat.id "
                    + " and a.updated_by = up.id "
                    + "and a.created_by = cr.id "
                    + " and a.diag_id =    " + diagID
                    + " and a.diag_id = diag.id  "
                    + " and diag.APP_INFO_ID = ap.id   "
                    + " and ap.doctor_id = f.id  "
                    + " and nvl(ap.doctor_id,-1) = nvl("+doctorID+",nvl(ap.doctor_id,-1)) "
 + " and ap.patient_id = nvl("+patientID+",ap.patient_id) "
 + " and a.created between nvl(to_date('" + fromDate + "','DD/MM/YYYY'),to_Date('01/01/1900','MM/DD/YYYY')) and nvl(to_date('" + toDate + "','DD/MM/YYYY'),to_Date('01/01/2100','MM/DD/YYYY')) "
                    + " and a.is_completed = nvl("+ isCompleted + " , a.is_completed) "
                    + " and a.is_paid = nvl(" + isPaid + " , a.is_paid ) "
                    + " and   " + diagID + "  != -1  "
                    + "union  "
                  + "select a.id id,  "
                    + " diag.id diag,  "
                    + " a.description,   "
                    + " a.cost,   "
                    + " a.results,   "
                    + " decode(a.is_paid,1,'Yes','No') Paid,   "
                    + " decode(a.status_id,1,'Completed','In progresss') status,   "
                    + " decode(a.is_completed,1,'Yes','No') is_completed,                  "
                    + " f.SURNAME || ' ' || f.FIRSTNAME doctor, "
                    + " a.created, "
                    + " cr.SURNAME || ' ' || cr.FIRSTNAME created_by, "
                    + " a.updated, "
                    + " up.SURNAME || ' ' || up.FIRSTNAME updated_by ,"
                    + " pat.SURNAME || ' ' || pat.FIRSTNAME patient "
                    + " from pm_diag_tests a, pm_diagnosis diag , pm_appointment_info ap,pm_users f, pm_users up, pm_users cr, pm_users pat"
                    + " where ap.patient_id = pat.id "
                    + " and a.updated_by = up.id "
                    + " and a.created_by = cr.id "
                    + " and  diag.APP_INFO_ID = ap.id "
                    + " and a.diag_id = diag.id "
                    + " and ap.doctor_id = f.id(+) "
                     + " and nvl(ap.doctor_id,-1) = nvl("+doctorID+",nvl(ap.doctor_id,-1)) "
 + " and ap.patient_id = nvl("+patientID+",ap.patient_id) "
 + " and a.created between nvl(to_date('" + fromDate + "','DD/MM/YYYY'),to_Date('01/01/1900','MM/DD/YYYY')) and nvl(to_date('" + toDate + "','DD/MM/YYYY'),to_Date('01/01/2100','MM/DD/YYYY')) "
                   + " and a.is_completed = nvl("+ isCompleted + " , a.is_completed) "
                    + " and a.is_paid = nvl(" + isPaid + " , a.is_paid ) "
                    + " and   " + user.getRoleID() + " = 1 "
                    + " and   " + diagID + "    = -1 "
                    + "union "
                  + "select a.id id,  "
                    + " diag.id diag, "
                    + " a.description,  "
                    + " a.cost,  "
                    + " a.results,  "
                    + " decode(a.is_paid,1,'Yes','No') Paid, "
                    + " decode(a.status_id,1,'Completed','In progresss') status, "
                    + " decode(a.is_completed,1,'Yes','No') is_completed, "
                    + " f.SURNAME || ' ' || f.FIRSTNAME doctor , "
                    + " a.created, "
                    + " cr.SURNAME || ' ' || cr.FIRSTNAME created_by, "
                    + " a.updated, "
                    + " up.SURNAME || ' ' || up.FIRSTNAME updated_by ,"
                    + " pat.SURNAME || ' ' || pat.FIRSTNAME patient"
                    + " from pm_diag_tests a, pm_diagnosis diag , pm_appointment_info ap,pm_users f, pm_users up, pm_users cr, pm_users pat "
                    + " where ap.patient_id = pat.id "
                    + " and a.updated_by = up.id"
                    + " and a.created_by = cr.id "
                    + " and diag.APP_INFO_ID = ap.id "
                    + " and a.diag_id = diag.id "
                    + " and ap.doctor_id = f.id "
                     + " and nvl(ap.doctor_id,-1) = nvl("+doctorID+",nvl(ap.doctor_id,-1)) "
 + " and ap.patient_id = nvl("+patientID+",ap.patient_id) "
 + " and a.created between nvl(to_date('" + fromDate + "','DD/MM/YYYY'),to_Date('01/01/1900','MM/DD/YYYY')) and nvl(to_date('" + toDate + "','DD/MM/YYYY'),to_Date('01/01/2100','MM/DD/YYYY')) "
                    + " and a.is_completed = nvl("+ isCompleted + " , a.is_completed) "
                    + " and a.is_paid = nvl(" + isPaid + " , a.is_paid ) "
                    + " and ap.doctor_id =    " + user.getId()
                    + " and   " + user.getRoleID() + " = 2 "
                    + " and   " + diagID + "    = -1  "
                    + "union  "
                 + "select a.id id, "
                    + " diag.id diag, "
                    + " a.description, "
                    + " a.cost, "
                    + " a.results, "
                    + " decode(a.is_paid,1,'Yes','No') Paid, "
                    + " decode(a.status_id,1,'Completed','In progresss') status, "
                    + " decode(a.is_completed,1,'Yes','No') is_completed, "
                    + " f.SURNAME || ' ' || f.FIRSTNAME doctor, "
                    + " a.created, "
                    + " cr.SURNAME || ' ' || cr.FIRSTNAME created_by, "
                    + " a.updated, "
                    + " up.SURNAME || ' ' || up.FIRSTNAME updated_by , "
                    + " pat.SURNAME || ' ' || pat.FIRSTNAME patient"
                    + " from pm_diag_tests a, pm_diagnosis diag , pm_appointment_info ap,pm_users f, pm_users up, pm_users cr, pm_users pat "
                    + " where ap.patient_id = pat.id "
                    + " and a.updated_by = up.id "
                    + " and a.created_by = cr.id "
                    + " and diag.APP_INFO_ID = ap.id "
                    + " and a.diag_id = diag.id "
                    + " and ap.doctor_id = f.id(+) "
                     + " and nvl(ap.doctor_id,-1) = nvl("+doctorID+",nvl(ap.doctor_id,-1)) "
 + " and ap.patient_id = nvl("+patientID+",ap.patient_id) "
 + " and a.created between nvl(to_date('" + fromDate + "','DD/MM/YYYY'),to_Date('01/01/1900','MM/DD/YYYY')) and nvl(to_date('" + toDate + "','DD/MM/YYYY'),to_Date('01/01/2100','MM/DD/YYYY')) "
                   + " and a.is_completed = nvl("+ isCompleted + " , a.is_completed) "
                    + " and a.is_paid = nvl(" + isPaid + " , a.is_paid ) "
                    + " and ap.patient_id =   " + user.getId()
                    + " and  " + user.getRoleID() + "  = 3 "
                    + " and   " + diagID + "    = -1 "
                    + "union "
                 + "select a.id id, "
                    + " diag.id diag, "
                    + " a.description,  "
                    + " a.cost, "
                    + " a.results, "
                    + " decode(a.is_paid,1,'Yes','No') Paid, "
                    + " decode(a.status_id,1,'Completed','In progresss') status, "
                    + " decode(a.is_completed,1,'Yes','No') is_completed, "
                    + " f.SURNAME || ' ' || f.FIRSTNAME doctor, "
                    + " a.created, "
                    + " cr.SURNAME || ' ' || cr.FIRSTNAME created_by, "
                    + " a.updated, "
                    + " up.SURNAME || ' ' || up.FIRSTNAME updated_by ,"
                    + " pat.SURNAME || ' ' || pat.FIRSTNAME patient "
                    + " from pm_diag_tests a, pm_diagnosis diag , pm_appointment_info ap,pm_users f, pm_users up, pm_users cr, pm_users pat "
                    + " where ap.patient_id = pat.id "
                    + " and a.updated_by = up.id "
                    + " and a.created_by = cr.id "
                    + " and diag.APP_INFO_ID = ap.id  "
                    + " and a.diag_id = diag.id  "
                    + " and ap.doctor_id = f.id(+) "
                     + " and nvl(ap.doctor_id,-1) = nvl("+doctorID+",nvl(ap.doctor_id,-1)) "
 + " and ap.patient_id = nvl("+patientID+",ap.patient_id) "
 + " and a.created between nvl(to_date('" + fromDate + "','DD/MM/YYYY'),to_Date('01/01/1900','MM/DD/YYYY')) and nvl(to_date('" + toDate + "','DD/MM/YYYY'),to_Date('01/01/2100','MM/DD/YYYY')) "
                   + " and a.is_completed = nvl("+ isCompleted + " , a.is_completed) "
                    + " and a.is_paid = nvl(" + isPaid + " , a.is_paid ) "
                    + " and  " + user.getRoleID() + " = 4 "
                    + " and   " + diagID + "    = -1 "
                    + " union "
                + " select a.id id, "
                    + "diag.id diag, "
                    + "a.description, "
                    + "a.cost, "
                    + "a.results, "
                    + "decode(a.is_paid,1,'Yes','No') Paid, "
                    + "decode(a.status_id,1,'Completed','In progresss') status, "
                    + "decode(a.is_completed,1,'Yes','No') is_completed, "
                    + "f.SURNAME || ' ' || f.FIRSTNAME doctor, "
                    + " a.created, "
                    + " cr.SURNAME || ' ' || cr.FIRSTNAME created_by, "
                    + " a.updated, "
                    + " up.SURNAME || ' ' || up.FIRSTNAME updated_by ,"
                    + " pat.SURNAME || ' ' || pat.FIRSTNAME patient"
                    + " from pm_diag_tests a, pm_diagnosis diag , pm_appointment_info ap,pm_users f, pm_users up, pm_users cr, pm_users pat "
                    + " where ap.patient_id = pat.id "
                    + " and a.updated_by = up.id "
                    + " and a.created_by = cr.id "
                    + " and  diag.APP_INFO_ID = ap.id  "
                    + "and a.diag_id = diag.id "
                    + "and ap.doctor_id = f.id(+) "
                     + " and nvl(ap.doctor_id,-1) = nvl("+doctorID+",nvl(ap.doctor_id,-1)) "
 + " and ap.patient_id = nvl("+patientID+",ap.patient_id) "
 + " and a.created between nvl(to_date('" + fromDate + "','DD/MM/YYYY'),to_Date('01/01/1900','MM/DD/YYYY')) and nvl(to_date('" + toDate + "','DD/MM/YYYY'),to_Date('01/01/2100','MM/DD/YYYY')) "
                    + " and a.is_completed = nvl("+ isCompleted + " , a.is_completed) "
                    + " and a.is_paid = nvl(" + isPaid + " , a.is_paid ) "
                    + "and    " + user.getRoleID() + "  = 5 "
                    + "and   " + diagID + "    = -1";
            rs = stmt.executeQuery(sql);
        } catch (SQLException ex) {
            Logger.getLogger(TestsModel.class.getName()).log(Level.SEVERE, null, ex);
        }

        return rs;
    }

    /**
     *
     * @param testID
     * @return
     */
    public ResultSet getTestByID(Integer testID) {
        try {
            object = DatabaseConnectionModel.getInstance();
            stmt = object.connection.createStatement();
            //sql = "select * from pm_diag_tests where id= " + testID;
            sql = "select a.id id, "
                    + "a.diag_id, "
                    + "a.description, "
                    + "a.cost, "
                    + "a.results, "
                    + "decode(a.is_paid,1,'Yes','No') Paid, "
                    + "decode(a.is_completed,1,'Yes','No') is_completed, "
                    + "to_char(a.created,'MM/DD/YYYY') created, "
                    + "c.SURNAME || ' ' || c.FIRSTNAME createdby, "
                    + " to_char(a.UPDATED,'MM/DD/YYYY') updated, "
                    + "d.SURNAME || ' ' || d.FIRSTNAME updated_by, "
                    + "e.SURNAME || ' ' || e.FIRSTNAME patient, "
                    + "f.SURNAME || ' ' || f.FIRSTNAME doctor, "
                    + "a.paid_amount, "
                    + "a.is_paid isPaidValue "
                    + "from pm_diag_tests a, pm_users c, pm_users d, pm_diagnosis diag , pm_appointment_info ap, pm_users e, pm_users f "
                    + "where a.created_by = c.id "
                    + "  and a.updated_by = d.id "
                    + "  and a.diag_id = diag.id "
                    + "  and diag.APP_INFO_ID = ap.id "
                    + "  and ap.patient_id = e.id "
                    + "  and ap.doctor_id = f.id(+) "
                    + "  and a.id = " + testID;
            rs = stmt.executeQuery(sql);
        } catch (SQLException ex) {
            Logger.getLogger(TestsModel.class.getName()).log(Level.SEVERE, null, ex);
        }
        return rs;
    }
    
    /**
     *
     * @param diagID
     * @param desc
     * @return
     */
    public Integer createTest(Integer diagID,String desc){
        Integer tmpID=0;
        try {
            object = DatabaseConnectionModel.getInstance();
            stmt = object.connection.createStatement();
            sql= "select seq_pm_diag_tests.nextval id from dual";
            rs = stmt.executeQuery(sql);
            while(rs.next()){
                tmpID = rs.getInt("id");
            }
            sql = "insert into pm_diag_tests (id,diag_id,description,created_by,updated_by) values ("+tmpID+","+diagID+",'"+desc+"',"+loggedInUser.getId()+","+loggedInUser.getId()+")";
            rs = stmt.executeQuery(sql);
            return tmpID;
        } catch (SQLException ex) {
            Logger.getLogger(TestsModel.class.getName()).log(Level.SEVERE, null, ex);
        }
        return tmpID;
    }
    
    /**
     *
     * @param ID
     * @param desc
     * @param isCompleted
     * @param cost
     * @param results
     */
    public void updateTest(Integer ID, String desc, Integer isCompleted, Integer cost,String results){
        try {
            object = DatabaseConnectionModel.getInstance();
            stmt = object.connection.createStatement();
            sql = "update pm_diag_tests set description='"+desc+"',is_completed="+isCompleted+",results='"+results+"',updated_by="+loggedInUser.getId()+" where id="+ID;
            rs = stmt.executeQuery(sql);
        } catch (SQLException ex) {
            Logger.getLogger(TestsModel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public boolean deleteTest(Integer ID){
        try {
            
            stmt = object.connection.createStatement();
            sql = "delete from pm_diag_tests where id = " +ID;
            rs = stmt.executeQuery(sql);
        } catch (SQLException ex) {
            Logger.getLogger(TestsModel.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        return true;
    }
    
    public void updateIsPaid(Integer ID, Integer amount) {
        try {
            getObject();
            stmt = object.connection.createStatement();
            sql = "update pm_diag_tests set paid_amount = "+ amount +",updated_by="+loggedInUser.getId()+" where id = " + ID;
            rs = stmt.executeQuery(sql);
        } catch (SQLException ex) {
            Logger.getLogger(AdmissionModel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
}
