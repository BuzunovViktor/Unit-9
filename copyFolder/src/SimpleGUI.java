import javax.swing.*;
import java.io.*;

public class SimpleGUI extends JFrame {
    public SimpleGUI() {
        int opt = 0;
        while (opt != JOptionPane.CANCEL_OPTION) {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            fileChooser.setDialogTitle("Какую папку скопировать?");
            int option = fileChooser.showSaveDialog(this);
            if (option == JFileChooser.CANCEL_OPTION) break;
            File fileSrc,fileDst;
            if (fileChooser.getSelectedFile() != null) {
                fileSrc = new File(fileChooser.getSelectedFile().getAbsolutePath());
            } else break;
            fileChooser.setDialogTitle("Папка назначения");
            option = fileChooser.showSaveDialog(this);
            if (option == JFileChooser.CANCEL_OPTION) break;
            if (fileChooser.getSelectedFile() != null) {
                fileDst = new File(fileChooser.getSelectedFile().getAbsolutePath());
            } else break;
            String message;
            try {
                copy(fileSrc,fileDst);
                message = "Успешное копирование";
            } catch (Exception e) {
                message = "Ошибка при копировании папки";
                e.printStackTrace();
            }

            opt = JOptionPane.showConfirmDialog(
                    null,
                    message,
                    "Результаты",
                    JOptionPane.OK_CANCEL_OPTION);
        }
        System.exit(0);
    }

    public void copy(File src, File dst) throws IOException {
        if (src.isDirectory()) {
           copyRoot(src,dst);
        } else {
            copyChild(src,dst);
        }
    }

    private void copyRoot(File src, File dst) throws IOException {
        File newRoot = new File(dst.getAbsolutePath() + File.separator +  src.getName());
        if (!newRoot.exists()) {
            newRoot.mkdir();
        }
        copyChild(src,newRoot);
    }

    private void copyChild(File src, File dst) throws IOException {
        if (src.isDirectory()) {
            copyDirectory(src, dst);
        } else {
            copyFile(src, dst);
        }
    }

    private void copyDirectory(File src, File dst) throws IOException {
        if (!dst.exists()) {
            dst.mkdir();
        }
        for (String f : src.list()) {
            copyChild(new File(src, f), new File(dst, f));
        }
    }

    private void copyFile(File src, File dst) throws IOException {
        try (   InputStream in = new FileInputStream(src);
                OutputStream out = new FileOutputStream(dst)) {
            byte[] buf = new byte[1024];
            int length;
            while ((length = in.read(buf)) > 0) {
                out.write(buf, 0, length);
            }
            in.close();
            out.flush();
            out.close();
        }
    }

}