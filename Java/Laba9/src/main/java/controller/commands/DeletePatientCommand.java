package controller.commands;

import SQL.PatientDAOImplSQL;
import controller.ConfigurationManager;
import patient.Patient;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DeletePatientCommand implements Command {

    private static final String PARAM_PATIENT_PATIENT_ID = "patient-id";

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String page = null;

        int id = Integer.parseInt(request.getParameter(PARAM_PATIENT_PATIENT_ID));

        PatientDAOImplSQL patientDAOImplSQL= new PatientDAOImplSQL();

        patientDAOImplSQL.deletePatient(id);

        page = ConfigurationManager.getInstance().getProperty(ConfigurationManager.DELETE_PATIENT_PAGE_PATH);
        return page;

    }
}
