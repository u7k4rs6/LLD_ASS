
public class CodeGrader implements CodeGradingService {

    @Override
    public int grade(Submission sub, Rubric rubric) {
        int baseValue = Math.min(80, 50 + sub.code.length() % 40);
        return baseValue + rubric.bonus;
    }
}
