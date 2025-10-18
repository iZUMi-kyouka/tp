package seedu.address.model;

import java.util.List;

/**
 * An AddressBook with version history to support undo operation
 */
public class VersionedAddressBook extends AddressBook {
    private List<AddressBook> addressBookStateList;
    private int currentStatePointer;

    /**
     * Creates a VersionedAddressBook using the Recruits in the {@code toBeCopied}
     */
    public VersionedAddressBook(ReadOnlyAddressBook toBeCopied) {
        this.addressBookStateList.add(new AddressBook(toBeCopied));
        currentStatePointer = 0;
    }

    public void commit() {

    }

    public boolean canUndoAddressBook() {
        return currentStatePointer > 0;
    }

    public void undo() {
        currentStatePointer--;
    }
}
