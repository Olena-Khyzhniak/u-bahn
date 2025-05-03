//package ok.ubahn.Data;
//
//import ok.ubahn.Model.GraphNodeAL;
//import ok.ubahn.Model.Graph;
//import java.io.BufferedReader;
//import java.io.FileReader;
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.List;
//
//
//
//import ok.ubahn.Model.GraphNodeAL;
//
//import java.io.BufferedReader;
//import java.io.FileReader;
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.List;
//
//
//
//    /**
//     * Loads station and link data from CSV files into the graph.
//     */
//    public class CSVDataSource extends DataSource {
//        private String stationsFile;
//        private String linksFile;
//
//        public CSVDataSource(String stationsFile, String linksFile){
//            this.stationsFile = stationsFile;
//            this.linksFile = linksFile;
//        }
//
//        @Override
//        public void load() throws IOException {
//            loadStations();
//            loadLinks();
//        }
//
//        private void loadStations() throws IOException{
//            try (BufferedReader br = new BufferedReader(new FileReader(stationsFile))){
//                String line;
//                while ((line = br.readLine()) !=null){
//                    String stationName = line.trim();
//                    if (!stationName.isEmpty()){
//                        GraphNodeAL<Void> station = new GraphNodeAL<>(null, stationName);
//                        graph.addStation(station);
//                    }
//                }
//            }
//
//        }
//
//        public static List<String> loadStationNames(String filePath) {
//            List<String> stationNames = new ArrayList<>();
//            try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
//                String line;
//                br.readLine(); // Пропустить заголовок
//                while ((line = br.readLine()) != null) {
//                    String[] parts = line.split(",");
//                    if (parts.length > 0) {
//                        stationNames.add(parts[0]); // Предполагается, что имя станции — в первом столбце
//                    }
//                }
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//            return stationNames;
//        }
//    }
//
//    private void loadLinks() throws IOException{
//        try (BufferedReader br = new BufferedReader(new FileReader(linksFile))){
//            String line;
//            while ((line = br.readLine()) !=null){
//                String[] parts = line.split(",");
//                if(parts.length == 4) {
//                    String srcName = parts[0].trim();
//                    String destName = parts[1].trim();
//                    double cost = Double.parseDouble(parts[2].trim());
//                    String lineName = parts[3].trim();
//
//                    GraphNodeAL srcStation = graph.findStation(srcName);
//                    GraphNodeAL destStation = graph.findStation(destName);
//
//                    if (srcStation != null && destStation != null) {
//                        graph.addLink(srcStation, destStation, cost, lineName);
//
//                    }
//                }
//            }
//        }
//    }
//}
//
//}
//
//
