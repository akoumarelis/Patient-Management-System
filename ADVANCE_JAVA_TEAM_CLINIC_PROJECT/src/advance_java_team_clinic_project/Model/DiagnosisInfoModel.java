/*
 *  Project for TEI OF CRETE lesson
 *  Plan Driven and Agile Programming
 *  TP4129 - TP4187 - TP4145
 */
package advance_java_team_clinic_project.Model;

import advance_java_team_clinic_project.classes.LoggedInUserClass;
import advance_java_team_clinic_project.Model.DAO.DiagnosisInfoDao;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.Alert;
import javafx.stage.StageStyle;

/**
 *
 * @author Beast
 */
public class DiagnosisInfoModel implements DiagnosisInfoDao {

    private Statement stmt;
    private String sql;
    private ResultSet rs;
    private Integer ak;
    private LoggedInUserClass user = LoggedInUserClass.getInstance();
    private Alert alert = new Alert(Alert.AlertType.INFORMATION);
    private DatabaseConnectionModel object;

    /**
     * Get the database connection
     */
    public void getObject() {
        try {
            object = DatabaseConnectionModel.getInstance();
        } catch (SQLException ex) {
            Logger.getLogger(InsuranceDetailsModel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     *
     * @param diag_id
     * @return
     *
     * Query to fetch the diagnose info data
     */
    @Override
    public ResultSet fetchDiagnoseInfoData(Integer diag_id) {
        try {
            getObject();
            stmt = object.connection.createStatement();
            sql = " select a.ID, "
                    + " a.APP_INFO_ID, "
                    + " a.COMMENTS, "
                    + " a.MEDS, "
                    + " decode(a.PATIENT_TYPE,0,'Outpatient','Inpatient') patient_type, "
                    + " to_char(a.CREATED,'MM/DD/YYYY') created, "
                    + " b.SURNAME || ' ' || b.FIRSTNAME created_by, "
                    + " to_char(a.UPDATED,'MM/DD/YYYY') updated, "
                    + " c.SURNAME || ' ' || c.FIRSTNAME updated_by, "
                    + " nvl(ad.id,-1) admission_id, "
                    + " pat.SURNAME || ' ' || pat.FIRSTNAME patient, "
                    + " doc.SURNAME || ' ' || doc.FIRSTNAME doctor "
                    + " from pm_diagnosis a, pm_users b, pm_users c, pm_addmissions ad, pm_appointment_info ap, pm_users doc, pm_users pat  "
                    + " where a.created_by = b.id "
                    + " and a.updated_by = c.id "
                    + " and ad.diag_id(+) = a.id "
                    + " and a.app_info_id = ap.id "
                    + " and ap.patient_id = pat.id "
                    + " and ap.doctor_id = doc.id(+) "
                    + " and  a.id = " + diag_id;
            rs = stmt.executeQuery(sql);
        } catch (SQLException ex) {
            Logger.getLogger(InsuranceDetailsModel.class.getName()).log(Level.SEVERE, null, ex);
        }
        return rs;
    }
    
    

    /**
     *
     * @param app_info_id
     * @param lComments
     * @param lMeds
     *
     * Function to create diagnose info details
     */
    @Override
    public void createDiagnoseDetails(Integer app_info_id, String lComments, String lMeds) {
        try {
            getObject();
            stmt = object.connection.createStatement();
            sql = "insert into pm_diagnosis (comments,meds,created_by,updated_by,APP_INFO_ID) values ('" + lComments + "','" + lMeds + "'," + user.getId() + "," + user.getId() + "," + app_info_id + ")";
            rs = stmt.executeQuery(sql);
            alert.setHeaderText(null);
            alert.initStyle(StageStyle.UTILITY);
            alert.setTitle("Success");
            alert.setContentText("Diagnose created!");
            alert.showAndWait();
        } catch (SQLException ex) {
            Logger.getLogger(DiagnosisInfoModel.class.getName()).log(Level.SEVERE, null, ex);
            alert.setHeaderText(null);
            alert.initStyle(StageStyle.UTILITY);
            alert.setTitle("Failed");
            alert.setContentText("Diagnose failed creation!");
            alert.showAndWait();
        }
    }

    /**
     *
     * @param diag_id
     * @param lComments
     * @param lMeds
     *
     * Function to update the diagnose info details.
     */
    @Override
    public void updateDiagnoseDetails(Integer diag_id, String lComments, String lMeds) {
        try {
            getObject();
            stmt = object.connection.createStatement();
            sql = "update pm_diagnosis set comments = '" + lComments + "' , meds = '" + lMeds + "' , updated_by = " + user.getId() + " where id = " + diag_id;
            rs = stmt.executeQuery(sql);
            alert.setHeaderText(null);
            alert.initStyle(StageStyle.UTILITY);
            alert.setTitle("Success!");
            alert.setContentText("Diagnose Updated!");
            alert.showAndWait();
        } catch (SQLException ex) {
            Logger.getLogger(DiagnosisInfoModel.class.getName()).log(Level.SEVERE, null, ex);
            alert.setHeaderText(null);
            alert.initStyle(StageStyle.UTILITY);
            alert.setTitle("Failure!");
            alert.setContentText("Diagnose failed to update!");
            alert.showAndWait();
        }
    }

    /**
     *
     * @param diag_id
     * @return
     *
     * Check if an admission exist for this diagnose
     */
    @Override
    public Integer getAdmissionId(Integer diag_id) {
        ak = -1;
        try {
            getObject();
            stmt = object.connection.createStatement();
            sql = "select id from pm_addmissions where diag_id = " + diag_id;
            rs = stmt.executeQuery(sql);
        } catch (SQLException ex) {
            Logger.getLogger(DiagnosisInfoModel.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            while (rs.next()) {
                ak = rs.getInt("id");
            }
        } catch (SQLException ex) {
            Logger.getLogger(DiagnosisInfoModel.class.getName()).log(Level.SEVERE, null, ex);
        }

        return ak;
    }

    @Override
    public ResultSet getDiagId(String app_id) {
       try {
            getObject();
            stmt = object.connection.createStatement();
            sql = "select id from pm_diagnosis where app_info_id = " + app_id;
            rs = stmt.executeQuery(sql);
        } catch (SQLException ex) {
            Logger.getLogger(DiagnosisInfoModel.class.getName()).log(Level.SEVERE, null, ex);
        }
       return rs;
    }

}
