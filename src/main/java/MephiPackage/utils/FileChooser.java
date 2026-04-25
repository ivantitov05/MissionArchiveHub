package MephiPackage.utils;

import MephiPackage.utils.FileFormat;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;
import java.util.List;
import java.util.stream.Collectors;

public class FileChooser {

    private static final String DEFAULT_DIRECTORY = "C:/Users/Ivan/Desktop/Java";

    public static File selectFile() {
        JFileChooser fileChooser = new JFileChooser();

        File defaultDir = new File(DEFAULT_DIRECTORY);
        if (defaultDir.exists() && defaultDir.isDirectory()) {
            fileChooser.setCurrentDirectory(defaultDir);
        } else {
            fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
        }

        List<String> extensions = FileFormat.getAllExtensions();

        if (extensions != null && !extensions.isEmpty()) {
            List<String> validExtensions = extensions.stream()
                    .filter(ext -> ext != null && !ext.isEmpty())
                    .collect(Collectors.toList());

            if (!validExtensions.isEmpty()) {
                String[] extArray = validExtensions.toArray(new String[0]);
                FileNameExtensionFilter filter = new FileNameExtensionFilter(
                        FileFormat.getDescription(),
                        extArray
                );
                fileChooser.setFileFilter(filter);
            }
        }

        int result = fileChooser.showOpenDialog(null);

        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            System.out.println("Выбран файл: " + selectedFile.getAbsolutePath());
            return selectedFile;
        } else {
            System.out.println("Файл не выбран");
            return null;
        }
    }

    public static File selectFolder() {
        JFileChooser folderChooser = new JFileChooser();
        folderChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        folderChooser.setDialogTitle("Выберите папку с миссиями");

        int result = folderChooser.showOpenDialog(null);

        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFolder = folderChooser.getSelectedFile();
            System.out.println("Выбрана папка: " + selectedFolder.getAbsolutePath());
            return selectedFolder;
        } else {
            System.out.println("Папка не выбрана");
            return null;
        }
    }
}