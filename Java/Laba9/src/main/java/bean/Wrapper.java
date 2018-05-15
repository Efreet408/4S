package bean;

import SQL.PatientDAOImplSQL;
import dao.exceptions.DAOException;
import patient.Patient;

import java.util.List;

public class Wrapper {
    private PatientDAOImplSQL dao;

    public Wrapper(){
        dao = new PatientDAOImplSQL();
    }
    public List<Patient> getPatients() throws DAOException {
        return dao.getAllPatients();
    }
    public List<Patient> getPatientsStartsWith(String firstName) throws DAOException {
        return dao.getPatientsStartsWith(firstName);
    }
}
