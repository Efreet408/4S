package implDAO.SQL;

import com.mysql.fabric.jdbc.FabricMySQLDriver;
import common.dao.PatientDAO;
import common.dao.exceptions.DAOException;
import common.patient.Patient;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>Класс, реализующий {@link PatientDAO}, к которым обращаются через
 * <tt><a href="https://en.wikipedia.org/wiki/Java_remote_method_invocation">RMI</a></tt>
 * для работы с базой данных <tt><a href="https://en.wikipedia.org/wiki/MySQL">MySQL</a></tt></p>
 *
 * @author Pechenev Nikolai
 * @version 1.1
 * @see PatientDAO
 */
public class PatientDAOImplSQL extends UnicastRemoteObject implements PatientDAO {
    /**
     * TCP/IP MySQL сервера, логин и пароль
     */
    private static final String URL = "jdbc:mysql://localhost:3306/mydb";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "root";

    /**
     * Запросы языка MySQL, для работы с базой данных
     *
     * @see PreparedStatement
     */
    private static final String INSERT_PATIENT =
            "insert into Patients (FirstName,LastName,WardNumber,DiagnosisID) values(?,?,?,?);";
    private static final String GET_DIAGNOSIS = "select Diagnosis from Diagnosis where id = ?;";
    private static final String GET_ID_DIAGNOSIS = "select id from Diagnosis where Diagnosis = ?;";
    private static final String DELETE_PATIENT = "delete from Patients where id = ?;";
    private static final String PRINT_ALL = "select * from Patients;";
    private static final String PRINT_STARTS = "select * from Patients p where p.FirstName like '";

    /**
     * Конструктор класса, который регистрирует драйвер
     *
     * @throws DAOException при ошибке регистрации
     */
    public PatientDAOImplSQL() throws DAOException, RemoteException {
        super();
        try {
            Driver driver = new FabricMySQLDriver();
            DriverManager.registerDriver(driver);
        } catch (SQLException e) {
            throw new DAOException(e.getMessage());
        }
    }

    /**
     * Метод для добавления пациента
     *
     * @param patient добавляемый пациент
     * @throws RemoteException исключение <tt>RMI</tt>
     * @throws DAOException    переобразованные в DAOException исключения, не являющиеся RemoteException
     */
    @Override
    public void insertPatient(Patient patient)
            throws DAOException {
        try {
            Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            try {
                PreparedStatement preparedStatement = connection.prepareStatement(GET_ID_DIAGNOSIS);
                preparedStatement.setString(1, patient.getDiagnosis());
                ResultSet resultSet = preparedStatement.executeQuery();
                resultSet.next();
                int id_d = resultSet.getInt(1);

                preparedStatement = connection.prepareStatement(INSERT_PATIENT);
                preparedStatement.setString(1, patient.getFirstName());
                preparedStatement.setString(2, patient.getLastName());
                preparedStatement.setInt(3, patient.getWardsNumber());
                preparedStatement.setInt(4, id_d);

                preparedStatement.execute();
            } catch (SQLException e) {
                throw new DAOException(e.getMessage());
            }
        } catch (SQLException e) {
            throw new DAOException(e.getMessage());
        }
    }

    /**
     * Метод для удаления пациента
     *
     * @param id удаляемого пациента
     * @throws RemoteException исключение <tt>RMI</tt>
     * @throws DAOException переобразованные в DAOException исключения, не являющиеся RemoteException
     */
    @Override
    public void deletePatient(int id) throws DAOException {
        try {
            Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            try {
                PreparedStatement preparedStatement = connection.prepareStatement(DELETE_PATIENT);
                preparedStatement.setInt(1, id);

                preparedStatement.executeUpdate();
            } catch (SQLException e) {
                connection.close();
                throw new DAOException(e.getMessage());
            }
        } catch (SQLException e) {
            throw new DAOException(e.getMessage());
        }
    }

    /**
     * Метод для получения всех пациентов
     *
     * @return Список пациентов
     * @throws RemoteException исключение <tt>RMI</tt>
     * @throws DAOException    переобразованные в DAOException исключения, не являющиеся RemoteException
     * @see List
     */
    @Override
    public List<Patient> getAllPatients() throws DAOException {
        try {
            Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            try {
                PreparedStatement preparedStatement = connection.prepareStatement(PRINT_ALL);
                ResultSet resultSet = preparedStatement.executeQuery();
                ArrayList<Patient> patients = new ArrayList<>();
                while (resultSet.next()) {
                    int id = resultSet.getInt(1);
                    String firstName = resultSet.getString(2);
                    String lastName = resultSet.getString(3);
                    int number = resultSet.getInt(4);
                    preparedStatement = connection.prepareStatement(GET_DIAGNOSIS);
                    preparedStatement.setInt(1, resultSet.getInt(5));
                    ResultSet subResultSet = preparedStatement.executeQuery();
                    subResultSet.next();
                    String diagnosis = subResultSet.getString(1);
                    patients.add(new Patient(id, firstName, lastName, number, diagnosis));
                }
                return patients;
            } catch (SQLException e) {
                connection.close();
                throw new DAOException(e.getMessage());
            }
        } catch (SQLException e) {
            throw new DAOException(e.getMessage());
        }
    }


    /**
     * Метод для получения пациентов, чье имя начинается на определенную последовательность букв
     *
     * @param firstN удаляемого пациента
     * @return Список пациентов, начинающихся на firstN
     * @throws RemoteException исключение <tt>RMI</tt>
     * @throws DAOException    переобразованные в DAOException исключения, не являющиеся RemoteException
     */
    @Override
    public List<Patient> getPatientsStartsWith(String firstN) throws DAOException {
        try {
            Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            try {
                PreparedStatement preparedStatement = connection.prepareStatement(PRINT_STARTS + firstN + "%'");
                ResultSet resultSet = preparedStatement.executeQuery();
                ArrayList<Patient> patients = new ArrayList<>();
                while (resultSet.next()) {
                    int id = resultSet.getInt(1);
                    String firstName = resultSet.getString(2);
                    String lastName = resultSet.getString(3);
                    int number = resultSet.getInt(4);
                    preparedStatement = connection.prepareStatement(GET_DIAGNOSIS);
                    preparedStatement.setInt(1, resultSet.getInt(5));
                    ResultSet subResultSet = preparedStatement.executeQuery();
                    subResultSet.next();
                    String diagnosis = subResultSet.getString(1);
                    patients.add(new Patient(id, firstName, lastName, number, diagnosis));
                }
                return patients;
            } catch (SQLException e) {
                connection.close();
                throw new DAOException(e.getMessage());
            }
        } catch (SQLException e) {
            throw new DAOException(e.getMessage());
        }
    }
}
