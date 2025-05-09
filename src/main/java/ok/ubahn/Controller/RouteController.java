package ok.ubahn.Controller;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import ok.ubahn.Model.GraphLinkAL;
import ok.ubahn.Model.GraphNodeAL;

import java.util.*;

public class RouteController {

    @FXML
    private ListView<String> resultListView;

    @FXML
    private ComboBox<String> startStationComboBox;

    @FXML
    private ComboBox<String> destinationStationComboBox;

    // Declare the stations graph (field at class level)
    private Map<String, GraphNodeAL<String>> stationsGraph = new HashMap<>();

    // Called once when the FXML file is loaded
    @FXML
    public void initialize() {
        // Create the graph (load the stations)
        createStationsGraph();

        // Populate the combo boxes with station names
        startStationComboBox.getItems().addAll(stationsGraph.keySet());
        destinationStationComboBox.getItems().addAll(stationsGraph.keySet());
    }

    // Method to create the station graph (You can replace this with your actual data)
    private void createStationsGraph() {
        // Example graph creation, replace this with actual stations and links
        GraphNodeAL<String> station1 = new GraphNodeAL<>("Station 1", "Stephansplatz");
        GraphNodeAL<String> station2 = new GraphNodeAL<>("Station 2", "Karlsplatz");
        GraphNodeAL<String> station3 = new GraphNodeAL<>("Station 3", "Landstrasse");

        station1.addLink(station2, 1.5, "U1");
        station2.addLink(station3, 2.0, "U2");

        stationsGraph.put(station1.getName(), station1);
        stationsGraph.put(station2.getName(), station2);
        stationsGraph.put(station3.getName(), station3);
    }

    @FXML
    public void onFindRoute() {
        String start = startStationComboBox.getValue();
        String end = destinationStationComboBox.getValue();

        if (start == null || end == null || start.equals(end)) {
            resultListView.getItems().setAll("Please select two different stations.");
            return;
        }

        // Call Dijkstra's algorithm to find the cheapest path
        List<String> route = findCheapestPath(start, end);
        double totalCost = calculateTotalCost(route);

        // Display the route + total cost in the ListView
        List<String> display = new ArrayList<>(route);
        display.add("Total Price: €" + String.format("%.2f", totalCost));
        resultListView.getItems().setAll(display);

        System.out.println("Find Route action triggered");
    }

    private List<String> findCheapestPath(String startName, String endName) {
        GraphNodeAL<String> startNode = stationsGraph.get(startName);
        GraphNodeAL<String> endNode = stationsGraph.get(endName);

        if (startNode == null || endNode == null) {
            return new ArrayList<>(List.of("Invalid stations"));
        }

        // Dijkstra's algorithm setup
        PriorityQueue<GraphNodeAL<String>> queue = new PriorityQueue<>(Comparator.comparingInt(GraphNodeAL::getNodeValue));
        startNode.setNodeValue(0);
        queue.add(startNode);

        Map<GraphNodeAL<String>, GraphNodeAL<String>> previousNodes = new HashMap<>();
        Map<GraphNodeAL<String>, Double> distances = new HashMap<>();
        distances.put(startNode, 0.0);

        while (!queue.isEmpty()) {
            GraphNodeAL<String> current = queue.poll();
            if (current == endNode) break;

            for (GraphLinkAL link : current.getAdjList()) {
                GraphNodeAL<String> neighbor = link.getDestNode();
                double newDist = distances.get(current) + link.getCost();

                if (newDist < distances.getOrDefault(neighbor, Double.MAX_VALUE)) {
                    distances.put(neighbor, newDist);
                    queue.add(neighbor);
                    previousNodes.put(neighbor, current);
                }
            }
        }

        // Reconstruct the shortest path
        List<String> path = new ArrayList<>();
        GraphNodeAL<String> current = endNode;
        while (current != null) {
            path.add(current.getName());
            current = previousNodes.get(current);
        }

        Collections.reverse(path); // Reverse to get the path from start to end
        return path;
    }

    private double calculateTotalCost(List<String> route) {
        double totalCost = 0.0;
        for (String station : route) {
            // This is just a placeholder for any real cost logic
            totalCost += 1.5; // Assuming each station has a cost of 1.5 €
        }
        return totalCost;
    }

    @FXML
    public void onClear() {
        startStationComboBox.getSelectionModel().clearSelection();
        destinationStationComboBox.getSelectionModel().clearSelection();
        resultListView.getItems().clear();
        System.out.println("Clear action triggered");
    }
}
