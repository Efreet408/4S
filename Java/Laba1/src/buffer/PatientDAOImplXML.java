package buffer;

import common.dao.PatientDAO;
import common.dao.exceptions.DAOException;
import common.patient.Patient;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import java.io.IOException;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by 1 on 26.02.2018.
 */
public class PatientDAOImplXML implements PatientDAO {
    private String xmlLocation = "src/implDAO.SQL.XML/XMLdata/patients.xml";
    private String schemaLocation = "/implDAO/XML/XMLdata/patients.xsd";

    private DocumentBuilder documentBuilder;

    public PatientDAOImplXML() throws ParserConfigurationException, IOException, org.xml.sax.SAXException {
        SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        URL url = Class.class.getResource(schemaLocation);
        Schema schema = factory.newSchema(url);
//        Validator validator = schema.newValidator();
//        Source source = new StreamSource(new File(xmlLocation));
        //validator.validate(source);
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        documentBuilderFactory.setSchema(schema);
        documentBuilder = documentBuilderFactory.newDocumentBuilder();
    }

    @Override
    public void insertPatient(Patient patient) throws DAOException, RemoteException {
        try {
            Document document = documentBuilder.parse(xmlLocation);
            NodeList nodeList = document.getElementsByTagName("patient");
            Element root = (Element) document.getElementsByTagName("patients").item(0);
            Element newPatient = document.createElement("patient");
            newPatient.setAttribute("id", Integer.toString(patient.getId()));
            Element firstName = document.createElement("firstName");
            Element lastName = document.createElement("lastName");
            Element wardsNumber = document.createElement("wardsNumber");
            Element diagnosis = document.createElement("diagnosis");
            firstName.appendChild(document.createTextNode(patient.getFirstName()));
            lastName.appendChild(document.createTextNode(patient.getLastName()));
            wardsNumber.appendChild(document.createTextNode(Integer.toString(patient.getWardsNumber())));
            diagnosis.appendChild(document.createTextNode(patient.getDiagnosis()));
            newPatient.appendChild(firstName);
            newPatient.appendChild(lastName);
            newPatient.appendChild(wardsNumber);
            newPatient.appendChild(diagnosis);

            if (nodeList.getLength() == 0 || isLastNode(nodeList, patient)) {
                root.appendChild(newPatient);
            } else {
                Node place = nodeList.item(0);
                for (int i = 0; i < nodeList.getLength(); i++) {
                    Node node = nodeList.item(i);
                    if (node.getNodeType() == Node.ELEMENT_NODE) {
                        Element element = (Element) node;
                        int id = Integer.parseInt(element.getAttribute("id"));
                        if (id == patient.getId()) {
                            throw new DAOException("Wrong ID");
                        }
                        if (patient.getId() < id) {
                            place = nodeList.item(i);
                            break;
                        }
                    }
                }
                root.insertBefore(newPatient, place);
            }
            Transformer tr = TransformerFactory.newInstance().newTransformer();
            tr.transform(new DOMSource(document), new StreamResult(xmlLocation));


        } catch (org.xml.sax.SAXException | IOException | TransformerException e) {
            throw new DAOException(e.getMessage());
        }

    }

    @Override
    public void deletePatient(int id) throws DAOException, RemoteException {
        try {
            Document document = documentBuilder.parse(xmlLocation);
            NodeList nodeList = document.getElementsByTagName("patient");
            Element root = (Element) document.getElementsByTagName("patients").item(0);
            for (int i = 0; i < nodeList.getLength(); i++) {
                Node node = nodeList.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) node;
                    int idDelete = Integer.parseInt(element.getAttribute("id"));
                    if (id == idDelete) {
                        root.removeChild(node);
                        Transformer tr = TransformerFactory.newInstance().newTransformer();
                        tr.transform(new DOMSource(document), new StreamResult(xmlLocation));
                        return;
                    }
                }
            }
            throw new DAOException("Wrong ID");
        } catch (org.xml.sax.SAXException | IOException | TransformerException e) {
            throw new DAOException(e.getMessage());
        }
    }

    @Override
    public List<Patient> getAllPatients() throws DAOException, RemoteException {
        try {
            ArrayList<Patient> patients = new ArrayList<>();
            Document document = documentBuilder.parse(xmlLocation);
            NodeList nodeList = document.getElementsByTagName("patient");
            for (int i = 0; i < nodeList.getLength(); i++) {
                Node node = nodeList.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) node;
                    int id = Integer.parseInt(element.getAttribute("id"));
                    String firstName = element.getElementsByTagName("firstName").item(0).getTextContent();
                    String lastName = element.getElementsByTagName("lastName").item(0).getTextContent();
                    int number = Integer.parseInt(element.getElementsByTagName("wardsNumber").item(0).getTextContent());
                    String diagnosis = element.getElementsByTagName("diagnosis").item(0).getTextContent();
                    patients.add(new Patient(id, firstName, lastName, number, diagnosis));
                }
            }
            return patients;
        } catch (org.xml.sax.SAXException | IOException e) {
            throw new DAOException(e.getMessage());
        }
    }

    @Override
    public List<Patient> getPatientsStartsWith(String firstN) throws DAOException, RemoteException {
        try {
            ArrayList<Patient> patients = new ArrayList<>();
            Document document = documentBuilder.parse(xmlLocation);
            NodeList nodeList = document.getElementsByTagName("patient");
            for (int i = 0; i < nodeList.getLength(); i++) {
                Node node = nodeList.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) node;
                    String firstName = element.getElementsByTagName("firstName").item(0).getTextContent();
                    if (firstName.toLowerCase().startsWith(firstN.toLowerCase())) {
                        int id = Integer.parseInt(element.getAttribute("id"));
                        String lastName = element.getElementsByTagName("lastName").item(0).getTextContent();
                        int number = Integer.parseInt(element.getElementsByTagName("wardsNumber").item(0).getTextContent());
                        String diagnosis = element.getElementsByTagName("diagnosis").item(0).getTextContent();
                        patients.add(new Patient(id, firstName, lastName, number, diagnosis));
                    }
                }
            }
            return patients;
        } catch (org.xml.sax.SAXException | IOException e) {
            throw new DAOException(e.getMessage());
        }
    }

    private boolean isLastNode(NodeList nodeList, Patient patient) {
        if (nodeList.getLength() == 0)
            return false;
        int id = Integer.parseInt(((Element) (nodeList.item(nodeList.getLength() - 1))).getAttribute("id"));
        if (patient.getId() > id)
            return true;
        else
            return false;
    }
}
