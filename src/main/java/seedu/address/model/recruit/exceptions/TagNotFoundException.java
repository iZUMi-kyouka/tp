package seedu.address.model.recruit.exceptions;

import java.util.ArrayList;
import java.util.List;

import seedu.address.model.tag.Tag;

/**
 * Represents an exception that is triggered when attempting to perform an action on
 * a {@code Tag} that cannot be found on a {@code Recruit}.
 */
public class TagNotFoundException extends IllegalRecruitBuilderActionException {
    private List<Tag> missingTags;


    public TagNotFoundException(String s) {
        super(s);
    }

    /**
     * Constructs a {@code TagNotFoundException} which indicates that the user is trying
     * to perform an action on {@link Tag}s that cannot be found on a {@code Recruit}
     *
     * @param missingTags a List of the tags that could not be found
     */
    public TagNotFoundException(List<? extends Tag> missingTags) {
        super(String.format("The following tags cannot be found: %s", missingTags));
        this.missingTags = new ArrayList<>(missingTags);
    }

    /**
     * Constructs a {@code TagNotFoundException} which indicates that the user is trying
     * to perform an action on a {@link Tag} that cannot be found on a {@code Recruit}
     *
     * @param missingEntry the tag that could not be found
     */
    public TagNotFoundException(Tag missingEntry) {
        super(String.format("%s cannot be found", missingEntry));
        this.missingTags = List.of(missingEntry);
    }

    public List<Tag> getMissingTags() {
        return missingTags;
    }
}
