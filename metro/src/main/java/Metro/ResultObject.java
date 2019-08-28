package Metro;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

public class ResultObject {

    private TreeSet<Line> lines = new TreeSet<>();
    private TreeSet<Station> stations = new TreeSet<>();
    private List<Connection> connections = new ArrayList<>();

    public ResultObject(TreeSet<Line> lines, TreeSet<Station> stations, List<Connection> connections) {
        this.lines = lines;
        this.stations = stations;
        this.connections = connections;
    }

    public TreeSet<Line> getLines() {
        return lines;
    }

    public void setLines(TreeSet<Line> lines) {
        this.lines = lines;
    }

    public TreeSet<Station> getStations() {
        return stations;
    }

    public void setStations(TreeSet<Station> stations) {
        this.stations = stations;
    }

    public List<Connection> getConnections() {
        return connections;
    }

    public void setConnections(List<Connection> connections) {
        this.connections = connections;
    }
}
