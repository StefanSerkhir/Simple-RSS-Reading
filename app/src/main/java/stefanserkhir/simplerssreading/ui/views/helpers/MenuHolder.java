package stefanserkhir.simplerssreading.ui.views.helpers;

import android.view.Menu;
import android.view.MenuItem;

import androidx.core.view.MenuCompat;

import java.util.List;

import stefanserkhir.simplerssreading.R;

public class MenuHolder {
    private static Menu sMenu;
    private static MenuItem sPreviousItem;
    private static final int CATEGORY_GROUP = 10;
    private static final int RESET_GROUP = 1;
    private static final int ROOT_GROUP = 0;

    public static void init(Menu menu) {
        sMenu = menu;
    }

    public static void createMenu(List<String> list) {
        if (sMenu != null) {
            if (sMenu.hasVisibleItems()) {
                sMenu.removeItem(0);
            }

            sMenu.addSubMenu(ROOT_GROUP, 0, 0, R.string.filter_news)
                    .setIcon(R.drawable.ic_filter);
            sMenu.getItem(ROOT_GROUP).setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);

            for (int i = 0; i < list.size(); i++) { // List.forEach(T -> {}) required API lvl 24
                sMenu.getItem(0).getSubMenu() // add(groupId, itemId, order, title)
                        .add(CATEGORY_GROUP, i + 1, i + 1, list.get(i));
            }

            sMenu.getItem(0).getSubMenu()
                    .add(RESET_GROUP, list.size() + 1, list.size() + 1, R.string.reset_filter)
                    .setIcon(R.drawable.ic_reset_filter);

            MenuCompat.setGroupDividerEnabled(sMenu, true);
        }
    }

    public static String selectItem(MenuItem item) {
        if (item.getGroupId() == ROOT_GROUP) {
            return null;
        } else {
            if (sPreviousItem != null) {
                sPreviousItem.setEnabled(true);
            }

            if (item.getGroupId() == RESET_GROUP) {
                return "";
            }

            item.setEnabled(false);
            sPreviousItem = item;

            return item.getTitle().toString();
        }
    }
}
