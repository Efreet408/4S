package bean;

import SQL.MyDAOImplSQL;
import student.Student;

import java.util.List;

public class Wrapper {
    private MyDAOImplSQL dao;

    public Wrapper(){
        dao = new MyDAOImplSQL();
    }
    public List<Student> getStudents(){
        return dao.getStudents();
    }
}
