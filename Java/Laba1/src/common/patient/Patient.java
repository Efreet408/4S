package common.patient;

import javax.xml.bind.annotation.*;
import java.io.Serializable;

/**
 * <p>class</p>
 *
 * @author Pechenev Nikolai
 * @version 1.1
 */
@XmlRootElement(name = "patient")
@XmlAccessorType(XmlAccessType.NONE)
@XmlType(propOrder = {
        "id",
        "firstName",
        "lastName",
        "wardsNumber",
        "diagnosis",
})
public class Patient implements Serializable {

    @XmlAttribute(required = true)
    private int id;
    @XmlElement
    private int wardsNumber;
    @XmlElement
    private String firstName;
    @XmlElement
    private String lastName;
    @XmlElement
    private String diagnosis;

    public Patient(int id, String firstName, String lastName, int wardsNumber, String diagnosis) {
        this.id = id;
        this.wardsNumber = wardsNumber;
        this.firstName = firstName;
        this.lastName = lastName;
        this.diagnosis = diagnosis;
    }

    public Patient() {
    }

//    @XmlAttribute
    public int getId() {
        return id;
    }

//    @XmlAttribute
    public void setId(int id) {
        this.id = id;
    }

    public int getWardsNumber() {
        return wardsNumber;
    }

    public void setWardsNumber(int wardsNumber) {
        this.wardsNumber = wardsNumber;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getDiagnosis() {
        return diagnosis;
    }

    public void setDiagnosis(String diagnosis) {
        this.diagnosis = diagnosis;
    }

    @Override
    public String toString() {
        return "id=" + id +
                ", wardsNumber=" + wardsNumber +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", diagnosis='" + diagnosis + '\'' ;
    }
}
