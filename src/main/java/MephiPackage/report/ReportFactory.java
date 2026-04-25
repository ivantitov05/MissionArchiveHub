package MephiPackage.report;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class ReportFactory {
    private static final Map<String, Supplier<ReportGenerator>> GENERATORS = new HashMap<>();

    static {
        GENERATORS.put("summary", SummaryReportGenerator::new);
        GENERATORS.put("detailed", DetailedReportGenerator::new);
        GENERATORS.put("risk", RiskReportGenerator::new);
        GENERATORS.put("customizable", () -> new CustomizableReportGenerator(new ReportConfig()));
    }

    public static ReportGenerator getGenerator(String type) {
        Supplier<ReportGenerator> supplier = GENERATORS.get(type);
        return supplier != null ? supplier.get() : new SummaryReportGenerator();
    }
}
