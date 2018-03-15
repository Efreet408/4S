package implDAO.XML;

import common.dao.PatientDAO;
import common.dao.exceptions.DAOException;
import common.patient.Patient;

import javax.xml.XMLConstants;
import javax.xml.namespace.QName;
import javax.xml.stream.*;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;


/**
 * <p>Класс, реализующий {@link PatientDAO}, к которым обращаются через
 * <tt><a href="https://en.wikipedia.org/wiki/Java_remote_method_invocation">RMI</a></tt> для работы с базой данных XML
 * посредством технологии <tt><a href="https://en.wikipedia.org/wiki/StAX">StAX</a></tt></p>
 *
 * @author Pechenev Nikolai
 * @version 1.2
 * @see PatientDAO
 */
public class PatientDAOImplStAX extends UnicastRemoteObject implements PatientDAO {
    /**
     * Расположение XML базы данных и XSD схемы
     */
    private String xmlLocation = "src/implDAO/XML/XMLdata/patients.xml";
    private String schemaLocation = "/implDAO/XML/XMLdata/patients.xsd";
    /**
     * Поля для работы со StAX
     */
    private XMLInputFactory inputFactory;
    private XMLOutputFactory outputFactory;
    private XMLEventFactory eventFactory = XMLEventFactory.newInstance();;
    private XMLEventWriter xmlWriter;
    private XMLEventReader xmlReader;
    /**
     * Часто используемые константы
     */
    private final XMLEvent NEW_LINE = eventFactory.createCharacters("\n");
    private final XMLEvent TAB = eventFactory.createCharacters("\t");

