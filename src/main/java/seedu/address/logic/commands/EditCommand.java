package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_RECRUITS;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.RecruitUtil;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.recruit.Recruit;
import seedu.address.model.recruit.RecruitBuilder;
import seedu.address.model.recruit.exceptions.DataEntryAlreadyExistsException;
import seedu.address.model.recruit.exceptions.DataEntryNotFoundException;
import seedu.address.model.recruit.exceptions.IllegalRecruitBuilderActionException;
import seedu.address.model.recruit.exceptions.TagAlreadyExistsException;
import seedu.address.model.recruit.exceptions.TagNotFoundException;

/**
 * Edits the details of an existing person in the address book.
 */
public class EditCommand extends Command {

    public static final String COMMAND_WORD = "edit";
    public static final String OPERATION_DESCRIPTOR = "modification of recruit:\n%s";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the recruit identified "
            + "by the index/uuid used in the displayed recruit list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX/UUID "
            + "[" + PREFIX_NAME + "NAME] "
            + "[" + PREFIX_PHONE + "PHONE] "
            + "[" + PREFIX_EMAIL + "EMAIL] "
            + "[" + PREFIX_ADDRESS + "ADDRESS] "
            + "[" + PREFIX_DESCRIPTION + "DESCRIPTION] "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_PHONE + "91234567 "
            + PREFIX_EMAIL + "johndoe@example.com";

    public static final String MESSAGE_EDIT_RECRUIT_SUCCESS = "Edited Recruit:\n%1$s";
    public static final String MESSAGE_NO_FIELD_PROVIDED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_RECRUIT = "This recruit already exists in the address book.";
    public static final String MESSAGE_DUPLICATE_ATTRIBUTE = "The following %s are already present: %s";
    public static final String MESSAGE_MISSING_ATTRIBUTE = "The following %s do not exist: %s";
    public static final String MESSAGE_INVALID_OPERATION = "Multiple edit operation type is not allowed.";
    private static final String DELTA_SEP = " -> "; // separator to show modified values in success message

    private final Optional<UUID> id;
    private final Optional<Index> index;
    private final EditRecruitDescriptor editRecruitDescriptor;

    /**
     * @param id of the person in the filtered recruit list to edit
     * @param editRecruitDescriptor details to edit the recruit with
     */
    public EditCommand(UUID id, EditRecruitDescriptor editRecruitDescriptor) {
        requireNonNull(id);
        requireNonNull(editRecruitDescriptor);

        this.id = Optional.of(id);
        this.index = Optional.empty();
        this.editRecruitDescriptor = new EditRecruitDescriptor(editRecruitDescriptor);
    }

    /**
     * @param index of the person in the filtered recruit list to edit
     * @param editRecruitDescriptor details to edit the recruit with
     */
    public EditCommand(Index index, EditRecruitDescriptor editRecruitDescriptor) {
        requireNonNull(index);
        requireNonNull(editRecruitDescriptor);

        this.id = Optional.empty();
        this.index = Optional.of(index);
        this.editRecruitDescriptor = new EditRecruitDescriptor(editRecruitDescriptor);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        Recruit recruitToEdit;

        if (id.isEmpty() && index.isEmpty()) {
            throw new CommandException(Messages.MESSAGE_NO_ID_OR_INDEX);
        }
        if (id.isPresent()) {
            recruitToEdit = model.getAddressBook().getRecruitList().stream()
                    .filter(recruit -> recruit.getID().equals(id.get()))
                    .findFirst()
                    .orElseThrow(() -> new CommandException(Messages.MESSAGE_INVALID_RECRUIT_ID));
        } else {
            List<Recruit> lastShownList = model.getFilteredRecruitList();
            if (index.get().getZeroBased() < 0 || index.get().getZeroBased() >= lastShownList.size()) {
                throw new CommandException(Messages.MESSAGE_INVALID_RECRUIT_DISPLAYED_INDEX);
            }
            recruitToEdit = lastShownList.get(index.get().getZeroBased());
        }

        Recruit editedRecruit = createEditedRecruit(recruitToEdit, editRecruitDescriptor);

        if (!recruitToEdit.isSameRecruit(editedRecruit) && model.hasRecruit(editedRecruit)) {
            throw new CommandException(MESSAGE_DUPLICATE_RECRUIT);
        }

        model.setRecruit(recruitToEdit, editedRecruit);
        model.commitAddressBook(String.format(OPERATION_DESCRIPTOR, formatDelta(recruitToEdit, editedRecruit)));
        model.updateFilteredRecruitList(PREDICATE_SHOW_ALL_RECRUITS);

        return new CommandResult(String.format(MESSAGE_EDIT_RECRUIT_SUCCESS,
                formatDelta(recruitToEdit, editedRecruit)));
    }
    /**
     * Creates and returns a {@code Person} with the details of {@code personToEdit}
     * edited with {@code editPersonDescriptor}.
     */
    private static Recruit createEditedRecruit(Recruit recruitToEdit, EditRecruitDescriptor descriptor)
            throws CommandException {
        return switch (descriptor.operation) {
        case APPEND -> createEditedRecruitWithAppendedAttributes(recruitToEdit, descriptor);
        case REMOVE -> createEditedRecruitWithRemovedAttributes(recruitToEdit, descriptor);
        case OVERWRITE -> createEditedRecruitWithOverwrittenAttributes(recruitToEdit, descriptor);
        };
    }

