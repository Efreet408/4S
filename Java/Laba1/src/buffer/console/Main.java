package buffer.console;

import common.dao.exceptions.DAOException;

import java.rmi.RemoteException;
import java.util.Scanner;

/**
 * Created by SLI.BY on 13.02.2018.
 */
public class Main {
//    public static void main(String... args) {
//        try {
//            Scanner in = new Scanner(System.in);
//            ConsoleWorker consoleWorker = new ConsoleWorker(in);
//            outbroke:
//            while (true) {
//                System.out.println("i - insert,d - delete, s - show, sa - show all");
//                String s = in.next();
//                try {
//                    switch (s) {
//                        case "i":
//                            consoleWorker.input();
//                            consoleWorker.showAll();
//                            break;
//                        case "d":
//                            consoleWorker.delete();
//                            consoleWorker.showAll();
//                            break;
//                        case "s":
//                            consoleWorker.showStartsWith();
//                            break;
//                        case "sa":
//                            consoleWorker.showAll();
//                            break;
//                        default:
//                            break outbroke;
//                    }
//                }catch (RemoteException |DAOException e){
//                    System.out.println(e.getMessage());
//                }
//            }
//        }catch (DAOException e){
//            System.out.println(e.getMessage());
//        } catch (RemoteException e) {
//            System.out.println(e.getMessage());
//        }
//    }
}
