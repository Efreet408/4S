package patient;

import java.io.Serializable;

public class Patient implements Serializable {

    private int id;
    private int wardsNumber;
    private String firstName;
    private String lastName;
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

    public int getId() {
        return id;
    }

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
                ", diagnosis='" + diagnosis + '\'';
    }
}
