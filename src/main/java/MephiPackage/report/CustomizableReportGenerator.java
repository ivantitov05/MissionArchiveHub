package MephiPackage.report;

import MephiPackage.objects.Mission;
import MephiPackage.objects.Curse;
import MephiPackage.objects.Sorcerer;
import MephiPackage.objects.Technique;
import MephiPackage.objects.EconomicAssessment;
import MephiPackage.objects.CivilianImpact;

public class CustomizableReportGenerator implements ReportGenerator {

    private ReportConfig config;

    public CustomizableReportGenerator(ReportConfig config) {
        this.config = config;
    }

    public void setConfig(ReportConfig config) {
        this.config = config;
    }

    public ReportConfig getConfig() {
        return config;
    }

    @Override
    public String generate(Mission mission) {
        if (mission == null) {
            return "Ошибка: миссия не загружена";
        }

        String result = "";

        result += "                 Пользовательский отчёт\n";

        if (config.isEnabled(ReportConfig.Section.BASIC_INFO)) {
            result += basicInfo(mission);
        }

        if (config.isEnabled(ReportConfig.Section.CURSES)) {
            result += cursesInfo(mission);
        }

        if (config.isEnabled(ReportConfig.Section.SORCERERS)) {
            result += sorcerersInfo(mission);
        }

        if (config.isEnabled(ReportConfig.Section.TECHNIQUES)) {
            result += techniquesInfo(mission);
        }

        if (config.isEnabled(ReportConfig.Section.ECONOMIC)) {
            result += economicInfo(mission);
        }

        if (config.isEnabled(ReportConfig.Section.CIVILIAN)) {
            result += civilianInfo(mission);
        }

        return result;
    }

    private String basicInfo(Mission mission) {
        String result = "";
        result += "Основные данные\n";
        result += "  ID миссии: " + nullSafe(mission.getMissionId()) + "\n";
        result += "  Дата: " + nullSafe(mission.getDate()) + "\n";
        result += "  Локация: " + nullSafe(mission.getLocation()) + "\n";
        result += "  Результат: " + nullSafe(mission.getOutcome()) + "\n";
        result += "  Ущерб: " + mission.getDamageCost() + "\n\n";
        return result;
    }

    private String cursesInfo(Mission mission) {
        String result = "";
        result += "Проклятия\n";

        if (mission.getCurses() == null || mission.getCurses().isEmpty()) {
            result += "  (нет данных)\n";
        } else {
            for (Curse c : mission.getCurses()) {
                result += "  • " + nullSafe(c.getName());
                if (c.getThreatLevel() != null) {
                    result += " (уровень: " + c.getThreatLevel() + ")";
                }
                result += "\n";
            }
        }
        result += "\n";
        return result;
    }

    private String sorcerersInfo(Mission mission) {
        String result = "";
        result += "Участники\n";

        if (mission.getSorcerers() == null || mission.getSorcerers().isEmpty()) {
            result += "  (нет данных)\n";
        } else {
            for (Sorcerer s : mission.getSorcerers()) {
                result += "  • " + nullSafe(s.getName());
                if (s.getRank() != null) {
                    result += " (ранг: " + s.getRank() + ")";
                }
                result += "\n";
            }
        }
        result += "\n";
        return result;
    }

    private String techniquesInfo(Mission mission) {
        String result = "";
        result += "Техники\n";

        if (mission.getTechniques() == null || mission.getTechniques().isEmpty()) {
            result += "  (нет данных)\n";
        } else {
            for (Technique t : mission.getTechniques()) {
                result += "  • " + nullSafe(t.getName()) + "\n";
                if (t.getType() != null) {
                    result += "    Тип: " + t.getType() + "\n";
                }
                if (t.getOwner() != null) {
                    result += "    Владелец: " + t.getOwner() + "\n";
                }
                result += "    Урон: " + t.getDamage() + "\n";
            }
        }
        result += "\n";
        return result;
    }

    private String economicInfo(Mission mission) {
        EconomicAssessment econ = mission.getEconomicAssessment();
        if (econ == null) {
            return "";
        }

        String result = "";
        result += "Экономическая оценка\n";

        if (econ.getTotalDamageCost() != null) {
            result += "  Общий ущерб: " + econ.getTotalDamageCost() + "\n";
        }
        if (econ.getRecoveryEstimateDays() != null) {
            result += "  Время восстановления: " + econ.getRecoveryEstimateDays() + " дней\n";
        }
        if (econ.getInsuranceCovered() != null) {
            result += "  Страховое покрытие: " + (econ.getInsuranceCovered() ? "Да" : "Нет") + "\n";
        }
        result += "\n";
        return result;
    }

    private String civilianInfo(Mission mission) {
        CivilianImpact impact = mission.getCivilianImpact();
        if (impact == null) {
            return "";
        }

        String result = "";
        result += "Влияние на гражданских\n";

        if (impact.getEvacuated() != null) {
            result += "  Эвакуировано: " + impact.getEvacuated() + "\n";
        }
        if (impact.getInjured() != null) {
            result += "  Пострадавшие: " + impact.getInjured() + "\n";
        }
        if (impact.getMissing() != null) {
            result += "  Пропавшие: " + impact.getMissing() + "\n";
        }
        result += "\n";
        return result;
    }

    private String nullSafe(Object obj) {
        return obj != null ? obj.toString() : "—";
    }

    @Override
    public String getType() {
        return "customizable";
    }
}