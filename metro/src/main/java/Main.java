import Metro.Connection;
import Metro.Line;
import Metro.ResultObject;
import Metro.Station;
import Utils.Converter;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;
import java.util.stream.Stream;

public class Main {

    private static final String url = "https://ru.wikipedia.org/wiki/%D0%A1%D0%BF%D0%B8%D1%81%D0%BE%D0%BA" +
            "_%D1%81%D1%82%D0%B0%D0%BD%D1%86%D0%B8%D0%B9_%D0%9C%D0%BE%D1%81%D0%BA%D0%BE%D0%B2%D1%81%D0%BA%D0%BE%D0%B3%D0%BE" +
            "_%D0%BC%D0%B5%D1%82%D1%80%D0%BE%D0%BF%D0%BE%D0%BB%D0%B8%D1%82%D0%B5%D0%BD%D0%B0";
    private static final String referer = "http://www.google.com";

    public static void main(String[] args) {

        TreeSet<Line> lines = new TreeSet<>();
        TreeSet<Station> stations = new TreeSet<>();
        List<Connection> connections = new ArrayList<>();

        /*Parse Wiki*/
        parseData(lines,stations,connections);
        /*Set objects data*/
        TreeSet<Station> finalStations = stations;
        lines.forEach(line -> {
            List<Station> lineStations = new ArrayList<>();
            for (Station station : finalStations) {
                if (line.equals(station.getLine())) {
                    lineStations.add(station);
                }
            }
            line.setStations(lineStations);
        });
        /*Convert to JSON single file*/
        ResultObject resultObject = new ResultObject(lines,stations,connections);
        try {
            Converter.toJSON(resultObject,"result.json");
        } catch (IOException e) {
            e.printStackTrace();
        }

        /*Clear Objects*/
        resultObject = null;
        lines = new TreeSet<>();
        stations = new TreeSet<>();
        connections = new ArrayList<>();
        /*Deserialize Object*/
        try {
            resultObject = Converter.toJavaObject("result.json");
        } catch (IOException e) {
            e.printStackTrace();
        }

        /*Set objects data*/
        assert resultObject != null;
        ResultObject finalResultObject = resultObject;
        resultObject.getLines().forEach(line -> {
            List<Station> lineStations = new ArrayList<>();
            for (Station station : finalResultObject.getStations()) {
                if (line.equals(station.getLine())) {
                    lineStations.add(station);
                }
            }
            line.setStations(lineStations);
        });

        /*Output result*/
        resultObject.getLines().forEach(line -> {
            System.out.printf("%-5s %-35s Кол-во станций: %s \n", line.getNumber(),line.getName(),line.getStations().size());
        });

    }

    private static void parseData(TreeSet<Line> lines, TreeSet<Station> stations, List<Connection> connections) {
        try {
            Document document = Jsoup.connect(url)
                    .header("Accept-Encoding", "gzip, deflate")
                    .userAgent("Mozilla/5.0 (Windows NT 6.1; WOW64; rv:23.0) Gecko/20100101 Firefox/23.0")
                    .referrer(referer)
                    .maxBodySize(1000000000)
                    .timeout(6000000)
                    .get();
            Element element = document.getElementById("mw-content-text");
            /*Parse Lines and Stations*/
            Stream<Integer> numbers = Stream.of(2,3,4);
            numbers.forEach(n->{
                element.getElementsByTag("table")
                        .get(n)
                        .getElementsByTag("tbody")
                        .get(0)
                        .getElementsByTag("tr")
                        .forEach(e->{
                            try {
                                String lineNumber = e.child(0).child(0).text();
                                String lineName = e.getElementsByTag("span").get(1).attr("title");
                                String stationName = e.child(1).child(0).text();

                                Line line = new Line(lineNumber,lineName);
                                Station station = new Station(stationName,line);
                                stations.add(station);
                                lines.add(line);
                            } catch (Exception ex) {
                            }
                        });
            });
            /*Parse connections*/
            numbers = Stream.of(2,3,4);
            numbers.forEach(n->{
                element.getElementsByTag("table")
                        .get(n)
                        .getElementsByTag("tbody")
                        .get(0)
                        .getElementsByTag("tr")
                        .forEach(e->{
                            TreeSet<Station> connectionSet = new TreeSet<>();
                            String lineNumber = null;
                            String stationName = null;
                            String connectionLine1 = null;
                            String connectionName1 = null;
                            String connectionLine2 = null;
                            String connectionName2 = null;
                            String connectionLine3 = null;
                            String connectionName3 = null;
                            try {
                                lineNumber = e.child(0).child(0).text();
                                stationName = e.child(1).child(0).text();
                                connectionLine1 = e.child(3).child(0).text();
                                connectionName1 = e.child(3).child(1).attr("title");
                                connectionLine2 = e.child(3).child(2).text();
                                connectionName2 = e.child(3).child(3).attr("title");
                                connectionLine3 = e.child(3).child(4).text();
                                connectionName3 = e.child(3).child(5).attr("title");
                            } catch (Exception ex){

                            }
                            if (stationName != null && lineNumber != null) {
                                String finalStationName = stationName;
                                String finalLineNumber = lineNumber;
                                Station st = stations.stream()
                                        .filter(station ->
                                                station.getName().toLowerCase().equals(finalStationName.toLowerCase())
                                                        &&
                                                        station.getLine().getNumber().toLowerCase().equals(finalLineNumber.toLowerCase())
                                        )
                                        .findFirst().orElse(null);
                                if (st != null) {
                                    //System.out.println("Original station " + st.getName());
                                    connectionSet.add(st);
                                }
                            }

                            if (connectionLine1 != null && connectionName1 != null) {
                                String finalConnectionLine = connectionLine1;
                                String finalConnectionName = connectionName1;
                                Station st = stations.stream()
                                        .filter(station ->
                                                finalConnectionName.toLowerCase().contains(station.getName().toLowerCase())
                                                &&
                                                station.getLine().getNumber().toLowerCase().equals(finalConnectionLine.toLowerCase())
                                        )
                                        .findFirst().orElse(null);
                                if (st != null) {
                                    //System.out.println("Connection1 " + st.getName());
                                    connectionSet.add(st);
                                }
                            }

                            if (connectionLine2 != null && connectionName2 != null) {
                                String finalConnectionLine = connectionLine2;
                                String finalConnectionName = connectionName2;
                                Station st = stations.stream()
                                        .filter(station ->
                                                finalConnectionName.toLowerCase().contains(station.getName().toLowerCase())
                                                &&
                                                station.getLine().getNumber().toLowerCase().equals(finalConnectionLine.toLowerCase())
                                        )
                                        .findFirst().orElse(null);
                                if (st != null) {
                                    //System.out.println("Connection2 " + st.getName());
                                    connectionSet.add(st);
                                }
                            }

                            if (connectionLine3 != null && connectionName3 != null) {
                                String finalConnectionLine = connectionLine3;
                                String finalConnectionName = connectionName3;
                                Station st = stations.stream()
                                        .filter(station ->
                                                finalConnectionName.toLowerCase().contains(station.getName().toLowerCase())
                                                &&
                                                station.getLine().getNumber().toLowerCase().equals(finalConnectionLine.toLowerCase())
                                        )
                                        .findFirst().orElse(null);
                                if (st != null) {
                                    //System.out.println("Connection3 " + st.getName());
                                    connectionSet.add(st);
                                }
                            }
                            if (connectionSet.size() > 1) {
                                connections.add(new Connection(connectionSet));
                            }
                        });
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
