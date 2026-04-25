package MephiPackage.state;

import MephiPackage.core.MissionToolGUI;
import MephiPackage.core.MissionReader;
import MephiPackage.objects.Mission;
import MephiPackage.collection.MissionCollection;
import MephiPackage.report.ReportFactory;
import MephiPackage.report.ReportGenerator;
import MephiPackage.utils.FileChooser;
import MephiPackage.utils.FileFormat;
import MephiPackage.utils.FileTypeDetector;

import javax.swing.*;
import java.io.File;
import java.io.IOException;

public class SingleMissionState implements MissionToolState {

    private MissionToolGUI gui;
    private Mission currentMission;
    private MissionCollection collection;

    public SingleMissionState(MissionToolGUI gui, Mission mission) {
        this.gui = gui;
        this.currentMission = mission;
        this.collection = new MissionCollection();
        this.collection.addMission(mission);
    }

    @Override
    public void display() {
        ReportGenerator generator = ReportFactory.getGenerator("detailed");
        String report = generator.generate(currentMission);
        gui.setTextArea(report);
        gui.updateModeLabel(getModeName());
    }

    @Override
    public void addMission(Mission newMission) {
        MissionCollection newCollection = new MissionCollection();
        newCollection.addMission(currentMission);
        newCollection.addMission(newMission);

        CollectionState newState = new CollectionState(gui, newCollection);
        gui.setState(newState);
        newState.display();

        JOptionPane.showMessageDialog(gui,
                "Переключение в режим коллекции. Всего миссий: 2",
                "Режим изменён",
                JOptionPane.INFORMATION_MESSAGE);
    }

    @Override
    public void addFolder(File folder) {
        MissionCollection newCollection = new MissionCollection();
        newCollection.addMission(currentMission);

        File[] files = folder.listFiles((dir, name) ->
                name.endsWith(".json") || name.endsWith(".xml") || name.endsWith(".txt"));

        if (files != null) {
            for (File file : files) {
                try {
                    FileFormat format = FileTypeDetector.checkType(file);
                    MissionReader reader = new MissionReader();
                    Mission mission = reader.readMission(file);
                    newCollection.addMission(mission);
                } catch (IOException e) {
                    System.err.println("Ошибка: " + file.getName());
                }
            }
        }

        CollectionState newState = new CollectionState(gui, newCollection);
        gui.setState(newState);
        newState.display();

        JOptionPane.showMessageDialog(gui,
                "Добавлено миссий: " + (newCollection.size() - 1),
                "Миссии добавлены",
                JOptionPane.INFORMATION_MESSAGE);
    }

    @Override
    public void showCurrentMission() {
        display();
    }

    @Override
    public void showStatistics() {
        display();
    }

    @Override
    public void showMissionList() {
        String text = "=== СПИСОК МИССИЙ ===\n\n";
        text += "1. " + currentMission.getMissionId() + "\n";
        text += "   Дата: " + currentMission.getDate() + "\n";
        text += "   Локация: " + currentMission.getLocation() + "\n";
        text += "   Результат: " + currentMission.getOutcome() + "\n";
        text += "   Ущерб: " + currentMission.getDamageCost() + "\n\n";
        text += "=== Всего: 1 миссия ===";
        gui.setTextArea(text);
        gui.updateModeLabel(getModeName());
    }

    @Override
    public String getModeName() {
        return "📄 Режим одной миссии";
    }

    @Override
    public int getMissionCount() {
        return 1;
    }

    @Override
    public MissionCollection getCollection() {
        return collection;
    }

    @Override
    public Mission getCurrentMission() {
        return currentMission;
    }
}