/*
 *  Project for TEI OF CRETE lesson
 *  Plan Driven and Agile Programming
 *  TP4129 - TP4187 - TP4145
 */
package advance_java_team_clinic_project.Model.DAO;

/**
 *
 * @author Tasos
 */
public interface RegisterAndLoginDao {

    /**
     *
     */
    public void getObject();

    /**
     *
     * @param userName
     * @param passWord
     * @return
     */
    public boolean loginQuery(String userName, String passWord);

    /**
     *
     * @param userName
     * @param passWord
     * @param question1
     * @param question2
     * @param answer1
     * @param answer2
     * @return
     */
    public boolean registerQuery(String userName, String passWord, Integer question1, Integer question2, String answer1, String answer2);

    /**
     *
     * @param passWord
     * @return
     */
    public String makeHashPwd(String passWord);
}
