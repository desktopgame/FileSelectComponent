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

/**
 *
 * @author desktopgame
 */
public enum FileSelectionMode {
    FILE_ONLY() {
        @Override
        public boolean accept(File f) {
            return f.isFile();
        }
    },
    DIRECTORY_ONLY {
        @Override
        public boolean accept(File f) {
            return f.isDirectory();
        }
    },
    FILE_OR_DIRECTORY {
        @Override
        public boolean accept(File f) {
            return true;
        }
    };

    public abstract boolean accept(File f);
}
