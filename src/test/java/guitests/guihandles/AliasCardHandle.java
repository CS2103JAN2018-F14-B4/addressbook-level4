package guitests.guihandles;

import javafx.scene.Node;
import javafx.scene.control.Label;
import seedu.address.model.alias.Alias;

/**
 * Provides a handle to an alias card in the alias list panel.
 */
public class AliasCardHandle extends NodeHandle<Node> {
    private static final String ID_FIELD_ID = "#id";
    private static final String NAME_FIELD_ID = "#name";
    private static final String PREFIX_FIELD_ID = "#prefix";
    private static final String ARGUMENTS_FIELD_ID = "#arguments";

    private final Label idLabel;
    private final Label nameLabel;
    private final Label prefixLabel;
    private final Label argumentsLabel;

    public AliasCardHandle(Node cardNode) {
        super(cardNode);

        this.idLabel = getChildNode(ID_FIELD_ID);
        this.nameLabel = getChildNode(NAME_FIELD_ID);
        this.prefixLabel = getChildNode(PREFIX_FIELD_ID);
        this.argumentsLabel = getChildNode(ARGUMENTS_FIELD_ID);
    }

    public String getId() {
        return idLabel.getText();
    }

    private String getName() {
        return nameLabel.getText();
    }

    private String getPrefix() {
        return prefixLabel.getText();
    }

    private String getArguments() {
        return argumentsLabel.getText();
    }

    /**
     * Returns true if all the fields of this handle are the same as {@code otherHandle}.
     */
    public boolean equals(AliasCardHandle otherHandle) {
        return getId().equals(otherHandle.getId())
                && getName().equals(otherHandle.getName())
                && getPrefix().equals(otherHandle.getPrefix())
                && getArguments().equals(otherHandle.getArguments());
    }

    /**
     * Returns true if this handle contains {@code alias}.
     */
    public boolean equals(Alias alias) {
        return getName().equals(alias.getName())
                && getPrefix().equals(alias.getPrefix())
                && getArguments().equals(alias.getArguments());
    }
}
