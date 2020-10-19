/*
 * FileSelectComponent
 *
 * Copyright (c) 2020 desktopgame
 *
 * This software is released under the MIT License.
 * http://opensource.org/licenses/mit-license.php
 */
package jp.desktopgame.fsc;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javax.swing.SwingWorker;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

/**
 *
 * @author desktopgame
 */
/* package private */ class DirectoryLoadWorker extends SwingWorker<List<DefaultMutableTreeNode>, DefaultMutableTreeNode> {

    private DefaultTreeModel model;
    private DefaultMutableTreeNode node;
    private File dir;
    private List<DefaultMutableTreeNode> list;

    public DirectoryLoadWorker(DefaultTreeModel model, DefaultMutableTreeNode node, File dir) {
        this.model = model;
        this.node = node;
        this.dir = dir;
        this.list = new ArrayList<>();
    }

    @Override
    protected List<DefaultMutableTreeNode> doInBackground() throws Exception {
        traverse(node, dir, 0);
        return list;
    }

    private void traverse(DefaultMutableTreeNode node, File dir, int d) {
        for (File file : dir.listFiles()) {
            if (file.isFile()) {
                continue;
            }
            DefaultMutableTreeNode child = new DefaultMutableTreeNode(file);
            traverse(child, file, d + 1);
            if (d == 0) {
                list.add(child);
                publish(child);
            } else {
                node.add(child);
            }
        }
    }

    @Override
    protected void process(List<DefaultMutableTreeNode> chunks) {
        chunks.forEach(node::add);
        model.nodeStructureChanged(node);
    }

    @Override
    protected void done() {
        super.done(); //To change body of generated methods, choose Tools | Templates.
    }

}
