package com.idk.demo.Controllers;

import com.idk.demo.Models.*;
import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.*;
import javafx.scene.chart.*;
import javafx.scene.control.*;
import javafx.stage.Stage;
import com.idk.demo.Models.Utility.SessionManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import java.io.*;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import static com.idk.demo.Models.DatabasesInteract.DeleteRow.deleteUserRow;
import static com.idk.demo.Models.DatabasesInteract.GetTables.*;
import static com.idk.demo.Models.DatabasesInteract.EditTables.promoteUser;
import static com.idk.demo.Models.DatabasesInteract.InsertRow.*;


public class ManagerHomepageController {
    private Stage stage;
    @FXML
    private TableView<Clients> clientCommissionTV;
    @FXML
    private TableView<Orders> eventCommissionTV;
    @FXML
    private Button displayHomepage;
    @FXML
    private TextField promoteUsernameTF;
    @FXML
    private TextField deleteUsernameTF;
    @FXML
    private TextField addUsernameTF;
    @FXML
    private TextField addPasswordTF;
    @FXML
    private RadioButton som;
    @FXML
    private RadioButton mos;
    @FXML
    private Label ctd;
    @FXML
    private PieChart pieChart;
    @FXML
    private BarChart barChart;

    //INTIALSIZE EVERYTHING!!!
    public void initialize() {
        // Get all venues from the data source
        ArrayList<Venues> venuesAll = getVenues();

        // Create a list of venue names from the venuesAll list
        ArrayList<String> venues = venuesAll.stream()
                .map(Venues::getVenueName) // Extracts the name of each venue
                .collect(Collectors.toCollection(ArrayList::new)); // Collects the names into a new ArrayList

        // Get all orders from the data source
        ArrayList<Orders> ordersAll = getOrders();

        // Initialize a counter for each venue
        int countA;

        // Loop through each venue and count the number of orders associated with it
        for(String a : venues) {
            countA = 0; // Reset counter for each venue

            // Loop through all orders to check if the order's venue matches the current venue
            for (Orders b : ordersAll) {
                if(a.equals(b.getVenueName())) { // Compare venue name with order's venue name
                    System.out.println(a); // Print the venue name
                    System.out.println(b.getVenueName()); // Print the venue name of the order
                    countA += 1; // Increment the count of orders for this venue
                }
            }

            // Add data to the pie chart for each venue and its corresponding order count
            pieChart.getData().add(new PieChart.Data(a, countA));
            System.out.println("Venue: " + a + " Orders: " + countA); // Print the venue and order count
        }

        // Initialize series for bar chart: one for income and one for commission
        String name;
        XYChart.Series prices = new XYChart.Series();
        XYChart.Series commissions = new XYChart.Series();
        prices.setName("Income $"); // Set the name of the income series
        commissions.setName("Commission $"); // Set the name of the commission series

        // Loop through orders and add data points to the income and commission series
        for (Orders o : ordersAll) {
            name = o.getVenueName(); // Get the venue name from the order
            prices.getData().add(new XYChart.Data(name, o.getPrice())); // Add income data point
            commissions.getData().add(new XYChart.Data(name, o.getCommission())); // Add commission data point
        }

        // Add both series (income and commission) to the bar chart
        barChart.getData().addAll(prices, commissions);

        // TableView to display client commissions
        ArrayList<Clients> clients = getClients(); // Get all clients
        ObservableList<Clients> commissionData = FXCollections.observableArrayList(); // Create observable list for client data

        // Loop through each client and calculate their total commission
        for (Clients c : clients) {
            float totalCommission = 0; // Initialize total commission for the client
            for(Orders o : ordersAll) {
                // Check if the order belongs to the current client
                if (c.getClientName().equals(o.getClientName())) {
                    totalCommission += o.getCommission(); // Add the order's commission to the client's total
                }
            }
            c.setClientCommission(totalCommission); // Set the calculated total commission for the client
            commissionData.add(c); // Add the client with their total commission to the list
            System.out.println("Client: " + c.getClientName() + " Commission: " + c.getClientCommission()); // Print client and their commission
        }

        // Set up columns for displaying client names and their total commissions in the table view
        TableColumn<Clients, String> clientNameColumn = new TableColumn<>("Client Name");
        clientNameColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getClientName()) // Wrap clientName in SimpleStringProperty for display
        );

        TableColumn<Clients, Float> commissionColumn = new TableColumn<>("Total Commission");
        commissionColumn.setCellValueFactory(cellData ->
                new SimpleFloatProperty(cellData.getValue().getClientCommission()).asObject() // Wrap totalCommission in SimpleFloatProperty and convert to Object
        );

        // Add the columns to the table view for client commissions
        clientCommissionTV.getColumns().add(clientNameColumn);
        clientCommissionTV.getColumns().add(commissionColumn);
        clientCommissionTV.setItems(commissionData); // Set the table view's data

        // Create and populate table for orders and event commissions
        ObservableList<Orders> eventsData = FXCollections.observableArrayList(); // Create observable list for orders
        eventsData.addAll(ordersAll); // Add all orders to the list

        // Set up columns for displaying event names and their commissions in the table view
        TableColumn<Orders, String> eventNameColumn = new TableColumn<>("Event Name");
        eventNameColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getEventName()) // Wrap event name in SimpleStringProperty for display
        );

        TableColumn<Orders, Float> commissionnColumn = new TableColumn<>("Commission");
        commissionnColumn.setCellValueFactory(cellData ->
                new SimpleFloatProperty(cellData.getValue().getCommission()).asObject() // Wrap commission in SimpleFloatProperty and convert to Object
        );

        // Add the columns to the table view for event commissions
        eventCommissionTV.getColumns().add(eventNameColumn);
        eventCommissionTV.getColumns().add(commissionnColumn);
        eventCommissionTV.setItems(eventsData); // Set the table view's data for event commissions

        // Calculate and display total commission to date
        float ctdd = 0; // Initialize total commission variable
        for (Orders o : ordersAll) {
            ctdd += o.getCommission(); // Add each order's commission to the total
        }

        // Update the label with the total commission to date
        ctd.setText("The total commission to date is: $" + ctdd);
    }

