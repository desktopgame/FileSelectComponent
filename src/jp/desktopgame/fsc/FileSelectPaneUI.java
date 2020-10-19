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
import java.util.List;
import javax.swing.plaf.ComponentUI;

/**
 *
 * @author desktopgame
 */
public abstract class FileSelectPaneUI extends ComponentUI {

    public abstract File getSelectedFile();

    public abstract List<File> getSelectedFiles();
}
