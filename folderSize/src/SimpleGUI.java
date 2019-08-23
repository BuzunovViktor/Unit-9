import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicLong;

public class SimpleGUI extends JFrame {
    public SimpleGUI() {
        int opt = 0;
        while (opt != 2) {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            fileChooser.setDialogTitle("Выберите папку");
            fileChooser.showSaveDialog(this);
            File file = new File(fileChooser.getSelectedFile().getAbsolutePath());
            //Рекурсивное решение
            Long sizeRecursion = getFolderSizeRecursion(file);
            //Java 8 решение
            Long sizeLambda = getFolderSize(file.toPath());
            String message = "Размер папки: " + file.getAbsolutePath() + " \n" +
                    "Рекурсивное решение: " + sizeRecursion + " байт, " + String.format("%.2f",sizeRecursion / 1024. / 1024.) + " мб\n" +
                    "Java 8 решение: " + sizeLambda + " байт, " + String.format("%.2f",sizeLambda / 1024. / 1024.) + " мб\n";
            opt = JOptionPane.showConfirmDialog(
                    null,
                    message,
                    "Результаты",
                    JOptionPane.OK_CANCEL_OPTION);
        }
        System.exit(0);
    }

    private Long getFolderSizeRecursion(File file) {
        Long sizeInBytes = 0L;
        try {
            for (File f : Objects.requireNonNull(file.listFiles())) {
                sizeInBytes += f.isFile() ? f.length() : getFolderSizeRecursion(f);
            }
        } catch (Exception e) {
            return 0L;
        }
        return sizeInBytes;
    }

    public long getFolderSize(Path path) {
        final AtomicLong size = new AtomicLong(0);
        try {
            Files.walkFileTree(path, new SimpleFileVisitor<Path>() {
                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {
                    size.addAndGet(attrs.size());
                    return FileVisitResult.CONTINUE;
                }
                @Override
                public FileVisitResult visitFileFailed(Path file, IOException exc) {

                    System.out.println("skipped: " + file + " (" + exc + ")");
                    return FileVisitResult.CONTINUE;
                }
                @Override
                public FileVisitResult postVisitDirectory(Path dir, IOException exc) {
                    if (exc != null)
                        System.out.println("had trouble traversing: " + dir + " (" + exc + ")");
                    return FileVisitResult.CONTINUE;
                }
            });
        } catch (IOException e) {
            throw new AssertionError("walkFileTree will not throw IOException if the FileVisitor does not");
        }
        return size.get();
    }

}