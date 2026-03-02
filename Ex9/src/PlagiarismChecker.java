
public class PlagiarismChecker implements PlagiarismCheckService {

    @Override
    public int check(Submission sub) {
        return (sub.code.contains("class") ? 12 : 40);
    }
}
