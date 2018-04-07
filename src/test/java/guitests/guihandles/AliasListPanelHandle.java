package guitests.guihandles;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import javafx.scene.Node;
import javafx.scene.control.ListView;
import seedu.address.model.alias.Alias;

/**
 * Provides a handle for {@code AliasListPanel} containing the list of {@code Alias}.
 */
public class AliasListPanelHandle extends NodeHandle<Node> {

    public static final String ALIAS_LIST_PANEL_ID = "#aliasListContainer";
    private static final String LIST_VIEW_FIELD_ID = "#aliasListView";
    private static final String CARD_PANE_ID = "#aliasCardPane";

    private final ListView<Alias> aliasListView;

    public AliasListPanelHandle(Node aliasListPanelNode) {
        super(aliasListPanelNode);
        aliasListView = getChildNode(LIST_VIEW_FIELD_ID);
    }

    /**
     * Returns a handle to the selected {@code AliasCardHandle}.
     * A maximum of 1 item can be selected at any time.
     * @throws AssertionError if no card is selected, or more than 1 card is selected.
     */
    public AliasCardHandle getHandleToSelectedCard() {
        List<Alias> aliasList = aliasListView.getSelectionModel().getSelectedItems();

        if (aliasList.size() != 1) {
            throw new AssertionError("Alias list size expected 1.");
        }

        return getAllCardNodes().stream()
                .map(AliasCardHandle::new)
                .filter(handle -> handle.equals(aliasList.get(0)))
                .findFirst()
                .orElseThrow(IllegalStateException::new);
    }

    /**
     * Navigates the listview to display and select {@code alias}.
     */
    public void navigateToCard(Alias alias) {
        if (!aliasListView.getItems().contains(alias)) {
            throw new IllegalArgumentException("Alias does not exist.");
        }

        guiRobot.interact(() -> aliasListView.scrollTo(alias));
        guiRobot.pauseForHuman();
    }

    /**
     * Navigates the listview to {@code index}.
     */
    public void navigateToCard(int index) {
        if (index < 0 || index >= aliasListView.getItems().size()) {
            throw new IllegalArgumentException("Index is out of bounds.");
        }

        guiRobot.interact(() -> aliasListView.scrollTo(index));
        guiRobot.pauseForHuman();
    }

    /**
     * Selects the {@code AliasCard} at {@code index} in the list.
     */
    public void select(int index) {
        aliasListView.getSelectionModel().select(index);
    }

    /**
     * Returns the alias card handle of an alias associated with the {@code index} in the list.
     */
    public Optional<AliasCardHandle> getAliasCardHandle(int index) {
        return getAllCardNodes().stream()
                .map(AliasCardHandle::new)
                .filter(handle -> handle.equals(getAlias(index)))
                .findFirst();
    }

    private Alias getAlias(int index) {
        return aliasListView.getItems().get(index);
    }

    /**
     * Returns all card nodes in the scene graph.
     * Card nodes that are visible in the listview are definitely in the scene graph, while some nodes that are not
     * visible in the listview may also be in the scene graph.
     */
    private Set<Node> getAllCardNodes() {
        return guiRobot.lookup(CARD_PANE_ID).queryAll();
    }

    public boolean isVisible() {
        return getRootNode().isVisible();
    }

    /**
     * Returns the size of the list.
     */
    public int getListSize() {
        return aliasListView.getItems().size();
    }
}
