/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Scanner;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.Query;
import model.Status;

/**
 *
 * @author smbillah
 */
public class FXMLDocumentController implements Initializable {

    @FXML
    private Label label;

    @FXML
    private Button buttonCreateStatus;

    @FXML
    private Button buttonDelete;

    @FXML
    private Button buttonRead;

    @FXML
    private Button buttonReadByID;

    @FXML
    private Button buttonReadByStatus;

    @FXML
    private Button buttonUpdate;

    @FXML
    private Button buttonReadByStatusDate;

    /**
     *
     * Quiz 4 begin
     */
    @FXML
    private TextField textboxStatus;

    @FXML
    private TableView<Status> statusTable;
    @FXML
    private TableColumn<Status, String> statusStatus;
    @FXML
    private TableColumn<Status, Integer> statusID;
    @FXML
    private TableColumn<Status, Date> statusDatePosted;

    // the observable list of students that is used to insert data into the table
    private ObservableList<Status> statusData;

    // add the proper data to the observable list to be rendered in the table
    public void setTableData(List<Status> statusList) {

        // initialize the studentData variable
        statusData = FXCollections.observableArrayList();

        // add the student objects to an observable list object for use with the GUI table
        statusList.forEach(s -> {
            statusData.add(s);
        });

        // set the the table items to the data in studentData; refresh the table
        statusTable.setItems(statusData);
        statusTable.refresh();
    }

    @FXML
    void searchByStatusAction(ActionEvent event) {
        System.out.println("clicked");
        
        // getting the name from input box     
        //Had the search feature done because of the source code
        String status = textboxStatus.getText();

        // calling a db read operaiton, readByName
        List<Status> statuses = readByStatus(status);

        if (statuses == null || statuses.isEmpty()) {

            // show an alert to inform user 
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Information Dialog Box");// line 2
            alert.setHeaderText("This is header section to write heading");// line 3
            alert.setContentText("No status");// line 4
            alert.showAndWait(); // line 5
        } else {

            // setting table data
            setTableData(statuses);
        }
        

    }

    @FXML
    void searchByStatusAdvancedAction(ActionEvent event) {
        System.out.println("clicked");

        //Had the advance search feature done because of the source code
        // getting the name from input box        
        String status = textboxStatus.getText();

        // calling a db read operaiton, readByName
        List<Status> statuses = readByStatusAdvanced(status);

        // setting table data
        //setTableData(students);
        // add an alert
        if (statuses == null || statuses.isEmpty()) {

            // show an alert to inform user 
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Information Dialog Box");// line 2
            alert.setHeaderText("This is header section to write heading");// line 3
            alert.setContentText("No status");// line 4
            alert.showAndWait(); // line 5
        } else {
            // setting table data
            setTableData(statuses);
        }

    }

    @FXML
    void actionShowDetails(ActionEvent event) throws IOException {
        System.out.println("clicked");
        
 
        


    }

    @FXML
    void actionShowDetailsInPlace(ActionEvent event) throws IOException {
  
    }

    /**
     *
     * @param Quiz 4 end
     */
    @FXML
    void createStatus(ActionEvent event) {
        Scanner input = new Scanner(System.in);

        // read input from command line
        System.out.println("Enter ID:");
        int id = input.nextInt();

        System.out.println("Enter Status:");
        String garbage = input.nextLine();
        String statusInput = input.nextLine();

        System.out.println("Enter Date (yyyy-mm-dd):");
        String date = input.next();

        // create a student instance
        Status status = new Status();

        // set properties
        status.setId(id);
        status.setStatus(statusInput);
        status.setDate(date);

        // save this student to databse by calling Create operation        
        create(status);

    }

    @FXML
    void deleteStatus(ActionEvent event) {
        Scanner input = new Scanner(System.in);

        // read input from command line
        System.out.println("Enter ID:");
        int id = input.nextInt();

        Status s = readById(id);
        System.out.println("we are deleting this status: " + s.toString());
        delete(s);

    }

    @FXML
    void readByID(ActionEvent event) {
        Scanner input = new Scanner(System.in);

        // read input from command line
        System.out.println("Enter ID:");
        int id = input.nextInt();

        Status s = readById(id);
        System.out.println(s.toString());

    }

    @FXML
    void readByStatus(ActionEvent event) {
        Scanner input = new Scanner(System.in);

        // read input from command line
        System.out.println("Enter Status:");
        String status = input.next();

        List<Status> s = readByStatus(status);
        System.out.println(s.toString());

    }

    @FXML
    void readByStatusDate(ActionEvent event) {
        // name and cpga

        Scanner input = new Scanner(System.in);

        // read input from command line
        System.out.println("Enter Status:");
        String status = input.next();

        System.out.println("Enter Date (yyyy-mm-dd):");
        String date = input.next();

        // create a student instance      
        List<Status> statuses = readByStatusAndDate(status, date);

    }

