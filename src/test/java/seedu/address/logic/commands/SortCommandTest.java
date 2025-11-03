package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.parser.CliSyntax.SORT_PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.SORT_PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.SORT_PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.SORT_PREFIX_PHONE;
import static seedu.address.testutil.TypicalRecruits.getTypicalAddressBook;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.SortCommand.SortCriterion;
import seedu.address.logic.commands.SortCommand.SortOrder;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.recruit.Recruit;
import seedu.address.model.recruit.RecruitBuilder;
import seedu.address.testutil.SimpleRecruitBuilder;

public class SortCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());

    @Test
    public void execute_sortByNameAscending_success() {
        List<SortCriterion> criteria = new ArrayList<>();
        criteria.add(new SortCriterion(SORT_PREFIX_NAME, SortOrder.ASCENDING));

        SortCommand sortCommand = new SortCommand(criteria);

        Comparator<Recruit> comparator = Comparator.comparing(recruit -> recruit.getName().value.toLowerCase());
        expectedModel.sortRecruits(comparator);

        String expectedMessage = String.format(SortCommand.MESSAGE_SUCCESS, "name (ascending)");
        assertCommandSuccess(sortCommand, model, expectedMessage, expectedModel);

        // Verify the list is sorted alphabetically by name
        var recruitList = model.getFilteredRecruitList();
        for (int i = 0; i < recruitList.size() - 1; i++) {
            String currentName = recruitList.get(i).getName().value.toLowerCase();
            String nextName = recruitList.get(i + 1).getName().value.toLowerCase();
            assertFalse(currentName.compareTo(nextName) > 0,
                    "Recruits should be sorted alphabetically. Found: "
                    + currentName + " before " + nextName);
        }
    }

    @Test
    public void execute_sortByNameDescending_success() {
        List<SortCriterion> criteria = new ArrayList<>();
        criteria.add(new SortCriterion(SORT_PREFIX_NAME, SortOrder.DESCENDING));

        SortCommand sortCommand = new SortCommand(criteria);

        Comparator<Recruit> comparator = Comparator.comparing((
                Recruit recruit) -> recruit.getName().value.toLowerCase()).reversed();
        expectedModel.sortRecruits(comparator);

        String expectedMessage = String.format(SortCommand.MESSAGE_SUCCESS, "name (descending)");
        assertCommandSuccess(sortCommand, model, expectedMessage, expectedModel);

        // Verify the list is sorted in reverse alphabetical order by name
        var recruitList = model.getFilteredRecruitList();
        for (int i = 0; i < recruitList.size() - 1; i++) {
            String currentName = recruitList.get(i).getName().value.toLowerCase();
            String nextName = recruitList.get(i + 1).getName().value.toLowerCase();
            assertFalse(currentName.compareTo(nextName) < 0,
                    "Recruits should be sorted in reverse alphabetically. Found: "
                    + currentName + " before " + nextName);
        }
    }

    @Test
    public void execute_sortByPhoneAscending_success() {
        List<SortCriterion> criteria = new ArrayList<>();
        criteria.add(new SortCriterion(SORT_PREFIX_PHONE, SortOrder.ASCENDING));

        SortCommand sortCommand = new SortCommand(criteria);

        Comparator<Recruit> comparator = Comparator.comparing(recruit -> recruit.getPhone().get().value);
        expectedModel.sortRecruits(comparator);

        String expectedMessage = String.format(SortCommand.MESSAGE_SUCCESS, "phone (ascending)");
        assertCommandSuccess(sortCommand, model, expectedMessage, expectedModel);

        // Verify the list is sorted by phone number
        var recruitList = model.getFilteredRecruitList();
        for (int i = 0; i < recruitList.size() - 1; i++) {
            String currentPhone = recruitList.get(i).getPhone().get().value;
            String nextPhone = recruitList.get(i + 1).getPhone().get().value;
            assertFalse(currentPhone.compareTo(nextPhone) > 0,
                    "Recruits should be sorted by phone number. Found: "
                    + currentPhone + " before " + nextPhone);
        }
    }

    @Test
    public void execute_sortByEmailDescending_success() {
        List<SortCriterion> criteria = new ArrayList<>();
        criteria.add(new SortCriterion(SORT_PREFIX_EMAIL, SortOrder.DESCENDING));

        SortCommand sortCommand = new SortCommand(criteria);

        Comparator<Recruit> comparator = Comparator.comparing((
                Recruit recruit) -> recruit.getEmail().get().value.toLowerCase()).reversed();
        expectedModel.sortRecruits(comparator);

        String expectedMessage = String.format(SortCommand.MESSAGE_SUCCESS, "email (descending)");
        assertCommandSuccess(sortCommand, model, expectedMessage, expectedModel);

        // Verify the list is sorted in reverse order by email
        var recruitList = model.getFilteredRecruitList();
        for (int i = 0; i < recruitList.size() - 1; i++) {
            String currentEmail = recruitList.get(i).getEmail().get().value.toLowerCase();
            String nextEmail = recruitList.get(i + 1).getEmail().get().value.toLowerCase();
            assertFalse(currentEmail.compareTo(nextEmail) < 0,
                    "Recruits should be sorted by email in reverse order. Found: "
                    + currentEmail + " before " + nextEmail);
        }
    }

    @Test
    public void execute_sortByAddressAscending_success() {
        List<SortCriterion> criteria = new ArrayList<>();
        criteria.add(new SortCriterion(SORT_PREFIX_ADDRESS, SortOrder.ASCENDING));

        SortCommand sortCommand = new SortCommand(criteria);

        Comparator<Recruit> comparator = Comparator.comparing(
                recruit -> recruit.getAddress().get().value.toLowerCase());
        expectedModel.sortRecruits(comparator);

        String expectedMessage = String.format(SortCommand.MESSAGE_SUCCESS, "address (ascending)");
        assertCommandSuccess(sortCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_sortByMultipleFields_success() {
        List<SortCriterion> criteria = new ArrayList<>();
        criteria.add(new SortCriterion(SORT_PREFIX_NAME, SortOrder.ASCENDING));
        criteria.add(new SortCriterion(SORT_PREFIX_PHONE, SortOrder.DESCENDING));
        criteria.add(new SortCriterion(SORT_PREFIX_EMAIL, SortOrder.ASCENDING));

        SortCommand sortCommand = new SortCommand(criteria);

        Comparator<Recruit> comparator = Comparator
                .comparing((Recruit recruit) -> recruit.getName().value.toLowerCase())
                .thenComparing(Comparator.comparing((Recruit r) -> r.getPhone().get().value).reversed())
                .thenComparing(recruit -> recruit.getEmail().get().value.toLowerCase());
        expectedModel.sortRecruits(comparator);

        String expectedMessage = String.format(SortCommand.MESSAGE_SUCCESS,
                "name (ascending), phone (descending), email (ascending)");
        assertCommandSuccess(sortCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_sortByAllFields_success() {
        List<SortCriterion> criteria = new ArrayList<>();
        criteria.add(new SortCriterion(SORT_PREFIX_NAME, SortOrder.ASCENDING));
        criteria.add(new SortCriterion(SORT_PREFIX_PHONE, SortOrder.ASCENDING));
        criteria.add(new SortCriterion(SORT_PREFIX_EMAIL, SortOrder.ASCENDING));
        criteria.add(new SortCriterion(SORT_PREFIX_ADDRESS, SortOrder.ASCENDING));

        SortCommand sortCommand = new SortCommand(criteria);

        Comparator<Recruit> comparator = Comparator
                .comparing((Recruit recruit) -> recruit.getName().value.toLowerCase())
                .thenComparing(recruit -> recruit.getPhone().get().value)
                .thenComparing(recruit -> recruit.getEmail().get().value.toLowerCase())
                .thenComparing(recruit -> recruit.getAddress().get().value.toLowerCase());
        expectedModel.sortRecruits(comparator);

        String expectedMessage = String.format(SortCommand.MESSAGE_SUCCESS,
                "name (ascending), phone (ascending), email (ascending), address (ascending)");
        assertCommandSuccess(sortCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_emptyList_success() {
        model = new ModelManager();
        expectedModel = new ModelManager();

        List<SortCriterion> criteria = new ArrayList<>();
        criteria.add(new SortCriterion(SORT_PREFIX_NAME, SortOrder.ASCENDING));

        SortCommand sortCommand = new SortCommand(criteria);

        String expectedMessage = String.format(SortCommand.MESSAGE_SUCCESS, "name (ascending)");
        assertCommandSuccess(sortCommand, model, expectedMessage, expectedModel);

        // Verify empty list remains empty
        assertEquals(0, model.getFilteredRecruitList().size());
    }

    @Test
    public void toString_sortCriterion_correctFormat() {
        SortCriterion criterion = new SortCriterion(SORT_PREFIX_NAME, SortOrder.ASCENDING);
        assertEquals("name (ascending)", criterion.toString());

        SortCriterion criterion2 = new SortCriterion(SORT_PREFIX_PHONE, SortOrder.DESCENDING);
        assertEquals("phone (descending)", criterion2.toString());
    }

    @Test
    public void execute_sortByNameAscending_numbers_success() {
        // Create recruits with names starting with letters and numbers
        Model testModel = new ModelManager();
        Recruit alice = new SimpleRecruitBuilder()
                .withName("Alice").withPhone("12345678").build();
        Recruit bob = new SimpleRecruitBuilder()
                .withName("Bob").withPhone("23456789").build();
        Recruit charlie = new SimpleRecruitBuilder()
                .withName("Charlie").withPhone("34567890").build();
        Recruit numeric1 = new SimpleRecruitBuilder()
                .withName("123 Company").withPhone("45678901").build();
        Recruit numeric2 = new SimpleRecruitBuilder()
                .withName("456 Corp").withPhone("56789012").build();
        Recruit zebra = new SimpleRecruitBuilder()
                .withName("Zebra").withPhone("67890123").build();

        testModel.addRecruit(numeric1);
        testModel.addRecruit(bob);
        testModel.addRecruit(numeric2);
        testModel.addRecruit(alice);
        testModel.addRecruit(zebra);
        testModel.addRecruit(charlie);

        // Sort by name ascending
        List<SortCriterion> criteria = new ArrayList<>();
        criteria.add(new SortCriterion(SORT_PREFIX_NAME, SortOrder.ASCENDING));
        SortCommand sortCommand = new SortCommand(criteria);
        sortCommand.execute(testModel);

        List<Recruit> sortedList = testModel.getFilteredRecruitList();

        // Verify ASCII ordering: numbers come before letters in ascending order
        // Expected order: 123 Company, 456 Corp, Alice, Bob, Charlie, Zebra
        assertEquals(6, sortedList.size());
        assertEquals("123 Company", sortedList.get(0).getName().value);
        assertEquals("456 Corp", sortedList.get(1).getName().value);
        assertEquals("Alice", sortedList.get(2).getName().value);
        assertEquals("Bob", sortedList.get(3).getName().value);
        assertEquals("Charlie", sortedList.get(4).getName().value);
        assertEquals("Zebra", sortedList.get(5).getName().value);
    }

    @Test
    public void execute_sortByNameDescending_numbers_success() {
        // Create recruits with names starting with letters and numbers
        Model testModel = new ModelManager();
        Recruit alice = new SimpleRecruitBuilder()
                .withName("Alice").withPhone("12345678").build();
        Recruit bob = new SimpleRecruitBuilder()
                .withName("Bob").withPhone("23456789").build();
        Recruit numeric1 = new SimpleRecruitBuilder()
                .withName("123 Company").withPhone("45678901").build();
        Recruit numeric2 = new SimpleRecruitBuilder()
                .withName("456 Corp").withPhone("56789012").build();

        testModel.addRecruit(numeric1);
        testModel.addRecruit(bob);
        testModel.addRecruit(numeric2);
        testModel.addRecruit(alice);

        // Sort by name descending
        List<SortCriterion> criteria = new ArrayList<>();
        criteria.add(new SortCriterion(SORT_PREFIX_NAME, SortOrder.DESCENDING));
        SortCommand sortCommand = new SortCommand(criteria);
        sortCommand.execute(testModel);

        List<Recruit> sortedList = testModel.getFilteredRecruitList();

        // Verify ASCII ordering reversed: letters come before numbers in descending order
        // Expected order (descending): Bob, Alice, 456 Corp, 123 Company
        assertEquals(4, sortedList.size());
        assertEquals("Bob", sortedList.get(0).getName().value);
        assertEquals("Alice", sortedList.get(1).getName().value);
        assertEquals("456 Corp", sortedList.get(2).getName().value);
        assertEquals("123 Company", sortedList.get(3).getName().value);
    }

    @Test
    public void execute_sortByPhoneAscending_emptyPhonesAtBottom_success() {
        // Create recruits with and without phone numbers
        Model testModel = new ModelManager();
        Recruit withPhone1 = new SimpleRecruitBuilder()
                .withName("Alice").withPhone("81234567").build();
        Recruit withPhone2 = new SimpleRecruitBuilder()
                .withName("Bob").withPhone("91234567").build();
        Recruit withPhone3 = new SimpleRecruitBuilder()
                .withName("Charlie").withPhone("71234567").build();

        // Create recruits without phone numbers
        Recruit noPhone1 = new SimpleRecruitBuilder()
                .withName("David").build();
        noPhone1 = new RecruitBuilder(noPhone1)
                .removePhones(List.of(noPhone1.getPhone().get()))
                .build();

        Recruit noPhone2 = new SimpleRecruitBuilder()
                .withName("Eve").build();
        noPhone2 = new RecruitBuilder(noPhone2)
                .removePhones(List.of(noPhone2.getPhone().get()))
                .build();

        testModel.addRecruit(noPhone1);
        testModel.addRecruit(withPhone2);
        testModel.addRecruit(noPhone2);
        testModel.addRecruit(withPhone1);
        testModel.addRecruit(withPhone3);

        // Sort by phone ascending
        List<SortCriterion> criteria = new ArrayList<>();
        criteria.add(new SortCriterion(SORT_PREFIX_PHONE, SortOrder.ASCENDING));
        SortCommand sortCommand = new SortCommand(criteria);
        sortCommand.execute(testModel);

        List<Recruit> sortedList = testModel.getFilteredRecruitList();

        // Verify that recruits with phone numbers come first, sorted by phone
        // Expected order: Charlie (71234567), Alice (81234567), Bob (91234567), David, Eve (no phones)
        assertEquals(5, sortedList.size());

        // First three should have phone numbers in ascending order
        assertFalse(sortedList.get(0).getPhone().isEmpty());
        assertFalse(sortedList.get(1).getPhone().isEmpty());
        assertFalse(sortedList.get(2).getPhone().isEmpty());
        assertEquals("71234567", sortedList.get(0).getPhone().get().value);
        assertEquals("81234567", sortedList.get(1).getPhone().get().value);
        assertEquals("91234567", sortedList.get(2).getPhone().get().value);

        // Last two should have no phone numbers
        assertFalse(sortedList.get(3).getPhone().isPresent());
        assertFalse(sortedList.get(4).getPhone().isPresent());
    }

    @Test
    public void execute_sortByPhoneDescending_emptyPhonesAtBottom_success() {
        // Create recruits with and without phone numbers
        Model testModel = new ModelManager();
        Recruit withPhone1 = new SimpleRecruitBuilder()
                .withName("Alice").withPhone("81234567").build();
        Recruit withPhone2 = new SimpleRecruitBuilder()
                .withName("Bob").withPhone("91234567").build();

        // Create recruit without phone number
        Recruit noPhone = new SimpleRecruitBuilder()
                .withName("Charlie").build();
        noPhone = new RecruitBuilder(noPhone)
                .removePhones(List.of(noPhone.getPhone().get()))
                .build();

        testModel.addRecruit(noPhone);
        testModel.addRecruit(withPhone1);
        testModel.addRecruit(withPhone2);

        // Sort by phone descending
        List<SortCriterion> criteria = new ArrayList<>();
        criteria.add(new SortCriterion(SORT_PREFIX_PHONE, SortOrder.DESCENDING));
        SortCommand sortCommand = new SortCommand(criteria);
        sortCommand.execute(testModel);

        List<Recruit> sortedList = testModel.getFilteredRecruitList();

        // Expected order (descending): Bob (91234567), Alice (81234567), Charlie (no phone)
        assertEquals(3, sortedList.size());

        // Verify first two have phones in descending order
        assertFalse(sortedList.get(0).getPhone().isEmpty(),
                "First recruit should have phone, got: " + sortedList.get(0).getName().value);
        assertFalse(sortedList.get(1).getPhone().isEmpty(),
                "Second recruit should have phone, got: " + sortedList.get(1).getName().value);
        assertEquals("91234567", sortedList.get(0).getPhone().get().value);
        assertEquals("81234567", sortedList.get(1).getPhone().get().value);

        // Verify last one has no phone
        assertFalse(sortedList.get(2).getPhone().isPresent());
        assertEquals("Charlie", sortedList.get(2).getName().value);
    }

    @Test
    public void execute_sortByPhoneAscending_allEmptyPhones_success() {
        // Create recruits all without phone numbers
        Model testModel = new ModelManager();
        Recruit noPhone1 = new SimpleRecruitBuilder()
                .withName("Alice").build();
        noPhone1 = new RecruitBuilder(noPhone1)
                .removePhones(List.of(noPhone1.getPhone().get()))
                .build();

        Recruit noPhone2 = new SimpleRecruitBuilder()
                .withName("Bob").build();
        noPhone2 = new RecruitBuilder(noPhone2)
                .removePhones(List.of(noPhone2.getPhone().get()))
                .build();

        testModel.addRecruit(noPhone2);
        testModel.addRecruit(noPhone1);

        // Sort by phone ascending
        List<SortCriterion> criteria = new ArrayList<>();
        criteria.add(new SortCriterion(SORT_PREFIX_PHONE, SortOrder.ASCENDING));
        SortCommand sortCommand = new SortCommand(criteria);
        sortCommand.execute(testModel);

        List<Recruit> sortedList = testModel.getFilteredRecruitList();

        // Both should have no phone numbers, order may vary but both should be present
        assertEquals(2, sortedList.size());
        assertFalse(sortedList.get(0).getPhone().isPresent());
        assertFalse(sortedList.get(1).getPhone().isPresent());
    }
}

