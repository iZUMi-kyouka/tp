package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.TypicalRecruits.AMY;
import static seedu.address.testutil.TypicalRecruits.BOB;
import static seedu.address.testutil.TypicalRecruits.getTypicalAddressBook;

import java.util.List;
import java.util.UUID;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;

import seedu.address.model.recruit.Recruit;
import seedu.address.testutil.RecruitBuilder;

public class VersionedAddressBookTest {
    private final VersionedAddressBook addressBook = new VersionedAddressBook(new AddressBook());

    @Test
    public void constructor() {
        // Test initialisation with empty address book
        List<AddressBookState> expectedAddressBookStateList = List.of(
                new AddressBookState(new AddressBook(), VersionedAddressBook.INITIAL_STATE_MARKER));

        assertEquals(0, addressBook.getCurrentStatePointer());
        assertEquals(expectedAddressBookStateList, addressBook.getAddressBookStateList());

        // Test initialisation with populated address book
        expectedAddressBookStateList = List.of(
                new AddressBookState(getTypicalAddressBook(), VersionedAddressBook.INITIAL_STATE_MARKER));
        VersionedAddressBook vab1 = new VersionedAddressBook(getTypicalAddressBook());

        assertEquals(0, vab1.getCurrentStatePointer());
        assertEquals(expectedAddressBookStateList, vab1.getAddressBookStateList());
    }

    @Test
    public void canUndoAddressBook_undoableOperationExists_returnsTrue() {
        addressBook.addRecruit(AMY);
        addressBook.commit("add Amy");
        assertTrue(addressBook.canUndoAddressBook());
    }

    @Test
    public void canUndoAddressBook_noUndoableOperationExists_returnsFalse() {
        assertFalse(addressBook.canUndoAddressBook());

        addressBook.addRecruit(AMY);
        addressBook.commit("add Amy");
        addressBook.removeRecruit(AMY);
        addressBook.commit("delete Amy");
        addressBook.undo();
        addressBook.undo();
        assertFalse(addressBook.canUndoAddressBook());
    }

    @Test
    public void canRedoAddressBook_redoableOperationExists_returnsTrue() {
        addressBook.addRecruit(AMY);
        addressBook.commit("add Amy");
        addressBook.undo();

        assertTrue(addressBook.canRedoAddressBook());
    }

    @Test
    public void canRedoAddressBook_noRedoableOperationExists_returnsFalse() {
        assertFalse(addressBook.canRedoAddressBook());

        addressBook.addRecruit(AMY);
        addressBook.commit("add Amy");
        addressBook.removeRecruit(AMY);
        addressBook.commit("delete Amy");

        assertFalse(addressBook.canRedoAddressBook());
    }

    @Test
    public void undo_undoableOperationExists_success() {
        addressBook.addRecruit(AMY);
        addressBook.commit("add Amy");
        addressBook.removeRecruit(AMY);
        addressBook.commit("delete Amy");
        addressBook.undo();

        AddressBook ab1 = new AddressBook();
        ab1.setRecruits(List.of(AMY));
        List<AddressBookState> expectedAddressBookStateList = List.of(
                new AddressBookState(new AddressBook(), "INITIAL STATE"),
                new AddressBookState(ab1, "add Amy"),
                new AddressBookState(new AddressBook(), "delete Amy"));

        assertEquals(1, addressBook.getCurrentStatePointer());
        assertEquals(expectedAddressBookStateList, addressBook.getAddressBookStateList());
        assertEquals(3, addressBook.getAddressBookStateList().size());
    }

    @Test
    public void undo_noUndoableOperation_exceptionThrown() {
        assertThrows(IllegalStateException.class, () -> addressBook.undo());

        addressBook.addRecruit(AMY);
        addressBook.commit("add Amy");
        addressBook.removeRecruit(AMY);
        addressBook.commit("delete Amy");
        addressBook.undo();
        addressBook.undo();
        assertThrows(IllegalStateException.class, () -> addressBook.undo());
    }

