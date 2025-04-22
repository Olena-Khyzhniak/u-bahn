package ok.ubahn.Controller;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;

import java.util.ArrayList;
import java.util.List;
public class RouteController {

    // change colours of map to reperesent different lines on route.
    // display list of stations on route
    // display cost of route (distance or price)

    @FXML
    private ListView<String> resultListView;

    @FXML
    public void onFindRoute() {

        String start = startStationComboBox.getValue();
        String end = destinationStationComboBox.getValue();

        if (start == null || end == null || start.equals(end)) {
            resultListView.getItems().setAll("Please select two different stations.");
            return;
        }

        // Dummy route for now – replace with actual pathfinding logic
        List<String> route = List.of(start, end);
        double price = calculatePrice(route); // You define this method!

        // Display the route + price in the ListView
        List<String> display = new ArrayList<>(route);
        display.add("Total Price: €" + String.format("%.2f", price));
        resultListView.getItems().setAll(display);

        // Implementation logic for finding the route
        System.out.println("Find Route action triggered");
    }
    private double calculatePrice(List<String> route) {
        return route.size() * 1.50; // €1.50 per station visited
    }

    @FXML
    private ComboBox<String> startStationComboBox;

    @FXML
    private ComboBox<String> destinationStationComboBox;

    @FXML
    public void onClear() {
        startStationComboBox.getSelectionModel().clearSelection();
        destinationStationComboBox.getSelectionModel().clearSelection();
        resultListView.getItems().clear();
        // Logic to clear inputs and outputs
        System.out.println("Clear action triggered");
    }

}