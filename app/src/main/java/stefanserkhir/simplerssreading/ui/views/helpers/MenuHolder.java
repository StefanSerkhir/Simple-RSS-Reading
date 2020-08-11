package stefanserkhir.simplerssreading.ui.views.helpers;

import android.view.Menu;
import android.view.MenuItem;

import androidx.core.view.MenuCompat;

import java.util.List;

import stefanserkhir.simplerssreading.R;
import stefanserkhir.simplerssreading.core.MenuGroups;

public class MenuHolder {
    private static Menu sMenu;
    private static MenuItem sPreviousItem;

    public static void init(Menu menu) {
        sMenu = menu;
    }

    public static void createMenu(List<String> list) {
        if (sMenu != null) {
            if (sMenu.hasVisibleItems()) {
                sMenu.removeItem(0);
            }

            sMenu.addSubMenu(MenuGroups.ROOT_GROUP.ordinal(), 0, 0, R.string.filter_news)
                    .setIcon(R.drawable.ic_filter);
            sMenu.getItem(MenuGroups.ROOT_GROUP.ordinal())
                    .setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);

            sMenu.getItem(0).getSubMenu()
                    .add(MenuGroups.RESET_GROUP.ordinal(), 0, 0, R.string.reset_filter)
                    .setIcon(R.drawable.ic_reset_filter);

            for (int i = 0; i < list.size(); i++) { // List.forEach(T -> {}) required API lvl 24
                sMenu.getItem(0).getSubMenu() // add(groupId, itemId, order, title)
                        .add(MenuGroups.CATEGORY_GROUP.ordinal(), i + 1, i + 1, list.get(i));
            }

            MenuCompat.setGroupDividerEnabled(sMenu, true);
        }
    }

    public static String selectItem(MenuItem item) {
        if (item.getGroupId() == MenuGroups.ROOT_GROUP.ordinal()) {
            return null;
        } else {
            if (sPreviousItem != null) {
                sPreviousItem.setEnabled(true);
            }

            if (item.getGroupId() == MenuGroups.RESET_GROUP.ordinal()) {
                return "";
            }

            item.setEnabled(false);
            sPreviousItem = item;

            return item.getTitle().toString();
        }
    }
}
