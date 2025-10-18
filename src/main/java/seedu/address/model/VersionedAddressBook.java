package seedu.address.model;

import java.util.ArrayList;
import java.util.List;

/**
 * An AddressBook with version history to support undo operation
 */
public class VersionedAddressBook extends AddressBook {
    private static final int MAX_UNDO_HISTORY_SIZE = 200;

    private final List<ReadOnlyAddressBook> addressBookStateList = new ArrayList<>();
    private int currentStatePtr;

    /**
     * Creates a VersionedAddressBook using the Recruits in the {@code toBeCopied}
     */
    public VersionedAddressBook(ReadOnlyAddressBook toBeCopied) {
        super(toBeCopied);

        this.addressBookStateList.add(new AddressBook(toBeCopied));
        currentStatePtr = 0;
    }

    /**
     * Saves the current address book state in history.
     */
    public void commit() {
        addressBookStateList.add(this);

        if (addressBookStateList.size() > MAX_UNDO_HISTORY_SIZE) {
            addressBookStateList.remove(0);
        }

        currentStatePtr++;
    }

    public boolean canUndoAddressBook() {
        return currentStatePtr > 0;
    }

    /**
     * Undoes the last performed operation.
     */
    public void undo() {
        currentStatePtr--;
        ReadOnlyAddressBook addressBookToRestore = addressBookStateList.get(currentStatePtr);
        this.setRecruits(addressBookToRestore.getRecruitList());
    }

    /**
     * Deletes all AddressBook states after the currently pointed state.
     */
    public void purgeFutureStates() {
        final int removeIndex = addressBookStateList.size();
        while (addressBookStateList.size() > currentStatePtr + 1) {
            addressBookStateList.remove(removeIndex);
        }
    }
}
