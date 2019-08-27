package Metro;

import java.util.List;
import java.util.Objects;

public class Line implements Comparable{

    private String number;
    private String name;
    private String color;
    private List<Station> stations;
    private List<Connection> connections;

    public Line(String number, String name) {
        this.number = number;
        this.name = name;
    }

    public Line(String number, String name, List<Station> stations) {
        this.number = number;
        this.name = name;
        this.stations = stations;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Station> getStations() {
        return stations;
    }

    public void setStations(List<Station> stations) {
        this.stations = stations;
    }

    public List<Connection> getConnections() {
        return connections;
    }

    public void setConnections(List<Connection> connections) {
        this.connections = connections;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Line)) return false;
        Line line = (Line) o;
        return getNumber().equals(line.getNumber());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getNumber(), getName(), getColor(), getStations(), getConnections());
    }

    @Override
    public String toString() {
        return "Line{" +
                "id='" + number + '\'' +
                ", name='" + name + '\'' +
                '}';
    }

    @Override
    public int compareTo(Object o) {
        if (this == o) return 0;
        if (!(o instanceof Line)) return 0;
        Line line = (Line) o;
        return this.getNumber().compareTo(line.getNumber());
    }
}
