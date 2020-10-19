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
import javax.swing.filechooser.FileFilter;

/**
 *
 * @author desktopgame
 */
public class AcceptAllFileFilter extends FileFilter {

    private String description;

    public AcceptAllFileFilter(String description) {
        this.description = description;
    }

    @Override
    public boolean accept(File f) {
        return true;
    }

    @Override
    public String getDescription() {
        return description;
    }

}
