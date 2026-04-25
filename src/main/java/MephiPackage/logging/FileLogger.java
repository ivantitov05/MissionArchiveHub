package MephiPackage.logging;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;


public class FileLogger implements EventListener {

    private PrintWriter writer;
    private boolean enabled = true;
    private static final String LOG_FILE = "mission_analyzer.log";

    public FileLogger() {
        try {
            writer = new PrintWriter(new FileWriter(LOG_FILE, true));
            System.out.println("Логирование в файл включено: " + LOG_FILE);
        } catch (IOException e) {
            System.err.println("Не удалось открыть лог-файл: " + LOG_FILE);
            System.err.println("Ошибка: " + e.getMessage());
            enabled = false;
        }
    }

    @Override
    public void onEvent(ApplicationEvent event) {
        if (!enabled) return;
        if (writer != null) {
            writer.println(event.format());
            writer.flush();
        }
    }

    public void close() {
        if (writer != null) {
            writer.close();
            System.out.println("Лог-файл закрыт");
        }
    }
}