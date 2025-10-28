package seedu.address.model.recruit.exceptions;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import seedu.address.model.tag.Tag;

/**
 * Represents an exception that is triggered when trying to add a {@link Tag} to a Recruit that
 * already has that tag.
 */
public class TagAlreadyExistsException extends IllegalRecruitBuilderActionException {
    private List<Tag> duplicatedTags;

    public TagAlreadyExistsException(String s) {
        super(s);
    }

    /**
     * Constructs a {@code TagAlreadyExistsException} which indicates that the user is trying
     * to add {@link Tag}s that are already present on {@code Recruit}
     *
     * @param duplicatedTags a list of duplicated tags
     */
    public TagAlreadyExistsException(List<? extends Tag> duplicatedTags) {
        super(String.format("The following tags were already found: %s",
                duplicatedTags.stream().map(Tag::toString).collect(Collectors.joining(", "))));
        this.duplicatedTags = new ArrayList<>(duplicatedTags);
    }

    /**
     * Constructs a {@code TagAlreadyExistsException} which indicates that the user is trying
     * to add a {@link Tag} that is already present on {@code Recruit}
     *
     * @param duplicatedTag the tag that is duplicated
     */
    public TagAlreadyExistsException(Tag duplicatedTag) {
        super(String.format("%s was already added!", duplicatedTag));
        this.duplicatedTags = List.of(duplicatedTag);
    }

    public List<Tag> getDuplicatedTags() {
        return duplicatedTags;
    }
}
