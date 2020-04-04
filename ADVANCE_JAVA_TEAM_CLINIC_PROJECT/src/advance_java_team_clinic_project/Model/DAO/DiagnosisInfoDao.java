/*
 *  Project for TEI OF CRETE lesson
 *  Plan Driven and Agile Programming
 *  TP4129 - TP4187 - TP4145
 */
package advance_java_team_clinic_project.Model.DAO;

import java.sql.ResultSet;

/**
 *
 * @author Beast
 */
public interface DiagnosisInfoDao {

    public ResultSet fetchDiagnoseInfoData(Integer diag_id);

    public void createDiagnoseDetails(Integer app_info_id, String lComments, String lMeds);

    public void updateDiagnoseDetails(Integer diag_id, String lComments, String lMeds);

    public Integer getAdmissionId(Integer diag_id);
    
    public ResultSet getDiagId(String app_id);

}
