package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static seedu.address.testutil.TypicalRecruits.ALICE;
import static seedu.address.testutil.TypicalRecruits.BOB;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.recruit.Recruit;
import seedu.address.testutil.SimpleRecruitBuilder;

public class ExportCommandTest {

    private final List<Path> tempFiles = new ArrayList<>(); // 🔹 Track all temp files
    private Path tempFile;
    private ModelStubAcceptingExport modelStub;

    @BeforeEach
    public void setUp() throws IOException {
        modelStub = new ModelStubAcceptingExport();
        tempFile = createTempFile("test_recruits.csv"); // 🔹 Use helper
    }

    @AfterEach
    public void tearDown() throws IOException {
        for (Path file : tempFiles) { // 🔹 Delete all tracked files
            Files.deleteIfExists(file);
        }
        tempFiles.clear();
    }

    /** Helper method to create a temp file and track it for cleanup */
    private Path createTempFile(String fileName) throws IOException {
        Path dataDir = Paths.get("data");
        Files.createDirectories(dataDir);
        Path file = dataDir.resolve(fileName);
        tempFiles.add(file);
        return file;
    }

    /** Helper method to create sample recruits */
    private static List<Recruit> createSampleRecruits() {
        List<Recruit> recruits = new ArrayList<>();
        recruits.add(new SimpleRecruitBuilder(ALICE).build());
        recruits.add(new SimpleRecruitBuilder(BOB).build());
        return recruits;
    }

    @Test
    public void constructor_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new ExportCommand(null));
    }

    @Test
    public void execute_validFilePath_exportsSuccessfully() throws Exception {
        ExportCommand command = new ExportCommand(tempFile);

        CommandResult result = command.execute(modelStub);

        assertEquals(String.format(ExportCommand.MESSAGE_SUCCESS, tempFile), result.getFeedbackToUser());
        List<String> lines = Files.readAllLines(tempFile);
        int expectedLineCount = modelStub.recruitsAdded.size() + 1; // +1 for CSV header
        assertEquals(expectedLineCount, lines.size());
    }

    @Test
    public void execute_nullFilePath_usesUserPrefsPath() throws Exception {
        Path newPath = createTempFile("test_recruits_2.csv");
        modelStub.setDefaultExportPath(newPath);
        ExportCommand command = new ExportCommand();

        CommandResult result = command.execute(modelStub);

        assertEquals(String.format(ExportCommand.MESSAGE_SUCCESS, newPath), result.getFeedbackToUser());
    }

    /**
     * Model stub that accepts recruits being exported.
     */
    private static class ModelStubAcceptingExport implements Model {

        final List<Recruit> recruitsAdded = createSampleRecruits();
        private Path defaultExportPath;

        public void setDefaultExportPath(Path path) {
            this.defaultExportPath = path;
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            return new ReadOnlyAddressBook() {
                private final javafx.collections.ObservableList<Recruit> recruits =
                        javafx.collections.FXCollections.observableArrayList(recruitsAdded);

                @Override
                public javafx.collections.ObservableList<Recruit> getRecruitList() {
                    return recruits;
                }
            };
        }

        @Override
        public seedu.address.model.ReadOnlyUserPrefs getUserPrefs() {
            return new seedu.address.model.UserPrefs() {
                @Override
                public Path getExportFilePath() {
                    return defaultExportPath;
                }
            };
        }

        /** All other Model methods throw AssertionError using helper */
        private static <T> T failStub() {
            throw new AssertionError("This method should not be called in the stub.");
        }

        @Override
        public void setUserPrefs(seedu.address.model.ReadOnlyUserPrefs userPrefs) {
            failStub();
        }

        @Override
        public void setGuiSettings(seedu.address.commons.core.GuiSettings guiSettings) {
            failStub();
        }

        @Override
        public seedu.address.commons.core.GuiSettings getGuiSettings() {
            return failStub();
        }

        @Override
        public Path getAddressBookFilePath() {
            return failStub();
        }

        @Override
        public void setAddressBookFilePath(Path path) {
            failStub();
        }

        @Override
        public void addRecruit(Recruit recruit) {
            failStub();
        }

        @Override
        public void setAddressBook(ReadOnlyAddressBook newData) {
            failStub();
        }

        @Override
        public boolean hasRecruit(Recruit recruit) {
            return failStub();
        }

        @Override
        public void deleteRecruit(Recruit target) {
            failStub();
        }

        @Override
        public void setRecruit(Recruit target, Recruit editedRecruit) {
            failStub();
        }

        @Override
        public void sortRecruits(java.util.Comparator<Recruit> comparator) {
            failStub();
        }

        @Override
        public void updateFilteredRecruitList(java.util.function.Predicate<Recruit> predicate) {
            failStub();
        }

        @Override
        public java.util.Optional<Recruit> getFilteredRecruitByID(java.util.UUID id) {
            return failStub();
        }

        @Override
        public java.util.Optional<Recruit> getUnfilteredRecruitByID(java.util.UUID id) {
            return failStub();
        }

        @Override
        public boolean canRedoAddressBook() {
            return failStub();
        }

        @Override
        public String redoAddressBook() {
            return failStub();
        }

        @Override
        public javafx.collections.ObservableList<Recruit> getFilteredRecruitList() {
            return failStub();
        }

        @Override
        public void commitAddressBook(String command) {
            failStub();
        }

        @Override
        public String undoAddressBook() {
            return failStub();
        }

        @Override
        public boolean canUndoAddressBook() {
            return failStub();
        }

        @Override
        public void refreshFilteredRecruitList() {
            failStub();
        }
    }
}
