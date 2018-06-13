package com.karlpu.trackcode;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.ui.Messages;

public class TrackActionNote extends AnAction {

    @Override
    public void actionPerformed(AnActionEvent e) {
        // TODO: insert action logic here
        InputNote note = new InputNote();
        note.pack();
        note.setSize(400,200);
        note.setVisible(true);
    }
}
