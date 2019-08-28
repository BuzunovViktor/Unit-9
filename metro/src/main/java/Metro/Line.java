package Metro;

import java.util.List;
import java.util.Objects;

public class Line implements Comparable{

    private String number;
    private String name;
    private List<Station> stations;

    public Line(String number, String name) {
        this.number = number;
        this.name = name;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Line)) return false;
        Line line = (Line) o;
        return getNumber().equals(line.getNumber());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getNumber(), getName(), getStations());
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
