package server;

import common.dao.PatientDAO;
import common.dao.exceptions.DAOException;
import implDAO.SQL.PatientDAOImplSQL;
import implDAO.XML.PatientDAOImplJAXB;
import implDAO.XML.PatientDAOImplStAX;
import javafx.application.Application;

import java.rmi.AlreadyBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

/**
 * <p>Класс сервера для передачи данных по технологии
 * <tt><a href="https://en.wikipedia.org/wiki/Java_remote_method_invocation">RMI</a></tt></p>
 *
 * @author Pechenev Nikolai
 * @version 1.0
 */
public class Server {
    /**
     * Адрес для обращения клиентов по сети
     */
    private static final String FOR_REGISTRY = "local/Patients";

    public static void main(String... args)   {
        try {
//            System.setProperties("java.rmi.server.hostname","myip");
            final Registry registry = LocateRegistry.createRegistry(2228);
            PatientDAO service = new PatientDAOImplJAXB();
            Remote stub = UnicastRemoteObject.exportObject(service, 0);
            registry.rebind(FOR_REGISTRY, stub);
        } catch (RemoteException |DAOException e) {
            e.printStackTrace();
        }
        while (true) {
            try {
                Thread.sleep(Integer.MAX_VALUE);
            } catch (InterruptedException e) {
                System.err.println(e.getMessage());
            }
        }
    }
}
