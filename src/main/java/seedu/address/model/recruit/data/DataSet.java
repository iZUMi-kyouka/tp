package seedu.address.model.recruit.data;

import java.util.Collection;
import java.util.Comparator;
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

    public DataSet() {
        super();
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
}
