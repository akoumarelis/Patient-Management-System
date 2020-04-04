package advance_java_team_clinic_project.Model.DAO;

import java.sql.ResultSet;

/**
 *
 * @author Tasos
 */
public interface InsuranceDetailsDao {

    /**
     *
     */
    public void getObject();

    /**
     *
     * @param userId
     * @return
     */
    public ResultSet fetchInsuranceInfoData(Integer userId);

    /**
     *
     * @param userId
     * @param ins_expire_date
     * @param european
     * @param ekas
     * @param ins_comments
     * @param ins_comp_id
     */
    public void updateInsuranceDetails(Integer userId, String ins_expire_date, Integer european, Integer ekas, String ins_comments, Integer ins_comp_id);
}
