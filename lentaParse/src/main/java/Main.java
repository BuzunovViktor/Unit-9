import org.jsoup.Jsoup;
import org.jsoup.helper.HttpConnection;
import org.jsoup.nodes.Document;
import javax.imageio.ImageIO;
import java.awt.image.RenderedImage;
import java.io.File;
import java.net.URL;

public class Main {

    public static void main(String[] args) throws Exception {

        final String url = "https://lenta.ru";
        final String referer = "http://www.google.com";
        final String fileFormat = "jpg";
        //Файлы будут сохранены в папку проекта
        final String path = System.getProperty("user.dir") + File.separator;
        final String directoryName = "savedImages";

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
            document.getElementsByTag("img").forEach(element -> {
                try {
                    String src = element.attr("src");
                    String filename = src.substring(src.lastIndexOf("/"),src.lastIndexOf("."));
                    RenderedImage image = ImageIO.read(new URL(src));
                    File imageFile = new File(output.getAbsolutePath() + File.separator + filename + "." + fileFormat);
                    ImageIO.write(image,"jpg", imageFile);
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
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
