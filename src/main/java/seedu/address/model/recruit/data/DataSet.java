package seedu.address.model.recruit.data;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.TreeSet;

/**
 * A minimally extended TreeSet with a primary data attribute.
 * Users are responsible for ensuring the current state of primary data
 * is valid i.e. it should generally be an item that is equal to one of the
 * items contained in this TreeSet.
 */
public class DataSet<T extends Data> extends TreeSet<T> {
    private Optional<T> primaryData;

    /**
     * Constructs an empty DataSet with unset primary data.
     */
    public DataSet() {
        super();
        primaryData = Optional.empty();
    }

    /**
     * Constructs a DataSet containing the only data {@code t}, which will
     * also be set as the primary data.
     * @param coll
     */
    public DataSet(T t) {
        this(List.of(t), null);
    }

    /**
     * Constructs a DataSet containing items in {@code ts}. The first item
     * in the resulting DataSet, will be set as the primary data.
     * @param coll
     */
    public DataSet(Collection<? extends T> coll) {
        this(coll, null);
    }

    /**
     * Constructs a DataSet containing items in {@code ts}, where the ordering
     * of the items is maintained based on the given {@code comparator}.
     * The first item in the resulting DataSet, will be set as the primary data.
     * @param coll
     */
    public DataSet(Collection<? extends T> coll, Comparator<? super T> comp) {
        super(comp);
        this.addAll(coll);
        primaryData = this.isEmpty() ? Optional.empty() : Optional.of(this.first());
    }

    public boolean setPrimary(T data) {
        if (this.stream().filter(t -> t.equals(data)).count() == 0) {
            return false;
        }

        primaryData = Optional.of(data);
        return true;
    }

    public Optional<T> getPrimary() {
        return primaryData;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append('[');

        // only show '(primary)' indicator when there are more than 1 data
        // and a primary data is set
        if (!primaryData.isPresent() || this.size() == 1) {
            return super.toString();
        }

        T primaryData = this.primaryData.get();
        int i = 0;
        int size = this.size();
        for (T data : this) {
            sb.append(data.toString());
            if (data.equals(primaryData)) {
                sb.append(" (primary)");
            }
            if (i != size - 1) {
                sb.append(", ");
            }
            i++;
        }

        sb.append(']');
        return sb.toString();
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }

        if (!(other instanceof DataSet)) {
            return false;
        }

        DataSet<?> ds = (DataSet<?>) other;
        return super.equals(other) && primaryData.equals(ds.primaryData);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), primaryData);
    }
}
