package implDAO.XML;

import common.dao.PatientDAO;
import common.dao.exceptions.DAOException;
import common.patient.Patient;

import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * <p>Класс, реализующий {@link PatientDAO}, к которым обращаются через
 * <tt><a href="https://en.wikipedia.org/wiki/Java_remote_method_invocation">RMI</a></tt> для работы с базой данных XML
 * посредством технологии <tt><a href="https://en.wikipedia.org/wiki/Java_Architecture_for_XML_Binding">JAXB</a></tt></p>
 *
 * @author Pechenev Nikolai
 * @version 1.1
 * @see PatientDAO
 */
public class PatientDAOImplJAXB extends UnicastRemoteObject implements PatientDAO {
    /**
     * Расположение XML базы данных и XSD схемы
     */
    private String xmlLocation = "src/implDAO/XML/XMLdata/patients.xml";
    private String schemaLocation = "/implDAO/XML/XMLdata/patients.xsd";
    /**
     * Контекст класса-обертки
     *
     * @see Wrapper,JAXBContext
     */
    private JAXBContext context;

    /**
     * Проверка валидности XML по схеме и получение контекста
     *
     * @see Wrapper,JAXBContext
     * @throws DAOException при неправильном XML файле
     */
    public PatientDAOImplJAXB() throws DAOException, RemoteException {
        super();
        try {
            SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            URL url = Class.class.getResource(schemaLocation);
            Schema schema = factory.newSchema(url);
            Validator validator = schema.newValidator();
            Source source = new StreamSource(new File(xmlLocation));
            validator.validate(source);
            context = JAXBContext.newInstance(Wrapper.class);
        } catch (IOException | org.xml.sax.SAXException | JAXBException e) {
            throw new DAOException(e.getMessage());
        }
    }

    /**
     * Метод для добавления пациента
     *
     * @param patient добавляемый пациент
     * @throws RemoteException исключение <tt>RMI</tt>
     * @throws DAOException    переобразованные в DAOException исключения, не являющиеся RemoteException
     */
    @Override
    public void insertPatient(Patient patient) throws DAOException, RemoteException {
        Wrapper wrapper = new Wrapper();
        try {
            File file = new File(xmlLocation);

            Unmarshaller unmarshaller = context.createUnmarshaller();
            wrapper = (Wrapper) unmarshaller.unmarshal(file);
            ArrayList<Patient> patients = (ArrayList<Patient>) wrapper.getPatients();
            patient.setId(patients.get(patients.size() - 1).getId() + 1);
            patients.add(patient);
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            marshaller.marshal(wrapper, file);
        } catch (JAXBException e) {
            throw new DAOException(e.getMessage());
        }
    }

    /**
     * Метод для удаления пациента
     *
     * @param id удаляемого пациента
     * @throws RemoteException исключение <tt>RMI</tt>
     * @throws DAOException    переобразованные в DAOException исключения, не являющиеся RemoteException
     */
    @Override
    public void deletePatient(int id) throws DAOException, RemoteException {
        Wrapper wrapper = new Wrapper();
        try {
            File file = new File(xmlLocation);

            Unmarshaller unmarshaller = context.createUnmarshaller();
            wrapper = (Wrapper) unmarshaller.unmarshal(file);
            ArrayList<Patient> patients = (ArrayList<Patient>) wrapper.getPatients();
            patients.removeIf(patient -> patient.getId() == id);
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            marshaller.marshal(wrapper, file);
        } catch (JAXBException e) {
            throw new DAOException(e.getMessage());
        }
    }

    /**
     * Метод для получения всех пациентов
     *
     * @return Список пациентов
     * @throws RemoteException исключение <tt>RMI</tt>
     * @throws DAOException    переобразованные в DAOException исключения, не являющиеся RemoteException
     * @see List
     */
    @Override
    public List<Patient> getAllPatients() throws DAOException, RemoteException {
        Wrapper wrapper = new Wrapper();
        try {
            File file = new File(xmlLocation);

            Unmarshaller unmarshaller = context.createUnmarshaller();
            wrapper = (Wrapper) unmarshaller.unmarshal(file);
        } catch (JAXBException e) {
            throw new DAOException(e.getMessage());
        }
        return wrapper.getPatients();
    }

    /**
     * Метод для получения пациентов, чье имя начинается на определенную последовательность букв
     *
     * @param firstN удаляемого пациента
     * @return Список пациентов, начинающихся на firstN
     * @throws RemoteException исключение <tt>RMI</tt>
     * @throws DAOException    переобразованные в DAOException исключения, не являющиеся RemoteException
     */
    @Override
    public List<Patient> getPatientsStartsWith(String firstN) throws DAOException, RemoteException {
        Wrapper wrapper = new Wrapper();
        try {
            File file = new File(xmlLocation);

            Unmarshaller unmarshaller = context.createUnmarshaller();
            wrapper = (Wrapper) unmarshaller.unmarshal(file);
            wrapper.getPatients().removeIf(patient -> !patient.getFirstName().toLowerCase().startsWith(firstN.toLowerCase()));
        } catch (JAXBException e) {
            throw new DAOException(e.getMessage());
        }
        return wrapper.getPatients();
    }
}
