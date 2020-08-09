package stefanserkhir.simplerssreading.ui.presenters.interfaces;

import stefanserkhir.simplerssreading.ui.views.interfaces.NewsListView;
import stefanserkhir.simplerssreading.ui.views.interfaces.RepositoryItemView;

public interface NewsListPresenter extends BasePresenter<NewsListView> {

    void onDataRequest();

    void onBindRepositoryItemViewAtPosition(RepositoryItemView item, int position);

    int getRepositoryItemsCount();

    void onSelectingFilters(String filter);
}
