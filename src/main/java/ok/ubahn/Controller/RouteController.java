package ok.ubahn.Controller;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;

import java.util.List;
public class RouteController {

    // change colours of map to repereesent different lines on route.
    // display list of stations on route
    // display cost of route (distance or price)

    @FXML
    public void onFindRoute() {
        // Implementation logic for finding the route
        System.out.println("Find Route action triggered");
    }

    @FXML
    private ComboBox<String> startStationComboBox;

    @FXML
    private ComboBox<String> destinationStationComboBox;

    @FXML
    public void onClear() {
        startStationComboBox.getSelectionModel().clearSelection();
        destinationStationComboBox.getSelectionModel().clearSelection();
        // Logic to clear inputs and outputs
        System.out.println("Clear action triggered");
    }

}