package ks3.oc.dialogs;

import ks3.oc.Sender;
import ks3.oc.res.ResourceManager;
import ks3.oc.swing.SwingMainWindow;

public interface NewGameConfirmation {
    void open(ResourceManager resourceManager, Sender send, SwingMainWindow own);
}
