package dao;
import dao.exceptions.DAOException;
import patient.Patient;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface PatientDAO extends Remote {
    void insertPatient(Patient patient) throws DAOException, RemoteException;

    void deletePatient(int id) throws DAOException, RemoteException;

    List<Patient> getAllPatients() throws DAOException, RemoteException;

    List<Patient> getPatientsStartsWith(String firstN) throws DAOException, RemoteException;
}
