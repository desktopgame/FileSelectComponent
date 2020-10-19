/*
 * FileSelectComponent
 *
 * Copyright (c) 2020 desktopgame
 *
 * This software is released under the MIT License.
 * http://opensource.org/licenses/mit-license.php
 */
package jp.desktopgame.fsc;

import java.util.EventObject;

/**
 *
 * @author desktopgame
 */
public class FilterUpdateEvent extends EventObject {

    public FilterUpdateEvent(FileInputPane fileInputPane) {
        super(fileInputPane);
    }

    @Override
    public FileInputPane getSource() {
        return (FileInputPane) super.getSource(); //To change body of generated methods, choose Tools | Templates.
    }

}
