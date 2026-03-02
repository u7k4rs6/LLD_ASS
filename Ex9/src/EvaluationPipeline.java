
public class EvaluationPipeline {

    private final PlagiarismCheckService pChecker;
    private final CodeGradingService cGrader;
    private final ReportWritingService rWriter;

    public EvaluationPipeline(
            PlagiarismCheckService pChecker,
            CodeGradingService cGrader,
            ReportWritingService rWriter) {
        this.pChecker = pChecker;
        this.cGrader = cGrader;
        this.rWriter = rWriter;
    }

    public void evaluate(Submission submission) {
        Rubric scoringRubric = new Rubric();

        int pScore = pChecker.check(submission);
        System.out.println("PlagiarismScore=" + pScore);

        int cScore = cGrader.grade(submission, scoringRubric);
        System.out.println("CodeScore=" + cScore);

        String reportName = rWriter.write(submission, pScore, cScore);
        System.out.println("Report written: " + reportName);

        int totalScore = pScore + cScore;
        String finalResult = (totalScore >= 90) ? "PASS" : "FAIL";
        System.out.println("FINAL: " + finalResult + " (total=" + totalScore + ")");
    }
}
