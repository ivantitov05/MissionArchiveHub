package MephiPackage.report;

import MephiPackage.objects.Mission;

public interface ReportGenerator {
    String generate(Mission mission);
    String getType();
}
