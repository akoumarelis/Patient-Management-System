/*
 *  Project for TEI OF CRETE lesson
 *  Plan Driven and Agile Programming
 *  TP4129 - TP4187 - TP4145
 */
package advance_java_team_clinic_project.classes;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author Chris
 */
public class RecordsClass {

    private StringProperty edit = new SimpleStringProperty();
    private StringProperty app_code = new SimpleStringProperty();
    private StringProperty app_date = new SimpleStringProperty();
    private StringProperty hour = new SimpleStringProperty();
    private StringProperty comments = new SimpleStringProperty();
    private StringProperty created = new SimpleStringProperty();
    private StringProperty updated = new SimpleStringProperty();
    private StringProperty patient = new SimpleStringProperty();
    private StringProperty doctor = new SimpleStringProperty();
    private StringProperty updated_by = new SimpleStringProperty();
    private StringProperty created_by = new SimpleStringProperty();

    /**
     *
     * @return
     */
    public StringProperty editProperty() {
        return app_code;
    }
    
    /**
     *
     * @return
     */
    public StringProperty app_codeProperty() {
        return app_code;
    }

    /**
     *
     * @return
     */
    public StringProperty app_dateProperty() {
        return app_date;
    }
    
     /**
     *
     * @return
     */
    public StringProperty hourProperty() {
        return hour;
    }

    /**
     *
     * @return
     */
    public StringProperty commentsProperty() {
        return comments;
    }

    /**
     *
     * @return
     */
    public StringProperty createdProperty() {
        return created;
    }

    /**
     *
     * @return
     */
    public StringProperty updatedProperty() {
        return updated;
    }

    /**
     *
     * @return
     */
    public StringProperty patientProperty() {
        return patient;
    }

    /**
     *
     * @return
     */
    public StringProperty doctorProperty() {
        return doctor;
    }

    /**
     *
     * @return
     */
    public StringProperty updated_byProperty() {
        return updated_by;
    }

    /**
     *
     * @return
     */
    public StringProperty created_byProperty() {
        return created_by;
    }

    public RecordsClass() {
    }

}
