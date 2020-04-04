/*
 *  Project for TEI OF CRETE lesson
 *  Plan Driven and Agile Programming
 *  TP4129 - TP4187 - TP4145
 */
package advance_java_team_clinic_project.Model;

import advance_java_team_clinic_project.classes.LoggedInUserClass;
import advance_java_team_clinic_project.Model.DAO.InsuranceDetailsDao;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.Alert;
import javafx.stage.StageStyle;

/**
 *
 * @author Tasos
 */
public class InsuranceDetailsModel implements InsuranceDetailsDao {

    private Statement stmt;
    private String sql, sql_contact;
    private ResultSet rs;
    private DatabaseConnectionModel object;
    private LoggedInUserClass user = LoggedInUserClass.getInstance();

    @Override
    public void getObject() {
        try {
            object = DatabaseConnectionModel.getInstance();
        } catch (SQLException ex) {
            Logger.getLogger(InsuranceDetailsModel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Returns userID's information from data.
     *
     * @param userId
     * @return
     */
    @Override
    public ResultSet fetchInsuranceInfoData(Integer userId) {
        try {
            getObject();
            stmt = object.connection.createStatement();
            sql = "select id, "
                    + "to_char(ins_expire_date,'dd-MM-yyyy') ins_expire_date,"
                    + "european, "
                    + "ekas, "
                    + "ins_comments, "
                    + "ins_comp_id "
                    + "from pm_patients_ins_info "
                    + "where user_id = " + userId;
            rs = stmt.executeQuery(sql);
        } catch (SQLException ex) {
            Logger.getLogger(InsuranceDetailsModel.class.getName()).log(Level.SEVERE, null, ex);
        }
        return rs;
    }
    
    @Override
    public void updateInsuranceDetails(Integer userId, String ins_expire_date, Integer european, Integer ekas, String ins_comments, Integer ins_comp_id) {
        try {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.initStyle(StageStyle.UTILITY);
            getObject();
            stmt = object.connection.createStatement();
            sql_contact = "update pm_patients_ins_info set ins_expire_date = to_date(\'" + ins_expire_date + "\','dd-mm-yyyy'),european=" + european + ",ekas=" + ekas + ",ins_comments=\'" + ins_comments + "\',ins_comp_id=" + ins_comp_id + ",updated_by=" + user.getId() + " where user_id =" + userId;
            rs = stmt.executeQuery(sql_contact);
            alert.setTitle("Update");
            alert.setContentText("Update submitted");
            alert.showAndWait();
        } catch (SQLException ex) {
            Logger.getLogger(InsuranceDetailsModel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
