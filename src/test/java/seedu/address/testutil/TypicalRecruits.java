package seedu.address.testutil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.AddressBook;
import seedu.address.model.recruit.Recruit;

import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ID_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ID_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;

/**
 * A utility class containing a list of {@code Person} objects to be used in tests.
 */
public class TypicalRecruits {

    public static final Recruit ALICE = new RecruitBuilder()
            .withID("eac9b117-2ded-42c3-9264-ccf3dfaaa950")
            .withName("Alice Pauline")
            .withAddress("123, Jurong West Ave 6, #08-111").withEmail("alice@example.com")
            .withPhone("94351253")
            .withTags("friends").build();
    public static final Recruit BENSON = new RecruitBuilder()
            .withID("eac9b117-2ded-42c3-9264-ccf3dfaaa951")
            .withName("Benson Meier")
            .withAddress("311, Clementi Ave 2, #02-25")
            .withEmail("johnd@example.com").withPhone("98765432")
            .withTags("owesMoney", "friends").build();
    public static final Recruit CARL = new RecruitBuilder()
            .withID("eac9b117-2ded-42c3-9264-ccf3dfaaa952")
            .withName("Carl Kurz")
            .withPhone("95352563")
            .withEmail("heinz@example.com")
            .withAddress("wall street").build();
    public static final Recruit DANIEL = new RecruitBuilder()
            .withID("eac9b117-2ded-42c3-9264-ccf3dfaaa953")
            .withName("Daniel Meier")
            .withPhone("87652533")
            .withEmail("cornelia@example.com")
            .withAddress("10th street")
            .withTags("friends").build();
    public static final Recruit ELLE = new RecruitBuilder()
            .withID("eac9b117-2ded-42c3-9264-ccf3dfaaa954")
            .withName("Elle Meyer")
            .withPhone("9482224")
            .withEmail("werner@example.com")
            .withAddress("michegan ave").build();
    public static final Recruit FIONA = new RecruitBuilder()
            .withID("eac9b117-2ded-42c3-9264-ccf3dfaaa955")
            .withName("Fiona Kunz")
            .withPhone("9482427")
            .withEmail("lydia@example.com")
            .withAddress("little tokyo").build();
    public static final Recruit GEORGE = new RecruitBuilder()
            .withID("eac9b117-2ded-42c3-9264-ccf3dfaaa956")
            .withName("George Best")
            .withPhone("9482442")
            .withEmail("anna@example.com")
            .withAddress("4th street").build();

    // Manually added
    public static final Recruit HOON = new RecruitBuilder().withName("Hoon Meier").withPhone("8482424")
            .withEmail("stefan@example.com").withAddress("little india").build();
    public static final Recruit IDA = new RecruitBuilder().withName("Ida Mueller").withPhone("8482131")
            .withEmail("hans@example.com").withAddress("chicago ave").build();

    // Manually added - Person's details found in {@code CommandTestUtil}
    public static final Recruit AMY = new RecruitBuilder()
            .withID(VALID_ID_AMY)
            .withName(VALID_NAME_AMY)
            .withPhone(VALID_PHONE_AMY)
            .withEmail(VALID_EMAIL_AMY)
            .withAddress(VALID_ADDRESS_AMY)
            .withTags(VALID_TAG_FRIEND).build();
    public static final Recruit BOB = new RecruitBuilder()
            .withID(VALID_ID_BOB)
            .withName(VALID_NAME_BOB)
            .withPhone(VALID_PHONE_BOB)
            .withEmail(VALID_EMAIL_BOB)
            .withAddress(VALID_ADDRESS_BOB)
            .withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND)
            .build();

    public static final String KEYWORD_MATCHING_MEIER = "Meier"; // A keyword that matches MEIER

    private TypicalRecruits() {} // prevents instantiation

    /**
     * Returns an {@code AddressBook} with all the typical recruits.
     */
    public static AddressBook getTypicalAddressBook() {
        AddressBook ab = new AddressBook();
        for (Recruit recruit : getTypicalRecruits()) {
            ab.addRecruit(recruit);
        }
        return ab;
    }

    public static List<Recruit> getTypicalRecruits() {
        return new ArrayList<>(Arrays.asList(ALICE, BENSON, CARL, DANIEL, ELLE, FIONA, GEORGE));
    }
}
