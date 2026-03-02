public class Main {
    public static void main(String[] args) {
        System.out.println("=== Student Onboarding ===");
        
        StudentRepository db = new FakeDb();
        RawInputParser parser = new RawInputParser();
        StudentValidator validator = new StudentValidator();
        OnboardingOutput output = new ConsoleOnboardingOutput();
        
        OnboardingService svc = new OnboardingService(parser, validator, db, output);

        String raw = "name=Riya;email=riya@sst.edu;phone=9876543210;program=CSE";
        svc.registerFromRawInput(raw);

        System.out.println();
        System.out.println("-- DB DUMP --");
        System.out.print(TextTable.render3(db));
    }
}
