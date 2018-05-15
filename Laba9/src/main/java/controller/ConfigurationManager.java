package controller;

public class ConfigurationManager {
    private static ConfigurationManager instance;

    public static final String INDEX_PAGE_PATH = "/";
    public static final String ERROR_PAGE_PATH = "/jsp/error.jsp";
    public static final String INSERT_PATIENT_PAGE_PATH = "/jsp/insert-patient.jsp";
    public static final String DELETE_PATIENT_PAGE_PATH = "/jsp/delete-patient.jsp";
    public static final String STARTS_WITH_PATIENT_PAGE_PATH = "/jsp/starts-with-patient.jsp";
    public static final String STARTS_WITH_RESULT_PAGE_PATH = "/jsp/starts-with-result.jsp";

    public static ConfigurationManager getInstance() {
        if (instance == null) {
            instance = new ConfigurationManager();
        }
        return instance;
    }

    public String getProperty(String key) {
        return key;
    }
}
