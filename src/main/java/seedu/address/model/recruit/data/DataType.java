package seedu.address.model.recruit.data;

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
