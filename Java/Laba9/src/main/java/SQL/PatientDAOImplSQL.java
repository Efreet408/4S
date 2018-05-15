package SQL;

import dao.PatientDAO;
import dao.exceptions.DAOException;
import patient.Patient;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PatientDAOImplSQL implements PatientDAO {
    private static final String URL = "jdbc:mysql://localhost:3306/mydb";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "root";

    private static final String INSERT_PATIENT =
            "insert into Patients (FirstName,LastName,WardNumber,DiagnosisID) values(?,?,?,?);";
    private static final String GET_DIAGNOSIS = "select Diagnosis from Diagnosis where id = ?;";
    private static final String GET_ID_DIAGNOSIS = "select id from Diagnosis where Diagnosis = ?;";
    private static final String DELETE_PATIENT = "delete from Patients where id = ?;";
    private static final String PRINT_ALL = "select * from Patients;";
    private static final String PRINT_STARTS = "select * from Patients p where p.FirstName like '";

    public PatientDAOImplSQL(){
    }

    public void insertPatient(Patient patient){
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            try {
                preparedStatement = connection.prepareStatement(GET_ID_DIAGNOSIS);
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
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        finally {
            try {
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            }catch (SQLException e) {
                System.out.println("SQL exception occurred during add client");
                e.printStackTrace();
            }
        }
    }

    public void deletePatient(int id){
        Connection connection = null;
        PreparedStatement preparedStatement =null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            try {
                preparedStatement = connection.prepareStatement(DELETE_PATIENT);
                preparedStatement.setInt(1, id);

                preparedStatement.executeUpdate();
            } catch (SQLException e) {
                connection.close();
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        finally {
            try {
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            }catch (SQLException e) {
                System.out.println("SQL exception occurred during add client");
                e.printStackTrace();
            }
        }
    }

    public List<Patient> getAllPatients() throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ArrayList<Patient> patients = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            try {
                preparedStatement = connection.prepareStatement(PRINT_ALL);
                ResultSet resultSet = preparedStatement.executeQuery();
                patients = new ArrayList<>();
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
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            }catch (SQLException e) {
                System.out.println("SQL exception occurred during add client");
                e.printStackTrace();
                return patients;
            }
            return patients;
        }
    }


    public List<Patient> getPatientsStartsWith(String firstN) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ArrayList<Patient> patients = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            try {
                preparedStatement = connection.prepareStatement(PRINT_STARTS + firstN + "%'");
                ResultSet resultSet = preparedStatement.executeQuery();
                patients = new ArrayList<>();
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
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
        finally {
            try {
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            }catch (SQLException e) {
                System.out.println("SQL exception occurred during add client");
                e.printStackTrace();
                return patients;
            }
            return patients;
        }
    }
}
