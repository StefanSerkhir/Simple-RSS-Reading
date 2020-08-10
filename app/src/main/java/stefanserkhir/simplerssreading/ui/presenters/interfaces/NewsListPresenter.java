package stefanserkhir.simplerssreading.ui.presenters.interfaces;

import stefanserkhir.simplerssreading.ui.views.interfaces.NewsListView;
import stefanserkhir.simplerssreading.ui.views.interfaces.RepositoryItemView;

public interface NewsListPresenter extends BasePresenter<NewsListView> {

    void onDataRequest();

    void onMenuRequest();

    void onBindRepositoryItemViewAtPosition(RepositoryItemView item, int position);

    int getRepositoryItemsCount();

    void onSelectingFilter(String filter);

    void onResettingFilter();
}
