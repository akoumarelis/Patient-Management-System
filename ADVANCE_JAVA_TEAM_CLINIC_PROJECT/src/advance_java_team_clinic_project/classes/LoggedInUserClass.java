/*
 *  Project for TEI OF CRETE lesson
 *  Plan Driven and Agile Programming
 *  TP4129 - TP4187 - TP4145
 */
package advance_java_team_clinic_project.classes;

public class LoggedInUserClass {

    private int id;
    private String username, name, surname, firstName;
    private int ruleID;
    private int addressID;
    private int contactID;

    /**
     *
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     *
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @param contactID
     */
    public void setContactID(int contactID) {
        this.contactID = contactID;
    }

    /**
     *
     * @return
     */
    public int getContactID() {
        return contactID;
    }

    /**
     *
     * @return
     */
    public int getAddressID() {
        return addressID;
    }

    /**
     *
     * @param addressID
     */
    public void setAddressID(int addressID) {
        this.addressID = addressID;
    }

    /**
     *
     * @return
     */
    public int getRuleID() {
        return ruleID;
    }

    /**
     *
     * @param ruleID
     */
    public void setRuleID(int ruleID) {
        this.ruleID = ruleID;
    }

    /**
     *
     * @return
     */
    public int getId() {
        return id;
    }

    /**
     *
     * @param id
     */
    public void setId(int id) {
        this.id = id;
    }
    private int roleID;
    private String code;
    private static LoggedInUserClass instance = null;

    /**
     *
     * @return
     */
    public int getRoleID() {
        return roleID;
    }

    /**
     *
     * @param roleID
     */
    public void setRoleID(int roleID) {
        this.roleID = roleID;
    }

    /**
     *
     * @return
     */
    public String getUsername() {
        return username;
    }

    /**
     *
     * @param username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     *
     * @return
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     *
     * @param firstName
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     *
     * @return
     */
    public String getSurname() {
        return surname;
    }

    /**
     *
     * @param surname
     */
    public void setSurname(String surname) {
        this.surname = surname;
    }

    /**
     *
     * @return
     */
    public String getCode() {
        return code;
    }

    /**
     *
     * @param code
     */
    public void setCode(String code) {
        this.code = code;
    }

    private LoggedInUserClass() {
    }

    /**
     *
     * @return
     */
    public static LoggedInUserClass getInstance() {
        if (instance == null) {
            instance = new LoggedInUserClass();
        }

        return instance;
    }

    /**
     *
     * @param instance
     */
    public void setInstance(LoggedInUserClass instance) {
        LoggedInUserClass.instance = null;
    }

}
