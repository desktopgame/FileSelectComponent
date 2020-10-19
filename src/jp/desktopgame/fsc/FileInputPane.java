/*
 * FileSelectComponent
 *
 * Copyright (c) 2020 desktopgame
 *
 * This software is released under the MIT License.
 * http://opensource.org/licenses/mit-license.php
 */
package jp.desktopgame.fsc;

import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javax.swing.Box;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.EventListenerList;
import javax.swing.filechooser.FileFilter;

/**
 *
 * @author desktopgame
 */
public class FileInputPane extends JPanel {

    private JLabel fileNameLabel;
    private JTextField fileNameTextField;
    private DefaultComboBoxModel<String> filterComboBoxModel;
    private JComboBox<String> filterComboBox;
    private JButton approveButton, cancelButton;

    private GridBagLayout gbl = new GridBagLayout();
    private GridBagConstraints gbc = new GridBagConstraints();

    private boolean acceptAllFileFilterUsed;
    private FileFilter acceptAllFileFilter;
    private List<FileFilter> choosableFileFilters;
    private EventListenerList listenerList;

    public FileInputPane() {
        this.choosableFileFilters = new ArrayList<>();
        this.acceptAllFileFilterUsed = true;
        this.acceptAllFileFilter = new AcceptAllFileFilter(Messages.FILTER_ALL_FILE);
        this.listenerList = new EventListenerList();
        buildLayout();
        filterComboBox.addItemListener(new FilterSelectionHandler());
        updateFilterComboBox();
    }

    private void buildLayout() {
        setLayout(gbl);
        this.fileNameLabel = new JLabel(Messages.FILE_TEXT);
        this.fileNameTextField = new JTextField();
        this.filterComboBoxModel = new DefaultComboBoxModel<>();
        this.filterComboBox = new JComboBox<>(filterComboBoxModel);
        this.approveButton = new JButton(Messages.OPEN_BUTTON_TEXT);
        this.cancelButton = new JButton(Messages.CANCEL_BUTTON_TEXT);
        // 一行目
        gbc.weightx = 1.0;
        gbc.gridx++;
        gbc.anchor = GridBagConstraints.LINE_END;
        put(fileNameLabel);

        gbc.gridx++;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        put(fileNameTextField);

        gbc.gridx++;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridwidth = 1;
        gbc.weightx = 0;
        put(Box.createHorizontalStrut(10));

        gbc.gridx++;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridwidth = 4;
        gbc.weightx = 0;
        put(filterComboBox);

        gbc.gridx++;
        gbc.gridwidth = 1;
        put(Box.createHorizontalStrut(5));
        // 二行目
        gbc.gridx = 4;
        gbc.gridy++;
        gbc.fill = GridBagConstraints.VERTICAL;
        put(Box.createVerticalStrut(50));
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // 三行目
        gbc.gridwidth = 1;
        gbc.gridx = 3;
        gbc.gridy++;
        gbc.gridx++;
        put(approveButton);

        gbc.gridx++;
        put(Box.createHorizontalStrut(5));

        gbc.gridx++;
        put(cancelButton);

        gbc.gridx++;
        gbc.gridwidth = 1;
        put(Box.createHorizontalStrut(5));
        // 四行目
        gbc.gridy++;
        gbc.fill = GridBagConstraints.VERTICAL;
        put(Box.createVerticalStrut(5));
    }

    private void put(Component c) {
        gbl.setConstraints(c, gbc);
        add(c);
    }

    private void updateFilterComboBox() {
        choosableFileFilters.remove(acceptAllFileFilter);
        filterComboBoxModel.removeAllElements();
        if (choosableFileFilters.isEmpty() && !isAcceptAllFileFilterUsed()) {
            setAcceptAllFileFilterUsed(true);
        }
        for (FileFilter ff : choosableFileFilters) {
            filterComboBoxModel.addElement(ff.getDescription());
        }
        if (isAcceptAllFileFilterUsed()) {
            filterComboBoxModel.insertElementAt(acceptAllFileFilter.getDescription(), 0);
            choosableFileFilters.add(0, acceptAllFileFilter);
        }
        filterComboBox.setSelectedIndex(0);
    }

    public void addFilterUpdateListener(FilterUpdateListener listener) {
        listenerList.add(FilterUpdateListener.class, listener);
    }

    public void removeFilterUpdateListener(FilterUpdateListener listener) {
        listenerList.remove(FilterUpdateListener.class, listener);
    }

    protected void fireFilterUpdate(FilterUpdateEvent e) {
        for (FilterUpdateListener listener : listenerList.getListeners(FilterUpdateListener.class)) {
            listener.filterUpdate(e);
        }
    }

    public void addChoosableFileFilter(FileFilter filter) {
        choosableFileFilters.add(filter);
        updateFilterComboBox();
    }

    public boolean removeChoosableFileFilter(FileFilter f) {
        int i = choosableFileFilters.indexOf(f);
        updateFilterComboBox();
        return choosableFileFilters.remove(f);
    }

    public void resetChoosableFileFilters() {
        choosableFileFilters.clear();
        updateFilterComboBox();
    }

    public FileFilter getAcceptAllFileFilter() {
        return acceptAllFileFilter;
    }

    public boolean isAcceptAllFileFilterUsed() {
        return acceptAllFileFilterUsed;
    }

    public void setAcceptAllFileFilterUsed(boolean b) {
        this.acceptAllFileFilterUsed = b;
        updateFilterComboBox();
    }

    public boolean isAcceptFile(File file) {
        int i = filterComboBox.getSelectedIndex();
        if ((i == 0 && choosableFileFilters.isEmpty()) || i < 0) {
            return true;
        }
        return choosableFileFilters.get(i).accept(file);
    }

    public JButton getApproveButton() {
        return approveButton;
    }

    public JButton getCancelButton() {
        return cancelButton;
    }

    public JTextField getFileNameTextField() {
        return fileNameTextField;
    }

    private class FilterSelectionHandler implements ItemListener {

        @Override
        public void itemStateChanged(ItemEvent e) {
            fireFilterUpdate(new FilterUpdateEvent(FileInputPane.this));
        }
    }
}
