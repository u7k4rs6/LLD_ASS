public class DisciplinaryFlagRule implements EligibilityRule {
    @Override
    public RuleResult evaluate(StudentProfile profile) {
        if (profile.disciplinaryFlag != LegacyFlags.NONE) {
            return RuleResult.fail("disciplinary flag present");
        }
        return RuleResult.pass();
    }
}
