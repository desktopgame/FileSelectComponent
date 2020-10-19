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
import java.util.function.Predicate;
import javax.swing.filechooser.FileFilter;

/**
 *
 * @author desktopgame
 */
public class GenericFileFilter extends FileFilter {

    private String desc;
    private Predicate<File> pred;

    public GenericFileFilter(String desc, Predicate<File> pred) {
        this.desc = desc;
        this.pred = pred;
    }

    @Override
    public boolean accept(File f) {
        return pred.test(f);
    }

    @Override
    public String getDescription() {
        return desc;
    }

}
