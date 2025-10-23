package seedu.address.model.recruit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalRecruits.ALICE;
import static seedu.address.testutil.TypicalRecruits.BOB;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.model.recruit.exceptions.DuplicateRecruitException;
import seedu.address.model.recruit.exceptions.RecruitNotFoundException;
import seedu.address.testutil.RecruitBuilder;

public class UniqueRecruitListTest {

    private final UniqueRecruitList uniqueRecruitList = new UniqueRecruitList();

    @Test
    public void contains_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueRecruitList.contains(null));
    }

    @Test
    public void contains_recruitNotInList_returnsFalse() {
        assertFalse(uniqueRecruitList.contains(ALICE));
    }

    @Test
    public void contains_recruitInList_returnsTrue() {
        uniqueRecruitList.add(ALICE);
        assertTrue(uniqueRecruitList.contains(ALICE));
    }

    @Test
    public void add_nullRecruit_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueRecruitList.add(null));
    }

    @Test
    public void add_duplicateRecruit_throwsDuplicatePersonException() {
        uniqueRecruitList.add(ALICE);
        assertThrows(DuplicateRecruitException.class, () -> uniqueRecruitList.add(ALICE));
    }

    @Test
    public void setRecruit_nullTargetRecruit_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueRecruitList.setRecruit(null, ALICE));
    }

    @Test
    public void setPerson_nullEditedPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueRecruitList.setRecruit(ALICE, null));
    }

    @Test
    public void setPerson_targetPersonNotInList_throwsPersonNotFoundException() {
        assertThrows(RecruitNotFoundException.class, () -> uniqueRecruitList.setRecruit(ALICE, ALICE));
    }

    @Test
    public void setRecruit_editedRecruitIsSameRecruit_success() {
        uniqueRecruitList.add(ALICE);
        uniqueRecruitList.setRecruit(ALICE, ALICE);
        UniqueRecruitList expectedUniqueRecruitList = new UniqueRecruitList();
        expectedUniqueRecruitList.add(ALICE);
        assertEquals(expectedUniqueRecruitList, uniqueRecruitList);
    }

    @Test
    public void setRecruit_editedRecruitHasSameIdentity_success() {
        uniqueRecruitList.add(ALICE);
        Recruit editedAlice = new RecruitBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        uniqueRecruitList.setRecruit(ALICE, editedAlice);
        UniqueRecruitList expectedUniqueRecruitList = new UniqueRecruitList();
        expectedUniqueRecruitList.add(editedAlice);
        assertEquals(expectedUniqueRecruitList, uniqueRecruitList);
    }

    @Test
    public void setPerson_editedPersonHasDifferentIdentity_success() {
        uniqueRecruitList.add(ALICE);
        uniqueRecruitList.setRecruit(ALICE, BOB);
        UniqueRecruitList expectedUniqueRecruitList = new UniqueRecruitList();
        expectedUniqueRecruitList.add(BOB);
        assertEquals(expectedUniqueRecruitList, uniqueRecruitList);
    }

    @Test
    public void setPerson_editedPersonHasNonUniqueIdentity_throwsDuplicatePersonException() {
        uniqueRecruitList.add(ALICE);
        uniqueRecruitList.add(BOB);
        assertThrows(DuplicateRecruitException.class, () -> uniqueRecruitList.setRecruit(ALICE, BOB));
    }

    @Test
    public void remove_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueRecruitList.remove(null));
    }

    @Test
    public void remove_personDoesNotExist_throwsPersonNotFoundException() {
        assertThrows(RecruitNotFoundException.class, () -> uniqueRecruitList.remove(ALICE));
    }

    @Test
    public void remove_existingPerson_removesPerson() {
        uniqueRecruitList.add(ALICE);
        uniqueRecruitList.remove(ALICE);
        UniqueRecruitList expectedUniqueRecruitList = new UniqueRecruitList();
        assertEquals(expectedUniqueRecruitList, uniqueRecruitList);
    }

    @Test
    public void setPersons_nullUniquePersonList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueRecruitList.setRecruits((UniqueRecruitList) null));
    }

    @Test
    public void setPersons_uniquePersonList_replacesOwnListWithProvidedUniquePersonList() {
        uniqueRecruitList.add(ALICE);
        UniqueRecruitList expectedUniqueRecruitList = new UniqueRecruitList();
        expectedUniqueRecruitList.add(BOB);
        uniqueRecruitList.setRecruits(expectedUniqueRecruitList);
        assertEquals(expectedUniqueRecruitList, uniqueRecruitList);
    }

    @Test
    public void setPersons_nullList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueRecruitList.setRecruits((List<Recruit>) null));
    }

    @Test
    public void setPersons_list_replacesOwnListWithProvidedList() {
        uniqueRecruitList.add(ALICE);
        List<Recruit> recruitList = Collections.singletonList(BOB);
        uniqueRecruitList.setRecruits(recruitList);
        UniqueRecruitList expectedUniqueRecruitList = new UniqueRecruitList();
        expectedUniqueRecruitList.add(BOB);
        assertEquals(expectedUniqueRecruitList, uniqueRecruitList);
    }

    @Test
    public void setPersons_listWithDuplicatePersons_throwsDuplicatePersonException() {
        List<Recruit> listWithDuplicateRecruits = Arrays.asList(ALICE, ALICE);
        assertThrows(DuplicateRecruitException.class, () -> uniqueRecruitList.setRecruits(listWithDuplicateRecruits));
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, ()
            -> uniqueRecruitList.asUnmodifiableObservableList().remove(0));
    }

    @Test
    public void toStringMethod() {
        assertEquals(uniqueRecruitList.asUnmodifiableObservableList().toString(), uniqueRecruitList.toString());
    }

    @Test
    public void hasSameRecruits_variousCasesIncludingDifferentFields() {
        UniqueRecruitList list1 = new UniqueRecruitList();
        UniqueRecruitList list2 = new UniqueRecruitList();

        // Add ALICE to both lists
        list1.add(ALICE);
        list2.add(ALICE);

        // 1. Same object
        assertTrue(list1.hasSameRecruits(list1));

        // 2. Different object, same recruits
        assertTrue(list1.hasSameRecruits(list2));

        // 3. Different object, different recruits
        list2.setRecruits(Collections.singletonList(BOB));
        assertFalse(list1.hasSameRecruits(list2));

        // 4. Same recruit but different fields
        Recruit editedBob = new RecruitBuilder(BOB)
                .withAddress(VALID_ADDRESS_BOB)
                .build();
        list2.setRecruits(Collections.singletonList(editedBob));
        UniqueRecruitList list3 = new UniqueRecruitList();
        list3.add(BOB);
        assertTrue(list2.hasSameRecruits(list3));

        // 5. Different type or null object
        assertFalse(list1.hasSameRecruits(null));
        assertFalse(list1.hasSameRecruits(1));

        // 6. Different sizes
        list2.add(ALICE);
        assertFalse(list1.hasSameRecruits(list2));
    }
}
