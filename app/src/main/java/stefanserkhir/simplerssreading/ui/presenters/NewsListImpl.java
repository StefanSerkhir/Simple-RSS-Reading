package stefanserkhir.simplerssreading.ui.presenters;

import stefanserkhir.simplerssreading.ui.presenters.interfaces.NewsListPresenter;
import stefanserkhir.simplerssreading.ui.views.interfaces.BaseView;
import stefanserkhir.simplerssreading.ui.views.interfaces.NewsListView;

public class NewsListImpl implements NewsListPresenter {
    private NewsListView mView;

    @Override
    public NewsListPresenter onAttachView(BaseView view) {
        mView = (NewsListView) view;
        return this;
    }

    @Override
    public void onDetachView() {
        mView = null;
    }

    @Override
    public void onDataRequest() {

    }

    @Override
    public void onSelectingFilters(String filter) {

    }
}
