package seedu.address.model.recruit.exceptions;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import seedu.address.model.recruit.data.Data;

/**
 * Represents an exception that is triggered when trying to add an entry to a field that already
 * has that entry.
 */
public class DataEntryAlreadyExistsException extends IllegalRecruitBuilderActionException {
    private String dataType;
    private List<Data> duplicatedEntries;


    public DataEntryAlreadyExistsException(String s) {
        super(s);
    }

    /**
     * Constructs a {@code DataEntryAlreadyExistsException} which indicates that the user is trying
     * to add a list of entries to a set that already contains that entry.
     *
     * @param dataType a String denoting the type of field
     * @param duplicatedEntries a List of entries that already exist
     */
    public DataEntryAlreadyExistsException(String dataType, List<? extends Data> duplicatedEntries) {
        super(String.format("The following %ss cannot be added as they already exist: %s",
                dataType, duplicatedEntries));
        this.dataType = dataType;
        this.duplicatedEntries = new ArrayList<>(duplicatedEntries);
    }

    /**
     * Constructs a {@code DataEntryAlreadyExistsException} which indicates that the user is trying
     * to add an entry that already exists in the set.
     *
     * @param duplicatedEntries a List of the entries that could not be found
     */
    public DataEntryAlreadyExistsException(List<? extends Data> duplicatedEntries) {
        super(String.format("The following data entries cannot be found: %s",
                duplicatedEntries.stream().map(Data::toString).collect(Collectors.joining(", "))));
        this.duplicatedEntries = new ArrayList<>(duplicatedEntries);
    }

    /**
     * Constructs a {@code DataEntryAlreadyExistsException} which indicates that the user is trying
     * to perform an action on an entry that can not be found in the field.
     *
     * @param missingEntry a List of the entries that could not be found
     */
    public DataEntryAlreadyExistsException(Data missingEntry) {
        super(String.format("%s cannot be found", missingEntry));
        this.duplicatedEntries = List.of(missingEntry);
    }

    public List<Data> getDuplicatedEntries() {
        return duplicatedEntries;
    }

    public String getDataType() {
        return dataType;
    }
}