    /**
     * Проверка валидности XML по схеме
     *
     * @throws DAOException при неправильном XML файле
     */
    public PatientDAOImplStAX() throws DAOException, RemoteException {
        super();
        try {
            SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            URL url = Class.class.getResource(schemaLocation);
            Schema schema = factory.newSchema(url);
            Validator validator = schema.newValidator();
            Source source = new StreamSource(new File(xmlLocation));
            validator.validate(source);
            inputFactory = XMLInputFactory.newInstance();
            outputFactory = XMLOutputFactory.newInstance();
        }catch (IOException| org.xml.sax.SAXException e){
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
        int place = 0;
        try {
            xmlReader = inputFactory.createXMLEventReader(new FileReader(xmlLocation));
            xmlWriter = outputFactory.createXMLEventWriter(new FileWriter(xmlLocation));
            while (xmlReader.hasNext()) {
                XMLEvent event = xmlReader.nextEvent();
                switch (event.getEventType()) {
                    case XMLEvent.START_ELEMENT:
                        StartElement startElement = event.asStartElement();
                        String tag = startElement.getName().getLocalPart();
                        if("patient".equals(tag)){
                            Attribute attribute = startElement.getAttributeByName(QName.valueOf("id"));
                            int current = Integer.parseInt(attribute.getValue());
                            if (current > place) {
                                place = current;
                            }
                        }
                        break;
                    case XMLEvent.END_ELEMENT:
                        EndElement endElement = event.asEndElement();
                        String tagE = endElement.getName().getLocalPart();
                        if("patients".equals(tagE)){
                            patient.setId(place + 1);
                            inputPatient(patient);
                            closeTag("patients");
                            return;
                        }
                        break;
                    default:
                        break;
                }
                xmlWriter.add(event);
            }
        } catch (XMLStreamException | IOException e) {
            throw new DAOException(e.getMessage());
        } finally {
            if (xmlReader != null) {
                try {
                    xmlReader.close();
                } catch (XMLStreamException e) {
                    throw new DAOException(e.getMessage());
                }
            }
            if (xmlWriter != null) {
                try {
                    xmlWriter.close();
                } catch (XMLStreamException e) {
                    throw new DAOException(e.getMessage());
                }
            }
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
        try {
            xmlReader = inputFactory.createXMLEventReader(new FileReader(xmlLocation));
            xmlWriter = outputFactory.createXMLEventWriter(new FileWriter(xmlLocation));
            boolean delete = false;
            while (xmlReader.hasNext()) {
                XMLEvent event = xmlReader.nextEvent();
                switch (event.getEventType()) {
                    case XMLEvent.START_ELEMENT:
                        StartElement startElement = event.asStartElement();
                        String tag = startElement.getName().getLocalPart();
                        if(delete){
                            continue;
                        }
                        if("patient".equals(tag)){
                            Attribute attribute = startElement.getAttributeByName(QName.valueOf("id"));
                            int current = Integer.parseInt(attribute.getValue());
                            if (current == id) {
                                delete = true;
                                continue;
                            }
                        }
                        break;
                    case XMLEvent.END_ELEMENT:
                        EndElement endElement = event.asEndElement();
                        String tagE = endElement.getName().getLocalPart();
                        if(delete){
                            continue;
                        }
                        if("patient".equals(tagE) && delete){
                            delete = false;
                            continue;
                        }
                        break;
                    default:
                        if(delete)
                            continue;
                        break;
                }
                xmlWriter.add(event);
            }
        } catch (XMLStreamException | IOException e) {
            throw new DAOException(e.getMessage());
        } finally {
            if (xmlReader != null) {
                try {
                    xmlReader.close();
                } catch (XMLStreamException e) {
                    throw new DAOException(e.getMessage());
                }
            }
            if (xmlWriter != null) {
                try {
                    xmlWriter.close();
                } catch (XMLStreamException e) {
                    throw new DAOException(e.getMessage());
                }
            }
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
        ArrayList<Patient> patients = new ArrayList<>();
        try {
            xmlReader = inputFactory.createXMLEventReader(new FileReader(xmlLocation));
            Patient patient = new Patient();
            XMLEvent event;
            String tag ="";
            while (xmlReader.hasNext()) {
                event = xmlReader.nextEvent();
                switch (event.getEventType()) {
                    case XMLEvent.START_ELEMENT:
                        StartElement startElement = event.asStartElement();
                        tag = startElement.getName().getLocalPart();
                        switch (tag) {
                            case "patient":
                                Attribute attribute = startElement.getAttributeByName(QName.valueOf("id"));
                                patient.setId(Integer.parseInt(attribute.getValue()));
                                break;
                        }
                        break;
                    case XMLEvent.CHARACTERS:
                        if(event.asCharacters().isWhiteSpace())
                            break;
                        switch (tag) {
                            case "firstName":
                                patient.setFirstName(event.asCharacters().getData());
                                break;
                            case "lastName":
                                patient.setLastName(event.asCharacters().getData());
                                break;
                            case "wardsNumber":
                                patient.setWardsNumber(Integer.parseInt(event.asCharacters().getData()));
                                break;
                            case "diagnosis":
                                patient.setDiagnosis(event.asCharacters().getData());
                                break;
                        }
                        break;
                    case XMLEvent.END_ELEMENT:
                        EndElement endElement = event.asEndElement();
                        String tagE = endElement.getName().getLocalPart();
                        if("patient".equals(tagE)){
                            patients.add(patient);
                            patient = new Patient();
                        }
                        break;
                }
            }
        } catch (XMLStreamException | IOException e) {
            throw new DAOException(e.getMessage());
        } finally {
            if (xmlReader != null) {
                try {
                    xmlReader.close();
                } catch (XMLStreamException e) {
                    throw new DAOException(e.getMessage());
                }
            }
        }
        return patients;
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
        ArrayList<Patient> patients = new ArrayList<>();
        try {
            xmlReader = inputFactory.createXMLEventReader(new FileReader(xmlLocation));
            Patient patient = new Patient();
            XMLEvent event;
            String tag ="";
            boolean flagStartsWith = false;
            while (xmlReader.hasNext()) {
                event = xmlReader.nextEvent();
                switch (event.getEventType()) {
                    case XMLEvent.START_ELEMENT:
                        StartElement startElement = event.asStartElement();
                        tag = startElement.getName().getLocalPart();
                        switch (tag) {
                            case "patient":
                                Attribute attribute = startElement.getAttributeByName(QName.valueOf("id"));
                                patient.setId(Integer.parseInt(attribute.getValue()));
                                break;
                        }
                        break;
                    case XMLEvent.CHARACTERS:
                        if(event.asCharacters().isWhiteSpace())
                            break;
                        switch (tag) {
                            case "firstName":
                                String firstName = event.asCharacters().getData();
                                if(firstName.toLowerCase().startsWith(firstN.toLowerCase())) {
                                    flagStartsWith = true;
                                    patient.setFirstName(firstName);
                                }
                                break;
                            case "lastName":
                                if(flagStartsWith){
                                    patient.setLastName(event.asCharacters().getData());
                                }
                                break;
                            case "wardsNumber":
                                if(flagStartsWith) {
                                    patient.setWardsNumber(Integer.parseInt(event.asCharacters().getData()));
                                }
                                break;
                            case "diagnosis":
                                if(flagStartsWith) {
                                    patient.setDiagnosis(event.asCharacters().getData());
                                    break;
                                }
                        }
                        break;
                    case XMLEvent.END_ELEMENT:
                        EndElement endElement = event.asEndElement();
                        String tagE = endElement.getName().getLocalPart();
                        if("patient".equals(tagE) && flagStartsWith){
                            patients.add(patient);
                            patient = new Patient();
                            flagStartsWith = false;
                        }
                        break;
                }
            }
        } catch (XMLStreamException | IOException e) {
            throw new DAOException(e.getMessage());
        } finally {
            if (xmlReader != null) {
                try {
                    xmlReader.close();
                } catch (XMLStreamException e) {
                    throw new DAOException(e.getMessage());
                }
            }
        }
        return patients;
    }

    private void inputPatient(Patient patient) throws XMLStreamException {
        xmlWriter.add(TAB);
        openTag("patient");
        xmlWriter.add(eventFactory.createAttribute("id", Integer.toString(patient.getId())));
        xmlWriter.add(NEW_LINE);
        writeElement("firstName", patient.getFirstName());
        xmlWriter.add(NEW_LINE);
        writeElement("lastName", patient.getLastName());
        xmlWriter.add(NEW_LINE);
        writeElement("wardsNumber", Integer.toString(patient.getWardsNumber()));
        xmlWriter.add(NEW_LINE);
        writeElement("diagnosis", patient.getDiagnosis());
        xmlWriter.add(NEW_LINE);
        xmlWriter.add(TAB);
        closeTag("patient");
        xmlWriter.add(NEW_LINE);
    }
    private void openTag(String tag) throws XMLStreamException{
        QName name = QName.valueOf(tag);
        XMLEvent event = eventFactory.createStartElement(name.getPrefix(), name.getNamespaceURI(), name.getLocalPart());
        xmlWriter.add(event);
    }
    private void closeTag(String tag) throws XMLStreamException{
        QName name = QName.valueOf(tag);
        XMLEvent event = eventFactory.createEndElement(name.getPrefix(), name.getNamespaceURI(), name.getLocalPart());
        xmlWriter.add(event);
    }

    private void writeElement(String tag, String value) throws XMLStreamException {
        xmlWriter.add(TAB);
        xmlWriter.add(TAB);
        openTag(tag);
        XMLEvent event = eventFactory.createCharacters(value);
        xmlWriter.add(event);
        closeTag(tag);
    }
}
