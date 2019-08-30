package Metro;

import Utils.JSONCompatible;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

public class Station implements Comparable, JSONCompatible {

    @JsonProperty("stationName")
    private String name;
    @JsonProperty("line")
    private Line line;

    public Station() {
    }

    public Station(String name, Line line) {
        this.name = name;
        this.line = line;
    }

    public Station(String name) {
        this.name = name;
    }

    public Line getLine() {
        return line;
    }

    public void setLine(Line line) {
        this.line = line;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Station{" +
                "line=" + line +
                ", name='" + name + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Station)) return false;
        Station station = (Station) o;
        return Objects.equals(getLine(), station.getLine()) &&
                Objects.equals(getName(), station.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getLine(), getName());
    }

    @Override
    public int compareTo(Object o) {
        if (this == o) return 0;
        if (!(o instanceof Station)) return 0;
        Station station = (Station) o;
        return this.toString().compareTo(station.toString());
    }
}
