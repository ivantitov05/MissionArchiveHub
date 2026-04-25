package MephiPackage.report;

import MephiPackage.enums.Outcome;
import MephiPackage.enums.ThreatLevel;
import MephiPackage.objects.Mission;
import MephiPackage.objects.Curse;

public class RiskReportGenerator implements ReportGenerator {

    private static final String SEPARATOR = "============================================================";

    @Override
    public String generate(Mission mission) {
        if (mission == null) {
            return "Ошибка: миссия не загружена";
        }

        String result = "";

        result += SEPARATOR + "\n";
        result += "                 АНАЛИЗ РИСКОВ\n";
        result += SEPARATOR + "\n\n";

        int riskLevel = calculateRiskLevel(mission);
        result += "Общий уровень риска\n";
        result += "  Оценка: " + getRiskLevelString(riskLevel) + "\n";
        result += "  Баллов: " + riskLevel + " / 10\n\n";

        result += riskFactors(mission);

        result += criticalIssues(mission);

        result += recommendations(mission);

        result += SEPARATOR + "\n";

        return result;
    }

    private int calculateRiskLevel(Mission mission) {
        int level = 0;

        if (mission.getCurses() != null) {
            for (Curse c : mission.getCurses()) {
                ThreatLevel threat = c.getThreatLevel();
                if (threat == null) continue;

                switch (threat) {
                    case SPECIAL_GRADE:
                        level += 3;
                        break;
                    case HIGH:
                        level += 2;
                        break;
                    case MEDIUM:
                        level += 1;
                        break;
                    case LOW:
                    case UNKNOWN:
                    default:
                        break;
                }
            }
        }

        if (mission.getDamageCost() > 1_000_000) {
            level += 2;
        } else if (mission.getDamageCost() > 500_000) {
            level += 1;
        }

        Outcome outcome = mission.getOutcome();
        if ("FAILURE".equals(outcome)) {
            level += 2;
        } else if ("PARTIAL".equals(outcome)) {
            level += 1;
        }

        return Math.min(level, 10);
    }

    private String getRiskLevelString(int level) {
        if (level >= 8) return "КРИТИЧЕСКИЙ";
        if (level >= 6) return "ВЫСОКИЙ";
        if (level >= 4) return "ПОВЫШЕННЫЙ";
        if (level >= 2) return "СРЕДНИЙ";
        return "НИЗКИЙ";
    }

    private String riskFactors(Mission mission) {
        String result = "";
        result += "【ФАКТОРЫ РИСКА】\n";

        if (mission.getCurses() != null && !mission.getCurses().isEmpty()) {
            result += "  Проклятия:\n";
            for (Curse c : mission.getCurses()) {
                ThreatLevel threat = c.getThreatLevel();
                result += "    • " + nullSafe(c.getName());
                if (threat != null) {
                    result += " — " + getThreatRiskDescription(threat);
                }
                result += "\n";
            }
        }

        if (mission.getDamageCost() > 500_000) {
            result += "  Финансовый ущерб: " + mission.getDamageCost() + "\n";
            if (mission.getDamageCost() > 1_000_000) {
                result += "    → КРИТИЧЕСКИЙ уровень материального урона\n";
            } else {
                result += "    → ВЫСОКИЙ уровень материального урона\n";
            }
        }

        Outcome outcome = mission.getOutcome();
        if (outcome != null && outcome != Outcome.SUCCESS) {
            result += "  Результат миссии: " + outcome + "\n";
            if (outcome == Outcome.FAILURE) {
                result += "    Полный провал, требуется пересмотр стратегии\n";
            } else if (outcome == Outcome.PARTIAL) {
                result += "    Частичный успех, цели достигнуты не полностью\n";
            }
        }

        result += "\n";
        return result;
    }

    private String criticalIssues(Mission mission) {
        String result = "";
        boolean hasCritical = false;

        result += "【КРИТИЧЕСКИЕ ЗАМЕЧАНИЯ】\n";

        if (mission.getCurses() != null) {
            for (Curse c : mission.getCurses()) {
                ThreatLevel threat = c.getThreatLevel();
                if (threat == ThreatLevel.SPECIAL_GRADE) {
                    result += "  ОБНАРУЖЕНО ПРОКЛЯТИЕ ОСОБОГО РАНГА: " + nullSafe(c.getName()) + "\n";
                    hasCritical = true;
                }
            }
        }

        Outcome outcome = mission.getOutcome();
        if (outcome != null && outcome == Outcome.FAILURE) {
            result += "  МИССИЯ ПРОВАЛЕНА — требуется служебное расследование\n";
            hasCritical = true;
        }

        if (mission.getDamageCost() > 2_000_000) {
            result += "  КАТАСТРОФИЧЕСКИЙ УЩЕРБ — " + mission.getDamageCost() + "\n";
            hasCritical = true;
        }

        if (!hasCritical) {
            result += "  (критических замечаний нет)\n";
        }

        result += "\n";
        return result;
    }

    private String recommendations(Mission mission) {
        String result = "";
        result += "【РЕКОМЕНДАЦИИ】\n";

        int recommendationsCount = 0;

        if (mission.getCurses() != null) {
            for (Curse c : mission.getCurses()) {
                ThreatLevel threat = c.getThreatLevel();
                if (threat == ThreatLevel.SPECIAL_GRADE) {
                    result += "  • Немедленно усилить состав группы для борьбы с проклятием \"" + nullSafe(c.getName()) + "\"\n";
                    recommendationsCount++;
                } else if (threat == ThreatLevel.HIGH) {
                    result += "  • Привлечь мага первого ранга для нейтрализации проклятия \"" + nullSafe(c.getName()) + "\"\n";
                    recommendationsCount++;
                }
            }
        }

        if (mission.getDamageCost() > 1_000_000) {
            result += "  • Разработать протокол минимизации ущерба для будущих операций\n";
            recommendationsCount++;
        }

        Outcome outcome = mission.getOutcome();
        if (outcome != null && outcome == Outcome.FAILURE) {
            result += "  • Провести разбор причин провала с участием всех участников\n";
            result += "  • Пересмотреть тактику и подготовку группы\n";
            recommendationsCount++;
        } else if (outcome != null && outcome == Outcome.PARTIAL) {
            result += "  • Проанализировать невыполненные цели миссии\n";
            recommendationsCount++;
        }

        if (mission.getEconomicAssessment() != null && mission.getEconomicAssessment().getInsuranceCovered() != null) {
            if (!mission.getEconomicAssessment().getInsuranceCovered()) {
                result += "  • Рекомендуется оформить страховое покрытие на будущие миссии\n";
                recommendationsCount++;
            }
        }

        if (recommendationsCount == 0) {
            result += "  (рекомендации не требуются, уровень риска приемлемый)\n";
        }

        result += "\n";
        return result;
    }

    private String getThreatRiskDescription(ThreatLevel threatLevel) {
        if (threatLevel == null) return "неизвестный уровень";
        switch (threatLevel) {
            case SPECIAL_GRADE: return "КРИТИЧЕСКАЯ ОПАСНОСТЬ";
            case HIGH: return "ВЫСОКАЯ ОПАСНОСТЬ";
            case MEDIUM: return "СРЕДНЯЯ ОПАСНОСТЬ";
            case LOW: return "НИЗКАЯ ОПАСНОСТЬ";
            default: return threatLevel.getDisplayName();
        }
    }

    private String nullSafe(String str) {
        return str != null ? str : "—";
    }

    @Override
    public String getType() {
        return "risk";
    }
}