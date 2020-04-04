/*
 *  Project for TEI OF CRETE lesson
 *  Plan Driven and Agile Programming
 *  TP4129 - TP4187 - TP4145
 */
package advance_java_team_clinic_project.Model;

import advance_java_team_clinic_project.classes.LoggedInUserClass;
import advance_java_team_clinic_project.Model.DAO.RegisterAndLoginDao;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.StageStyle;

public class RegisterAndLoginModel implements RegisterAndLoginDao {

    private Statement stmt;
    private String sql, existSql, regSql, pwdSql, questSql, chgRs;
    private ResultSet rs, regRs, pwdRs;
    private String username, password, role, created, updated, hashPwd, answer1, answer2;
    private DatabaseConnectionModel object;
    private int questionId1, questionId2;
    LoggedInUserClass user = LoggedInUserClass.getInstance();
    public Integer roleId, Id;
    Alert alert = new Alert(AlertType.INFORMATION);

    /**
     *
     */
    @Override
    public void getObject() {
        try {
            object = DatabaseConnectionModel.getInstance();
        } catch (SQLException ex) {
            Logger.getLogger(RegisterAndLoginModel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Returns true/false if the login information is correct or not.
     *
     * @param userName
     * @param passWord
     * @return
     */
    @Override
    public boolean loginQuery(String userName, String passWord) {
        try {
            getObject();
            /* Alert Initialization */
            alert.setHeaderText(null);
            alert.initStyle(StageStyle.UTILITY);
            /* End of Alert Initialiization*/
            stmt = object.connection.createStatement();
            hashPwd = makeHashPwd(passWord);
            sql = "select id, password, role_id, firstname as firstname, surname, username, address_id, contact_id from pm_users where upper(username) = upper('" + userName + "')";
            rs = stmt.executeQuery(sql);

            if (rs.next()) {
                try {
                    password = rs.getString("password");
                    if (password.equals(hashPwd)) {
                        try {
                            user.setRoleID(rs.getInt("role_id"));
                            user.setId(rs.getInt("id"));
                            user.setFirstName(rs.getString("firstname"));
                            user.setSurname(rs.getString("surname"));
                            user.setUsername(rs.getString("username"));
                            user.setAddressID(rs.getInt("address_id"));
                            user.setContactID(rs.getInt("contact_id"));
                            return true;
                        } catch (SQLException ex) {
                            Logger.getLogger(RegisterAndLoginModel.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    } else if (password != passWord) {
                        alert.setTitle("Incorrect Password");
                        alert.setContentText("The Password you have entered is not correct!");
                        alert.showAndWait();
                        return false;
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(RegisterAndLoginModel.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                alert.setTitle("Incorrect Username");
                alert.setContentText("The Username you have entered does not match any existing user!");
                alert.showAndWait();
                return false;
            }
        } catch (SQLException ex) {
            Logger.getLogger(RegisterAndLoginModel.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    /**
     * Returns true/false if the register information is correct or not.
     *
     * @param userName
     * @param passWord
     * @param question1
     * @param question2
     * @param answer1
     * @param answer2
     * @return
     */
    @Override
    public boolean registerQuery(String userName, String passWord, Integer question1, Integer question2, String answer1, String answer2) {
        try {
            getObject();
            alert.setHeaderText(null);
            alert.initStyle(StageStyle.UTILITY);
            stmt = object.connection.createStatement();
            existSql = "select count(*) as existing from pm_users where username ='" + userName + "'";
            rs = stmt.executeQuery(existSql);
            rs.next();
            if (rs.getInt("existing") > 0) {
                alert.setTitle("Wrong username");
                alert.setContentText("Username already existing, please add another username.");
                alert.showAndWait();
                return false;
            } else if (rs.getInt("existing") == 0) {
                hashPwd = makeHashPwd(passWord);
                regSql = "insert into pm_users (username, password, role_id, question_1_id , answer_1, question_2_id, answer_2) values ('" + userName + "','" + hashPwd + "',3, " + question1 + ",'" + answer1 + "'," + question2 + ",'" + answer2 + "')";
                regRs = stmt.executeQuery(regSql);
                alert.setTitle("Success!");
                alert.setContentText("User was succesfully registered! You may login now.");
                alert.showAndWait();
                return true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(RegisterAndLoginModel.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    /**
     * Generates the string password to Hash.
     *
     * @param passWord
     * @return
     */
    @Override
    public String makeHashPwd(String passWord) {
        String localPwd;
        try {
            getObject();
            pwdSql = "SELECT DBMS_OBFUSCATION_TOOLKIT.md5(input => UTL_I18N.STRING_TO_RAW (\'" + passWord + "\', 'AL32UTF8')) pwd from dual";
            pwdRs = stmt.executeQuery(pwdSql);
            if (pwdRs.next()) {
                localPwd = pwdRs.getString("pwd");
                pwdRs.close();
                return localPwd;

            }
        } catch (SQLException ex) {
            Logger.getLogger(RegisterAndLoginModel.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    /**
     *
     * @param username
     * @param questionAnswerId
     * @param answer
     * @return
     */
    public int recoveryPassword(String username, Integer questionAnswerId, String answer) { //Sigrinei to id tou question kai to answer pou edwse o xristis sto recovery password kai an einai idia me tin basi tote girnaei 1 allios 0
        try {
            getObject();
            stmt = object.connection.createStatement();
            questSql = "select id,question_1_id,question_2_id,answer_1,answer_2 from pm_users where username = '" + username + "'";
            rs = stmt.executeQuery(questSql);
            Id = -1;
            if (rs.next()) {
                questionId1 = rs.getInt("question_1_id");
                answer1 = rs.getString("answer_1");
                questionId2 = rs.getInt("question_2_id");
                answer2 = rs.getString("answer_2");
                if ((questionAnswerId == questionId1) && (answer.equals(answer1)) || (questionAnswerId == questionId2) && (answer.equals(answer2))) {
                    Id = rs.getInt("Id");
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(RegisterAndLoginModel.class.getName()).log(Level.SEVERE, null, ex);
        }
        return Id;
    }

    /**
     *
     * @param password
     * @param Id
     */
    public void changePassword(String password, Integer Id) {
        try {
            getObject();
            stmt = object.connection.createStatement();
            hashPwd = makeHashPwd(password);
            chgRs = "update pm_users set password = '" + hashPwd + "' where id = " + Id;;
            rs = stmt.executeQuery(chgRs);
        } catch (SQLException ex) {
            Logger.getLogger(RegisterAndLoginModel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