    @Test
    public void redo_redoableOperationExists_success() {
        addressBook.addRecruit(AMY);
        addressBook.commit("add Amy");
        addressBook.removeRecruit(AMY);
        addressBook.commit("delete Amy");

        addressBook.undo();
        addressBook.undo();
        addressBook.redo();
        addressBook.redo();

        AddressBook ab1 = new AddressBook();
        ab1.setRecruits(List.of(AMY));
        List<AddressBookState> expectedAddressBookStateList = List.of(
                new AddressBookState(new AddressBook(), "INITIAL STATE"),
                new AddressBookState(ab1, "add Amy"),
                new AddressBookState(new AddressBook(), "delete Amy"));

        assertEquals(2, addressBook.getCurrentStatePointer());
        assertEquals(expectedAddressBookStateList, addressBook.getAddressBookStateList());
        assertEquals(3, addressBook.getAddressBookStateList().size());
    }

    @Test
    public void redo_noRedoableOperation_exceptionThrown() {
        assertThrows(IllegalStateException.class, () -> addressBook.redo());

        addressBook.addRecruit(AMY);
        addressBook.commit("add Amy");
        addressBook.removeRecruit(AMY);
        addressBook.commit("delete Amy");

        addressBook.undo();
        addressBook.undo();
        addressBook.redo();
        addressBook.redo();

        assertThrows(IllegalStateException.class, () -> addressBook.redo());
    }

    @Test
    public void commit_historyStateSizeLimitExceeded_listSizeBounded() {
        for (int i = 0; i < VersionedAddressBook.MAX_UNDO_HISTORY_SIZE + 50; i++) {
            String uuid = UUID.randomUUID().toString();
            Recruit r = new RecruitBuilder(AMY).withID(uuid).withName(AMY.getName().toString() + i).build();
            addressBook.addRecruit(r);
            addressBook.commit("add Amy");
        }

        assertEquals(199, addressBook.getCurrentStatePointer());
        assertEquals(VersionedAddressBook.MAX_UNDO_HISTORY_SIZE, addressBook.getAddressBookStateList().size());
    }

    @Test
    public void commit_historyStateSizeLimitExceeded_oldestStatePurged() {
        AddressBook expectedAddressBook = new AddressBook();
        int extraCommits = 50;
        int totalCommits = VersionedAddressBook.MAX_UNDO_HISTORY_SIZE + extraCommits;
        List<UUID> uuids = IntStream.range(0, totalCommits)
                .mapToObj(i -> UUID.randomUUID())
                .toList();
        for (int i = 0; i < totalCommits; i++) {
            String uuid = uuids.get(i).toString();
            Recruit r = new RecruitBuilder(AMY)
                    .withID(uuid)
                    .withName(AMY.getName().toString() + i)
                    .build();
            addressBook.addRecruit(r);
            addressBook.commit("add Amy");
        }
        expectedAddressBook.setRecruits(
                IntStream.range(0, extraCommits + 1)
                        .mapToObj(i -> new RecruitBuilder(AMY)
                                .withID(uuids.get(i).toString())
                                .withName(AMY.getName().toString() + i)
                                .build())
                        .toList()
        );

        AddressBookState expectedAddressBookState = new AddressBookState(
                expectedAddressBook,
                "add Amy"
        );
        System.out.println(expectedAddressBookState.getAddressBook());
        System.out.println(addressBook.getAddressBookStateList().get(0).getAddressBook());
        assertEquals(expectedAddressBookState, addressBook.getAddressBookStateList()
                .get(0));
    }
    @Test
    public void purgeFutureStates_commitAfterUndo_futureStatesPurged() {
        addressBook.addRecruit(AMY);
        addressBook.commit("add Amy");
        addressBook.removeRecruit(AMY);
        addressBook.commit("delete Amy");
        addressBook.undo();
        addressBook.addRecruit(BOB);
        addressBook.commit("add Bob");

        AddressBook ab1 = new AddressBook();
        ab1.setRecruits(List.of(AMY));
        AddressBook ab2 = new AddressBook(ab1);
        ab2.addRecruit(BOB);

        List<AddressBookState> expectedAddressBookStateList = List.of(
                new AddressBookState(new AddressBook(), "INITIAL STATE"),
                new AddressBookState(ab1, "add Amy"),
                new AddressBookState(ab2, "add Bob"));

        assertEquals(3, addressBook.getAddressBookStateList().size());
        assertEquals(expectedAddressBookStateList, addressBook.getAddressBookStateList());
    }
}
