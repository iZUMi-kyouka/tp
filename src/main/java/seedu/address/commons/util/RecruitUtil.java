package seedu.address.commons.util;

import seedu.address.model.recruit.Recruit;

/**
 * Utility class for various operations involving Recruits.
 */
public class RecruitUtil {
    /**
     * Returns true if both recruit have the same names.
     */
    public static boolean hasSameName(Recruit r1, Recruit r2) {
        return r1.getNames().equals(r2.getNames());
    }

    /**
     * Returns true if both recruit have the same phones.
     */
    public static boolean hasSamePhone(Recruit r1, Recruit r2) {
        return r1.getPhones().equals(r2.getPhones());
    }

    /**
     * Returns true if both recruit have the same emails.
     */
    public static boolean hasSameEmail(Recruit r1, Recruit r2) {
        return r1.getEmails().equals(r2.getEmails());
    }

    /**
     * Returns true if both recruit have the same addresses.
     */
    public static boolean hasSameAddress(Recruit r1, Recruit r2) {
        return r1.getAddresses().equals(r2.getAddresses());
    }

    /**
     * Returns true if both recruit have the same tags.
     */
    public static boolean hasSameTags(Recruit r1, Recruit r2) {
        return r1.getTags().equals(r2.getTags());
    }
}
