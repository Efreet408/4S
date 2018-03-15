package common.dao.exceptions;

import java.io.Serializable;

/**
 * <p> Класс исключения для работы с DAO</p>
 *
 * @author Pechenev Nikolai
 * @version 1.0
 * @see common.dao.PatientDAO
 */
public class DAOException extends Exception implements Serializable {
    public DAOException(String message) {
        super(message);
    }
}
