import java.util.*;

public class EligibilityEngine {
    private final List<EligibilityRule> rules;

    public EligibilityEngine(List<EligibilityRule> rules) {
        this.rules = rules;
    }

    public EligibilityEngineResult evaluate(StudentProfile s) {
        List<String> reasons = new ArrayList<>();
        String status = "ELIGIBLE";

        for (EligibilityRule rule : rules) {
            RuleResult result = rule.evaluate(s);
            if (!result.passed) {
                status = "NOT_ELIGIBLE";
                reasons.add(result.reason);
            }
        }

        return new EligibilityEngineResult(status, reasons);
    }
}

class EligibilityEngineResult {
    public final String status;
    public final List<String> reasons;
    
    public EligibilityEngineResult(String status, List<String> reasons) {
        this.status = status;
        this.reasons = reasons;
    }
}