public void OnAddAccount(ActionEvent event) {
    // Check if either username or password field is empty
    if ((addUsernameTF.getText().isEmpty()) || (addPasswordTF.getText().isEmpty())) {
        // Display a warning if fields are empty
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Validation Error");
        alert.setHeaderText("Username or Password is empty.");
        alert.setContentText("Please fill in all required fields.");
        alert.showAndWait();
        return;
    }

    // Check if a role (Staff or Manager) is selected
    if (!som.isSelected() && !mos.isSelected()) {
        // Display a warning if no role is selected
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Validation Error");
        alert.setHeaderText("Role not selected.");
        alert.setContentText("Please select either 'Manager' or 'Staff' for the position.");
        alert.showAndWait();
        return;
    }

    String role = "";

    // Determine the selected role
    if (som.isSelected()){
        role = "Staff";
    }
    else if (mos.isSelected()){
        role = "Manager";
    }

    // Get the list of existing users
    ArrayList<Users> users = getUsers();

    // Check if a user already exists with the given username and password
    boolean exists = users.stream()
            .anyMatch(p -> p.getUsername().equals(addUsernameTF.getText().trim()) && p.getPassword().equals(addPasswordTF.getText()));

    // If user already exists, show a warning
    if (exists) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Validation Error");
        alert.setHeaderText("Username already exists.");
        alert.setContentText("Please choose a different username.");
        alert.showAndWait();
        return;
    }
    else{
        // Insert new user into the database and show a success message
        insertUserRow(addUsernameTF.getText(), addPasswordTF.getText(), role);
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success! Below user has been added");
        alert.setContentText("Username: " + addUsernameTF.getText() + "\n Password: " + addPasswordTF.getText() + "\n Role: " + role);

        // Clear the input fields and reset the role selection
        addUsernameTF.clear();
        addPasswordTF.clear();
        som.setSelected(false);
        mos.setSelected(false);
        alert.showAndWait();
    }
}

    public void OnDeleteUsername(ActionEvent event){
        // Check if the delete username field is empty
        if ((deleteUsernameTF.getText().isEmpty())) {
            // Display a warning if username is empty
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Validation Error");
            alert.setHeaderText("Username or Password is empty.");
            alert.setContentText("Please fill in all required fields.");
            alert.showAndWait();
            return;
        }

        // Get the list of existing users
        ArrayList<Users> users = getUsers();

        // Check if the username exists in the system
        boolean exists = users.stream()
                .anyMatch(p -> p.getUsername().equals(deleteUsernameTF.getText().trim()));

        // If username exists, show confirmation dialog to delete the user
        if (exists) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("WAIT");
            alert.setContentText("Are you sure you want to delete account: \n Username: " + deleteUsernameTF.getText());

            try {
                // If confirmed, delete the user and clear the field
                if (alert.showAndWait().get() == ButtonType.OK) {
                    deleteUsernameTF.clear();
                    deleteUserRow(deleteUsernameTF.getText());
                }
                // Catch exception if the user cancels or an error occurs
            } catch (NoSuchElementException e) {
                e.printStackTrace();
            }
        }
        else{
            // If username does not exist, show a warning
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Validation Error");
            alert.setHeaderText("Username does not exist.");
            alert.setContentText("Please choose a different username.");
            alert.showAndWait();
        }
    }

    public void OnPromoteUsername(ActionEvent event){
        // Check if the promote username field is empty
        if ((promoteUsernameTF.getText().isEmpty())) {
            // Display a warning if the field is empty
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Validation Error");
            alert.setHeaderText("Username is empty.");
            alert.setContentText("Please fill in all required fields.");
            alert.showAndWait();
            return;
        }

        // Get the list of existing users
        ArrayList<Users> users = getUsers();

        // Check if the username exists in the system
        boolean exists = users.stream()
                .anyMatch(p -> p.getUsername().equals(promoteUsernameTF.getText().trim()));

        // If username exists, proceed with promotion logic
        if (exists) {
            // Get the current position of the user (Staff or Manager)
            String position = "";
            for (Users user : users) {
                if (user.getUsername().equalsIgnoreCase(promoteUsernameTF.getText())){
                    position = user.getPosition();
                    System.out.println(position);
                }
            }

            // If the user is a staff member, show promotion confirmation
            if (position.equalsIgnoreCase("Staff")){
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Promotion Confirmation");
                alert.setHeaderText("User: \"" + promoteUsernameTF.getText() + "\" to be made a Manager?");
                alert.setContentText("Please confirm.");
                try {
                    // If confirmed, promote the user
                    if (alert.showAndWait().get() == ButtonType.OK) {
                        promoteUser(promoteUsernameTF.getText());
                        promoteUsernameTF.clear();
                    }
                } catch (NoSuchElementException e) {
                    e.printStackTrace();
                }
            }
            // If the user is already a Manager, show a warning
            else if (position.equalsIgnoreCase("Manager")){
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Validation Error");
                alert.setHeaderText("User is already a Manager.");
                alert.setContentText("Please choose a different username.");
                alert.showAndWait();
            }
        }
        else{
            // If the username does not exist, show a warning
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Validation Error");
            alert.setHeaderText("Username does not exist.");
            alert.setContentText("Please choose a different username.");
        }
    }

    public void displayHomepage(ActionEvent event) throws Exception {
        // Load the homepage view and display it in a new window
        Parent root = FXMLLoader.load(getClass().getResource("/com/idk/demo/Views/HomepageView.fxml"));
        Stage stage = (Stage) displayHomepage.getScene().getWindow();

        // Set window size and other properties
        stage.setWidth(1930);  // Set the window width
        stage.setHeight(1080);  // Set the window height
        stage.setResizable(false);  // Optional: makes the window non-resizable
        stage.centerOnScreen();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void onSignout(ActionEvent event) throws Exception {
        // Clear the session and load the login view
        SessionManager.clearSession();
        Parent root = FXMLLoader.load(getClass().getResource("/com/idk/demo/Views/LoginView.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();

        // Make the window resizable and show the login screen
        stage.setResizable(true);  // Optional: makes the window non-resizable
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void onDisplayProfile(ActionEvent event) throws Exception {
        // Load the user profile view and display it in a new window
        Parent root = FXMLLoader.load(getClass().getResource("/com/idk/demo/Views/UserProfileView.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();

        // Set window size and other properties
        stage.setWidth(1930);  // Set the window width
        stage.setHeight(1080);  // Set the window height
        stage.setResizable(false);  // Optional: makes the window non-resizable
        stage.centerOnScreen();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }


    public void onExportMD(ActionEvent event) throws Exception {
    // Export master data (users and clients)
    try {
        // Create an ArrayList to store objects to be exported
        ArrayList<Object> objects = new ArrayList<>();

        // Add users and clients to the list
        objects.addAll(getUsers());
        objects.addAll(getClients());

        // Create an ObjectOutputStream to write objects to a file
        ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("masterData.lmvm"));

        // Write the objects to the file
        out.writeObject(objects);
        out.close();

        // Print a message to indicate successful save
        System.out.println("Master saved successfully.");
    } catch (IOException e) {
        // If there's an error during file writing, print the stack trace
        e.printStackTrace();
    }
}

    public void onExportT(ActionEvent event) throws Exception {
        // Export transactional data (orders, events, venues)
        try {
            // Create an ArrayList to store objects to be exported
            ArrayList<Object> objects = new ArrayList<>();

            // Add orders, events, and venues to the list
            objects.addAll(getOrders());
            objects.addAll(getEvents());
            objects.addAll(getVenues());

            // Create an ObjectOutputStream to write objects to a file
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("transactionalData.lmvm"));

            // Write the objects to the file
            out.writeObject(objects);
            out.close();

            // Print a message to indicate successful save
            System.out.println("Transactional successfully.");
        } catch (IOException e) {
            // If there's an error during file writing, print the stack trace
            e.printStackTrace();
        }
    }

    public void onImportMD(ActionEvent event) throws Exception {
        // Import master data (users and clients)
        try {
            // Create an ObjectInputStream to read objects from a file
            ObjectInputStream in = new ObjectInputStream(new FileInputStream("masterData.lmvm"));

            // Deserialize the objects from the file into a list
            ArrayList<Object> loadedList = (ArrayList<Object>) in.readObject();
            in.close();

            // Print the deserialized data for verification
            System.out.println("Deserialized Data:");

            // Loop through each object in the loaded list
            for (Object obj : loadedList) {
                // If the object is an instance of Users, insert it into the database
                if(obj instanceof Users){
                    insertUserRow(
                            ((Users) obj).getUsername(), // Insert user data
                            ((Users) obj).getPassword(),
                            ((Users) obj).getPosition()
                    );
                }
                // If the object is an instance of Clients, insert it into the database
                // FIX: There might be issues with client IDs
                else if(obj instanceof Clients){
                    insertClientRow(
                            ((Clients) obj).getClientName(),
                            ((Clients) obj).getClientCommission()
                    );
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            // If there's an error during deserialization or class casting, print the stack trace
            e.printStackTrace();
        }
    }

    public void onImportT(ActionEvent event) throws Exception {
        // Import transactional data (orders, events, venues)
        try {
            // Create an ObjectInputStream to read objects from a file
            ObjectInputStream in = new ObjectInputStream(new FileInputStream("transactionalData.lmvm"));

            // Deserialize the objects from the file into a list
            ArrayList<Object> loadedList = (ArrayList<Object>) in.readObject();
            in.close();

            // Print the deserialized data for verification
            System.out.println("Deserialized Data:");

            // Loop through each object in the loaded list
            for (Object obj : loadedList) {
                // If the object is an instance of Orders, insert it into the database
                if(obj instanceof Orders){
                    insertOrderRow(
                            ((Orders) obj).getEventName(),
                            ((Orders) obj).getVenueName(),
                            ((Orders) obj).getOrderDate(),
                            ((Orders) obj).getDuration(),
                            ((Orders) obj).getPrice(),
                            ((Orders) obj).getCommission(),
                            ((Orders) obj).getClientName());
                }
                // If the object is an instance of Events, insert it into the database
                // FIX: There might be issues with event IDs
                else if(obj instanceof Events){
                    insertEventRow(
                            ((Events) obj).getClientName(),
                            ((Events) obj).getEventName(),
                            ((Events) obj).getMainArtist(),
                            ((Events) obj).getDate(),
                            ((Events) obj).getTime(),
                            ((Events) obj).getDuration(),
                            ((Events) obj).getAudienceSize(),
                            ((Events) obj).getType(),
                            ((Events) obj).getCategory(),
                            ((Events) obj).isConfirmed()
                    );
                }
                // If the object is an instance of Venues, insert it into the database
                else if(obj instanceof Venues){
                    insertVenueRow(
                            ((Venues) obj).getVenueName(),
                            ((Venues) obj).getCapacity(),
                            ((Venues) obj).getSuitable(),
                            ((Venues) obj).getCategory(),
                            ((Venues) obj).getPrice()
                    );
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            // If there's an error during deserialization or class casting, print the stack trace
            e.printStackTrace();
        }
    }
}


