import Metro.Line;
import org.jsoup.Jsoup;
import org.jsoup.helper.HttpConnection;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import javax.imageio.ImageIO;
import java.awt.image.RenderedImage;
import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.Set;
import java.util.TreeSet;

public class Main {

    private static final String url = "https://ru.wikipedia.org/wiki/%D0%A1%D0%BF%D0%B8%D1%81%D0%BE%D0%BA" +
            "_%D1%81%D1%82%D0%B0%D0%BD%D1%86%D0%B8%D0%B9_%D0%9C%D0%BE%D1%81%D0%BA%D0%BE%D0%B2%D1%81%D0%BA%D0%BE%D0%B3%D0%BE" +
            "_%D0%BC%D0%B5%D1%82%D1%80%D0%BE%D0%BF%D0%BE%D0%BB%D0%B8%D1%82%D0%B5%D0%BD%D0%B0";
    private static final String referer = "http://www.google.com";
    private static final String path = System.getProperty("user.dir") + File.separator;
    private static final String directoryName = "data";

    public static void main(String[] args) {

        Set<Line> lines = parseLines();
        lines.forEach(line -> {
            System.out.println(line.getNumber());
            System.out.println(line.getName());
        });

        //parseStations();

    }

    private static TreeSet<Line> parseLines() {
        try {
            TreeSet<Line> lines = new TreeSet<>();
            Document document = Jsoup.connect(url)
                    .header("Accept-Encoding", "gzip, deflate")
                    .userAgent("Mozilla/5.0 (Windows NT 6.1; WOW64; rv:23.0) Gecko/20100101 Firefox/23.0")
                    .referrer(referer)
                    .maxBodySize(1000000000)
                    .timeout(6000000)
                    .get();
            Element element = document.getElementById("mw-content-text");
            element.getElementsByTag("table")
                    .get(2)
                    .getElementsByTag("tbody")
                    .get(0)
                    .getElementsByTag("tr")
                    .forEach(e->{
                        String lineNumber = e.child(0).attr("data-sort-value");
                        String lineName = e.getElementsByTag("span").get(1).attr("title");
                        Line line = new Line(lineNumber,lineName);
                        lines.add(line);
                    });
            return lines;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new TreeSet<>();
    }

    private static void parseStations() {
        try {
            File output = new File(path + directoryName);
            if (output.exists()) {
                removeFile(output.getPath());
            }
            if (! output.mkdir() ) {
                throw new Exception("Couldn't find output file");
            }
            Document document = Jsoup.connect(url)
                    .userAgent(HttpConnection.DEFAULT_UA)
                    .referrer(referer)
                    .get();
            Element element = document.getElementById("mw-content-text");
            element.getElementsByTag("table")
                    .get(2)
                    .getElementsByTag("tbody")
                    .get(0)
                    .getElementsByTag("tr")
                    .forEach(e->{
                        //System.out.println(e.text());
                        String lineNumber = e.child(0).attr("data-sort-value");
                        String stationName = e.child(1).child(0).text();
                        System.out.println(lineNumber);
                        System.out.println(stationName);
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static boolean removeFile(String path) {
        File fileToDelete = new File(path);
        if (fileToDelete.isDirectory()) {
            for (File sub : fileToDelete.listFiles()) {
                removeFile(sub.getAbsolutePath());
            }
        }
        return fileToDelete.delete();
    }

}
