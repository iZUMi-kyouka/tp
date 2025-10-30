package seedu.address.ui;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.stage.Stage;
import seedu.address.commons.core.LogsCenter;

/**
 * Controller for a help page
 */
public class HelpWindow extends UiPart<Stage> {

    public static final String USERGUIDE_URL = "https://ay2526s1-cs2103t-f09-3.github.io/tp/UserGuide.html";
    public static final String COMMAND_HELP_MESSAGE = """
            TLDR List of Commands:
            \s
            1. add n/NAME... [p/PHONE_NUMBER]... [e/EMAIL]... [a/ADDRESS]... [d/DESCRIPTION] [t/TAG]...
            Adds a recruit with the provided details into the recruit list.
            \s
            2. view INDEX|UUID
            View the recruit at the provided INDEX in the recruit list or with the provided UUID.
            \s
            3. edit INDEX|UUID [OPERATION] [n/NAME]... [p/PHONE_NUMBER]... [e/EMAIL]... [a/ADDRESS]... [d/DESCRIPTION]\
             [t/TAG]...
            Edit the details of the recruit at the provided INDEX in the recruit list or with the provided UUID.
            There are 3 different types of OPERATION:
            -o : Overrides the details of the target recruit with the provided attributes.
            -ap : Appends the provided attributes to the target recruit.
            -rm : Remove the provided attributes from the target recruit.
            \s
            4. delete INDEX|UUID
            Deletes the recruit at the provided INDEX in the recruit list or with the provided UUID.
            \s
            5. list [MODE]
            Lists the recruits as a recruit list.
            By default it lists all unarchived recruits.
            There are 2 different MODE:
            -archived : Shows a the list of only archived recruits.
            -all : Shows all archive and unarchived recruits.
            \s
            6. sort [n/ ORDER] [p/ ORDER] [e/ ORDER] [a/ ORDER]
            Sorts the recruits based on the field specified by the provided prefixes in ascending or descending \
            order depending on the ORDER specified.
            ORDER can be either:
            asc : to sort in ascending order.
            desc : to sort in descending order.
            \s
            7. find KEYWORD [MORE_KEYWORDS]
            Finds and lists all recruits whose details match any of the given keywords.
            The search is case-insensitive and can be filtered by different fields using flags.
            For more details, please check our user guide.
            \s
            8. archive INDEX
            Archives the recruit at the provided INDEX, hiding them from the standard recruit list.
            \s
            9. unarchive INDEX
            Brings the recruit out of the archived state, allowing the recruit to be visible in \
            the standard recruit list.
            \s
            10. clear
            Clears all recruits in the recruit list, effectively deleting them permanently.
            \s
            12. export [FILEPATH]
            Export the recruits to the given FILEPATH.
            \s
            13. undo
            Undoes the previously executed command.
            \s
            14. redo
            Redoes the last undo-ed command.
            \s
            15. dismiss
            Dismisses the result of the previously executed command, hence its the result message.
            \s
            16. help
            Opens this help menu.
           """;
    public static final String HELP_MESSAGE = "Refer to the user guide: "
            + USERGUIDE_URL + "\n\n" + COMMAND_HELP_MESSAGE;

    private static final Logger logger = LogsCenter.getLogger(HelpWindow.class);
    private static final String FXML = "HelpWindow.fxml";

    @FXML
    private Button copyButton;

    @FXML
    private Label helpMessage;

    /**
     * Creates a new HelpWindow.
     *
     * @param root Stage to use as the root of the HelpWindow.
     */
    public HelpWindow(Stage root) {
        super(FXML, root);
        helpMessage.setText(HELP_MESSAGE);
    }

    /**
     * Creates a new HelpWindow.
     */
    public HelpWindow() {
        this(new Stage());
    }

    /**
     * Shows the help window.
     * @throws IllegalStateException
     *     <ul>
     *         <li>
     *             if this method is called on a thread other than the JavaFX Application Thread.
     *         </li>
     *         <li>
     *             if this method is called during animation or layout processing.
     *         </li>
     *         <li>
     *             if this method is called on the primary stage.
     *         </li>
     *         <li>
     *             if {@code dialogStage} is already showing.
     *         </li>
     *     </ul>
     */
    public void show() {
        logger.fine("Showing help page about the application.");
        getRoot().show();
        getRoot().centerOnScreen();
    }

    /**
     * Returns true if the help window is currently being shown.
     */
    public boolean isShowing() {
        return getRoot().isShowing();
    }

    /**
     * Hides the help window.
     */
    public void hide() {
        getRoot().hide();
    }

    /**
     * Focuses on the help window.
     */
    public void focus() {
        getRoot().requestFocus();
    }

    /**
     * Copies the URL to the user guide to the clipboard.
     */
    @FXML
    private void copyUrl() {
        final Clipboard clipboard = Clipboard.getSystemClipboard();
        final ClipboardContent url = new ClipboardContent();
        url.putString(USERGUIDE_URL);
        clipboard.setContent(url);
    }

    /**
     * Opens the user guide in the user's browser.
     */
    @FXML
    private void openUserGuide() {
        new Thread(() -> {
            try {
                Desktop.getDesktop().browse(URI.create(USERGUIDE_URL));
            } catch (IOException e) {
                logger.warning("Failed to open default browser.");
            }
        }).start();
    }
}
