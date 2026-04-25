package MephiPackage.state;

import MephiPackage.core.MissionToolGUI;
import MephiPackage.core.MissionReader;
import MephiPackage.objects.Mission;
import MephiPackage.collection.MissionCollection;
import MephiPackage.collection.AggregatedStatistics;
import MephiPackage.report.ReportFactory;
import MephiPackage.report.ReportGenerator;
import MephiPackage.utils.FileChooser;
import MephiPackage.utils.FileFormat;
import MephiPackage.utils.FileTypeDetector;

import javax.swing.*;
import java.io.File;
import java.io.IOException;

public class CollectionState implements MissionToolState {

    private MissionToolGUI gui;
    private MissionCollection collection;
    private Mission currentViewMission;

    public CollectionState(MissionToolGUI gui, MissionCollection collection) {
        this.gui = gui;
        this.collection = collection;
        this.currentViewMission = collection.getAll().isEmpty() ? null : collection.getAll().get(0);
    }

    @Override
    public void display() {
        showStatistics();
    }

    @Override
    public void addMission(Mission newMission) {
        collection.addMission(newMission);
        showStatistics();
        JOptionPane.showMessageDialog(gui,
                "Миссия добавлена. Всего миссий: " + collection.size(),
                "Миссия добавлена",
                JOptionPane.INFORMATION_MESSAGE);
    }

    @Override
    public void addFolder(File folder) {
        File[] files = folder.listFiles((dir, name) ->
                name.endsWith(".json") || name.endsWith(".xml") || name.endsWith(".txt"));

        if (files == null || files.length == 0) {
            JOptionPane.showMessageDialog(gui,
                    "В папке нет файлов миссий",
                    "Информация",
                    JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        int added = 0;
        int errors = 0;

        for (File file : files) {
            try {
                // Определяем тип файла
                FileFormat format = FileTypeDetector.checkType(file);

                // Создаём ридер через MissionReader (он сам выберет нужного)
                MissionReader reader = new MissionReader();
                Mission mission = reader.readMission(file);

                collection.addMission(mission);
                added++;
            } catch (IOException e) {
                errors++;
                System.err.println("Ошибка: " + file.getName() + " - " + e.getMessage());
            }
        }

        showStatistics();

        JOptionPane.showMessageDialog(gui,
                "Добавлено миссий: " + added + "\nОшибок: " + errors,
                "Миссии добавлены",
                JOptionPane.INFORMATION_MESSAGE);
    }

    @Override
    public void showCurrentMission() {
        if (currentViewMission == null) {
            gui.setTextArea("Нет выбранной миссии");
            return;
        }

        ReportGenerator generator = ReportFactory.getGenerator("detailed");
        String report = generator.generate(currentViewMission);
        gui.setTextArea(report);
        gui.updateModeLabel(getModeName() + " — просмотр: " + currentViewMission.getMissionId());
    }

    @Override
    public void showStatistics() {
        AggregatedStatistics stats = collection.getStatistics();
        String report = stats.toString();

        StringBuilder sb = new StringBuilder();
        sb.append(report);
        sb.append("\n\n【СПИСОК МИССИЙ】\n");

        for (int i = 0; i < collection.size(); i++) {
            Mission m = collection.getAll().get(i);
            sb.append("  ").append(i + 1).append(". ");
            sb.append(m.getMissionId()).append(" | ");
            sb.append(m.getDate()).append(" | ");
            sb.append(m.getOutcome()).append(" | ");
            sb.append("Ущерб: ").append(m.getDamageCost());
            sb.append("\n");
        }

        gui.setTextArea(sb.toString());
        gui.updateModeLabel(getModeName());
    }

    @Override
    public void showMissionList() {
        StringBuilder sb = new StringBuilder();
        sb.append("=== СПИСОК ЗАГРУЖЕННЫХ МИССИЙ ===\n\n");

        for (int i = 0; i < collection.size(); i++) {
            Mission m = collection.getAll().get(i);
            sb.append((i + 1) + ". " + m.getMissionId() + "\n");
            sb.append("   Дата: " + m.getDate() + "\n");
            sb.append("   Локация: " + m.getLocation() + "\n");
            sb.append("   Результат: " + m.getOutcome() + "\n");
            sb.append("   Ущерб: " + m.getDamageCost() + "\n");
            sb.append("\n");
        }

        sb.append("=== " + collection.size() + " миссий ===");

        gui.setTextArea(sb.toString());
        gui.updateModeLabel(getModeName());

        sb.append("\nДля просмотра миссии введите её номер: ");

        String input = JOptionPane.showInputDialog(gui, sb.toString(), "Выбор миссии", JOptionPane.QUESTION_MESSAGE);
        if (input != null) {
            try {
                int index = Integer.parseInt(input) - 1;
                if (index >= 0 && index < collection.size()) {
                    currentViewMission = collection.getAll().get(index);
                    showCurrentMission();
                }
            } catch (NumberFormatException e) {
                // игнорируем
            }
        }
    }

    @Override
    public String getModeName() {
        return "📊 Режим коллекции (" + collection.size() + " миссий)";
    }

    @Override
    public int getMissionCount() {
        return collection.size();
    }

    @Override
    public MissionCollection getCollection() {
        return collection;
    }

    @Override
    public Mission getCurrentMission() {
        return currentViewMission;
    }

    public void setCurrentViewMission(Mission mission) {
        this.currentViewMission = mission;
    }
}