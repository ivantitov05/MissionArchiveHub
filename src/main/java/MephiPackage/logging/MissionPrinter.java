package MephiPackage.logging;

import MephiPackage.objects.Curse;
import MephiPackage.objects.Mission;
import MephiPackage.objects.Sorcerer;
import MephiPackage.objects.Technique;
import java.util.List;

public class MissionPrinter implements EventListener {

    private static MissionPrinter instance;

    private MissionPrinter() {}

    public static MissionPrinter getInstance() {
        if (instance == null) {
            instance = new MissionPrinter();
        }
        return instance;
    }

    @Override
    public void onEvent(ApplicationEvent event) {
        // Реагируем только на событие загрузки миссии
        if (event.getType() == EventType.MISSION_LOADED) {
            Object source = event.getSource();
            if (source instanceof Mission) {
                print((Mission) source);
            }
        }
    }

    public void print(Mission mission) {
        if (mission == null) {
            System.out.println("Миссия не загружена");
            return;
        }

        System.out.println("Данные о прочитанной миссии:\n");
        printBasicInfo(mission);
        printCurseInfo(mission.getCurses());
        printSorcerersInfo(mission.getSorcerers());
        printTechniquesInfo(mission.getTechniques());
    }

    private void printBasicInfo(Mission mission) {
        System.out.println("Основные данные:");
        System.out.println("ID миссии: " + safeString(mission.getMissionId()));
        System.out.println("Дата: " + safeString(mission.getDate()));
        System.out.println("Локация: " + safeString(mission.getLocation()));
        System.out.println("Результат: " + safeString(mission.getOutcome()));
        System.out.println("Ущерб: " + mission.getDamageCost());
    }

    private void printCurseInfo(List<Curse> curses) {
        System.out.println("Проклятья :");
        if (curses == null || curses.isEmpty()) {
            System.out.println("Нет проклятий");
            return;
        }

        for (int i = 0; i < curses.size(); i++) {
            Curse s = curses.get(i);
            System.out.println("Проклятье: " + safeString(s.getName()));
        }
    }

    private void printSorcerersInfo(List<Sorcerer> sorcerers) {
        System.out.println("Волшебники на задании:");

        if (sorcerers == null || sorcerers.isEmpty()) {
            System.out.println("Нет волшебников");
            return;
        }

        for (int i = 0; i < sorcerers.size(); i++) {
            Sorcerer s = sorcerers.get(i);
            String rankDisplay = s.getRank() != null ? s.getRank().toString() : "-";
            System.out.println("  " + (i+1) + ". " + safeString(s.getName()) +
                    " (ранг: " + rankDisplay + ")");
        }
    }

    private void printTechniquesInfo(List<Technique> techniques) {
        System.out.println("Техники: ");

        if (techniques == null || techniques.isEmpty()) {
            System.out.println("Нет техник");
            return;
        }

        for (int i = 0; i < techniques.size(); i++) {
            Technique t = techniques.get(i);
            System.out.println("  " + (i+1) + ". " + safeString(t.getName()));
            System.out.println("Тип: " + safeString(t.getType()));
            System.out.println("Владелец: " + safeString(t.getOwner()));
            System.out.println("Урон: " + t.getDamage());
        }
    }

    private String safeString(Object obj) {
        if (obj == null) return "-";
        String str = obj.toString();
        return str.isEmpty() ? "-" : str;
    }
}