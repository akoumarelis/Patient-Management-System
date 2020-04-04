/*
 *  Project for TEI OF CRETE lesson
 *  Plan Driven and Agile Programming
 *  TP4129 - TP4187 - TP4145
 */
package advance_java_team_clinic_project.classes;

public class ContactDetailsClass {

    private int id;
    private int telNumber;
    private int celNumber;
    private String email;
    private int relativeID;
    private int relativeTelNumber;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTelNumber() {
        return telNumber;
    }

    public void setTelNumber(int telNumber) {
        this.telNumber = telNumber;
    }

    public int getCelNumber() {
        return celNumber;
    }

    public void setCelNumber(int celNumber) {
        this.celNumber = celNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getRelativeID() {
        return relativeID;
    }

    public void setRelativeID(int relativeID) {
        this.relativeID = relativeID;
    }

    public int getRelativeTelNumber() {
        return relativeTelNumber;
    }

    public void setRelativeTelNumber(int relativeTelNumber) {
        this.relativeTelNumber = relativeTelNumber;
    }

}
