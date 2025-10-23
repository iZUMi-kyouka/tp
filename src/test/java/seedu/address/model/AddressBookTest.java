package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalRecruits.ALICE;
import static seedu.address.testutil.TypicalRecruits.BENSON;
import static seedu.address.testutil.TypicalRecruits.CARL;
import static seedu.address.testutil.TypicalRecruits.getTypicalAddressBook;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.recruit.Recruit;
import seedu.address.model.recruit.exceptions.DuplicateRecruitException;
import seedu.address.testutil.RecruitBuilder;

public class AddressBookTest {

    private final AddressBook addressBook = new AddressBook();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), addressBook.getRecruitList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> addressBook.resetData(null));
    }

    @Test
    public void resetData_withValidReadOnlyAddressBook_replacesData() {
        AddressBook newData = getTypicalAddressBook();
        addressBook.resetData(newData);
        assertEquals(newData, addressBook);
    }

    @Test
    public void resetData_withDuplicatePersons_throwsDuplicatePersonException() {
        // Two persons with the same identity fields
        Recruit editedAlice = new RecruitBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        List<Recruit> newRecruits = Arrays.asList(ALICE, editedAlice);
        AddressBookStub newData = new AddressBookStub(newRecruits);

        assertThrows(DuplicateRecruitException.class, () -> addressBook.resetData(newData));
    }

    @Test
    public void hasPerson_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> addressBook.hasRecruit(null));
    }

    @Test
    public void hasPerson_personNotInAddressBook_returnsFalse() {
        assertFalse(addressBook.hasRecruit(ALICE));
    }

    @Test
    public void hasPerson_personInAddressBook_returnsTrue() {
        addressBook.addRecruit(ALICE);
        assertTrue(addressBook.hasRecruit(ALICE));
    }

    @Test
    public void hasPerson_personWithSameIdentityFieldsInAddressBook_returnsTrue() {
        addressBook.addRecruit(ALICE);
        Recruit editedAlice = new RecruitBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        assertTrue(addressBook.hasRecruit(editedAlice));
    }

    @Test
    public void getPersonList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> addressBook.getRecruitList().remove(0));
    }

    @Test
    public void sortRecruits_validComparator_success() {
        addressBook.addRecruit(BENSON);
        addressBook.addRecruit(ALICE);
        addressBook.addRecruit(CARL);

        addressBook.sortRecruits(Comparator.comparing(r -> r.getName().fullName));

        List<Recruit> sortedRecruits = addressBook.getRecruitList();
        assertEquals("Alice Pauline", sortedRecruits.get(0).getName().fullName);
        assertEquals("Benson Meier", sortedRecruits.get(1).getName().fullName);
        assertEquals("Carl Kurz", sortedRecruits.get(2).getName().fullName);
    }

    @Test
    public void toStringMethod() {
        String expected = AddressBook.class.getCanonicalName() + "{recruits=" + addressBook.getRecruitList() + "}";
        assertEquals(expected, addressBook.toString());
    }

    /**
     * A stub ReadOnlyAddressBook whose persons list can violate interface constraints.
     */
    private static class AddressBookStub implements ReadOnlyAddressBook {
        private final ObservableList<Recruit> recruits = FXCollections.observableArrayList();

        AddressBookStub(Collection<Recruit> recruits) {
            this.recruits.setAll(recruits);
        }

        @Override
        public ObservableList<Recruit> getRecruitList() {
            return recruits;
        }
    }

}
