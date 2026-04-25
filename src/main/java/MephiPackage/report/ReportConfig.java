package MephiPackage.report;

import java.util.HashSet;
import java.util.Set;

import java.util.HashSet;
import java.util.Set;

public class ReportConfig {

    public enum Section {
        BASIC_INFO("Основные данные"),
        CURSES("Проклятия"),
        SORCERERS("Участники"),
        TECHNIQUES("Техники"),
        ECONOMIC("Экономическая оценка"),
        CIVILIAN("Влияние на гражданских"),
        ENEMY("Поведение противника"),
        ENVIRONMENT("Условия среды");

        private final String displayName;

        Section(String displayName) {
            this.displayName = displayName;
        }

        public String getDisplayName() {
            return displayName;
        }
    }

    private Set<Section> enabledSections = new HashSet<>();

    public ReportConfig() {
        // По умолчанию включены основные разделы
        enabledSections.add(Section.BASIC_INFO);
        enabledSections.add(Section.CURSES);
        enabledSections.add(Section.SORCERERS);
        enabledSections.add(Section.TECHNIQUES);
    }

    public void enableSection(Section section) {
        if (section != null) {
            enabledSections.add(section);
        }
    }

    public void disableSection(Section section) {
        if (section != null) {
            enabledSections.remove(section);
        }
    }

    public boolean isEnabled(Section section) {
        return section != null && enabledSections.contains(section);
    }

    public Set<Section> getEnabledSections() {
        return new HashSet<>(enabledSections);
    }

    public void setEnabledSections(Set<Section> sections) {
        if (sections != null) {
            this.enabledSections = new HashSet<>(sections);
        }
    }

    public void enableAll() {
        for (Section section : Section.values()) {
            enabledSections.add(section);
        }
    }

    public void disableAll() {
        enabledSections.clear();
    }

    public void resetToDefault() {
        enabledSections.clear();
        enabledSections.add(Section.BASIC_INFO);
        enabledSections.add(Section.CURSES);
        enabledSections.add(Section.SORCERERS);
        enabledSections.add(Section.TECHNIQUES);
    }
}
