import java.util.List;

public class Main {
    public static void main(String[] args) {
        System.out.println("=== Placement Eligibility ===");
        
        List<EligibilityRule> rules = List.of(
            new DisciplinaryFlagRule(),
            new CgrRule(8.0),
            new AttendanceRule(75),
            new CreditsRule(20)
        );

        EligibilityEngine engine = new EligibilityEngine(rules);
        FakeEligibilityStore store = new FakeEligibilityStore();
        ReportPrinter printer = new ReportPrinter();

        StudentProfile s = new StudentProfile("23BCS1001", "Ayaan", 8.10, 72, 18, LegacyFlags.NONE);
        
        EligibilityEngineResult result = engine.evaluate(s);
        
        printer.print(s, result);
        store.save(s.rollNo, result.status);
    }
}
