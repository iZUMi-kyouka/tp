package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.recruit.Recruit;

/**
 * Archives a recruit entry
 */
public class UnarchiveCommand extends Command {
    public static final String COMMAND_WORD = "unarchive";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Unarchives the details of an "
            + "unarchived recruit identified by the index number used in the displayed recruit list. "
            + "Parameters: INDEX (must be a positive integer) "
            + "Example: " + COMMAND_WORD + " 1 ";
    public static final String OPERATION_DESCRIPTOR = "unarchival of recruit:%s\n";
    public static final String RECRUIT_ALREADY_UNARCHIVED = "This recruit is not archived.";
    public static final String MESSAGE_ARCHIVE_RECRUIT_SUCCESS = "Unarchived Recruit:\n%1$s";

    private final Index index;
    private final UUID uuid;

    /**
     * Creates an Unarchive Command that unarchives by Index
     * @param index of the person in the filtered recruit list to unarchive
     */
    public UnarchiveCommand(Index index) {
        requireNonNull(index);
        this.index = index;
        this.uuid = null;
    }

    /**
     * Creates an Unarchive Command that unarchives by Index
     * @param uuid of the person to unarchive
     */
    public UnarchiveCommand(UUID uuid) {
        requireNonNull(uuid);
        this.index = null;
        this.uuid = uuid;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Recruit> lastShownList = model.getFilteredRecruitList();

        Recruit recruitToUnarchive;
        if (this.uuid == null) {
            if (index.getZeroBased() >= lastShownList.size()) {
                throw new CommandException(Messages.MESSAGE_INVALID_RECRUIT_DISPLAYED_INDEX);
            }
            recruitToUnarchive = lastShownList.get(index.getZeroBased());
        } else {
            Optional<Recruit> findRecruit = model.getUnfilteredRecruitByID(uuid);
            if (findRecruit.isEmpty()) {
                throw new CommandException(Messages.MESSAGE_INVALID_RECRUIT_ID);
            } else {
                recruitToUnarchive = findRecruit.get();
            }
        }

        assert recruitToUnarchive != null;
        if (!recruitToUnarchive.isArchived()) {
            throw new CommandException(RECRUIT_ALREADY_UNARCHIVED);
        }

        Recruit recruit = unarchiveRecruit(recruitToUnarchive);

        model.setRecruit(recruitToUnarchive, recruit);
        model.commitAddressBook(String.format(OPERATION_DESCRIPTOR, Messages.format(recruitToUnarchive)));
        model.refreshFilteredRecruitList();

        return new CommandResult(String.format(
                MESSAGE_ARCHIVE_RECRUIT_SUCCESS, Messages.format(recruitToUnarchive)));
    }

    /**
     * Creates and returns a {@code Person} with the details of {@code personToEdit}
     * edited with {@code editPersonDescriptor}.
     */
    private static Recruit unarchiveRecruit(Recruit recruitToEdit) {
        assert recruitToEdit != null;
        return recruitToEdit.unarchive();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof UnarchiveCommand otherCommand)) {
            return false;
        }
        if (index == null) {
            return uuid.equals(otherCommand.uuid);
        } else {
            return index.equals(otherCommand.index);
        }
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("index", index)
                .add("uuid", uuid)
                .toString();
    }
}
