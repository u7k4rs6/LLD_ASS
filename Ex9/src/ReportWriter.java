
public class ReportWriter implements ReportWritingService {

    @Override
    public String write(Submission sub, int pScore, int cScore) {
        return "report-" + sub.roll + ".txt";
    }
}
