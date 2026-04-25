package MephiPackage.collection;

import java.util.HashMap;
import java.util.Map;

public class AggregatedStatistics {

    private int totalMissions;
    private int successCount;
    private int failureCount;
    private int partialCount;
    private long totalDamage;
    private Map<String, Integer> outcomeDistribution;

    public AggregatedStatistics() {
        this.outcomeDistribution = new HashMap<>();
    }

    public int getTotalMissions() {
        return totalMissions;
    }

    public int getSuccessCount() {
        return successCount;
    }

    public int getFailureCount() {
        return failureCount;
    }

    public int getPartialCount() {
        return partialCount;
    }

    public long getTotalDamage() {
        return totalDamage;
    }

    public Map<String, Integer> getOutcomeDistribution() {
        return outcomeDistribution;
    }

    public void setTotalMissions(int totalMissions) {
        this.totalMissions = totalMissions;
    }

    public void setSuccessCount(int successCount) {
        this.successCount = successCount;
    }

    public void setFailureCount(int failureCount) {
        this.failureCount = failureCount;
    }

    public void setPartialCount(int partialCount) {
        this.partialCount = partialCount;
    }

    public void setTotalDamage(long totalDamage) {
        this.totalDamage = totalDamage;
    }

    public void setOutcomeDistribution(Map<String, Integer> outcomeDistribution) {
        this.outcomeDistribution = outcomeDistribution;
    }

    public double getSuccessRate() {
        if (totalMissions == 0) {
            return 0.0;
        }
        return (double) successCount / totalMissions * 100;
    }

    public double getAverageDamage() {
        if (totalMissions == 0) {
            return 0.0;
        }
        return (double) totalDamage / totalMissions;
    }

    public void addMission(String outcome, long damage) {
        totalMissions++;
        totalDamage += damage;

        if ("SUCCESS".equalsIgnoreCase(outcome)) {
            successCount++;
        } else if ("FAILURE".equalsIgnoreCase(outcome)) {
            failureCount++;
        } else if ("PARTIAL".equalsIgnoreCase(outcome)) {
            partialCount++;
        }

        String key = outcome != null ? outcome.toUpperCase() : "UNKNOWN";
        outcomeDistribution.put(key, outcomeDistribution.getOrDefault(key, 0) + 1);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("                 Агрегированная статистика\n");
        sb.append("Всего миссий:          ").append(totalMissions).append("\n");
        sb.append("Успешных:              ").append(successCount);
        sb.append(" (").append(String.format("%.1f", getSuccessRate())).append("%)\n");
        sb.append("Проваленных:           ").append(failureCount).append("\n");
        sb.append("Частичных:             ").append(partialCount).append("\n");
        sb.append("Общий ущерб:           ").append(totalDamage).append("\n");
        sb.append("Средний ущерб:         ").append(String.format("%.1f", getAverageDamage())).append("\n");
        return sb.toString();
    }
}