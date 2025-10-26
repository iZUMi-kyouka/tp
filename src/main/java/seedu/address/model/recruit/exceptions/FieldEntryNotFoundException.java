package seedu.address.model.recruit.exceptions;

import java.util.List;

/**
 * Represents an exception that is triggered when attempting to perform an action on
 * a field entry that cannot be found.
 */
public class FieldEntryNotFoundException extends IllegalRecruitBuilderActionException {
    private String fieldType;
    private List<String> missingEntries;


    public FieldEntryNotFoundException(String s) {
        super(s);
    }

    /**
     * Constructs a FieldEntryAlreadyExistsException which indicates that the user is trying
     * to perform an action on an entry that can not be found in the field.
     *
     * @param fieldType a String denoting the type of field
     * @param missingEntries a List of String representations of the duplicated Elements
     */
    public FieldEntryNotFoundException(String fieldType, List<String> missingEntries) {
        super(String.format("The following %ss cannot be found: %s", fieldType, missingEntries));
        this.fieldType = fieldType;
        this.missingEntries = missingEntries;
    }

    public List<String> getMissingEntries() {
        return missingEntries;
    }

    public String getFieldType() {
        return fieldType;
    }
}
