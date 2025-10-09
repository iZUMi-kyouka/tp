package seedu.address.ui;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.recruit.Recruit;

/**
 * Panel containing the list of recruits.
 */
public class RecruitListPanel extends UiPart<Region> {
    private static final String FXML = "RecruitListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(RecruitListPanel.class);

    @FXML
    private ListView<Recruit> recruitListView;

    /**
     * Creates a {@code PersonListPanel} with the given {@code ObservableList}.
     */
    public RecruitListPanel(ObservableList<Recruit> recruitList) {
        super(FXML);
        recruitListView.setItems(recruitList);
        recruitListView.setCellFactory(listView -> new RecruitListViewCell());
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Person} using a {@code PersonCard}.
     */
    class RecruitListViewCell extends ListCell<Recruit> {
        @Override
        protected void updateItem(Recruit recruit, boolean empty) {
            super.updateItem(recruit, empty);

            if (empty || recruit == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new RecruitCard(recruit, getIndex() + 1).getRoot());
            }
        }
    }

}
