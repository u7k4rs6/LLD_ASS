public class CgrRule implements EligibilityRule {
    private final double minimumCgr;

    public CgrRule(double minimumCgr) {
        this.minimumCgr = minimumCgr;
    }

    @Override
    public RuleResult evaluate(StudentProfile profile) {
        if (profile.cgr < minimumCgr) {
            return RuleResult.fail("CGR below " + minimumCgr);
        }
        return RuleResult.pass();
    }
}
