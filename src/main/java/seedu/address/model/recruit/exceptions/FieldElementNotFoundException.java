package seedu.address.model.recruit.exceptions;

import java.util.List;

/**
 * Represents an exception that is triggered when attempting to perform an action on
 * a field element that cannot be found.
 */
public class FieldElementNotFoundException extends IllegalRecruitBuilderActionException {
    private List<String> missingElements;

    public FieldElementNotFoundException(String s) {
        super(s);
    }

    public FieldElementNotFoundException(List<String> missingElements) {
        super("The following fields are missing: " + missingElements);
    }

    public List<String> getMissingElements() {
        return missingElements;
    }
}
