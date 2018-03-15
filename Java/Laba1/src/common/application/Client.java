package common.application;

import common.dao.PatientDAO;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import common.patient.Patient;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.StringTokenizer;

/**
 * <p>Класс клиента на основе {@link Application} для получения данных по технологии
 * <tt><a href="https://en.wikipedia.org/wiki/Java_remote_method_invocation">RMI</a></tt></p>
 *
 * @author Pechenev Nikolai
 * @version 1.1
 */
public class Client extends Application {

    /**
     * GUI JavaFX
     */
    private TextField textField;
    private Button insertButton;
    private Button deleteButton;
    private Button showSWButton;
    private Button showAllButton;
    private TableView<Patient> table;
    /**
     * Remote интерфейс
     */
    private PatientDAO patientDAO;
    /**
     * Расположение забинженного RemoteObject
     */
    private final String FOR_REGISTRY = "local/Patients";

    /**
     * Метод, для запуска клиента
     * @param primaryStage каскад клиентского приложения
     */
    @Override
    public void start(Stage primaryStage) {
        try {
            //Registry registry = LocateRegistry.getRegistry("192.168.0.89", 2228);
            Registry registry = LocateRegistry.getRegistry("localhost", 2228);
            patientDAO = (PatientDAO) registry.lookup(FOR_REGISTRY);
        }catch (NotBoundException|RemoteException e) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText(e.getMessage());
            alert.showAndWait();
            return;
        }
        BorderPane pane = new BorderPane();
        Scene scene = new Scene(pane);
        HBox hBox = new HBox(10);
        textField = new TextField();

        TableColumn<Patient,Integer> idColumn = new TableColumn<>("ID");
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        TableColumn<Patient,String> fnColumn = new TableColumn<>("First Name");
        fnColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        TableColumn<Patient,String> lnColumn = new TableColumn<>("Last Name");
        lnColumn.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        TableColumn<Patient,Integer> numberColumn = new TableColumn<>("Ward's number");
        numberColumn.setCellValueFactory(new PropertyValueFactory<>("wardsNumber"));
        TableColumn<Patient,String> diagnosisColumn = new TableColumn<>("Diagnosis");
        diagnosisColumn.setCellValueFactory(new PropertyValueFactory<>("diagnosis"));

        table = new TableView<>();
        table.getColumns().addAll(idColumn, fnColumn, lnColumn, numberColumn, diagnosisColumn);

        insertButton = new Button("Insert");
        deleteButton = new Button("Delete");
        showSWButton = new Button("Show sw");
        showAllButton = new Button("Show all");
        insertButton.setOnAction(event -> {
            try {
                String input = textField.getText();
                StringTokenizer tokenizer = new StringTokenizer(input,",");
                String firstName = tokenizer.nextToken();
                String lastName = tokenizer.nextToken();
                int number = Integer.parseInt(tokenizer.nextToken());
                String diagnosis = tokenizer.nextToken();
                patientDAO.insertPatient(new Patient(0,firstName,lastName,number,diagnosis));
                table.setItems(FXCollections.observableArrayList(patientDAO.getAllPatients()));
            } catch (Exception e) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText("firstName,lastName,number,diagnosis");
                alert.showAndWait();
            }
        });
        deleteButton.setOnAction(event -> {
            try {
                String input = textField.getText();
                int id = Integer.parseInt(input);
                patientDAO.deletePatient(id);
                table.setItems(FXCollections.observableArrayList(patientDAO.getAllPatients()));
            } catch (Exception e) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText("Input ID");
                alert.showAndWait();
            }
        });
        showSWButton.setOnAction(event -> {
            try {
                String input = textField.getText();
                table.setItems(FXCollections.observableArrayList(patientDAO.getPatientsStartsWith(input)));
            } catch (Exception e) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                //alert.setContentText("Input first letters of the first name");v
                alert.setContentText(e.getMessage());
                alert.showAndWait();
            }
        });
        showAllButton.setOnAction(event -> {
            try {
                table.setItems(FXCollections.observableArrayList(patientDAO.getAllPatients()));

            } catch (Exception e) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText(e.getMessage());
                alert.showAndWait();
            }
        });
        hBox.getChildren().addAll(textField,insertButton,deleteButton,showSWButton,showAllButton);
        pane.setBottom(hBox);
        pane.setCenter(table);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String... args){
        launch(args);
    }
}
