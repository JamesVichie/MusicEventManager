package com.idk.demo.Controllers;

import com.idk.demo.Models.Events;
import com.idk.demo.Models.Orders;
import com.idk.demo.Models.Utility.SessionManager;
import com.idk.demo.Models.Venues;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import org.w3c.dom.Text;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import java.io.*;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

import static com.idk.demo.Models.DatabasesInteract.EditTables.updateConfirmed;
import static com.idk.demo.Models.DatabasesInteract.GetTables.*;
import static com.idk.demo.Models.DatabasesInteract.InsertRow.*;


public class HomepageController {
    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    private Button signout;
    @FXML
    private Button addVenueButton;
    @FXML
    private Button managerOptions;
    @FXML
    private Button addVenue;
    @FXML
    private ListView<String> venueList;
    @FXML
    private TextField searchVenueTF;
    @FXML
    private ComboBox<String> venueFilterCategoryDL;
    @FXML
    private TextField venueFilterMin;
    @FXML
    private TextField venueFilterMax;
    @FXML
    private ComboBox<String> venueFilterEventType;
    @FXML
    private ComboBox<String> venueFilterAvalibility;
    @FXML
    private Button importVenuesButton;
    @FXML
    private Button importEventRequestsButton;

    private ObservableList<String> venueNames;
    @FXML
    private Button displayProfile;
    @FXML
    private ListView<String> eventList;
    @FXML
    private ListView<String> confirmedBookingList;
    @FXML private Button confirmRequestButton;
    @FXML
    public void initialize() {


        setIntegerOnly(venueFilterMax);
        setIntegerOnly(venueFilterMin);

        //EVENT LISRT
        ArrayList<Events> eventsAll = getEvents();
        ArrayList<String> events = eventsAll.stream()
                .filter(event -> !event.isConfirmed()) // Filter only unconfirmed events
                .map(Events::getEventName)             // Map to event names
                .collect(Collectors.toCollection(ArrayList::new));

        ArrayList<String> confirmedEvents = eventsAll.stream()
                .filter(Events::isConfirmed) // Filter only unconfirmed events
                .map(Events::getEventName)             // Map to event names
                .collect(Collectors.toCollection(ArrayList::new));

        confirmedBookingList.setItems(FXCollections.observableArrayList(confirmedEvents));
        eventList.setItems(FXCollections.observableArrayList(events));

        eventList.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) { // Double-click detected
                String selectedEvent = eventList.getSelectionModel().getSelectedItem();
                if (selectedEvent != null) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Event Selected");
                    alert.setHeaderText(null);
                    Events selectedEventNow = getEvent(selectedEvent);
                    try {
                        alert.setContentText(
                                  "Client Name: " + selectedEventNow.getClientName()
                                + "\nEvent Name: " + selectedEventNow.getEventName()
                                + "\nMain Artist: " + selectedEventNow.getMainArtist()
                                + "\nDate: " + selectedEventNow.getDate()
                                + "\nTime: " + selectedEventNow.getTime()
                                + "\nDuration: " + selectedEventNow.getDuration() + " hours"
                                + "\nAudience Size: " + selectedEventNow.getAudienceSize()
                                + "\nType: " + selectedEventNow.getType()
                                + "\nCategory: " + selectedEventNow.getCategory()
                                + "\nConfirmed: " + selectedEventNow.isConfirmed());
                    }
                    catch (NullPointerException e){
                        Alert alert2 = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Sorry, Error with venue");
                        alert.setHeaderText("Error loading venue");
                        alert.setContentText("Im very sorry");
                        alert.showAndWait();
                    }

                    alert.showAndWait();
                }
            }
        });

        confirmedBookingList.setOnMouseClicked(event -> {
            //get order where event name is selected eventname
            //get all order details and print :)
            ArrayList<Orders> ordersAll = getOrders();



            if (event.getClickCount() == 2) { // Double-click detected
                String selectedEvent = confirmedBookingList.getSelectionModel().getSelectedItem();

                if (selectedEvent != null) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Event Selected");
                    alert.setHeaderText(null);
                    Events selectedEventNow = getEvent(selectedEvent);
                    Orders order = ordersAll.stream()
                            .filter(o -> o.getEventName().equals(selectedEventNow.getEventName())) // Filter by event name
                            .findFirst() // Get the first matching order
                            .orElse(null); // Return null if not found;
                    try {
                        alert.setContentText(
                                "Client Name: " + selectedEventNow.getClientName()
                                        + "\nEvent Name: " + selectedEventNow.getEventName()
                                        + "\nMain Artist: " + selectedEventNow.getMainArtist()
                                        + "\nDate: " + selectedEventNow.getDate()
                                        + "\nTime: " + selectedEventNow.getTime()
                                        + "\nDuration: " + selectedEventNow.getDuration() + " hours"
                                        + "\nAudience Size: " + selectedEventNow.getAudienceSize()
                                        + "\nType: " + selectedEventNow.getType()
                                        + "\nCategory: " + selectedEventNow.getCategory()
//                                        + "\nConfirmed: " + selectedEventNow.isConfirmed()
                                        + "\n\nVenue: " + order.getVenueName()
                                        + "\nPrice: " + order.getPrice()
                                        + "\nCommission Earned: " + order.getCommission());
                    }
                    catch (NullPointerException e){
                        Alert alert2 = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Sorry, Error with venue");
                        alert.setHeaderText("Error loading venue");
                        alert.setContentText("Im very sorry");
                        alert.showAndWait();
                    }

                    alert.showAndWait();
                }
            }
        });

        //VENUE LIST
        ArrayList<Venues> venuesAll = getVenues();
        ArrayList<String> venues = venuesAll.stream()
                .map(Venues::getVenueName)
                .collect(Collectors.toCollection(ArrayList::new));

        Set<String> categories = getVenues().stream()
                .map(Venues::getCategory)
                .collect(Collectors.toSet());

        Set<String> eventTypes = getVenues().stream()
                .map(Venues::getSuitable)
                .collect(Collectors.toSet());

        venueFilterEventType.getItems().add("All");
        venueFilterEventType.getItems().addAll(eventTypes);

        venueFilterCategoryDL.getItems().add("All");
        venueFilterCategoryDL.getItems().addAll(categories);
        venueNames = FXCollections.observableArrayList(venues);

        // Wrap in a FilteredList
        FilteredList<String> filteredList = new FilteredList<>(venueNames);

        searchVenueTF.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredList.setPredicate(venue -> {
                String selectedCategory = venueFilterCategoryDL.getValue();
                String selectedEvent = venueFilterEventType.getValue();

                return matchesFilter(venue, newValue, selectedCategory, selectedEvent, venueFilterMin.getText(), venueFilterMax.getText()); // Call the helper method
            });
        });

        venueFilterCategoryDL.valueProperty().addListener((observable, oldCategory, newCategory) -> {
            filteredList.setPredicate(venue -> {
                String searchText = searchVenueTF.getText();
                String selectedEvent = venueFilterEventType.getValue();

                return matchesFilter(venue, searchText, newCategory, selectedEvent, venueFilterMin.getText(), venueFilterMax.getText()); // Call the helper method
            });
        });

        venueFilterEventType.valueProperty().addListener((observable, oldValue, newEventType) -> {
            filteredList.setPredicate(venue -> {
                String searchText = searchVenueTF.getText();
                String selectedCategory = venueFilterCategoryDL.getValue();

                return matchesFilter(venue, searchText, selectedCategory, newEventType, venueFilterMin.getText(), venueFilterMax.getText()); // Call the helper method
            });
        });

        venueFilterMax.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredList.setPredicate(venue -> {
                String searchText = searchVenueTF.getText();
                String selectedCategory = venueFilterCategoryDL.getValue();
                String selectedEvent = venueFilterEventType.getValue();

                return matchesFilter(venue, searchText, selectedCategory, selectedEvent, venueFilterMin.getText(), newValue); // Pass min and max capacity values
            });
        });

        venueFilterMin.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredList.setPredicate(venue -> {
                String searchText = searchVenueTF.getText();
                String selectedCategory = venueFilterCategoryDL.getValue();
                String selectedEvent = venueFilterEventType.getValue();

                return matchesFilter(venue, searchText, selectedCategory, selectedEvent, newValue, venueFilterMax.getText()); // Pass min and max capacity values
            });
        });


        // Set filtered list in ListView
        venueList.setItems(filteredList);

        // Add double-click event listener
        venueList.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) { // Double-click detected
                String selectedVenue = venueList.getSelectionModel().getSelectedItem();
                if (selectedVenue != null) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Venue Selected");
                    alert.setHeaderText(null);
                    Venues selectedVenueNow = getVenue(selectedVenue);

                    try {
                        alert.setContentText("Venue Name: " + selectedVenueNow.getVenueName()
                        + "\nVenue ID: " + selectedVenueNow.getVenueID()
                        + "\nCapacity: " + selectedVenueNow.getCapacity()
                        + "\nSuitable for: " + selectedVenueNow.getSuitable()
                        + "\nCategory: " + selectedVenueNow.getCategory()
                        + "\nPrice: " + selectedVenueNow.getPrice());

                    }
                    catch (NullPointerException e){
                        Alert alert2 = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Sorry, Error with venue");
                        alert.setHeaderText("Error loading venue");
                        alert.setContentText("Im very sorry");
                        alert.showAndWait();
                    }

                    alert.showAndWait();
                }
            }
        });
    }

