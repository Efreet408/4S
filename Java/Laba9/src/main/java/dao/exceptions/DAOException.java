package dao.exceptions;

import java.io.Serializable;

public class DAOException extends Exception implements Serializable {
    public DAOException(String message) {
        super(message);
    }
}
