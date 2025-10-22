package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.combine;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_RECRUITS;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.commons.util.RecruitUtil;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.recruit.Address;
import seedu.address.model.recruit.Email;
import seedu.address.model.recruit.Name;
import seedu.address.model.recruit.Phone;
import seedu.address.model.recruit.Recruit;
import seedu.address.model.tag.Tag;

/**
 * Edits the details of an existing person in the address book.
 */
public class EditCommand extends Command {

    public static final String COMMAND_WORD = "edit";
    public static final String OPERATION_DESCRIPTOR = "modification of recruit:\n%s";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the recruit identified "
            + "by the index number used in the displayed recruit list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_NAME + "NAME] "
            + "[" + PREFIX_PHONE + "PHONE] "
            + "[" + PREFIX_EMAIL + "EMAIL] "
            + "[" + PREFIX_ADDRESS + "ADDRESS] "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_PHONE + "91234567 "
            + PREFIX_EMAIL + "johndoe@example.com";

    public static final String MESSAGE_EDIT_RECRUIT_SUCCESS = "Edited Recruit:\n%1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_RECRUIT = "This recruit already exists in the address book.";
    public static final String MESSAGE_DUPLICATE_ATTRIBUTE =
            "The attribute '%s' with value '%s' already exists for this recruit.";
    private static final String DELTA_SEP = " -> "; // separator to show modified values in success message

    private final Index index;
    private final EditRecruitDescriptor editRecruitDescriptor;

    /**
     * @param index of the person in the filtered recruit list to edit
     * @param editRecruitDescriptor details to edit the recruit with
     */
    public EditCommand(Index index, EditRecruitDescriptor editRecruitDescriptor) {
        requireNonNull(index);
        requireNonNull(editRecruitDescriptor);

        this.index = index;
        this.editRecruitDescriptor = new EditRecruitDescriptor(editRecruitDescriptor);
    }

    /**
     * @param index of the person in the filtered recruit list to edit
     * @param editRecruitDescriptor details to edit the recruit with
     * @param operation the type of edit operation to be performed
     */
    public EditCommand(Index index, EditRecruitDescriptor editRecruitDescriptor,
            EditRecruitDescriptor.EditRecruitOperation operation) {
        requireNonNull(index);
        requireNonNull(editRecruitDescriptor);

        this.index = index;
        this.editRecruitDescriptor = new EditRecruitDescriptor(editRecruitDescriptor, operation);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Recruit> lastShownList = model.getFilteredRecruitList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_RECRUIT_DISPLAYED_INDEX);
        }

        Recruit recruitToEdit = lastShownList.get(index.getZeroBased());
        Recruit editedRecruit = createEditedRecruit(recruitToEdit, editRecruitDescriptor);

        if (!recruitToEdit.isSameRecruit(editedRecruit) && model.hasRecruit(editedRecruit)) {
            throw new CommandException(MESSAGE_DUPLICATE_RECRUIT);
        }

        model.setRecruit(recruitToEdit, editedRecruit);
        model.commitAddressBook(String.format(OPERATION_DESCRIPTOR, formatDelta(recruitToEdit, editedRecruit)));
        model.updateFilteredRecruitList(PREDICATE_SHOW_ALL_RECRUITS);
        return new CommandResult(String.format(
                MESSAGE_EDIT_RECRUIT_SUCCESS, formatDelta(recruitToEdit, editedRecruit)));
    }

    /**
     * Creates and returns a {@code Person} with the details of {@code personToEdit}
     * edited with {@code editPersonDescriptor}.
     */
    private static Recruit createEditedRecruit(Recruit recruitToEdit, EditRecruitDescriptor editRecruitDescriptor)
            throws CommandException {
        assert recruitToEdit != null;

        UUID updatedId = editRecruitDescriptor.getID().orElse(recruitToEdit.getID());
        List<Name> updatedName = new ArrayList<>(editRecruitDescriptor.getName().orElse(recruitToEdit.getNames()));
        List<Phone> updatedPhone = new ArrayList<>(editRecruitDescriptor.getPhone().orElse(recruitToEdit.getPhones()));
        List<Email> updatedEmail = new ArrayList<>(editRecruitDescriptor.getEmail().orElse(recruitToEdit.getEmails()));
        List<Address> updatedAddress = new ArrayList<>(
                editRecruitDescriptor.getAddress().orElse(recruitToEdit.getAddresses()));
        Set<Tag> updatedTags = editRecruitDescriptor.getTags().orElse(recruitToEdit.getTags());
        LogsCenter.getLogger(EditCommand.class).info(editRecruitDescriptor.operation.toString());

        switch (editRecruitDescriptor.operation) {
        case APPEND: {
            updatedName = combine(List.of(recruitToEdit.getNames(), editRecruitDescriptor.getName().orElse(List.of())));
            updatedPhone = combine(
                    List.of(recruitToEdit.getPhones(), editRecruitDescriptor.getPhone().orElse(List.of())));
            updatedEmail = combine(
                    List.of(recruitToEdit.getEmails(), editRecruitDescriptor.getEmail().orElse(List.of())));
            updatedAddress = combine(
                    List.of(recruitToEdit.getAddresses(), editRecruitDescriptor.getAddress().orElse(List.of())));
            updatedTags = new HashSet<>(
                    combine(List.of(recruitToEdit.getTags(), editRecruitDescriptor.getTags().orElse(new HashSet<>()))));
            break;
        }
        case REMOVE: {
            throw new RuntimeException("not implemented");
        }
        default: { }
        }
        LogsCenter.getLogger(EditCommand.class).info(updatedName.toString());

        return new Recruit(updatedId, updatedName, updatedPhone, updatedEmail, updatedAddress, updatedTags);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditCommand)) {
            return false;
        }

        EditCommand otherEditCommand = (EditCommand) other;
        return index.equals(otherEditCommand.index)
                && editRecruitDescriptor.equals(otherEditCommand.editRecruitDescriptor);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("index", index)
                .add("editPersonDescriptor", editRecruitDescriptor)
                .toString();
    }

    String formatDelta(Recruit oldRecruit, Recruit newRecruit) {
        final StringBuilder builder = new StringBuilder();
        builder.append("Name: ").append(oldRecruit.getNames())
                .append(RecruitUtil.hasSameName(oldRecruit, newRecruit)
                        ? "" : DELTA_SEP + newRecruit.getNames().toString())
                .append("\nPhone: ").append(oldRecruit.getPhones())
                .append(RecruitUtil.hasSamePhone(oldRecruit, newRecruit)
                        ? "" : DELTA_SEP + newRecruit.getPhones())
                .append("\nEmail: ").append(oldRecruit.getEmails())
                .append(RecruitUtil.hasSameEmail(oldRecruit, newRecruit)
                        ? "" : DELTA_SEP + newRecruit.getEmails())
                .append("\nAddress: ").append(oldRecruit.getAddresses())
                .append(RecruitUtil.hasSameAddress(oldRecruit, newRecruit)
                        ? "" : DELTA_SEP + newRecruit.getAddresses())
                .append("\nTags: ").append(oldRecruit.getTags())
                .append(RecruitUtil.hasSameTags(oldRecruit, newRecruit)
                        ? "" : DELTA_SEP + newRecruit.getTags());
        return builder.toString();
    }

    /**
     * Stores the details to edit the person with. Each non-empty field value will replace the
     * corresponding field value of the person.
     */
    public static class EditRecruitDescriptor {
        private UUID id;
        private List<Name> name;
        private List<Phone> phone;
        private List<Email> email;
        private List<Address> address;
        private Set<Tag> tags;
        private EditRecruitOperation operation = EditRecruitOperation.OVERWRITE;

        public EditRecruitDescriptor() {}

        public EditRecruitDescriptor(EditRecruitOperation operation) {
            this.operation = operation;
        }

        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         */
        public EditRecruitDescriptor(EditRecruitDescriptor toCopy) {
            setID(toCopy.id);
            setNames(toCopy.name);
            setPhones(toCopy.phone);
            setEmails(toCopy.email);
            setAddresses(toCopy.address);
            setTags(toCopy.tags);
            setOperation(toCopy.operation);
        }

        /**
         * Copy constructor of an {@code EditRecruitDescriptor} with the specified {@code operation}.
         */
        public EditRecruitDescriptor(EditRecruitDescriptor toCopy, EditRecruitOperation operation) {
            this(toCopy);
            setOperation(operation);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(id, name, phone, email, address, tags);
        }

        public void setID(UUID id) {
            this.id = id;
        }
        public Optional<UUID> getID() {
            return Optional.ofNullable(id);
        }
        public void setNames(List<Name> name) {
            this.name = name;
        }

        public Optional<List<Name>> getName() {
            return Optional.ofNullable(name);
        }

        public void setPhones(List<Phone> phone) {
            this.phone = phone;
        }

        public Optional<List<Phone>> getPhone() {
            return Optional.ofNullable(phone);
        }

        public void setEmails(List<Email> email) {
            this.email = email;
        }

        public Optional<List<Email>> getEmail() {
            return Optional.ofNullable(email);
        }

        public void setAddresses(List<Address> address) {
            this.address = address;
        }

        public Optional<List<Address>> getAddress() {
            return Optional.ofNullable(address);
        }

        /**
         * Sets {@code tags} to this object's {@code tags}.
         * A defensive copy of {@code tags} is used internally.
         */
        public void setTags(Set<Tag> tags) {
            this.tags = (tags != null) ? new HashSet<>(tags) : null;
        }

        /**
         * Returns an unmodifiable tag set, which throws {@code UnsupportedOperationException}
         * if modification is attempted.
         * Returns {@code Optional#empty()} if {@code tags} is null.
         */
        public Optional<Set<Tag>> getTags() {
            return (tags != null) ? Optional.of(Collections.unmodifiableSet(tags)) : Optional.empty();
        }

        public EditRecruitOperation getOperation() {
            return operation;
        }

        public void setOperation(EditRecruitOperation op) {
            requireNonNull(op);
            this.operation = op;
        }

        @Override
        public boolean equals(Object other) {
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditRecruitDescriptor)) {
                return false;
            }

            EditRecruitDescriptor otherEditRecruitDescriptor = (EditRecruitDescriptor) other;
            return Objects.equals(name, otherEditRecruitDescriptor.name)
                    && Objects.equals(phone, otherEditRecruitDescriptor.phone)
                    && Objects.equals(email, otherEditRecruitDescriptor.email)
                    && Objects.equals(address, otherEditRecruitDescriptor.address)
                    && Objects.equals(tags, otherEditRecruitDescriptor.tags)
                    && Objects.equals(operation, otherEditRecruitDescriptor.operation);
        }

        @Override
        public String toString() {
            return new ToStringBuilder(this)
                    .add("name", name)
                    .add("phone", phone)
                    .add("email", email)
                    .add("address", address)
                    .add("tags", tags)
                    .add("operation", operation)
                    .toString();
        }

        /**
         * Represents all the possible types of operation that an 'edit' command can do
         * to the attributes of a Recruit.
         */
        public static enum EditRecruitOperation {
            APPEND, OVERWRITE, REMOVE
        }
    }
}
