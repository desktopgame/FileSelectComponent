/*
 * FileSelectComponent
 *
 * Copyright (c) 2020 desktopgame
 *
 * This software is released under the MIT License.
 * http://opensource.org/licenses/mit-license.php
 */
package jp.desktopgame.fsc;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.io.File;
import java.util.List;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileSystemView;

/**
 * ファイル選択ダイアログのためのコンポーネントです.
 *
 * @author desktopgame
 */
public class FileSelectPane extends JComponent {

    private FileSystemView fsv = FileSystemView.getFileSystemView();
    private JDialog dialog;
    private FileInputPane inputPane;
    private FileDialogResult result;

    private File currentDirectory;
    private boolean multiSelectionEnabled;
    private FileSelectionMode fileSelectionMode;

    private static final String uiClassID = "FileSelectComponentUI";

    public FileSelectPane() {
        this.currentDirectory = fsv.getHomeDirectory();
        this.fileSelectionMode = FileSelectionMode.FILE_OR_DIRECTORY;
        updateUI();
    }

    public void setUI(FileSelectPaneUI ui) {
        super.setUI(ui);
    }

    public FileSelectPaneUI getUI() {
        return (FileSelectPaneUI) ui;
    }

    @Override
    public void updateUI() {
        setUI(new BasicFileSelectPaneUI());
    }

    @Override
    public String getUIClassID() {
        return uiClassID;
    }

    private void onApprove(ActionEvent e) {
        this.result = FileDialogResult.OK;
        dialog.setVisible(false);
    }

    private void onCancel(ActionEvent e) {
        this.result = FileDialogResult.CANCELED;
        dialog.setVisible(false);
    }

    public void createDialog() {
        if (dialog != null) {
            return;
        }
        dialog = new JDialog();

        dialog.setLayout(new BorderLayout());
        dialog.setDefaultCloseOperation(JDialog.HIDE_ON_CLOSE);
        dialog.setModal(true);
        dialog.add(this, BorderLayout.CENTER);
        dialog.add(getInputPane(), BorderLayout.SOUTH);
        dialog.pack();
    }

    public FileInputPane getInputPane() {
        if (inputPane == null) {
            inputPane = new FileInputPane();
            inputPane.getApproveButton().addActionListener(this::onApprove);
            inputPane.getCancelButton().addActionListener(this::onCancel);
        }
        return inputPane;
    }

    public FileDialogResult getResult() {
        return result;
    }

    //
    // JFileChooserと同じインターフェイスを提供するためのメソッド
    //
    public void showOpenDialog() {
        this.result = FileDialogResult.CLOSED;
        createDialog();
        getInputPane().getApproveButton().setText(Messages.OPEN_BUTTON_TEXT);
        dialog.setTitle(Messages.OPEN_DIALOG_TITLE);
        dialog.pack();
        dialog.setLocationRelativeTo(null);
        dialog.setVisible(true);
    }

    public void showSaveDialog() {
        this.result = FileDialogResult.CLOSED;
        createDialog();
        getInputPane().getApproveButton().setText(Messages.SAVE_BUTTON_TEXT);
        dialog.setTitle(Messages.SAVE_DIALOG_TITLE);
        dialog.pack();
        dialog.setLocationRelativeTo(null);
        dialog.setVisible(true);
    }

    public void addChoosableFileFilter(FileFilter filter) {
        getInputPane().addChoosableFileFilter(filter);
    }

    public boolean removeChoosableFileFilter(FileFilter f) {
        return getInputPane().removeChoosableFileFilter(f);
    }

    public void resetChoosableFileFilters() {
        getInputPane().resetChoosableFileFilters();
    }

    public FileFilter getAcceptAllFileFilter() {
        return getInputPane().getAcceptAllFileFilter();
    }

    public boolean isAcceptAllFileFilterUsed() {
        return getInputPane().isAcceptAllFileFilterUsed();
    }

    public void setAcceptAllFileFilterUsed(boolean b) {
        getInputPane().setAcceptAllFileFilterUsed(b);
    }

    public void setCurrentDirectory(File currentDirectory) {
        File a = this.currentDirectory;
        this.currentDirectory = currentDirectory;
        firePropertyChange("currentDirectory", a, currentDirectory);
    }

    public File getCurrentDirectory() {
        return currentDirectory;
    }

    public void setMultiSelectionEnabled(boolean b) {
        boolean a = this.multiSelectionEnabled;
        this.multiSelectionEnabled = b;
        firePropertyChange("multiSelectionEnabled", a, multiSelectionEnabled);
    }

    public boolean isMultiSelectionEnabled() {
        return multiSelectionEnabled;
    }

    public void setFileSelectionMode(FileSelectionMode fileSelectionMode) {
        FileSelectionMode a = this.fileSelectionMode;
        this.fileSelectionMode = fileSelectionMode;
        firePropertyChange("fileSelectionMode", a, fileSelectionMode);
    }

    public FileSelectionMode getFileSelectionMode() {
        return fileSelectionMode;
    }

    public File getSelectedFile() {
        return getUI().getSelectedFile();
    }

    public List<File> getSelectedFiles() {
        return getUI().getSelectedFiles();
    }
}
