package Metro;

import java.util.Objects;

public class Station {

    private String name;
    private Line line;
    private Connection connection;

    public Station(String name, Line line, Connection connection) {
        this.name = name;
        this.line = line;
        this.connection = connection;
    }

    public Station(Line line, String name) {
        this.line = line;
        this.name = name;
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

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
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

}
