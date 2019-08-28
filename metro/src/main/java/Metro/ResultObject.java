package Metro;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

public class ResultObject {

    private List<Line> lines;
    private List<Station> stations;
    private List<Connection> connections;

    public ResultObject(List<Line> lines, List<Station> stations, List<Connection> connections) {
        this.lines = lines;
        this.stations = stations;
        this.connections = connections;
    }

}
