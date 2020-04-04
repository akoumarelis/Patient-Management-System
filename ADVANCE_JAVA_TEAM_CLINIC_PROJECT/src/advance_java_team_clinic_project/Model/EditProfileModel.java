/*
 *  Project for TEI OF CRETE lesson
 *  Plan Driven and Agile Programming
 *  TP4129 - TP4187 - TP4145
 */
package advance_java_team_clinic_project.Model;

import advance_java_team_clinic_project.classes.LoggedInUserClass;
import advance_java_team_clinic_project.Controller.PasswordWindowController;
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
public class EditProfileModel {

    private Statement stmt;
    private String sql, sql_users, sql_user_details;
    private ResultSet rs;
    private DatabaseConnectionModel object;
    LoggedInUserClass user = LoggedInUserClass.getInstance();

    public void getObject() throws SQLException {
        object = DatabaseConnectionModel.getInstance();
    }

    /**
     * Returns userID's information from data.
     *
     * @param userId
     * @return
     * @throws SQLException
     */
    public ResultSet fetchBasicInfoData(Integer userId) throws SQLException {
        getObject();
        stmt = object.connection.createStatement();
        sql = "select a.id, "
                + "a.username,"
                + "a.role_id, "
                + "a.surname, "
                + "a.firstname, "
                + "a.address_id, "
                + "a.contact_id, "
                + "b.amka, "
                + "b.ama, "
                + "to_char(b.date_of_birth,'dd-MM-yyyy') date_of_birth, "
                + "b.fathers_name, "
                + "b.mothers_name, "
                + "b.gender_id, "
                + "b.eco_status_id, "
                + "b.global_code, "
                + "b.nationality_id, "
                + "b.profession, "
                + "b.place_of_birth, "
                + "b.member_id, "
                + "b.id basic_info_id "
                + "from pm_users a, pm_patients_basic_info b "
                + "where a.id = " + userId + " and a.id = b.user_id(+)";
        rs = stmt.executeQuery(sql);
        return rs;
    }

    /**
     * Returns all the users from the database.
     *
     * @return
     * @throws SQLException
     */
    public ResultSet fetchAllUsernames() throws SQLException {
        getObject();
        stmt = object.connection.createStatement();
        sql = "select username from pm_users";
        rs = stmt.executeQuery(sql);
        return rs;
    }

    /**
     * Updates information from the database.
     *
     * @param userId
     * @param lRole_Id
     * @param lSurname
     * @param lName
     * @param lAmka
     * @param lAma
     * @param lDate_of_birth
     * @param lFathers_name
     * @param lMothers_name
     * @param lGender_id
     * @param lEco_status_id
     * @param lNationality_id
     * @param lProffesion
     * @param lPlace_of_birth
     * @throws SQLException
     */
    public void updateBasicInfoData(Integer userId, Integer lRole_Id, String lSurname, String lName, String lAmka, String lAma, String lDate_of_birth, String lFathers_name, String lMothers_name,
            Integer lGender_id, Integer lEco_status_id, Integer lNationality_id, String lProffesion, String lPlace_of_birth/*, Integer lMember_id*/) throws SQLException {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        alert.initStyle(StageStyle.UTILITY);
        getObject();
        stmt = object.connection.createStatement();
        sql_users = "update pm_users set role_id=" + lRole_Id + ",surname=\'" + lSurname + "\',firstname=\'" + lName + "\',updated_by= " + user.getId() + " where id =" + userId;
        sql_user_details = "update pm_patients_basic_info set "
                + "amka=\'" + lAmka
                + "\',ama=\'" + lAma
                + "\',date_of_birth = to_date(\'" + lDate_of_birth + "\','dd-mm-yyyy')"
                + ",fathers_name=\'" + lFathers_name
                + "\',mothers_name=\'" + lMothers_name
                + "\',gender_id =" + lGender_id
                + ",eco_status_id =" + lEco_status_id
                + ",nationality_id=" + lNationality_id
                + ",profession=\'" + lProffesion
                + "\',place_of_birth=\'" + lPlace_of_birth
                + "\',updated_by= " + user.getId() + " where user_id =" + userId;
        rs = stmt.executeQuery(sql_users);
        rs = stmt.executeQuery(sql_user_details);
        alert.setTitle("Update");
        alert.setContentText("Update submitted");
        alert.showAndWait();
    }

    /**
     * Update userID's username.
     *
     * @param userId
     * @param userName
     */
    public void updateUsername(Integer userId, String userName) {
        try {
            getObject();
            stmt = object.connection.createStatement();
            sql = "update pm_users set username = \'" + userName + "\',updated_by= " + user.getId() + " where id = " + userId;
            rs = stmt.executeQuery(sql);
        } catch (SQLException ex) {
            Logger.getLogger(EditProfileModel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public ResultSet getPassword(Integer userId) {
        try {
            getObject();
            stmt = object.connection.createStatement();
            sql = "select password from pm_users where id= " + userId;
            rs = stmt.executeQuery(sql);
        } catch (SQLException ex) {

        }
        return rs;
    }

    public boolean updatePassword(Integer userID, String password, String newPassword) {
        String hashPwd = null;
        rs = getPassword(userID);
        try {
            if (rs.next()) {
                hashPwd = rs.getString("password"); //get password from database
            }
        } catch (SQLException ex) {
            Logger.getLogger(EditProfileModel.class.getName()).log(Level.SEVERE, null, ex);
        }

        String hashPassword = makeHashPwd(password); //hash current password textfield
        if (hashPassword.equals(hashPwd)) {
            String newHashPwd = makeHashPwd(newPassword);
            String updateSql = "update pm_users set password = \'" + newHashPwd + "\',updated_by = " + user.getId() + " where id = " + userID;
            try {
                rs = stmt.executeQuery(updateSql);
            } catch (SQLException ex) {
                Logger.getLogger(PasswordWindowController.class.getName()).log(Level.SEVERE, null, ex);
            }
            return true;

        } else {
            return false;
        }
    }

    /**
     * Generates the string password to Hash.
     *
     * @param passWord
     * @return
     */
    private String makeHashPwd(String passWord) {
        String localPwd;
        String pwdSql;
        try {
            pwdSql = "SELECT DBMS_OBFUSCATION_TOOLKIT.md5(input => UTL_I18N.STRING_TO_RAW (\'" + passWord + "\', 'AL32UTF8')) pwd from dual";
            rs = stmt.executeQuery(pwdSql);
            if (rs.next()) {
                localPwd = rs.getString("pwd");
                rs.close();
                return localPwd;
            }
        } catch (SQLException ex) {
            Logger.getLogger(RegisterAndLoginModel.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

}
