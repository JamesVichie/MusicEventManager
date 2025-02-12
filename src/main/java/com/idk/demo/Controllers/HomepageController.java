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
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

import static com.idk.demo.Models.DatabasesInteract.EditTables.updateConfirmed;
import static com.idk.demo.Models.DatabasesInteract.GetTables.*;
import static com.idk.demo.Models.DatabasesInteract.InsertRow.*;

public class HomepageController {
    private Stage stage;
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
    private ObservableList<String> venueNames;
    @FXML
    private ListView<String> eventList;
    @FXML
    private ListView<String> confirmedBookingList;

@FXML
public void initialize() {
    // Set integer-only restriction on text fields for venue filters
    setIntegerOnly(venueFilterMax);
    setIntegerOnly(venueFilterMin);

    // Event List setup
    ArrayList<Events> eventsAll = getEvents();  // Retrieve all events
    ArrayList<String> events = eventsAll.stream()
            .filter(event -> !event.isConfirmed()) // Filter only unconfirmed events
            .map(Events::getEventName)             // Map to event names
            .collect(Collectors.toCollection(ArrayList::new));

    ArrayList<String> confirmedEvents = eventsAll.stream()
            .filter(Events::isConfirmed) // Filter only confirmed events
            .map(Events::getEventName)             // Map to event names
            .collect(Collectors.toCollection(ArrayList::new));

    // Set the event list items in the ListView
    confirmedBookingList.setItems(FXCollections.observableArrayList(confirmedEvents));
    eventList.setItems(FXCollections.observableArrayList(events));

    // Event List Double-click event handler
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
                    alert.showAndWait();
                }
                catch (NullPointerException e) {
                    Alert alert2 = new Alert(Alert.AlertType.ERROR);
                    alert2.setTitle("Sorry, Error with venue");
                    alert2.setHeaderText("Error loading venue");
                    alert2.setContentText("I'm very sorry");
                    alert2.showAndWait();
                }
            }
        }
    });

    // Confirmed Booking List Double-click event handler
    confirmedBookingList.setOnMouseClicked(event -> {
        ArrayList<Orders> ordersAll = getOrders();
        if (event.getClickCount() == 2) { // Double-click detected
            String selectedEvent = confirmedBookingList.getSelectionModel().getSelectedItem();
            if (selectedEvent != null) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Event Selected");
                alert.setHeaderText(null);
                Events selectedEventNow = getEvent(selectedEvent);
                Orders order = ordersAll.stream()
                        .filter(o -> o.getEventName().equals(selectedEventNow.getEventName())) // Filter orders by event name
                        .findFirst() // Get the first matching order
                        .orElse(null); // Return null if no matching order found
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
                                    + "\nVenue: " + order.getVenueName()
                                    + "\nPrice: " + order.getPrice()
                                    + "\nCommission Earned: " + order.getCommission());
                }
                catch (NullPointerException e) {
                    Alert alert2 = new Alert(Alert.AlertType.ERROR);
                    alert2.setTitle("Sorry, Error with venue");
                    alert2.setHeaderText("Error loading venue");
                    alert2.setContentText("I'm very sorry");
                    alert2.showAndWait();
                }
                alert.showAndWait();
            }
        }
    });

    // Venue List setup
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

    // Add items to the venue filter dropdowns
    venueFilterEventType.getItems().add("All");
    venueFilterEventType.getItems().addAll(eventTypes);

    venueFilterCategoryDL.getItems().add("All");
    venueFilterCategoryDL.getItems().addAll(categories);
    venueNames = FXCollections.observableArrayList(venues);

    // FilteredList setup for venue filtering based on search and filters
    FilteredList<String> filteredList = new FilteredList<>(venueNames);

    // Event listener for the search field
    searchVenueTF.textProperty().addListener((observable, oldValue, newValue) -> {
        filteredList.setPredicate(venue -> {
            String selectedCategory = venueFilterCategoryDL.getValue();
            String selectedEvent = venueFilterEventType.getValue();
            return matchesFilter(venue, newValue, selectedCategory, selectedEvent, venueFilterMin.getText(), venueFilterMax.getText());
        });
    });

    // Event listener for category dropdown
    venueFilterCategoryDL.valueProperty().addListener((observable, oldCategory, newCategory) -> {
        filteredList.setPredicate(venue -> {
            String searchText = searchVenueTF.getText();
            String selectedEvent = venueFilterEventType.getValue();
            return matchesFilter(venue, searchText, newCategory, selectedEvent, venueFilterMin.getText(), venueFilterMax.getText());
        });
    });

    // Event listener for event type dropdown
    venueFilterEventType.valueProperty().addListener((observable, oldValue, newEventType) -> {
        filteredList.setPredicate(venue -> {
            String searchText = searchVenueTF.getText();
            String selectedCategory = venueFilterCategoryDL.getValue();
            return matchesFilter(venue, searchText, selectedCategory, newEventType, venueFilterMin.getText(), venueFilterMax.getText());
        });
    });

    // Event listener for max capacity filter
    venueFilterMax.textProperty().addListener((observable, oldValue, newValue) -> {
        filteredList.setPredicate(venue -> {
            String searchText = searchVenueTF.getText();
            String selectedCategory = venueFilterCategoryDL.getValue();
            String selectedEvent = venueFilterEventType.getValue();
            return matchesFilter(venue, searchText, selectedCategory, selectedEvent, venueFilterMin.getText(), newValue);
        });
    });

    // Event listener for min capacity filter
    venueFilterMin.textProperty().addListener((observable, oldValue, newValue) -> {
        filteredList.setPredicate(venue -> {
            String searchText = searchVenueTF.getText();
            String selectedCategory = venueFilterCategoryDL.getValue();
            String selectedEvent = venueFilterEventType.getValue();
            return matchesFilter(venue, searchText, selectedCategory, selectedEvent, newValue, venueFilterMax.getText());
        });
    });

    // Set the filtered list as the items in the venue list
    venueList.setItems(filteredList);

    // Double-click event handler for venue list
    venueList.setOnMouseClicked(event -> {
        if (event.getClickCount() == 2) { // Double-click detected
            String selectedVenue = venueList.getSelectionModel().getSelectedItem();
            if (selectedVenue != null) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Venue Selected");
                alert.setHeaderText(null);
                Venues selectedVenueNow = getVenue(selectedVenue);
                try {
                    alert.setContentText(
                            "Venue Name: " + selectedVenueNow.getVenueName()
                                    + "\nVenue ID: " + selectedVenueNow.getVenueID()
                                    + "\nCapacity: " + selectedVenueNow.getCapacity()
                                    + "\nSuitable for: " + selectedVenueNow.getSuitable()
                                    + "\nCategory: " + selectedVenueNow.getCategory()
                                    + "\nPrice: " + selectedVenueNow.getPrice());
                }
                catch (NullPointerException e) {
                    Alert alert2 = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Sorry, Error with venue");
                    alert.setHeaderText("Error loading venue");
                    alert.setContentText("I'm very sorry");
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
        // Retrieve all venues and event names for the dialog's ComboBoxes
        ArrayList<Venues> venues = getVenues();
        ArrayList<String> venueNames = venues.stream()
                .map(Venues::getVenueName)  // Extract venue names from Venues list
                .collect(Collectors.toCollection(ArrayList::new));

        ArrayList<Events> events = getEvents();
        ArrayList<String> eventNames = events.stream()
                .filter(s -> !s.isConfirmed())  // Filter only unconfirmed events
                .map(Events::getEventName)  // Extract event names
                .collect(Collectors.toCollection(ArrayList::new));

        // Create a new dialog for confirming event requests
        Dialog<Venues> dialog = new Dialog<>();
        dialog.setTitle("Confirm an event Request");
        dialog.setHeaderText("Please choose a Venue for an Event:");

        // Create the UI components for the dialog
        Label venueNameLabel = new Label("Venue Name:");
        ComboBox<String> venueNameDL = new ComboBox<>();  // Dropdown for venue names

        Label eventNameLabel = new Label("Event Name:");
        ComboBox<String> eventNameDL = new ComboBox<>();  // Dropdown for event names

        // Add available venue names and event names to the ComboBoxes
        venueNameDL.getItems().addAll(venueNames);
        eventNameDL.getItems().addAll(eventNames);

        // Layout the dialog UI components using a GridPane
        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);

        gridPane.add(venueNameLabel, 0, 0);  // Add venue label
        gridPane.add(venueNameDL, 1, 0);     // Add venue dropdown

        gridPane.add(eventNameLabel, 0, 1);  // Add event label
        gridPane.add(eventNameDL, 1, 1);     // Add event dropdown

        // Set the dialog content (GridPane with labels and combo boxes)
        dialog.getDialogPane().setContent(gridPane);

        // Add button types to the dialog (Confirm and Cancel buttons)
        ButtonType okButtonType = new ButtonType("Confirm", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(okButtonType, ButtonType.CANCEL);

        // Handle the result when the "OK" button is clicked
        dialog.setResultConverter(dialogButton -> {
            // If "Confirm" is clicked
            if (dialogButton == okButtonType) {
                // Check if both dropdowns have selected items
                if (!venueNameDL.getItems().isEmpty() && !eventNameDL.getItems().isEmpty()) {
                    try {
                        // Retrieve selected venue and event details
                        Venues selectedVenue = getVenue(venueNameDL.getSelectionModel().getSelectedItem());
                        Events selectedEvent = getEvent(eventNameDL.getSelectionModel().getSelectedItem());

                        // Ensure selected venue and event are not null
                        assert selectedVenue != null;
                        assert selectedEvent != null;

                        // Calculate the commission as 10% of the venue price
                        float commi = selectedVenue.getPrice() / 10;

                        // Insert the order row (creating a booking) and mark the event as confirmed
                        insertOrderRow(eventNameDL.getValue(), venueNameDL.getValue(), selectedEvent.getDate(),
                                selectedEvent.getDuration(), selectedVenue.getPrice(), commi, selectedEvent.getClientName());
                        updateConfirmed(eventNameDL.getValue(), true);  // Update the event status to confirmed

                    } catch (Exception e) {
                        // Show error alert if there was an issue with the order insertion
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Invalid Input");
                        alert.setHeaderText("Error adding order");
                        alert.setContentText("Please help.");
                        alert.showAndWait();
                        return null;  // Prevent the dialog from returning any result if error occurs
                    }
                }
            }
            return null;  // Return null as the result (no additional processing after confirm)
        });

        // Show the dialog to the user and wait for a response
        dialog.showAndWait();

        // Refresh the page to show new thingos
        Parent root = FXMLLoader.load(getClass().getResource("/com/idk/demo/Views/HomepageView.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }


    public void OnAddVenue(ActionEvent event) throws Exception {
        // Create the dialog for adding a new venue
        Dialog<Venues> dialog = new Dialog<>();
        dialog.setTitle("Add Venue");
        dialog.setHeaderText("Enter the details of the new venue:");

        // Create labels and text fields for user input
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

        // Set up the layout for the dialog using GridPane
        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);

        // Add labels and text fields to the grid
        gridPane.add(venueNameLabel, 0, 0);
        gridPane.add(venueNameTF, 1, 0);

        gridPane.add(venueCapacity, 0, 1);
        gridPane.add(venueCapacityTF, 1, 1);

        gridPane.add(venueSuitabilityLabel, 0, 2);
        gridPane.add(venueSuitabilityTF, 1, 2);

        gridPane.add(venueCategoryLabel, 0, 3);
        gridPane.add(venueCategoryTF, 1, 3);

        gridPane.add(venuePriceLabel, 0, 4);
        gridPane.add(venuePriceTF, 1, 4);

        // Set the content of the dialog to the GridPane
        dialog.getDialogPane().setContent(gridPane);

        // Add the "Add" and "Cancel" buttons to the dialog
        ButtonType okButtonType = new ButtonType("Add", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(okButtonType, ButtonType.CANCEL);

        // Handle the "OK" button action when clicked
        dialog.setResultConverter(dialogButton -> {
            // If the "Add" button is clicked
            if (dialogButton == okButtonType) {
                // Check that all required fields are filled in
                if (!venueNameTF.getText().isEmpty()
                        && !venueCategoryTF.getText().isEmpty()
                        && !venueCapacityTF.getText().isEmpty()
                        && !venuePriceTF.getText().isEmpty()
                        && !venueSuitabilityTF.getText().isEmpty()) {

                    try {
                        // Parse the capacity as an integer and price as a float
                        int capacity = Integer.parseInt(venueCapacityTF.getText());
                        float price = Float.parseFloat(venuePriceTF.getText());

                        // Insert the new venue data into the system (e.g., database or list)
                        insertVenueRow(venueNameTF.getText(), capacity, venueSuitabilityTF.getText(), venueCategoryTF.getText(), price);

                        // Add the new venue name to the venueNames list for display
                        venueNames.add(venueNameTF.getText());

                        // Add the new category to the venue filter dropdown if it's not already present
                        if (!venueFilterCategoryDL.getItems().contains(venueCategoryTF.getText())) {
                            venueFilterCategoryDL.getItems().add(venueCategoryTF.getText());
                        }

                        // Update the venue list with the new venue name
                        venueList.setItems(FXCollections.observableArrayList(venueNames));

                    } catch (NumberFormatException e) {
                        // Handle invalid input for capacity and price (if they're not the correct data types)
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Invalid Input");
                        alert.setHeaderText("Error adding venue");
                        alert.setContentText("Please ensure that capacity is an integer and price is a floating point number.");
                        alert.showAndWait();
                        return null;  // Return null to avoid adding an invalid venue
                    }
                }
            }
            return null;  // Return null to close the dialog without further action
        });

        dialog.showAndWait();
    }

    // Method to handle user sign out action
    public void onSignout(ActionEvent event) throws Exception {
        // Clear any session data (such as user info)
        SessionManager.clearSession();
        Parent root = FXMLLoader.load(getClass().getResource("/com/idk/demo/Views/LoginView.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
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
//    public void onDisplayProfile(ActionEvent event) throws Exception{
//        Parent root = FXMLLoader.load(getClass().getResource("/com/idk/demo/Views/UserProfileView.fxml"));
//        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
//        stage.setWidth(1930);  // Set the window width
//        stage.setHeight(1080);  // Set the window height
//        stage.setResizable(false);  // Optional: makes the window non-resizable
//        stage.centerOnScreen();
//        Scene scene = new Scene(root);
//        stage.setScene(scene);
//        stage.show();
//    }
//
//    public void onImportVenues(ActionEvent event) throws Exception {
//        InputStream is = getClass().getResourceAsStream("/com/idk/demo/Views/files/venues.csv");
//        try {
//            assert is != null;
//            try (BufferedReader br = new BufferedReader(new InputStreamReader(is))) {
//                String line;
//                // Skip the header line
//                br.readLine();
//
//                while ((line = br.readLine()) != null) {
//                    String[] values = line.split(",");  // Split each line by commas
//                    String name = values[0];
//                    int capacity = Integer.parseInt(values[1]);
//                    String suitableFor = values[2];
//                    String category = values[3];
//                    int bookingPricePerHour = Integer.parseInt(values[4]);
//                    insertVenueRow(name, capacity, suitableFor, category, bookingPricePerHour);
//                }
//
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        Parent root = FXMLLoader.load(getClass().getResource("/com/idk/demo/Views/HomepageView.fxml"));
//        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
//        Scene scene = new Scene(root);
//        stage.setScene(scene);
//        stage.show();
//    }
//
//    public void onImportEventRequests(ActionEvent event) throws Exception {
//        InputStream is = getClass().getResourceAsStream("/com/idk/demo/Views/files/requests.csv");
//        try {
//            assert is != null;
//            try (BufferedReader br = new BufferedReader(new InputStreamReader(is))) {
//                String line;
//                // Skip the header line
//                br.readLine();
//                while ((line = br.readLine()) != null) {
//
//                    String[] values = line.split(",");  // Split each line by commas
//                    String clientName = values[0];
//                    String eventName = values[1];
//                    String mainArtist = values[2];
//                    String date = values[3];
//                    String time = values[4];
//                    int duration = Integer.parseInt(values[5]);
//                    int auidenceSize = Integer.parseInt(values[6]);
//                    String type = values[7];
//                    String category = values[8];
//                    SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
//                    java.util.Date utilDate = sdf.parse(date);
//                    java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
//                    insertEventRow(clientName, eventName, mainArtist, sqlDate, time, duration, auidenceSize, type, category, false);
//                    //batchAll later. rewokr needed to work
//                }
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        Parent root = FXMLLoader.load(getClass().getResource("/com/idk/demo/Views/HomepageView.fxml"));
//        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
//        Scene scene = new Scene(root);
//        stage.setScene(scene);
//        stage.show();
//    }
    public void onDisplayProfile(ActionEvent event) throws Exception {
        // Load the User Profile view from FXML
        Parent root = FXMLLoader.load(getClass().getResource("/com/idk/demo/Views/UserProfileView.fxml"));

        // Get the current stage (window) from the event source
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        // Set the width and height of the window
        stage.setWidth(1930);  // Set the window width
        stage.setHeight(1080);  // Set the window height

        // Make the window non-resizable (optional)
        stage.setResizable(false);

        // Center the window on the screen
        stage.centerOnScreen();

        // Create a new scene using the loaded FXML and set it to the stage
        Scene scene = new Scene(root);
        stage.setScene(scene);

        // Show the window with the new scene
        stage.show();
    }

    public void onImportVenues(ActionEvent event) throws Exception {
        // Load the venues data from the CSV file
        InputStream is = getClass().getResourceAsStream("/com/idk/demo/Views/files/venues.csv");

        try {
            // Ensure the input stream is not null
            assert is != null;

            // Read the CSV file line by line using BufferedReader
            try (BufferedReader br = new BufferedReader(new InputStreamReader(is))) {
                String line;

                // Skip the header line in the CSV file
                br.readLine();

                // Read each line in the file and process the data
                while ((line = br.readLine()) != null) {
                    // Split the line by commas to extract the values for each field
                    String[] values = line.split(",");

                    // Extract the values from the split line
                    String name = values[0];
                    int capacity = Integer.parseInt(values[1]);
                    String suitableFor = values[2];
                    String category = values[3];
                    int bookingPricePerHour = Integer.parseInt(values[4]);

                    // Insert the venue into the database or list (via insertVenueRow method)
                    insertVenueRow(name, capacity, suitableFor, category, bookingPricePerHour);
                }
            }
        } catch (IOException e) {
            // Handle any IO exceptions that might occur during file reading
            e.printStackTrace();
        }

        Parent root = FXMLLoader.load(getClass().getResource("/com/idk/demo/Views/HomepageView.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void onImportEventRequests(ActionEvent event) throws Exception {
        // Load the event requests data from the CSV file
        InputStream is = getClass().getResourceAsStream("/com/idk/demo/Views/files/requests.csv");

        try {
            // Ensure the input stream is not null
            assert is != null;

            // Read the CSV file line by line using BufferedReader
            try (BufferedReader br = new BufferedReader(new InputStreamReader(is))) {
                String line;

                // Skip the header line in the CSV file
                br.readLine();

                // Read each line in the file and process the data
                while ((line = br.readLine()) != null) {
                    // Split the line by commas to extract the values for each field
                    String[] values = line.split(",");

                    // Extract the values from the split line
                    String clientName = values[0];
                    String eventName = values[1];
                    String mainArtist = values[2];
                    String date = values[3];
                    String time = values[4];
                    int duration = Integer.parseInt(values[5]);
                    int audienceSize = Integer.parseInt(values[6]);
                    String type = values[7];
                    String category = values[8];

                    // Parse the date from string format (dd-MM-yyyy) to java.sql.Date
                    SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
                    java.util.Date utilDate = sdf.parse(date);
                    java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());

                    // Insert the event request into the database or list (via insertEventRow method)
                    insertEventRow(clientName, eventName, mainArtist, sqlDate, time, duration, audienceSize, type, category, false);
                }
            }
        } catch (IOException e) {
            // Handle any IO exceptions that might occur during file reading
            e.printStackTrace();
        } catch (ParseException e) {
            // Handle any date parsing exceptions
            e.printStackTrace();
        }

        Parent root = FXMLLoader.load(getClass().getResource("/com/idk/demo/Views/HomepageView.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
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
