package seedu.address.model.recruit.exceptions;

import java.util.List;

/**
 * Represents an exception that is triggered when a particular Field element is not found.
 */
public class FieldNotFoundException extends RecruitException {
    private List<String> missingFields;

    public FieldNotFoundException(String s) {
        super(s);
    }

    public FieldNotFoundException(List<String> missingFields) {
        super("The following fields are missing: " + missingFields);
    }

    public List<String> getMissingFields() {
        return missingFields;
    }
}
