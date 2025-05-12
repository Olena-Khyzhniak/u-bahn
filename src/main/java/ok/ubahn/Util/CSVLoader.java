package ok.ubahn.Util;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import ok.ubahn.Model.Graph;
import ok.ubahn.Model.GraphNodeAL;
import ok.ubahn.UI.MainApp;

public class CSVLoader {

    String stationCsv = "/ok/ubahn/stations.csv";
    String subwayCsv = "/ok/ubahn/vienna_subway.csv";


    public static Graph loadGraph(String csvFilePath) {
        Graph graph = new Graph();

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(
                MainApp.class.getResourceAsStream("/ok/ubahn/" + csvFilePath)))) {

            String line;
            boolean firstLine = true;
            while ((line = reader.readLine()) != null) {
                if (firstLine) {
                    firstLine = false;
                    continue; // skip header
                }

                String[] parts = line.split(",");
                if (parts.length < 5) continue;

                String startName = parts[0].trim();
                String endName = parts[1].trim();
                String lineName = parts[2].trim();
                // parts[3] is Color, can be used for UI if needed
                double distance = Double.parseDouble(parts[4].trim());

                GraphNodeAL<String> start = StationRegistry.get(startName);
                GraphNodeAL<String> end = StationRegistry.get(endName);

                if (start != null && end != null) {
                    graph.addLink(start, end, distance, lineName);
                } else {
                    System.err.println("Missing station in registry: " + startName + " or " + endName);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return graph;
    }


    private static List<String> readLinesFromCSV(String csvPath) {
        List<String> lines = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(csvPath))) {
            String line;
            while ((line = br.readLine()) != null) {
                lines.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lines;
    }

    public static List<String> loadStationNames(String csvPath) {
        List<String> stationNames = new ArrayList<>();
        List<String> lines = readLinesFromCSV(csvPath);

        for (String line : lines) {
            String[] parts = line.split(",");
            if (parts.length < 1) continue;

            String stationName = parts[0].trim();
            if (!stationNames.contains(stationName)) {
                stationNames.add(stationName);
            }
        }

        return stationNames;
    }

    public static Graph loadGraphWithCoordinatesAndLinks(String stationCsv, String subwayCsv) {
        Graph graph = new Graph();

               try (BufferedReader br = new BufferedReader(new InputStreamReader(
                CSVLoader.class.getResourceAsStream("/ok/ubahn/stations.csv")))) {

            String line = br.readLine();
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length < 3) continue;

                String name = parts[0].trim();
                int x = Integer.parseInt(parts[1].trim());
                int y = Integer.parseInt(parts[2].trim());

                GraphNodeAL<String> node = new GraphNodeAL<>(name, name, x, y);
                graph.addStation(node);
                StationRegistry.register(node);
            }

        } catch (Exception e) {
            System.err.println("Error to load: " + e.getMessage());
            e.printStackTrace();
        }


        try (BufferedReader br = new BufferedReader(new InputStreamReader(
                CSVLoader.class.getResourceAsStream("/ok/ubahn/vienna_subway.csv")))) {

            String line = br.readLine();
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length < 4) continue;

                String startName = parts[0].trim();
                String endName = parts[1].trim();
                String lineName = parts[2].trim();

                double distance = 1.0;

                GraphNodeAL<String> start = StationRegistry.get(startName);
                GraphNodeAL<String> end = StationRegistry.get(endName);

                if (start != null && end != null) {
                    graph.addLink(start, end, distance, lineName);
                } else {
                    System.err.println("No connection: " + startName + " → " + endName);
                }
            }

        } catch (Exception e) {
            System.err.println("Erorr to load: " + e.getMessage());
            e.printStackTrace();
        }

        return graph;
    }





    public static Graph loadGraphFromSubwayCSV(String csvPath) {
        Graph graph = new Graph();
        try (BufferedReader br = new BufferedReader(new FileReader(csvPath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length < 3) continue;

                String from = parts[0].trim();
                String to = parts[1].trim();
                String lineName = parts[2].trim();

                if (StationRegistry.contains(from) && StationRegistry.contains(to)) {
                    GraphNodeAL<String> fromNode = StationRegistry.get(from);
                    GraphNodeAL<String> toNode = StationRegistry.get(to);

                    graph.addStation(fromNode);
                    graph.addStation(toNode);
                    graph.addLink(fromNode, toNode, 1.0, lineName);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return graph;
    }

}
