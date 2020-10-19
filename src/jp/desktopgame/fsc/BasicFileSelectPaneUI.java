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
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.text.CharacterIterator;
import java.text.SimpleDateFormat;
import java.text.StringCharacterIterator;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTree;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.filechooser.FileSystemView;
import javax.swing.table.DefaultTableModel;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;

/**
 *
 * @author desktopgame
 */
public class BasicFileSelectPaneUI extends FileSelectPaneUI {

    private static final FileSystemView fsv = FileSystemView.getFileSystemView();
    private FileSelectPane fsd;
    private JPanel root;

    private DefaultComboBoxModel<File> comboBoxModel;
    private JComboBox<File> comboBox;

    private DefaultMutableTreeNode treeRoot;
    private DefaultTreeModel treeModel;
    private JTree tree;

    private JSplitPane treeTableSplit;

    private static final String[] tableColumns = new String[]{"名前", "更新日時", "種類", "サイズ"};
    private DefaultTableModel tableModel;
    private JTable table;

    private List<File> fileList;

    private PropertyChangeHandler propChangeHandler;
    private FilterUpdateHandler filterUpdateHandler;

    public BasicFileSelectPaneUI() {
        this.propChangeHandler = new PropertyChangeHandler();
        this.filterUpdateHandler = new FilterUpdateHandler();
    }

    @Override
    public void installUI(JComponent c) {
        this.fsd = (FileSelectPane) c;
        this.root = new JPanel(new BorderLayout());
        this.comboBoxModel = new DefaultComboBoxModel<>();
        this.comboBox = new JComboBox<>(comboBoxModel);
        this.treeRoot = new DefaultMutableTreeNode();
        this.treeModel = new DefaultTreeModel(treeRoot);
        this.tree = new JTree(treeModel);
        this.tableModel = createTableModel();
        this.table = new JTable(tableModel);
        this.treeTableSplit = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, new JScrollPane(tree), new JScrollPane(table));
        this.fileList = new ArrayList<>();
        fsd.addPropertyChangeListener(propChangeHandler);
        fsd.getInputPane().addFilterUpdateListener(filterUpdateHandler);
        comboBox.addItemListener(new ComboBoxSelectionHandler());
        comboBox.setRenderer(new FileListCellRenderer());
        table.getSelectionModel().addListSelectionListener(new TableSelectionHandler());
        table.setSelectionMode(fsd.isMultiSelectionEnabled() ? ListSelectionModel.MULTIPLE_INTERVAL_SELECTION : ListSelectionModel.SINGLE_SELECTION);
        table.getColumnModel().getColumn(0).setCellRenderer(new FileTableCellRenderer());
        tree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
        tree.getSelectionModel().addTreeSelectionListener(new TreeSelectionHandler());
        tree.setEditable(false);
        tree.setCellRenderer(new FileTreeCellRenderer());
        root.add(comboBox, BorderLayout.NORTH);
        root.add(treeTableSplit, BorderLayout.CENTER);
        fsd.setLayout(new BorderLayout());
        fsd.add(root, BorderLayout.CENTER);

