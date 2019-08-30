package Metro;

import Utils.JSONCompatible;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.TreeSet;

public class Connection implements JSONCompatible {

    @JsonProperty("Connection")
    private TreeSet<Station> stations;

    public Connection() {

    }

    public Connection(TreeSet<Station> stations) {
        this.stations = stations;
    }

    public TreeSet<Station> getStations() {
        return stations;
    }

    public void setStations(TreeSet<Station> stations) {
        this.stations = stations;
    }

}
