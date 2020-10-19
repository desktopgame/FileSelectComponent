/*
 * FileSelectComponent
 *
 * Copyright (c) 2020 desktopgame
 *
 * This software is released under the MIT License.
 * http://opensource.org/licenses/mit-license.php
 */
package jp.desktopgame.fsc;

import java.util.EventListener;

/**
 *
 * @author desktopgame
 */
public interface FilterUpdateListener extends EventListener {

    public void filterUpdate(FilterUpdateEvent e);
}
