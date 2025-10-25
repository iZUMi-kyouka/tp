package seedu.address.model.recruit.exceptions;

import java.util.List;

/**
 * Represents an exception that is triggered when trying to add an element that already exists to a field.
 */
public class FieldElementAlreadyExistsException extends IllegalRecruitBuilderActionException {
    private List<String> duplicates;

    public FieldElementAlreadyExistsException(String s) {
        super(s);
    }

    public FieldElementAlreadyExistsException(List<String> duplicatedElements) {
        super("The following fields are duplicated: " + duplicatedElements);
    }

    public List<String> getDuplicatedElements() {
        return duplicates;
    }
}
