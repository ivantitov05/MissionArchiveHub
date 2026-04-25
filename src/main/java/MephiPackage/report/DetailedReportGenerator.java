package MephiPackage.report;

import MephiPackage.objects.Mission;
import MephiPackage.objects.Curse;
import MephiPackage.objects.Sorcerer;
import MephiPackage.objects.Technique;
import MephiPackage.objects.EconomicAssessment;
import MephiPackage.objects.CivilianImpact;
import MephiPackage.objects.EnemyActivity;
import MephiPackage.objects.EnvironmentConditions;
import MephiPackage.objects.OperationTimeline;

public class DetailedReportGenerator implements ReportGenerator {

    private static final String SEPARATOR = "============================================================";
    private static final String LINE = "------------------------------------------------------------";

    @Override
    public String generate(Mission mission) {
        if (mission == null) {
            return "Ошибка: миссия не загружена";
        }

        String result = "";

        result += SEPARATOR + "\n";
        result += "                 Детальный отчёт\n";
        result += SEPARATOR + "\n\n";

        result += basicInfo(mission);

        result += cursesInfo(mission);

        result += sorcerersInfo(mission);

        result += techniquesInfo(mission);

        result += economicInfo(mission);

        result += civilianInfo(mission);

        result += enemyInfo(mission);

        result += environmentInfo(mission);

        result += timelineInfo(mission);

        result += additionalInfo(mission);

        result += SEPARATOR + "\n";

        return result;
    }

    private String basicInfo(Mission mission) {
        String result = "";
        result += "Освноные данные\n";
        result += "  ID миссии:   " + nullSafe(mission.getMissionId()) + "\n";
        result += "  Дата:        " + nullSafe(mission.getDate()) + "\n";
        result += "  Локация:     " + nullSafe(mission.getLocation()) + "\n";
        result += "  Результат:   " + nullSafe(mission.getOutcome()) + "\n";
        result += "  Ущерб:       " + mission.getDamageCost() + "\n";
        result += "  Комментарий: " + nullSafe(mission.getComment()) + "\n\n";
        return result;
    }

