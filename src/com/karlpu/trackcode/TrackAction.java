package com.karlpu.trackcode;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.DataKeys;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.editor.CaretModel;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.SelectionModel;
import com.intellij.openapi.editor.event.SelectionEvent;
import com.intellij.openapi.editor.event.SelectionListener;
import com.intellij.openapi.ui.Messages;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.karlpu.trackcode.utils.PluginUtil;

import java.io.IOException;

public class TrackAction extends AnAction {
    private static String BASE_PATH = "";

    @Override
    public void actionPerformed(AnActionEvent e) {
        // TODO: insert action logic here
        PluginUtil.getInstance().init(e.getProject());
        if(PluginUtil.classNum == 0){
            String className = getClassName(e);
            PluginUtil.getInstance().writeTrackFile(className+"->");
            PluginUtil.getInstance().markFirstClassName(className);
            PluginUtil.classNum++;
            PluginUtil.note = false;
        }else if(PluginUtil.classNum == 1){
            PluginUtil.getInstance().writeTrackFile(getClassName(e));
            PluginUtil.getInstance().writeTrackFile(getMethodName(e));
            PluginUtil.classNum = 0;
            PluginUtil.note = true;
        }

    }



    public String getClassName(AnActionEvent e) {
        String className = "";
        PsiFile file = e.getData(PlatformDataKeys.PSI_FILE);
        for (PsiElement element : file.getChildren()) {
            if (element instanceof PsiClass) {
                PsiClass psiClass = (PsiClass) element;
                className = psiClass.getName();
                System.out.print("class name = " + psiClass.getName());
            }
        }
        return className;
    }

    public String getMethodName(AnActionEvent e) {
        String methodName = "";
        Editor editor = e.getData(DataKeys.EDITOR);
        if (editor != null) {
            //得到编辑器的光标类
            SelectionModel selectionModel = editor.getSelectionModel();
            selectionModel.selectWordAtCaret(false);
            methodName = selectionModel.getSelectedText();
        }
        return ":"+methodName+"()"+System.getProperty("line.separator");
    }



}
