public class CreditsRule implements EligibilityRule {
    private final int minimumCredits;

    public CreditsRule(int minimumCredits) {
        this.minimumCredits = minimumCredits;
    }

    @Override
    public RuleResult evaluate(StudentProfile profile) {
        if (profile.earnedCredits < minimumCredits) {
            return RuleResult.fail("credits below " + minimumCredits);
        }
        return RuleResult.pass();
    }
}
