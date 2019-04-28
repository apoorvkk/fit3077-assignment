package edu.monash.it.fit3077.vjak.view;

import edu.monash.it.fit3077.vjak.model.PatientModelInterface;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.HBox;

public class PatientListItemView implements JavaFXView {
    private Node rootNode;

    public PatientListItemView(PatientModelInterface model) {
        HBox cellNode = new HBox();

        CheckBox cb2 = new CheckBox(model.getName());
        cellNode.getChildren().add(cb2);

        this.rootNode = cellNode;
    }

    public Node getRootNode() {
        return this.rootNode;
    }
}
