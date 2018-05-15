package SQL;

import dao.MyDAO;
import student.Student;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MyDAOImplSQL implements MyDAO {
    private static final String URL = "jdbc:mysql://localhost:3306/students";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "root";

    private static final String PRINT_ALL = "select * from Students;";

    public MyDAOImplSQL(){
    }

    public List<Student> getStudents() {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ArrayList<Student> patients = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            try {
                preparedStatement = connection.prepareStatement(PRINT_ALL);
                ResultSet resultSet = preparedStatement.executeQuery();
                patients = new ArrayList<>();
                while (resultSet.next()) {
                    int id = resultSet.getInt(1);
                    String fullName = resultSet.getString(2);
                    int year = resultSet.getInt(3);
                    int group = resultSet.getInt(4);
                    double avarageRating = resultSet.getDouble(5);
                    patients.add(new Student(id, fullName, year, group, avarageRating));
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


}
