package com.karlpu.trackcode;

import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.DataKeys;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.SelectionModel;
import com.intellij.openapi.ui.Messages;
import com.karlpu.trackcode.utils.PluginUtil;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class InputNote extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JTextArea textArea1;

    public InputNote() {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);
        this.setLocationRelativeTo(null);//窗体居中显示
        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        });
    }

    private void onOK() {
        // add your code here
        String str = textArea1.getText();
        PluginUtil.getInstance().writeTrackFile("Note right of " + PluginUtil.FIRST_CLASS_NAME +": " + str + System.getProperty("line.separator"));
        dispose();
    }



}
