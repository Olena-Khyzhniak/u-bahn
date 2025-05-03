package ok.ubahn.Util;


import ok.ubahn.Model.GraphNodeAL;
import java.util.HashMap;

public class StationRegistry {

    private static final HashMap<String, GraphNodeAL<String>> stationMap = new HashMap<>();

    public static void register(String name, int x, int y) {
        stationMap.put(name, new GraphNodeAL<>(name, name, x, y));
    }

    public static GraphNodeAL<String> get(String name) {
        return stationMap.get(name);
    }

    public static boolean contains(String name) {
        return stationMap.containsKey(name);
    }

    public static Iterable<GraphNodeAL<String>> getAllStations() {
        return stationMap.values();
    }

}
