package ok.ubahn.Controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.image.*;
import javafx.scene.paint.Color;
import ok.ubahn.Model.Graph;
import ok.ubahn.Model.GraphLinkAL;
import ok.ubahn.Model.GraphNodeAL;
import ok.ubahn.Util.CSVLoader;
import ok.ubahn.Util.GraphAlgorithms;
import ok.ubahn.Util.StationRegistry;


import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;



public class RouteController implements Initializable {



    private Graph graph;
    // change colours of map to reperesent different lines on route.
    // display list of stations on route
    // display cost of route (distance or price)

    @FXML
    private ListView<String> resultListView;

    @FXML
    private ComboBox<String> startStationComboBox;

    @FXML
    private ComboBox<String> destinationStationComboBox;

    @FXML
    private Button onFindRoute;

    @FXML
    private Button onClear;

    @FXML
    private ComboBox<String> algorithmComboBox;
    @FXML
    private Canvas routeCanvas;

    @FXML
    private ImageView routeImage;

    private WritableImage writableMap;

    @FXML
    private Canvas canvas;



    @FXML
    private ImageView mapImageView;

    private static Graph subwayGraph;


    public void setGraph(Graph graph) {
        this.graph = graph;
    }


    @FXML
    private void onFindRoute() {
        String start = startStationComboBox.getValue();
        String end = destinationStationComboBox.getValue();
        String algorithm = algorithmComboBox.getValue();

        if (start == null || end == null || algorithm == null) {
            System.out.println("Please select all options.");
            return;
        }

        List<GraphNodeAL<String>> path = findPath(algorithm, start, end);
        if (path == null || path.isEmpty()) {
            System.out.println("No path found.");
            return;
        }


        for (int i = 0; i < path.size() - 1; i++) {
            GraphNodeAL<String> from = path.get(i);
            GraphNodeAL<String> to = path.get(i + 1);
            drawPath(Color.RED, from.getX(), from.getY(), to.getX(), to.getY());
        }


        resultListView.getItems().clear();
        for (GraphNodeAL<String> node : path) {
            resultListView.getItems().add(node.data);
        }


        double cost = calculatePrice(path);
        resultListView.getItems().add("Total price: €" + String.format("%.2f", cost));
    }


    private List<GraphNodeAL<String>> findPath(String algorithm, String startName, String endName) {
        GraphNodeAL<String> start = StationRegistry.get(startName);
        if (start == null) return null;

        GraphAlgorithms.CostedPath result;

        switch (algorithm) {
            case "Dijkstra":
                result = GraphAlgorithms.findCheapestPathDijkstra(start, endName);
                break;
            case "DFS":
                result = GraphAlgorithms.DFSCheapestPath(start, null, 0, endName);
                break;
            case "BFS":
                result = GraphAlgorithms.findAnyRouteBFS(start, endName);
                break;
            case "Penalty":
                result = GraphAlgorithms.findCheapestPathWithPenalty(start, endName, 5);
                break;
            default:
                return null;
        }

        return (result != null) ? castPath(result.pathList) : null;
    }


    @SuppressWarnings("unchecked")
    private List<GraphNodeAL<String>> castPath(List<GraphNodeAL<?>> pathList) {
        List<GraphNodeAL<String>> casted = new ArrayList<>();
        for (GraphNodeAL<?> node : pathList) {
            casted.add((GraphNodeAL<String>) node);
        }
        return casted;
    }



    private void drawPath(Color lineColor, double startX, double startY, double endX, double endY) {

            javafx.scene.canvas.Canvas canvas = new javafx.scene.canvas.Canvas(writableMap.getWidth(), writableMap.getHeight());
            GraphicsContext gc = canvas.getGraphicsContext2D();

            gc.setStroke(lineColor);
            gc.setLineWidth(4);
            gc.strokeLine(startX, startY, endX, endY);

            SnapshotParameters params = new SnapshotParameters();
            params.setFill(Color.TRANSPARENT);
            WritableImage tempImage = canvas.snapshot(params, null);

            PixelWriter writer = writableMap.getPixelWriter();
            PixelReader reader = tempImage.getPixelReader();

            for (int x = 0; x < tempImage.getWidth(); x++) {
                for (int y = 0; y < tempImage.getHeight(); y++) {
                    Color color = reader.getColor(x, y);
                    if (!color.equals(Color.TRANSPARENT)) {
                        writer.setColor(x, y, color);
                    }
                }
            }
        }



    private double calculatePrice(List<GraphNodeAL<String>> route) {
        return route.size() * 1.50;
    }



    @FXML
    public void onClear() {
        resultListView.getItems().clear();
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        startStationComboBox.setValue(null);
        destinationStationComboBox.setValue(null);
    }

    @FXML
    public void initialize(URL location, ResourceBundle resources) {

        try {

        Graph graph = CSVLoader.loadGraphWithCoordinatesAndLinks("/ok/ubahn/route.csv", "/ok/ubahn/vienna_subway.csv");
            System.out.println("Loading Stations:");
            for (GraphNodeAL<String> station : graph.getStations()) {
                System.out.println(station.getName());
            }



            startStationComboBox.getItems().clear();
            destinationStationComboBox.getItems().clear();
            for (GraphNodeAL<String> station : graph.getStations()) {
                startStationComboBox.getItems().add(station.getName());
                destinationStationComboBox.getItems().add(station.getName());
            }


            Image originalMap = routeImage.getImage();
            writableMap = new WritableImage(originalMap.getPixelReader(),
                    (int) originalMap.getWidth(),
                    (int) originalMap.getHeight());
            routeImage.setImage(writableMap);




        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Controller Error: " + e.getMessage());
        }
    }




}