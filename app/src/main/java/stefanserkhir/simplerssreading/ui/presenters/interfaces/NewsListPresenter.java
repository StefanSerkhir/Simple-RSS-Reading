package stefanserkhir.simplerssreading.ui.presenters.interfaces;

import android.view.Menu;

import stefanserkhir.simplerssreading.ui.views.interfaces.NewsListView;

public interface NewsListPresenter extends BasePresenter<NewsListView> {

    void onDataRequest();

    void onSelectingFilters(String filter);
}
