package buffer.console;

import common.dao.exceptions.DAOException;
import common.dao.PatientDAO;
import common.patient.Patient;

import java.rmi.AccessException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by SLI.BY on 14.02.2018.
 */
public class ConsoleWorker {

    private PatientDAO patientDAOImpl;
    private final String FOR_REGISTRY = "local/Patients";
    private Scanner in;
    public ConsoleWorker(Scanner in) throws DAOException, RemoteException {
        try {
            Registry registry = LocateRegistry.getRegistry("192.168.0.89", 2228);
            patientDAOImpl = (PatientDAO) registry.lookup(FOR_REGISTRY);
        }catch (AccessException|NotBoundException e) {
            throw new DAOException(e.getMessage());
        }
//        this.patientDAOImpl = new PatientDAOImplSQL();
        this.in = in;
    }

    public void input() throws DAOException,RemoteException {
        System.out.println("Input first name");
        String firstName = in.next();
        System.out.println("Input last name");
        String lastName = in.next();
        System.out.println("Input number of wards");
        int number = in.nextInt();
        System.out.println("Input diagnosis");
        String diagnosis = in.next();
        patientDAOImpl.insertPatient(new Patient(0,firstName, lastName, number, diagnosis));
    }

    public void delete() throws DAOException,RemoteException {
        System.out.println("Input ID");
        int idToDelete = in.nextInt();
        patientDAOImpl.deletePatient(idToDelete);
    }

    public void showStartsWith() throws DAOException,RemoteException {
        System.out.println("Input first name");
        String firstName = in.next();
        showResultSet((ArrayList<Patient>)patientDAOImpl.getPatientsStartsWith(firstName));
    }

    public void showAll() throws DAOException,RemoteException {

        showResultSet((ArrayList<Patient>)patientDAOImpl.getAllPatients());
    }

    public void showResultSet(ArrayList<Patient> patients) throws DAOException {
        for(Patient p:patients){
            System.out.println(p);
        }
    }
}