    @FXML
    void readStatus(ActionEvent event) {
        System.out.println("I am working!");

    }

    @FXML
    void updateStatus(ActionEvent event) {
        Scanner input = new Scanner(System.in);

        // read input from command line
        System.out.println("Enter ID:");
        int id = input.nextInt();

        System.out.println("Enter Status:");
        String garbage = input.nextLine();
        String statusInput = input.next();

        System.out.println("Enter Date (yyyy-mm-dd):");
        String date = input.next();

        // create a student instance
        Status status = new Status();

        
        // set properties
        status.setId(id);
        status.setStatus(statusInput);
        status.setDate(date);

        // save this student to databse by calling Create operation        
        update(status);

    }

    @FXML
    private void handleButtonAction(ActionEvent event) {
        System.out.println("You clicked me!");
        label.setText("Hello World!");

        Query query = manager.createNamedQuery("Status.findAll");
        List<Status> data = query.getResultList();

        for (Status s : data) {
            System.out.println(s.getId() + " " + s.getStatus() + " " + s.getDate());
        }
    }

    EntityManager manager;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // loading students from database
        //database reference: "IntroJavaFXPU"
        manager = (EntityManager) Persistence.createEntityManagerFactory("IntroJavaFXPU").createEntityManager();

        /**
         * Quiz 4 begin
         */
        // set the cell value factories for the TableView Columns
        statusStatus.setCellValueFactory(new PropertyValueFactory<>("Status"));
        statusID.setCellValueFactory(new PropertyValueFactory<>("Id"));
        statusDatePosted.setCellValueFactory(new PropertyValueFactory<>("Date"));

        //eanble row selection
        // (SelectionMode.MULTIPLE);
        statusTable.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
//        studentTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        /**
         * Quiz 4 end
         */
    }

    /*
    Implementing CRUD operations
     */
    // Create operation
    //
    public void create(Status status) {
        try {
            // begin transaction
            manager.getTransaction().begin();

            // sanity check
            if (status.getId() != null) {

                // create student
                manager.persist(status);

                // end transaction
                manager.getTransaction().commit();

                System.out.println(status.toString() + " is created");
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    // Read Operations
    public List<Status> readAll() {
        Query query = manager.createNamedQuery("Status.findAll");
        List<Status> statuses = query.getResultList();

        for (Status status : statuses) {
            System.out.println(status.getId() + " " + status.getStatus() + " " + status.getDate());
        }

        return statuses;
    }

    public Status readById(int id) {
        Query query = manager.createNamedQuery("Status.findById");

        // setting query parameter
        query.setParameter("id", id);

        // execute query
        Status status = (Status) query.getSingleResult();
        if (status != null) {
            System.out.println(status.getId() + " " + status.getStatus() + " " + status.getDate());
        }

        return status;
    }

    public List<Status> readByStatus(String statusInput) {
        Query query = manager.createNamedQuery("Status.findByStatus");

        // setting query parameter
        query.setParameter("status", statusInput);

        // execute query
        List<Status> statuses = query.getResultList();
        for (Status status : statuses) {
            System.out.println(status.getId() + " " + status.getStatus() + " " + status.getDate());
        }

        return statuses;
    }

    /**
     *
     * Quiz 4 begin
     */
    public List<Status> readByStatusAdvanced(String statusInput) {
        Query query = manager.createNamedQuery("Status.findByStatusAdvanced");

        // setting query parameter
        query.setParameter("status", statusInput);

        // execute query
        List<Status> statuses = query.getResultList();
        for (Status status : statuses) {
            System.out.println(status.getId() + " " + status.getStatus() + " " + status.getDate());
        }

        return statuses;
    }

    /**
     *
     * Quiz 4 end
     */
    public List<Status> readByStatusAndDate(String statusInput, String date) {
        Query query = manager.createNamedQuery("Status.findByStatusAndDate");

        // setting query parameter
        query.setParameter("date", date);
        query.setParameter("status", statusInput);

        // execute query
        List<Status> statuses = query.getResultList();
        for (Status status : statuses) {
            System.out.println(status.getId() + " " + status.getStatus() + " " + status.getDate());
        }

        return statuses;
    }

    // Update operation
    public void update(Status model) {
        try {

            Status existingStatus = manager.find(Status.class, model.getId());

            if (existingStatus != null) {
                // begin transaction
                manager.getTransaction().begin();

                // update all atttributes
                existingStatus.setStatus(model.getStatus());
                existingStatus.setDate(model.getDate());

                // end transaction
                manager.getTransaction().commit();
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    // Delete operation
    public void delete(Status status) {
        try {
            Status existingStatus = manager.find(Status.class, status.getId());

            // sanity check
            if (existingStatus != null) {

                // begin transaction
                manager.getTransaction().begin();

                //remove student
                manager.remove(existingStatus);

                // end transaction
                manager.getTransaction().commit();
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

}
