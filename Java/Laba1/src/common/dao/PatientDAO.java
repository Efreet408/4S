package common.dao;

import common.dao.exceptions.DAOException;
import common.patient.Patient;
import javafx.application.Application;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

/**
 * <p> Interface удаленного сервиса, для реализации технологии
 * <tt><a href="https://en.wikipedia.org/wiki/Java_remote_method_invocation">RMI</a></tt></p>
 *
 * @author Pechenev Nikolai
 * @version 1.1
 * @see Remote
 */
public interface PatientDAO extends Remote {
    /**
     * Метод для добавления пациента
     *
     * @param patient добавляемый пациент
     * @throws RemoteException исключение <tt>RMI</tt>
     * @throws DAOException    переобразованные в DAOException исключения, не являющиеся RemoteException
     */
    void insertPatient(Patient patient) throws DAOException, RemoteException;

    /**
     * Метод для удаления пациента
     *
     * @param id удаляемого пациента
     * @throws RemoteException исключение <tt>RMI</tt>
     * @throws DAOException    переобразованные в DAOException исключения, не являющиеся RemoteException
     */
    void deletePatient(int id) throws DAOException, RemoteException;

    /**
     * Метод для получения всех пациентов
     *
     * @return Список пациентов
     * @throws RemoteException исключение <tt>RMI</tt>
     * @throws DAOException    переобразованные в DAOException исключения, не являющиеся RemoteException
     * @see List
     */
    List<Patient> getAllPatients() throws DAOException, RemoteException;

    /**
     * Метод для получения пациентов, чье имя начинается на определенную последовательность букв
     *
     * @param firstN удаляемого пациента
     * @return Список пациентов, начинающихся на firstN
     * @throws RemoteException исключение <tt>RMI</tt>
     * @throws DAOException    переобразованные в DAOException исключения, не являющиеся RemoteException
     */
    List<Patient> getPatientsStartsWith(String firstN) throws DAOException, RemoteException;
}
