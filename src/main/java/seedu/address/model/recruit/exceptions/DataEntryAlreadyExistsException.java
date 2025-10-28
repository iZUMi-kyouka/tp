package seedu.address.model.recruit.exceptions;

import java.util.List;

/**
 * Represents an exception that is triggered when trying to add an entry to a field that already
 * has that entry.
 */
public class FieldEntryAlreadyExistsException extends IllegalRecruitBuilderActionException {
    private String fieldType;
    private List<String> duplicates;

    public FieldEntryAlreadyExistsException(String s) {
        super(s);
    }

    /**
     * Constructs a FieldEntryAlreadyExistsException which indicates that the user is trying
     * to add an entry that already exists in the field.
     *
     * @param fieldType a String denoting the type of field
     * @param duplicatedElements a List of String representations of the duplicated Elements
     */
    public FieldEntryAlreadyExistsException(String fieldType, List<String> duplicatedElements) {
        super(String.format("The following %ss are duplicated: %s", fieldType, duplicatedElements));
        this.fieldType = fieldType;
        this.duplicates = duplicatedElements;
    }

    public List<String> getDuplicatedEntries() {
        return duplicates;
    }

    public String getFieldType() {
        return fieldType;
    }
}
