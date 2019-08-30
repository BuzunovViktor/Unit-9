package Metro;

import Utils.JSONCompatible;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import java.util.Objects;

public class Line implements Comparable, JSONCompatible {

    @JsonProperty("lineNumber")
    private String number;
    @JsonProperty("lineName")
    private String name;
    @JsonIgnore
    private List<Station> stations;

    public Line() {
    }

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
        String thisObj = this.getNumber();
        String compareObj = line.getNumber();
        if (thisObj.length() != compareObj.length()) {
            StringBuilder stringBuilderThis = new StringBuilder();
            StringBuilder stringBuilderCompare = new StringBuilder();
            int diff = this.getNumber().length() - line.getNumber().length();
            if (diff < 0) {
                diff = Math.abs(diff);
                for (int i = 0; i < diff; i++) {
                    stringBuilderCompare.insert(0,"0");
                }
            } else {
                for (int i = 0; i < diff; i++) {
                    stringBuilderThis.insert(0, "0");
                }
            }
            thisObj = stringBuilderThis.toString();
            compareObj = stringBuilderCompare.toString();
        }
        return thisObj.compareTo(compareObj);
    }
}
