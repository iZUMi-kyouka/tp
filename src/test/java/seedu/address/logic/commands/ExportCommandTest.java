package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static seedu.address.testutil.TypicalRecruits.ALICE;
import static seedu.address.testutil.TypicalRecruits.BOB;

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
import seedu.address.testutil.RecruitBuilder;

public class ExportCommandTest {
    private Path tempFile;
    private ModelStubAcceptingExport modelStub;

    @BeforeEach
    public void setUp() throws Exception {
        modelStub = new ModelStubAcceptingExport();
        Path dataDir = Paths.get("data");
        Files.createDirectories(dataDir);
        tempFile = dataDir.resolve("test_recruits.csv");
    }

    @AfterEach
    public void tearDown() throws Exception {
        Files.deleteIfExists(tempFile);
    }

    @Test
    public void constructor_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new ExportCommand(null));
    }

    @Test
    public void execute_validFilePath_exportsSuccessfully() throws Exception {
        Path filePath = tempFile;
        ExportCommand command = new ExportCommand(filePath);

        CommandResult result = command.execute(modelStub);

        assertEquals(String.format(ExportCommand.MESSAGE_SUCCESS, filePath), result.getFeedbackToUser());
        List<String> lines = Files.readAllLines(filePath);
        assertEquals(modelStub.recruitsAdded.size() + 1, lines.size());
    }

    @Test
    public void execute_nullFilePath_usesUserPrefsPath() throws Exception {
        ModelStubAcceptingExport modelStub = new ModelStubAcceptingExport();
        Path newPath = Paths.get("data" , "test_recruits_2.csv");
        Files.createDirectories(newPath.getParent());
        modelStub.setDefaultExportPath(newPath);
        ExportCommand command = new ExportCommand();

        CommandResult result = command.execute(modelStub);

        assertEquals(String.format(ExportCommand.MESSAGE_SUCCESS, newPath), result.getFeedbackToUser());
        Files.deleteIfExists(newPath);
    }
    /**
     * A model stub that accepts recruits being exported.
     */
    private static class ModelStubAcceptingExport implements Model {
        final List<Recruit> recruitsAdded = new ArrayList<>();
        private Path defaultExportPath;

        public ModelStubAcceptingExport() {
            recruitsAdded.add(new RecruitBuilder(ALICE).build());
            recruitsAdded.add(new RecruitBuilder(BOB).build());
        }


        @Override
        public ReadOnlyAddressBook getAddressBook() {
            return new ReadOnlyAddressBook() {
                private final javafx.collections.ObservableList<Recruit> recruits =
                        javafx.collections.FXCollections.observableArrayList();

                {
                    if (recruits.isEmpty()) {
                        recruits.add(new RecruitBuilder(ALICE).build());
                        recruits.add(new RecruitBuilder(BOB).build());
                    }
                }

                @Override
                public javafx.collections.ObservableList<Recruit> getRecruitList() {
                    return recruits;
                }
            };
        }

        public void setDefaultExportPath(Path path) {
            this.defaultExportPath = path;
        }


        // all other Model methods throw AssertionError
        @Override
        public void setUserPrefs(seedu.address.model.ReadOnlyUserPrefs userPrefs) {
            throw new AssertionError();
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

        @Override
        public void setGuiSettings(seedu.address.commons.core.GuiSettings guiSettings) {
            throw new AssertionError();
        }

        @Override
        public seedu.address.commons.core.GuiSettings getGuiSettings() {
            throw new AssertionError();
        }

        @Override
        public Path getAddressBookFilePath() {
            throw new AssertionError();
        }

        @Override
        public void setAddressBookFilePath(Path path) {
            throw new AssertionError();
        }

        @Override
        public void addRecruit(Recruit recruit) {
            throw new AssertionError();
        }

        @Override
        public void setAddressBook(ReadOnlyAddressBook newData) {
            throw new AssertionError();
        }

        @Override
        public boolean hasRecruit(Recruit recruit) {
            throw new AssertionError();
        }

        @Override
        public void deleteRecruit(Recruit target) {
            throw new AssertionError();
        }

        @Override
        public void setRecruit(Recruit target, Recruit editedRecruit) {
            throw new AssertionError();
        }

        @Override
        public void sortRecruits() {
            throw new AssertionError();
        }

        @Override
        public void updateFilteredRecruitList(java.util.function.Predicate<Recruit> predicate) {
            throw new AssertionError();
        }

        @Override
        public java.util.Optional<Recruit> getFilteredRecruitByID(java.util.UUID id) {
            throw new AssertionError();
        }

        @Override
        public java.util.Optional<Recruit> getUnfilteredRecruitByID(java.util.UUID id) {
            throw new AssertionError();
        }
        @Override
        public boolean canRedoAddressBook() {
            throw new AssertionError();
        }

        @Override
        public String redoAddressBook() {
            throw new AssertionError();
        }

        @Override
        public javafx.collections.ObservableList<Recruit> getFilteredRecruitList() {
            throw new AssertionError();
        }

        @Override
        public void commitAddressBook(String command) {
            throw new AssertionError();
        }

        @Override
        public String undoAddressBook() {
            throw new AssertionError();
        }

        @Override
        public boolean canUndoAddressBook() {
            throw new AssertionError();
        }
    }
}

