package seedu.address.model.recruit.exceptions;

/**
 * Represents an exception that is triggered if one tries to create a new Recruit without specifying a name
 */
public class NoNameRecruitException extends IllegalRecruitBuilderActionException {
    public NoNameRecruitException(String message) {
        super(message);
    }
}
