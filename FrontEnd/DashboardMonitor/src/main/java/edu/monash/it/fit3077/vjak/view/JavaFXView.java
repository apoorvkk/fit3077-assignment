package edu.monash.it.fit3077.vjak.view;

import javafx.scene.Node;

/*
This interface is mainly used to get the root node of a view so it can be attached to the hosted view.
 */
interface JavaFXView {
    Node getRootNode();
}
