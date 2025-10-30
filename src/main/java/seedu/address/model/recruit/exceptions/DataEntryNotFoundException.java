package seedu.address.model.recruit.exceptions;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import seedu.address.model.recruit.data.Data;

/**
 * Represents an exception that is triggered when attempting to perform an action on
 * a field entry that cannot be found.
 */
public class DataEntryNotFoundException extends IllegalRecruitBuilderActionException {
    private String dataType;
    private List<Data> missingEntries;


    public DataEntryNotFoundException(String s) {
        super(s);
    }

    /**
     * Constructs a {@code DataEntryNotFound} which indicates that the user is trying
     * to perform an action on an entry that cannot be found in the field.
     *
     * @param dataType a String denoting the type of field
     * @param missingEntries a List of entries that could not be found
     */
    public DataEntryNotFoundException(String dataType, List<? extends Data> missingEntries) {
        super(String.format("The following %ss cannot be found: %s", dataType, missingEntries));
        this.dataType = dataType;
        this.missingEntries = new ArrayList<>(missingEntries);
    }

    /**
     * Constructs a {@code DataEntryNotFound} which indicates that the user is trying
     * to perform an action on an entry that cannot be found in the field.
     *
     * @param missingEntries a List of the entries that could not be found
     */
    public DataEntryNotFoundException(List<? extends Data> missingEntries) {
        super(String.format("The following data entries cannot be found: %s",
                missingEntries.stream().map(Data::toString).collect(Collectors.joining(", "))));
        this.missingEntries = new ArrayList<>(missingEntries);
    }

    /**
     * Constructs a {@code DataEntryNotFound} which indicates that the user is trying
     * to perform an action on an entry that can not be found in the field.
     *
     * @param missingEntry a List of the entries that could not be found
     */
    public DataEntryNotFoundException(Data missingEntry) {
        super(String.format("%s cannot be found", missingEntry));
        this.missingEntries = List.of(missingEntry);
    }

    public List<Data> getMissingEntries() {
        return missingEntries;
    }

    public String getUserPresentableMissingEntries() {
        return missingEntries.stream().map(Data::toString).collect(Collectors.joining(", ")).trim();
    }

    public String getDataType() {
        return dataType;
    }
}
