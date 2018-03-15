package implDAO.XML;

import common.patient.Patient;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * <p>Класс-обертка , содержащий список пациентов</p>
 *
 * @author Pechenev Nikolai
 * @version 1.1
 * @see PatientDAOImplJAXB
 */
@XmlRootElement(name = "patients")
@XmlAccessorType(XmlAccessType.FIELD)
public class Wrapper {
    @XmlElement(name = "patient")
    public List<Patient> patients = null;

    public List<Patient> getPatients() {
        return patients;
    }

    public void setPatients(List<Patient> patients) {
        this.patients = patients;
    }
}
