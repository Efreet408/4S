package controller.commands;

import SQL.PatientDAOImplSQL;
import com.mysql.jdbc.interceptors.SessionAssociationInterceptor;
import controller.ConfigurationManager;
import patient.Patient;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class StartsWithCommand implements Command {
    private static final String PARAM_PATIENT_STARTS_WITH = "starts-with-value";

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String page = null;

        String starts = request.getParameter(PARAM_PATIENT_STARTS_WITH);

        PatientDAOImplSQL patientDAOImplSQL= new PatientDAOImplSQL();

        List<Patient> patients = patientDAOImplSQL.getPatientsStartsWith(starts);

        request.getSession().setAttribute("patients",patients);

        page = ConfigurationManager.getInstance().getProperty(ConfigurationManager.STARTS_WITH_RESULT_PAGE_PATH);
        return page;

    }
}
