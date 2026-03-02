import java.util.*;

public class OnboardingService {
    private final RawInputParser parser;
    private final StudentValidator validator;
    private final StudentRepository repository;
    private final OnboardingOutput output;

    public OnboardingService(RawInputParser parser, StudentValidator validator, StudentRepository repository, OnboardingOutput output) {
        this.parser = parser;
        this.validator = validator;
        this.repository = repository;
        this.output = output;
    }

    public void registerFromRawInput(String raw) {
        output.printInput(raw);

        RawStudentData data = parser.parse(raw);
        List<String> errors = validator.validate(data);

        if (!errors.isEmpty()) {
            output.printErrors(errors);
            return;
        }

        String id = IdUtil.nextStudentId(repository.count());
        StudentRecord rec = new StudentRecord(id, data.name, data.email, data.phone, data.program);

        repository.save(rec);

        output.printSuccess(rec, repository.count());
    }
}
