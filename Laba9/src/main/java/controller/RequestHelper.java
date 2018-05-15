package controller;

import controller.commands.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

public class RequestHelper {

    private static RequestHelper instance = null;

    HashMap<String, Command> commands = new HashMap<>();

    private RequestHelper() {
        commands.put("insert-patient", new InsertPatientCommand());
        commands.put("delete-patient", new DeletePatientCommand());
        commands.put("starts-with", new StartsWithCommand());
    }

    public Command getCommand(HttpServletRequest request) {
        String action = request.getParameter("command");

        Command command = commands.get(action);
        if (command == null) {
            command = new NoCommand();
        }
        return command;
    }

    public static RequestHelper getInstance() {
        if (instance == null) {
            instance = new RequestHelper();
        }
        return instance;
    }
}
