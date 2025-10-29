package seedu.address.model.recruit.data;

/**
 * Different kinds of data that a Recruit has.
 */
public enum DataType {
    NAME("name"),
    PHONE("phone"),
    EMAIL("email"),
    ADDRESS("address");

    private final String displayName;

    DataType(String displayName) {
        this.displayName = displayName;
    }

    @Override
    public String toString() {
        return displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