    private static Recruit createEditedRecruitWithAppendedAttributes(Recruit rec, EditRecruitDescriptor desc)
            throws CommandException {
        try {
            return new RecruitBuilder(rec).append(desc).build();
        } catch (DataEntryAlreadyExistsException e) {
            throw new CommandException(String.format(MESSAGE_DUPLICATE_ATTRIBUTE,
                    e.getDataType(), e.getDuplicatedEntries()));
        } catch (TagAlreadyExistsException e) {
            throw new CommandException(String.format(MESSAGE_DUPLICATE_ATTRIBUTE,
                    "tag", e.getDuplicatedTags()));
        }
    }

    private static Recruit createEditedRecruitWithOverwrittenAttributes(Recruit rec, EditRecruitDescriptor desc)
            throws CommandException {
        return new RecruitBuilder(rec).override(desc).build();
    }

    private static Recruit createEditedRecruitWithRemovedAttributes(Recruit rec, EditRecruitDescriptor desc)
            throws CommandException {
        try {
            return new RecruitBuilder(rec).remove(desc).build();
        } catch (DataEntryNotFoundException e) {
            throw new CommandException(String.format(MESSAGE_MISSING_ATTRIBUTE,
                    e.getDataType(), e.getMissingEntries()));
        } catch (TagNotFoundException e) {
            throw new CommandException(String.format(MESSAGE_MISSING_ATTRIBUTE,
                    "tag", e.getMissingTags()));
        }
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
        return (id.isPresent() && otherEditCommand.id.isPresent() && id.equals(otherEditCommand.id)
                || (index.isPresent() && otherEditCommand.index.isPresent() && index.equals(otherEditCommand.index)))
                && editRecruitDescriptor.equals(otherEditCommand.editRecruitDescriptor);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("ID", id)
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
                .append("\nDescription: ").append(oldRecruit.getDescription())
                .append(RecruitUtil.hasSameDescription(oldRecruit, newRecruit)
                        ? "" : DELTA_SEP + newRecruit.getDescription())
                .append("\nTags: ").append(oldRecruit.getTags())
                .append(RecruitUtil.hasSameTags(oldRecruit, newRecruit)
                        ? "" : DELTA_SEP + newRecruit.getTags());
        return builder.toString();
    }

    /**
     * Stores the details to edit the person with. Each non-empty field value will replace the
     * corresponding field value of the person. Directly extends {@link RecruitBuilder}
     */
    public static class EditRecruitDescriptor extends RecruitBuilder {

        private final EditCommand.EditOperation operation;

        /**
         * Creates an EditRecruitDescriptor assigned with the default operation (overwrite)
         */
        public EditRecruitDescriptor() {
            super();

            this.operation = EditOperation.OVERWRITE;
        }

        /**
         * Creates an EditRecruitDescriptor assigned with the following operation.
         *
         * @param op the operation of to perform during edit.
         */
        public EditRecruitDescriptor(EditOperation op) {
            super();

            requireNonNull(op);
            this.operation = op;
        }

        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         */
        public EditRecruitDescriptor(EditRecruitDescriptor toCopy) {
            super(toCopy);
            this.operation = toCopy.operation;
        }

        public EditOperation getOperation() {
            return operation;
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

            // Check if data fields are equal
            if (!super.equals(other)) {
                return false;
            }
            EditRecruitDescriptor otherBuilder = (EditRecruitDescriptor) other;
            return this.operation.equals(otherBuilder.operation);

        }

        @Override
        public String toString() {
            return new ToStringBuilder(this)
                    .add("name", names)
                    .add("phone", phones)
                    .add("email", emails)
                    .add("address", addresses)
                    .add("description", description)
                    .add("tags", tags)
                    .add("operation", operation)
                    .toString();
        }

        // TODO:: WARNING! THIS VIOLATES LSP I THINK - replace with composition in the future I guess
        @Override
        public Recruit build() {
            throw new IllegalRecruitBuilderActionException("Cannot build an EditRecruitDescriptor");
        }
    }


    /**
     * Represents all the possible types of operation that an 'edit' command can do
     * to the attributes of a Recruit.
     */
    public static enum EditOperation {
        APPEND, OVERWRITE, REMOVE
    }
}
