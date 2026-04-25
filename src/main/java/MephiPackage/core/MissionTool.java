package MephiPackage.core;

import MephiPackage.logging.ConsoleLogger;
import MephiPackage.logging.EventManager;
import MephiPackage.logging.EventType;
import MephiPackage.logging.FileLogger;
import MephiPackage.objects.Mission;
import MephiPackage.report.ReportConfig;
import MephiPackage.utils.FileChooser;
import MephiPackage.logging.MissionPrinter;
import MephiPackage.utils.ValidationException;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class MissionTool {
    public static void main(String[] args) {
        EventManager eventManager = EventManager.getInstance();

        // Подписываем наблюдателей
        eventManager.subscribeToAll(new ConsoleLogger());
        FileLogger fileLogger = new FileLogger();
        eventManager.subscribeToAll(fileLogger);
        eventManager.subscribe(EventType.MISSION_LOADED, MissionPrinter.getInstance());

        eventManager.notify(EventType.APP_START, "Запуск приложения");

        File file = FileChooser.selectFile();
        if (file == null) return;

        MissionReader missionReader = new MissionReader();

        try {
            Mission mission = missionReader.readMission(file);

            if (mission != null) { //все ли поля на месте?
                List<String> errors = mission.validateRequiredFields();
                if (!errors.isEmpty()) {
                    String errorMessage = "Файл не соответствует формату миссии:\n" +
                            String.join("\n  • ", errors);
                    throw new ValidationException(errorMessage);
                }

                new MissionToolGUI(mission, new ReportConfig()).setVisible(true);
            }

        } catch (ValidationException e) {
            //Все ли поля на месте, ловим
            System.err.println("Ошибка валидации: " + e.getMessage());
            JOptionPane.showMessageDialog(null,
                    "Ошибка валидации:\n" + e.getMessage(),
                    "Неверный формат файла",
                    JOptionPane.ERROR_MESSAGE);

        } catch (IOException e) {
            System.err.println("Ошибка: " + e.getMessage());
            JOptionPane.showMessageDialog(null,
                    "Ошибка чтения файла:\n" + e.getMessage(),
                    "Ошибка",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
}