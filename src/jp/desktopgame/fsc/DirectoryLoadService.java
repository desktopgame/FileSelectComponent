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
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javax.swing.SwingUtilities;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

/**
 *
 * @author desktopgame
 */
public class DirectoryLoadService implements Runnable {

    private DefaultTreeModel model;
    private DefaultMutableTreeNode node;
    private File dir;
    private ExecutorService executor;

    public DirectoryLoadService(DefaultTreeModel model, DefaultMutableTreeNode node, File dir) {
        this.model = model;
        this.node = node;
        this.dir = dir;
        this.executor = Executors.newFixedThreadPool(10);
    }

    @Override
    public void run() {
        load(node, dir);
    }

    private void load(DefaultMutableTreeNode node, File dir) {
        List<File> fileList = Arrays.asList(dir.listFiles()).stream().filter((e) -> e.isDirectory()).collect(Collectors.toList());
        //List<DefaultMutableTreeNode> nodes = new ArrayList<>();
        for (File file : fileList) {

            DefaultMutableTreeNode child = new DefaultMutableTreeNode();
            child.setUserObject(file);
            //   nodes.add(child);
            SwingUtilities.invokeLater(() -> {
                node.add(child);
                model.nodeStructureChanged(node);
            });
            load(child, file);
        }
        /*
        List<Future<?>> futures = new ArrayList<>();
        for (int i = 0; i < nodes.size(); i++) {
            DefaultMutableTreeNode A = nodes.get(i);
            File B = fileList.get(i);
            futures.add(executor.submit(() -> {
                load(A, B);
            }));
            if (futures.size() >= 3) {
                futures.forEach((e) -> {
                    try {
                        e.get();
                    } catch (InterruptedException | ExecutionException ex) {
                        Logger.getLogger(DirectoryLoadService.class.getName()).log(Level.SEVERE, null, ex);
                    }
                });
                futures.clear();
            }
        }*/
    }
}
