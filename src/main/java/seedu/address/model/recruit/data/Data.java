package seedu.address.model.recruit.data;

public abstract class Data {
    public final String value;

    /**
     * Constructs a {@code Phone}.
     *
     * @param data A valid data field
     */
    public Data(String data) {
        value = data;
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Data)) {
            return false;
        }

        Data otherData = (Data) other;
        return value.equals(otherData.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
