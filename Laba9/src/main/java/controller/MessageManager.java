package controller;


public class MessageManager {

    private static MessageManager instance;

    public static final String SERVLET_EXCEPTION_ERROR_MESSAGE = "ServletException: Servlet encounters difficulty";
    public static final String IO_EXCEPTION_ERROR_MESSAGE = "IOException: wrong input";
    public static final String DAO_EXCEPTION_ERROR_MESSAGE = "DAOException: wrong input";

    public static MessageManager getInstance() {
        if (instance == null) {
            instance = new MessageManager();
        }
        return instance;
    }

    public String getProperty(String key) {
        return key;
    }
}