private boolean matchesFilter(String venue, String searchText, String selectedCategory, String selectedEvent, String minCapacityText, String maxCapacityText) {
    // Check if venue matches the search term
    boolean matchesSearch = (searchText == null || searchText.isEmpty())
            || venue.toLowerCase().contains(searchText.toLowerCase());

    // Check if venue matches the category filter
    boolean matchesCategory = (selectedCategory == null || selectedCategory.isEmpty() || selectedCategory.equals("All"))
            || getVenues().stream().anyMatch(v -> v.getVenueName().equals(venue) && v.getCategory().equals(selectedCategory));

    // Check if venue matches the event type filter
    boolean matchesEventType = (selectedEvent == null || selectedEvent.isEmpty() || selectedEvent.equals("All"))
            || getVenues().stream().anyMatch(v -> v.getVenueName().equals(venue) && v.getSuitable().equals(selectedEvent));

    // Check if venue matches the minimum capacity filter
    boolean matchesMinCapacity = (minCapacityText == null || minCapacityText.isEmpty())
            || getVenues().stream().anyMatch(v -> v.getVenueName().equals(venue) && v.getCapacity() >= Integer.parseInt(minCapacityText));

    // Check if venue matches the maximum capacity filter
    boolean matchesMaxCapacity = (maxCapacityText == null || maxCapacityText.isEmpty())
            || getVenues().stream().anyMatch(v -> v.getVenueName().equals(venue) && v.getCapacity() <= Integer.parseInt(maxCapacityText));

    return matchesSearch && matchesCategory && matchesEventType && matchesMinCapacity && matchesMaxCapacity;
}

    public void onConfirmRequest(ActionEvent event) throws Exception {
        // Create the dialog
        ArrayList<Venues> venues = getVenues();
        ArrayList<String> venueNames = venues.stream()
                .map(Venues::getVenueName)
                .collect(Collectors.toCollection(ArrayList::new));

        ArrayList<Events> events = getEvents();
        ArrayList<String> eventNames = events.stream()
                .filter(s -> !s.isConfirmed())
                .map(Events::getEventName)
                .collect(Collectors.toCollection(ArrayList::new));


        Dialog<Venues> dialog = new Dialog<>();
        dialog.setTitle("Confirm an event Request");
        dialog.setHeaderText("Please choose a Venue for an Event:");

        // Set dialog content
        Label venueNameLabel = new Label("Venue Name:");
        ComboBox<String> venueNameDL = new ComboBox<>();

        Label eventNameLabel = new Label("Event Name:");
        ComboBox<String> eventNameDL = new ComboBox<>();

        venueNameDL.getItems().addAll(venueNames);
        eventNameDL.getItems().addAll(eventNames);


//        Button confirmButton = new Button("Confirm");
//        Button cancelButton = new Button("Cancel");

        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);

        gridPane.add(venueNameLabel, 0, 0);
        gridPane.add(venueNameDL, 1, 0);

        gridPane.add(eventNameLabel, 0, 1);
        gridPane.add(eventNameDL, 1, 1);

        dialog.getDialogPane().setContent(gridPane);
        ButtonType okButtonType = new ButtonType("Confirm", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(okButtonType, ButtonType.CANCEL);

        // Handle "OK" button
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == okButtonType) {
                if (!venueNameDL.getItems().isEmpty()
                        && !eventNameDL.getItems().isEmpty()) {
                    try {
                        Venues selectedVenue = getVenue(venueNameDL.getSelectionModel().getSelectedItem());
                        Events selectedEvent = getEvent(eventNameDL.getSelectionModel().getSelectedItem());
//                            public Orders(String eventName, String venueName, Date orderDate, int duration, float price, float commission) {

                        assert selectedVenue != null;
                        float commi = selectedVenue.getPrice() / 10;

                        assert selectedEvent != null;
                        insertOrderRow(eventNameDL.getValue(), venueNameDL.getValue(), selectedEvent.getDate(), selectedEvent.getDuration(), selectedVenue.getPrice(), commi, selectedEvent.getClientName());
                        updateConfirmed(eventNameDL.getValue(), true);
//                        System.out.println(venueNameDL.getValue() + "VENUE");
//                        System.out.println(eventNameDL.getValue() + "EVENT");
                        //get event and set the confirmed to true
                    } catch (Exception e) {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Invalid Input");
                        alert.setHeaderText("Error adding ordder");
                        alert.setContentText("Please help.");
                        alert.showAndWait();
                        return null;
                    }
                }
            }
            return null;
        });
        dialog.showAndWait();
        Parent root = FXMLLoader.load(getClass().getResource("/com/idk/demo/Views/HomepageView.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }



    public void OnAddVenue(ActionEvent event) throws Exception {
        // Create the dialog
        Dialog<Venues> dialog = new Dialog<>();
        dialog.setTitle("Add Venue");
        dialog.setHeaderText("Enter the details of the new venue:");

        // Set dialog content
        Label venueNameLabel = new Label("Venue Name:");
        TextField venueNameTF = new TextField();

        Label venueCapacity = new Label("Venue Capacity: (must be an integer)");
        TextField venueCapacityTF = new TextField();

        Label venueSuitabilityLabel = new Label("Venue Suitability:");
        TextField venueSuitabilityTF = new TextField();

        Label venueCategoryLabel = new Label("Venue Category:");
        TextField venueCategoryTF = new TextField();

        Label venuePriceLabel = new Label("Venue Price: (must be a floating point number)");
        TextField venuePriceTF = new TextField();


        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);

        gridPane.add(venueNameLabel, 0, 0);
        gridPane.add(venueNameTF, 1, 0);

        gridPane.add(venueCapacity, 0, 1);
        gridPane.add(venueCapacityTF, 1, 1);

        gridPane.add(venueSuitabilityLabel, 0, 2);
        gridPane.add(venueSuitabilityTF, 1, 2);
//
        gridPane.add(venueCategoryLabel, 0, 3);
        gridPane.add(venueCategoryTF, 1, 3);

        gridPane.add(venuePriceLabel, 0, 4);
        gridPane.add(venuePriceTF, 1, 4);

        dialog.getDialogPane().setContent(gridPane);

        // Add buttons
        ButtonType okButtonType = new ButtonType("Add", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(okButtonType, ButtonType.CANCEL);

        // Handle "OK" button
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == okButtonType) {
                if (       !venueNameTF.getText().isEmpty()
                        && !venueCategoryTF.getText().isEmpty()
                        && !venueCapacityTF.getText().isEmpty()
                        && !venuePriceTF.getText().isEmpty()
                        && !venueSuitabilityTF.getText().isEmpty()) {

                    try {
                        int capacity = Integer.parseInt(venueCapacityTF.getText());
                        float price = Float.parseFloat(venuePriceTF.getText());
//                        return new Venues(venueNameTF.getText(), capacity, venueSuitabilityTF.getText(), venueCategoryTF.getText(), price);
                        insertVenueRow(venueNameTF.getText(), capacity, venueSuitabilityTF.getText(), venueCategoryTF.getText(), price);
                        venueNames.add(venueNameTF.getText());
                        if (!venueFilterCategoryDL.getItems().contains(venueCategoryTF.getText())) {
                            venueFilterCategoryDL.getItems().add(venueCategoryTF.getText());
                        }
                        venueList.setItems(FXCollections.observableArrayList(venueNames));

                    } catch (NumberFormatException e) {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Invalid Input");
                        alert.setHeaderText("Error adding venue");
                        alert.setContentText("Please ensure that capacity is an integer and price is a floating point number.");
                        alert.showAndWait();
                        return null;
                    }
                }
            }
            return null;
        });
        dialog.showAndWait();
    }

    public void onSignout(ActionEvent event) throws Exception {
        SessionManager.clearSession();
        Parent root = FXMLLoader.load(getClass().getResource("/com/idk/demo/Views/LoginView.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void displayManagerMenu(ActionEvent event) throws Exception {
        if (!Objects.requireNonNull(SessionManager.loadSessionPosition()).equalsIgnoreCase("Manager")){
            //displaying warning, eat input
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setHeaderText("You do not have required permissions");
            alert.setContentText("Managers only!");
            alert.showAndWait();


        }
        else{
            Parent root = FXMLLoader.load(getClass().getResource("/com/idk/demo/Views/ManagerHomepageView.fxml"));
            stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            stage.setWidth(1930);  // Set the window width
            stage.setHeight(1080);  // Set the window height
            stage.setResizable(false);  // Optional: makes the window non-resizable
            stage.centerOnScreen();

            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
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

    public void onImportVenues(ActionEvent event) throws Exception {
//        List<Venues> venues = new ArrayList<>();
        InputStream is = getClass().getResourceAsStream("/com/idk/demo/Views/files/venues.csv");

        try {
            assert is != null;
            try (BufferedReader br = new BufferedReader(new InputStreamReader(is))) {
                String line;
                // Skip the header line
                br.readLine();

                while ((line = br.readLine()) != null) {
                    String[] values = line.split(",");  // Split each line by commas
                    String name = values[0];
                    int capacity = Integer.parseInt(values[1]);
                    String suitableFor = values[2];
                    String category = values[3];
                    int bookingPricePerHour = Integer.parseInt(values[4]);
                    insertVenueRow(name, capacity, suitableFor, category, bookingPricePerHour);
//                    Venues venue = new Venues(name, capacity, suitableFor, category, bookingPricePerHour);
//                    venues.add(venue);

                    //        insertVenuesBatch(venues);

                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        Parent root = FXMLLoader.load(getClass().getResource("/com/idk/demo/Views/HomepageView.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }

    public void onImportEventRequests(ActionEvent event) throws Exception {
        InputStream is = getClass().getResourceAsStream("/com/idk/demo/Views/files/requests.csv");

        try {
            assert is != null;
            try (BufferedReader br = new BufferedReader(new InputStreamReader(is))) {
                String line;
                // Skip the header line
                br.readLine();
                while ((line = br.readLine()) != null) {

                    String[] values = line.split(",");  // Split each line by commas
                    String clientName = values[0];
                    String eventName = values[1];
                    String mainArtist = values[2];
                    String date = values[3];
                    String time = values[4];
                    int duration = Integer.parseInt(values[5]);
                    int auidenceSize = Integer.parseInt(values[6]);
                    String type = values[7];
                    String category = values[8];
//                    Boolean confirmed = Boolean.parseBoolean(values[9]);
                    SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");  // Define date format

                   java.util.Date utilDate = sdf.parse(date);
                   java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());

                    insertEventRow(clientName, eventName, mainArtist, sqlDate, time, duration, auidenceSize, type, category, false);

                    //batchAll later. rewokr needed to work
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        Parent root = FXMLLoader.load(getClass().getResource("/com/idk/demo/Views/HomepageView.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }



    private void setIntegerOnly(TextField textField) {
        // Create a TextFormatter with a regular expression that allows only digits
        TextFormatter<String> integerFormatter = new TextFormatter<>(change -> {
            String newText = change.getControlNewText();
            if (newText.matches("[0-9]*")) {
                return change; // Allow the change
            }
            return null; // Reject the change if it's not a digit
        });

        textField.setTextFormatter(integerFormatter);
    }


    
    
}