        loadDir(fsd.getCurrentDirectory());
    }

    private void loadDir(File dir) {
        comboBoxModel.removeAllElements();
        // ルートまでを表示
        File cdir = dir;
        while (cdir != null) {
            comboBoxModel.addElement(cdir);
            cdir = cdir.toPath().toFile().getParentFile();
        }
        loadTable(dir);
        loadDirTree(dir);
    }

    private void loadTable(File dir) {
        while (tableModel.getRowCount() > 0) {
            tableModel.removeRow(0);
        }
        fileList.clear();
        for (File child : dir.listFiles()) {
            if (!fsd.getInputPane().isAcceptFile(child) || !fsd.getFileSelectionMode().accept(child)) {
                continue;
            }
            long lastModified = child.lastModified();
            String pattern = "yyyy-MM-dd hh:mm aa";
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
            Date lastModifiedDate = new Date(lastModified);
            tableModel.addRow(new Object[]{child, simpleDateFormat.format(lastModifiedDate), fsv.getSystemTypeDescription(child), humanReadableByteCountSI(child.length())});
            fileList.add(child);
        }
    }

    private void loadDirTree(File dir) {
        treeRoot.removeAllChildren();
        treeRoot.setUserObject(dir);
        for (File file : dir.listFiles()) {
            if (file.isFile()) {
                continue;
            }
            treeRoot.add(new DefaultMutableTreeNode(file));
        }
        treeModel.nodeStructureChanged(treeRoot);
    }

    private static String humanReadableByteCountSI(long bytes) {
        if (-1000 < bytes && bytes < 1000) {
            return bytes + " B";
        }
        CharacterIterator ci = new StringCharacterIterator("kMGTPE");
        while (bytes <= -999_950 || bytes >= 999_950) {
            bytes /= 1000;
            ci.next();
        }
        return String.format("%.1f %cB", bytes / 1000.0, ci.current());
    }

    private DefaultTableModel createTableModel() {
        return new DefaultTableModel(tableColumns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
    }

    @Override
    public void uninstallUI(JComponent c) {
        fsd.removePropertyChangeListener(propChangeHandler);
        fsd.getInputPane().removeFilterUpdateListener(filterUpdateHandler);
        fsd.removeAll();
        fsd.revalidate();
        this.fsd = null;
    }

    @Override
    public File getSelectedFile() {
        List<File> f = getSelectedFiles();
        if (f.isEmpty()) {
            return null;
        }
        return f.get(0);
    }

    @Override
    public List<File> getSelectedFiles() {
        int[] rows = table.getSelectedRows();
        List<File> r = new ArrayList<>();
        for (int i = 0; i < rows.length; i++) {
            r.add(fileList.get(rows[i]));
        }
        return r;
    }

    private String getSelectedFileText() {
        List<File> fl = getSelectedFiles();
        if (fl.isEmpty()) {
            return "";
        }
        if (fl.size() == 1) {
            return fl.get(0).getName();
        }
        return String.join(" ", getSelectedFiles().stream().map((e) -> "\"" + e.getName() + "\"").collect(Collectors.toList()));
    }

    private class ComboBoxSelectionHandler implements ItemListener {

        @Override
        public void itemStateChanged(ItemEvent e) {
            int i = comboBox.getSelectedIndex();
            File f = comboBoxModel.getElementAt(i);
            loadTable(f);
            loadDirTree(f);
        }
    }

    private class TableSelectionHandler implements ListSelectionListener {

        @Override
        public void valueChanged(ListSelectionEvent lse) {
            fsd.getInputPane().getFileNameTextField().setText(getSelectedFileText());
        }
    }

    private class TreeSelectionHandler implements TreeSelectionListener {

        @Override
        public void valueChanged(TreeSelectionEvent e) {
            DefaultMutableTreeNode selectedNode
                    = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
            if (selectedNode == null) {
                return;
            }
            File dir = (File) selectedNode.getUserObject();
            if (dir != null && (selectedNode.getChildCount() == 0 || isNetworkDrive(dir))) {
                for (File file : dir.listFiles()) {
                    if (file.isFile()) {
                        continue;
                    }
                    selectedNode.add(new DefaultMutableTreeNode(file));
                }
                treeModel.nodeStructureChanged(selectedNode);
                SwingUtilities.invokeLater(() -> {
                    tree.expandPath(new TreePath(selectedNode.getPath()));
                });
            }
            loadTable(dir);
        }

        private boolean isNetworkDrive(File file) {
            return file.getName().contains("OneDrive");
        }
    }

    private class FilterUpdateHandler implements FilterUpdateListener {

        @Override
        public void filterUpdate(FilterUpdateEvent e) {
            loadTable(fsd.getCurrentDirectory());
        }
    }

    private class PropertyChangeHandler implements PropertyChangeListener {

        @Override
        public void propertyChange(PropertyChangeEvent evt) {
            String name = evt.getPropertyName();
            Object o = evt.getOldValue();
            Object n = evt.getNewValue();
            if (name.equals("multiSelectionEnabled")) {
                table.setSelectionMode(((boolean) n) ? ListSelectionModel.MULTIPLE_INTERVAL_SELECTION : ListSelectionModel.SINGLE_SELECTION);
            } else if (name.equals("currentDirectory")) {
                loadDir((File) n);
            }
        }
    }
}
