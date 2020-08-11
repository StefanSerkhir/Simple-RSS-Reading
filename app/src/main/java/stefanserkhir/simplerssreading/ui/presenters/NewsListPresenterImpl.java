package stefanserkhir.simplerssreading.ui.presenters;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import stefanserkhir.simplerssreading.R;
import stefanserkhir.simplerssreading.core.ErrorType;
import stefanserkhir.simplerssreading.core.KeyExtra;
import stefanserkhir.simplerssreading.data.local.model.NewsItem;
import stefanserkhir.simplerssreading.data.repository.RepositoryImpl;
import stefanserkhir.simplerssreading.data.repository.interfaces.Repository;
import stefanserkhir.simplerssreading.ui.presenters.interfaces.NewsListPresenter;
import stefanserkhir.simplerssreading.ui.views.interfaces.RepositoryItemView;
import stefanserkhir.simplerssreading.ui.views.interfaces.NewsListView;

public class NewsListPresenterImpl implements NewsListPresenter, RepositoryImpl.RepositoryCallback {
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
        NewsItem newsItem = fetchNewsItem(position);
        itemView.setTitle(newsItem.getTitle());
        itemView.setDate(newsItem.getDate());
    }

    @Override
    public void onClick(int position) {
        NewsItem newsItem = fetchNewsItem(position);
        Map<KeyExtra, String> kitExtra = new HashMap<>(6);
        kitExtra.put(KeyExtra.TITLE, newsItem.getTitle());
        kitExtra.put(KeyExtra.LINK, newsItem.getLink());
        kitExtra.put(KeyExtra.DATE, newsItem.getDate());
        kitExtra.put(KeyExtra.CATEGORY, newsItem.getCategory());
        kitExtra.put(KeyExtra.FULL_TEXT, newsItem.getFullText());
        kitExtra.put(KeyExtra.IMAGE, newsItem.getImage());
        mView.openNewScreen(kitExtra);
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
    public void onRepositoryFailure(ErrorType errorType) {
        switch (errorType) {
            case EMPTY_NEWS_LIST:
                mView.showError(R.string.error_empty_list);
                break;
            case UNSUCCESSFUL_RESPONSE:
                mView.showError(R.string.error_unsuccessful);
                break;
            case FAILED_FETCH:
                mView.showError(R.string.error_failed);
                break;
            default:
                mView.showError(R.string.error_unknown);
        }
    }

    private NewsItem fetchNewsItem(int position) {
        if (mFilter == null) {
            return mRepository.getNewsItem(position);
        } else {
            return mRepository.getNewsItem(position, mFilter);
        }
    }
}
