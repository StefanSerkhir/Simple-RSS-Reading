package stefanserkhir.simplerssreading.ui.views.interfaces;

import java.util.List;

public interface NewsListView extends BaseView  {

    void createMenu(List<String> list);

    void showError(int message);
}
