package controller.commands;

import SQL.PatientDAOImplSQL;
import controller.ConfigurationManager;
import dao.exceptions.DAOException;
import patient.Patient;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class InsertPatientCommand implements Command {

    private static final String PARAM_PATIENT_FIRST_NAME = "first-name";
    private static final String PARAM_PATIENT_LAST_NAME = "last-name";
    private static final String PARAM_PATIENT_WARD_NUMBER = "wards-number";
    private static final String PARAM_PATIENT_DIAGNOSIS = "diagnosis";

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String page = null;

        String firstName = request.getParameter(PARAM_PATIENT_FIRST_NAME);
        String lastName = request.getParameter(PARAM_PATIENT_LAST_NAME);
        int wardsNumber = Integer.parseInt(request.getParameter(PARAM_PATIENT_WARD_NUMBER));
        String diagnosis = request.getParameter(PARAM_PATIENT_DIAGNOSIS);

        Patient patient = new Patient(0,firstName, lastName, wardsNumber, diagnosis);

        PatientDAOImplSQL patientDAOImplSQL= new PatientDAOImplSQL();

        patientDAOImplSQL.insertPatient(patient);

        page = ConfigurationManager.getInstance().getProperty(ConfigurationManager.INSERT_PATIENT_PAGE_PATH);
        return page;

    }
}
