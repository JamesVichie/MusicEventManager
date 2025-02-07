package com.idk.demo.Controllers;

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

import java.util.*;
import java.util.stream.Collectors;

import static com.idk.demo.Models.DatabasesInteract.GetTables.getVenues;
import static com.idk.demo.Models.DatabasesInteract.InsertRow.insertVenueRow;


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
    private ObservableList<String> venueNames;

    public void initialize() {
        ArrayList<String> venues = getVenues().stream()
                .map(Venues::getVenueName)
                .collect(Collectors.toCollection(ArrayList::new));

        Set<String> categories = getVenues().stream()
                .map(Venues::getCategory)
                .collect(Collectors.toSet());

        venueFilterCategoryDL.getItems().add("All");
        venueFilterCategoryDL.getItems().addAll(categories);
        venueNames = FXCollections.observableArrayList(venues);

        // Wrap in a FilteredList
        FilteredList<String> filteredList = new FilteredList<>(venueNames, s -> true);

        // Add listener to TextField for filtering
        searchVenueTF.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredList.setPredicate(venue -> {
                boolean matchesSearch = (newValue == null || newValue.isEmpty()) || venue.toLowerCase().contains(newValue.toLowerCase());
                String selectedCategory = venueFilterCategoryDL.getValue();
                boolean matchesCategory = (selectedCategory == null || selectedCategory.isEmpty() || selectedCategory.equals("All")) || getVenues().stream().anyMatch(v -> v.getVenueName().equals(venue) && v.getCategory().equals(selectedCategory));
                return matchesSearch && matchesCategory; // Combine both conditions
            });
        });

        // Add listener to ComboBox for category filtering
        venueFilterCategoryDL.valueProperty().addListener((observable, oldCategory, newCategory) -> {
            filteredList.setPredicate(venue -> {
                boolean matchesCategory = (newCategory == null || newCategory.isEmpty() || newCategory.equals("All")) || getVenues().stream().anyMatch(v -> v.getVenueName().equals(venue) && v.getCategory().equals(newCategory));
                String searchText = searchVenueTF.getText();
                boolean matchesSearch = (searchText == null || searchText.isEmpty()) || venue.toLowerCase().contains(searchText.toLowerCase());
                return matchesSearch && matchesCategory; // Combine both conditions
            });
        });

        // Set filtered list in ListView
        venueList.setItems(filteredList);
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
                        venueFilterCategoryDL.getItems().add(venueCategoryTF.getText());

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

        //wtf is optional. research later
        dialog.showAndWait();
//        Optional<Venues> result = dialog.showAndWait();
//        result.ifPresent(venue -> {
//            venueNames.add(venue.getVenueName());
//            insertVenueRow()
///             Optionally, save the venue to the database or process it further
//        });
    }

//    public void displayHomepage(ActionEvent event) throws Exception{
//        Parent root = FXMLLoader.load(getClass().getResource("Views/HomepageView.fxml"));
//        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
//        Scene scene = new Scene(root);
//        stage.setScene(scene);
//        stage.show();
//
//    }

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
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }

    }
}
