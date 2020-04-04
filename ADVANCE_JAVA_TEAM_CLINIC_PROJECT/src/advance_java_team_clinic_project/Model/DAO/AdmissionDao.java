/*
 *  Project for TEI OF CRETE lesson
 *  Plan Driven and Agile Programming
 *  TP4129 - TP4187 - TP4145
 */
package advance_java_team_clinic_project.Model.DAO;

import java.sql.ResultSet;

/**
 *
 * @author Vincent
 */
public interface AdmissionDao {

    /**
     *
     */
    public void getObject();

    /**
     *
     * @param ID
     * @return
     */
    public ResultSet fetchAdmissionData(Integer ID);

    /**
     *
     * @param diagID
     * @param costPerDay
     * @param room
     * @param bed
     * @param ad_date
     * @return
     */
    public boolean createAdmissionData(Integer diagID, Integer costPerDay, String room, String bed, String ad_date);

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
    public boolean updateAdmissionData(Integer ID, Integer costPerDay, String room, String bed, String ad_date, String dis_date);

    /**
     *
     * @param ID
     * @param amount
     */
    public void updateIsPaid(Integer ID, Integer amount);

}
