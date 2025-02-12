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
    private Scene scene;
    private Parent root;
    @FXML
    private Label ctd;
    @FXML
    private Button onSignout;
    @FXML
    private TableView<Clients> clientCommissionTV;
    @FXML
    private TableView<Orders> eventCommissionTV;
    @FXML
    private Button displayHomepage;
    @FXML
    private Button deleteButton;
    @FXML
    private Button promoteButton;
    @FXML
    private Button addAccountButton;
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
    private PieChart pieChart;
    @FXML
    private BarChart barChart;
    @FXML private Button displayProfile;




    public void initialize() {
        //for each venue, get countt he amount of times each venue name appears in the orders list, and display name and count
        ArrayList<Venues> venuesAll = getVenues();
        ArrayList<String> venues = venuesAll.stream()
                .map(Venues::getVenueName)
                .collect(Collectors.toCollection(ArrayList::new));

        ArrayList<Orders> ordersAll = getOrders();

        int countA;
        for(String a : venues) {
            countA = 0;
            for (Orders b : ordersAll) {
                if(a.equals(b.getVenueName())){
                    System.out.println(a);
                    System.out.println(b.getVenueName());
                    countA += 1;
                }
            }
            pieChart.getData().add(new PieChart.Data(a, countA));
            System.out.println("Venue: " + a + " Orders: " + countA);
        }

        //get orders`
        //for each EVENT NAME in orders
            //get price and get commsison
            //new bar chart data = (event name, price, comssions)

        String name;
        XYChart.Series prices = new XYChart.Series();
        XYChart.Series commissions = new XYChart.Series();
        prices.setName("Income $");
        commissions.setName("Commission $");

        for (Orders o : ordersAll) {
            name = o.getVenueName();
            prices.getData().add(new XYChart.Data(name, o.getPrice()));
            commissions.getData().add(new XYChart.Data(name, o.getCommission()));

        }
        barChart.getData().addAll(prices, commissions);


        //tableview client commissions

        ArrayList<Clients> clients = getClients();
        ObservableList<Clients> commissionData = FXCollections.observableArrayList();

        for (Clients c : clients) {
            float totalCommission = 0;
            for(Orders o : ordersAll) {
               if (c.getClientName().equals(o.getClientName())) {
                   totalCommission += o.getCommission();
               }
            }
            c.setClientCommission(totalCommission);
            commissionData.add(c);
            System.out.println("Client: " + c.getClientName() + " Commission: " + c.getClientCommission());
        }


        TableColumn<Clients, String> clientNameColumn = new TableColumn<>("Client Name");
        clientNameColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getClientName()) // Wrap clientName in SimpleStringProperty
        );

        TableColumn<Clients, Float> commissionColumn = new TableColumn<>("Total Commission");
        commissionColumn.setCellValueFactory(cellData ->
                new SimpleFloatProperty(cellData.getValue().getClientCommission()).asObject() // Wrap totalCommission in SimpleFloatProperty and convert to Object
        );

        clientCommissionTV.getColumns().add(clientNameColumn);
        clientCommissionTV.getColumns().add(commissionColumn);
        clientCommissionTV.setItems(commissionData);

        ObservableList<Orders> eventsData = FXCollections.observableArrayList();
        eventsData.addAll(ordersAll);
        TableColumn<Orders, String> eventNameColumn = new TableColumn<>("Event Name");
        eventNameColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getEventName()) // Wrap clientName in SimpleStringProperty
        );
        TableColumn<Orders, Float> commissionnColumn = new TableColumn<>("Commission");
        commissionnColumn.setCellValueFactory(cellData ->
                new SimpleFloatProperty(cellData.getValue().getCommission()).asObject() // Wrap totalCommission in SimpleFloatProperty and convert to Object
        );
        eventCommissionTV.getColumns().add(eventNameColumn);
        eventCommissionTV.getColumns().add(commissionnColumn);
        eventCommissionTV.setItems(eventsData);

        //commission to date
        float ctdd = 0;
        for (Orders o : ordersAll) {
            ctdd += o.getCommission();
        }
        ctd.setText("The total commission to date is: $" + ctdd);


    }


    public void OnAddAccount(ActionEvent event) {
        if ((addUsernameTF.getText().isEmpty()) || (addPasswordTF.getText().isEmpty())) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Validation Error");
            alert.setHeaderText("Username or Password is empty.");
            alert.setContentText("Please fill in all required fields.");
            alert.showAndWait();
            return;
        }

        // Check if a radio button is selected
        if (!som.isSelected() && !mos.isSelected()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Validation Error");
            alert.setHeaderText("Role not selected.");
            alert.setContentText("Please select either 'Manager' or 'Staff' for the position.");
            alert.showAndWait();
            return;
        }
        String role = "";


        if (som.isSelected()){
            role = "Staff";
        }
        else if (mos.isSelected()){
            role = "Manager";
        }

        ArrayList<Users> users = getUsers();
        boolean exists = users.stream()
                .anyMatch(p -> p.getUsername().equals(addUsernameTF.getText().trim()) && p.getPassword().equals(addPasswordTF.getText()));
        if (exists) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Validation Error");
            alert.setHeaderText("Username already exists.");
            alert.setContentText("Please choose a different username.");
            alert.showAndWait();
            return;
        }
        else{
            insertUserRow(addUsernameTF.getText(), addPasswordTF.getText(), role);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success! Below user has been added");
            alert.setContentText("Username: " + addUsernameTF.getText() + "\n Password: " + addPasswordTF.getText() + "\n Role: " + role);
            addUsernameTF.clear();
            addPasswordTF.clear();
            som.setSelected(false);
            mos.setSelected(false);
            alert.showAndWait();
        }
    }

    public void OnDeleteUsername(ActionEvent event){
        if ((deleteUsernameTF.getText().isEmpty())) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Validation Error");
            alert.setHeaderText("Username or Password is empty.");
            alert.setContentText("Please fill in all required fields.");
            alert.showAndWait();
            return;
        }

        ArrayList<Users> users = getUsers();
        boolean exists = users.stream()
                .anyMatch(p -> p.getUsername().equals(deleteUsernameTF.getText().trim()));

        if (exists) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("WAIT");
            alert.setContentText("Are you sure you want to delete account: \n Username: " + deleteUsernameTF.getText());
            try {
                if (alert.showAndWait().get() == ButtonType.OK) {
                    deleteUsernameTF.clear();
                    deleteUserRow(deleteUsernameTF.getText());
                }
            //i dont undertsand why the .get() needs the isPresent() or how to add it so added an exception which shouldnt ever trigger
            } catch (NoSuchElementException e) {
                e.printStackTrace();
            }
        }
        else{
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Validation Error");
            alert.setHeaderText("Username does not exist.");
            alert.setContentText("Please choose a different username.");
            alert.showAndWait();
        }
    }

    public void OnPromoteUsername(ActionEvent event){
        if ((promoteUsernameTF.getText().isEmpty())) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Validation Error");
            alert.setHeaderText("Username is empty.");
            alert.setContentText("Please fill in all required fields.");
            alert.showAndWait();
            return;
        }
        ArrayList<Users> users = getUsers();
        boolean exists = users.stream()
                .anyMatch(p -> p.getUsername().equals(promoteUsernameTF.getText().trim()));

        if (exists) {
            //check they are staff
            String position = "";
            for (Users user : users) {
                if (user.getUsername().equalsIgnoreCase(promoteUsernameTF.getText())){
                    position = user.getPosition();
                    System.out.println(position);
                }
            }
            if (position.equalsIgnoreCase("Staff")){
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Promotion Confirmation");
                alert.setHeaderText("User: \"" + promoteUsernameTF.getText() + "\" to be made a Manager?");
                alert.setContentText("Please confirm.");
               try {
                   if (alert.showAndWait().get() == ButtonType.OK) {
                       promoteUser(promoteUsernameTF.getText());
                       promoteUsernameTF.clear();
                   }
               } catch (NoSuchElementException e) {
                   e.printStackTrace();
               }
            }
            else if (position.equalsIgnoreCase("Manager")){
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Validation Error");
                alert.setHeaderText("User is already a Manager.");
                alert.setContentText("Please choose a different username.");
                alert.showAndWait();
            }
        }
        else{
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Validation Error");
            alert.setHeaderText("Username does not exist.");
            alert.setContentText("Please choose a different username.");
        }

    }

    public void displayHomepage(ActionEvent event) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("/com/idk/demo/Views/HomepageView.fxml"));
        Stage stage = (Stage) displayHomepage.getScene().getWindow();
        stage.setWidth(1930);  // Set the window width
        stage.setHeight(1080);  // Set the window height
        stage.setResizable(false);  // Optional: makes the window non-resizable
        stage.centerOnScreen();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void onSignout(ActionEvent event) throws Exception {
        SessionManager.clearSession();
        Parent root = FXMLLoader.load(getClass().getResource("/com/idk/demo/Views/LoginView.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setResizable(true);  // Optional: makes the window non-resizable
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void onExportMD(ActionEvent event) throws Exception {
        //users clients
        try {
            ArrayList<Object> objects = new ArrayList<>();
            objects.addAll(getUsers());
            objects.addAll(getClients());

            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("masterData.lmvm"));
            out.writeObject(objects);
            out.close();
            System.out.println("Master saved successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void onExportT(ActionEvent event) throws Exception {
        //users clients
        try {
            ArrayList<Object> objects = new ArrayList<>();
            objects.addAll(getOrders());
            objects.addAll(getEvents());
            objects.addAll(getVenues());

            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("transactionalData.lmvm"));
            out.writeObject(objects);
            out.close();
            System.out.println("Transactional successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void onDisplayProfile(ActionEvent event) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("/com/idk/demo/Views/UserProfileView.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setWidth(1930);  // Set the window width
        stage.setHeight(1080);  // Set the window height
        stage.setResizable(false);  // Optional: makes the window non-resizable
        stage.centerOnScreen();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void onImportMD(ActionEvent event) throws Exception {
            // Deserialize from file
        try{
            ObjectInputStream in = new ObjectInputStream(new FileInputStream("masterData.lmvm"));
            ArrayList<Object> loadedList = (ArrayList<Object>) in.readObject();
            in.close();

            System.out.println("Deserialized Data:");
            for (Object obj : loadedList) {
                if(obj instanceof Users){
                    insertUserRow(
                            ((Users) obj).getUsername(),
                            ((Users) obj).getPassword(),
                            ((Users) obj).getPosition()
                    );
                }
                //FIX ME! id issues
                else if(obj instanceof Clients){
                    insertClientRow(
                            ((Clients) obj).getClientName(),
                            ((Clients) obj).getClientCommission()
                    );
                }
            }


        }catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    public void onImportT(ActionEvent event) throws Exception {
        // Deserialize from file
        try{
            ObjectInputStream in = new ObjectInputStream(new FileInputStream("transactionalData.lmvm"));
            ArrayList<Object> loadedList = (ArrayList<Object>) in.readObject();
            in.close();

            System.out.println("Deserialized Data:");
            for (Object obj : loadedList) {
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
                //FIX ME! id issues
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
                else if(obj instanceof Venues){
                    insertVenueRow
                            (((Venues) obj).getVenueName(),
                            ((Venues) obj).getCapacity(),
                            ((Venues) obj).getSuitable(),
                            ((Venues) obj).getCategory(),
                            ((Venues) obj).getPrice()
                    );
                }
            }


        }catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}


