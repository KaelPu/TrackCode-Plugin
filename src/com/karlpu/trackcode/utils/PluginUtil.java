package com.karlpu.trackcode.utils;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.LocalFileSystem;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiManager;
import com.intellij.psi.xml.XmlDocument;

import java.io.File;
import java.io.IOException;

public class PluginUtil {

    public static final String TRACK_DIR_NAME = "track";
    public static final String TRACK_MARKDOWN_NAME = "track.md";

    public static int classNum = 0;


    Project project;

    private static PluginUtil ourInstance = new PluginUtil();

    public static PluginUtil getInstance() {
        return ourInstance;
    }

    private PluginUtil() {
    }

    public void init(Project pro){
        this.project = pro;
    }


    /**
     * 获取App对应的包名根目录
     */
    public VirtualFile getAppPackageBaseDir() {
        String path = project.getBasePath() + File.separator +
                "app" + File.separator +
                "src" + File.separator +
                "main" + File.separator +
                "java" + File.separator +
                getAppPackageName().replace(".", File.separator);
        return LocalFileSystem.getInstance().findFileByPath(path);
    }

    // 获得track File
    public VirtualFile getTrackPluginFile(){
        VirtualFile trackFile = null;
        VirtualFile baseFile = LocalFileSystem.getInstance().findFileByPath(project.getBasePath());
        VirtualFile trackDir = baseFile.findChild(TRACK_DIR_NAME);
        if(trackDir == null){
            try {
                trackDir = baseFile.createChildDirectory(null,TRACK_DIR_NAME);
                trackFile = trackDir.createChildData(null,TRACK_MARKDOWN_NAME);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else{
            trackFile = trackDir.findChild(TRACK_MARKDOWN_NAME);
            if(trackFile == null){
                try {
                    trackFile = trackDir.createChildData(null,TRACK_MARKDOWN_NAME);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return trackFile;
    }

    public PsiFile getManifestFile() {
        String path = project.getBasePath() + File.separator +
                "app" + File.separator +
                "src" + File.separator +
                "main" + File.separator +
                "AndroidManifest.xml";
        VirtualFile virtualFile = LocalFileSystem.getInstance().findFileByPath(path);
        if(virtualFile == null) return null;
        return PsiManager.getInstance(project).findFile(virtualFile);
    }

    public String getAppPackageName() {
        PsiFile manifestFile = getManifestFile();
        XmlDocument xml = (XmlDocument) manifestFile.getFirstChild();
        return xml.getRootTag().getAttribute("package").getValue();
    }

    public String getFilePackageName(VirtualFile dir) {
        if(!dir.isDirectory()) {
            // 非目录的取所在文件夹路径
            dir = dir.getParent();
        }
        String path = dir.getPath().replace("/", ".");
        String preText = "src.main.java";
        int preIndex = path.indexOf(preText) + preText.length() + 1;
        path = path.substring(preIndex);
        return path;
    }

    public void writeTrackFile(String str) {
        if (str != null) {
            VirtualFile trackFile = PluginUtil.getInstance().getTrackPluginFile();
            if (trackFile != null) {
                try {
                    String oldStr = new String(trackFile.contentsToByteArray());
                    String newStr = oldStr + str;
                    trackFile.setBinaryContent(newStr.getBytes());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


}