    private String cursesInfo(Mission mission) {
        String result = "";
        result += "Проклятия\n";

        if (mission.getCurses() == null || mission.getCurses().isEmpty()) {
            result += "  (нет данных)\n";
        } else {
            for (int i = 0; i < mission.getCurses().size(); i++) {
                Curse c = mission.getCurses().get(i);
                result += "  " + (i + 1) + ". " + nullSafe(c.getName()) + "\n";
                result += "     Уровень угрозы: " + nullSafe(c.getThreatLevel()) + "\n";
                if (i < mission.getCurses().size() - 1) {
                    result += "     " + LINE + "\n";
                }
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
            for (int i = 0; i < mission.getSorcerers().size(); i++) {
                Sorcerer s = mission.getSorcerers().get(i);
                result += "  " + (i + 1) + ". " + nullSafe(s.getName()) + "\n";
                result += "     Ранг: " + nullSafe(s.getRank()) + "\n";
                if (i < mission.getSorcerers().size() - 1) {
                    result += "     " + LINE + "\n";
                }
            }
        }
        result += "\n";
        return result;
    }

    private String techniquesInfo(Mission mission) {
        String result = "";
        result += "【ПРИМЕНЁННЫЕ ТЕХНИКИ】\n";

        if (mission.getTechniques() == null || mission.getTechniques().isEmpty()) {
            result += "  (нет данных)\n";
        } else {
            for (int i = 0; i < mission.getTechniques().size(); i++) {
                Technique t = mission.getTechniques().get(i);
                result += "  " + (i + 1) + ". " + nullSafe(t.getName()) + "\n";
                result += "     Тип: " + nullSafe(t.getType()) + "\n";
                result += "     Владелец: " + nullSafe(t.getOwner()) + "\n";
                result += "     Урон: " + t.getDamage() + "\n";
                if (i < mission.getTechniques().size() - 1) {
                    result += "     " + LINE + "\n";
                }
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
        if (econ.getInfrastructureDamage() != null) {
            result += "  Ущерб инфраструктуре: " + econ.getInfrastructureDamage() + "\n";
        }
        if (econ.getCommercialDamage() != null) {
            result += "  Коммерческий ущерб: " + econ.getCommercialDamage() + "\n";
        }
        if (econ.getTransportDamage() != null) {
            result += "  Ущерб транспорту: " + econ.getTransportDamage() + "\n";
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
        if (impact.getPublicExposureRisk() != null) {
            result += "  Риск раскрытия: " + impact.getPublicExposureRisk() + "\n";
        }
        result += "\n";
        return result;
    }

    private String enemyInfo(Mission mission) {
        EnemyActivity enemy = mission.getEnemyActivity();
        if (enemy == null) {
            return "";
        }

        String result = "";
        result += "Поведение противника\n";
        if (enemy.getBehaviorType() != null) {
            result += "  Тип поведения: " + enemy.getBehaviorType() + "\n";
        }
        if (enemy.getTargetPriority() != null && !enemy.getTargetPriority().isEmpty()) {
            result += "  Приоритет целей: " + String.join(", ", enemy.getTargetPriority()) + "\n";
        }
        if (enemy.getAttackPatterns() != null && !enemy.getAttackPatterns().isEmpty()) {
            result += "  Паттерны атак: " + String.join(", ", enemy.getAttackPatterns()) + "\n";
        }
        if (enemy.getMobility() != null) {
            result += "  Мобильность: " + enemy.getMobility() + "\n";
        }
        if (enemy.getEscalationRisk() != null) {
            result += "  Риск эскалации: " + enemy.getEscalationRisk() + "\n";
        }
        result += "\n";
        return result;
    }

    private String environmentInfo(Mission mission) {
        EnvironmentConditions env = mission.getEnvironmentConditions();
        if (env == null) {
            return "";
        }

        String result = "";
        result += "Условия среды\n";
        if (env.getWeather() != null) {
            result += "  Погода: " + env.getWeather() + "\n";
        }
        if (env.getTimeOfDay() != null) {
            result += "  Время суток: " + env.getTimeOfDay() + "\n";
        }
        if (env.getVisibility() != null) {
            result += "  Видимость: " + env.getVisibility() + "\n";
        }
        if (env.getCursedEnergyDensity() != null) {
            result += "  Плотность проклятой энергии: " + env.getCursedEnergyDensity() + "\n";
        }
        result += "\n";
        return result;
    }

    private String timelineInfo(Mission mission) {
        java.util.List<OperationTimeline> timeline = mission.getOperationTimeline();
        if (timeline == null || timeline.isEmpty()) {
            return "";
        }

        String result = "";
        result += "Хронология событий \n";
        for (OperationTimeline event : timeline) {
            result += "  " + nullSafe(event.getTimestamp()) + " - " + nullSafe(event.getType()) + "\n";
            result += "     " + nullSafe(event.getDescription()) + "\n";
        }
        result += "\n";
        return result;
    }

    private String additionalInfo(Mission mission) {
        String result = "";

        if (mission.getOperationTags() != null && !mission.getOperationTags().isEmpty()) {
            result += "Теги \n";
            result += "  " + String.join(", ", mission.getOperationTags()) + "\n\n";
        }

        if (mission.getSupportUnits() != null && !mission.getSupportUnits().isEmpty()) {
            result += "Поддержка\n";
            result += "  " + String.join(", ", mission.getSupportUnits()) + "\n\n";
        }

        if (mission.getRecommendations() != null && !mission.getRecommendations().isEmpty()) {
            result += "Рекомендации \n";
            for (String rec : mission.getRecommendations()) {
                result += "  • " + rec + "\n";
            }
            result += "\n";
        }

        if (mission.getArtifactsRecovered() != null && !mission.getArtifactsRecovered().isEmpty()) {
            result += "Артефакты\n";
            result += "  " + String.join(", ", mission.getArtifactsRecovered()) + "\n\n";
        }

        if (mission.getEvacuationZones() != null && !mission.getEvacuationZones().isEmpty()) {
            result += "Зоны эвакуации\n";
            result += "  " + String.join(", ", mission.getEvacuationZones()) + "\n\n";
        }

        if (mission.getStatusEffects() != null && !mission.getStatusEffects().isEmpty()) {
            result += "Эффект\n";
            result += "  " + String.join(", ", mission.getStatusEffects()) + "\n\n";
        }

        if (mission.getNotes() != null && !mission.getNotes().isEmpty()) {
            result += "【ПРИМЕЧАНИЯ】\n";
            result += "  " + mission.getNotes() + "\n\n";
        }

        return result;
    }

    private String nullSafe(Object obj) {
        return obj != null ? obj.toString() : "—";
    }

    @Override
    public String getType() {
        return "detailed";
    }
}