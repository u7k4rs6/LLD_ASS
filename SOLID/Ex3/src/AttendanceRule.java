public class AttendanceRule implements EligibilityRule {
    private final int minimumAttendance;

    public AttendanceRule(int minimumAttendance) {
        this.minimumAttendance = minimumAttendance;
    }

    @Override
    public RuleResult evaluate(StudentProfile profile) {
        if (profile.attendancePct < minimumAttendance) {
            return RuleResult.fail("attendance below " + minimumAttendance);
        }
        return RuleResult.pass();
    }
}
