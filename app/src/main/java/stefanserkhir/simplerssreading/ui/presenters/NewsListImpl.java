package stefanserkhir.simplerssreading.ui.presenters;

import java.util.List;

import stefanserkhir.simplerssreading.data.local.model.NewsItem;
import stefanserkhir.simplerssreading.data.repository.RepositoryImpl;
import stefanserkhir.simplerssreading.data.repository.interfaces.Repository;
import stefanserkhir.simplerssreading.ui.presenters.interfaces.NewsListPresenter;
import stefanserkhir.simplerssreading.ui.views.interfaces.RepositoryItemView;
import stefanserkhir.simplerssreading.ui.views.interfaces.NewsListView;

public class NewsListImpl implements NewsListPresenter, RepositoryImpl.RepositoryCallback {
    private NewsListView mView;
    private Repository mRepository;
    private String mFilter;

    @Override
    public void onAttachView(NewsListView view) {
        mView = view;
        mRepository = new RepositoryImpl(this);
        mRepository.getLocalNewsList();
    }

    @Override
    public void onDetachView() {
        mView = null;
    }

    @Override
    public void onDataRequest() {
        mRepository.refreshNewsList();
    }

    @Override
    public void onMenuRequest() {
        mView.createMenu(mRepository.getCategoriesList());
    }

    @Override
    public void onBindRepositoryItemViewAtPosition(RepositoryItemView itemView, int position) {
        NewsItem newsItem;
        if (mFilter == null) {
            newsItem = mRepository.getNewsItem(position);
        } else {
            newsItem = mRepository.getNewsItem(position, mFilter);
        }
        itemView.setTitle(newsItem.getTitle());
        itemView.setDate(newsItem.getDate());
    }

    @Override
    public int getRepositoryItemsCount() {
        if (mFilter == null) {
            return mRepository.getNewsCount();
        } else {
            return mRepository.getNewsCount(mFilter);
        }
    }

    @Override
    public void onSelectingFilter(String filter) {
        mFilter = filter;
        mView.updateUI();
    }

    @Override
    public void onResettingFilter() {
        mFilter = null;
        mView.updateUI();
    }

    @Override
    public void onRepositoryResponse(List<NewsItem> list, List<String> categoriesList) {
        mView.updateUI();
        mView.createMenu(categoriesList);
    }

    @Override
    public void onRepositoryFailure(int errorType) {
        switch (errorType) {
            case 0:
                mView.showError("There are no news in the feed");
                break;
            case 1:
                mView.showError("Response was unsuccessful");
                break;
            case 2:
                mView.showError("Failed to fetch news");
                break;
            default:
                mView.showError("Unspecified error");
        }
    }
}
