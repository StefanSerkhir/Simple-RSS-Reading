package stefanserkhir.simplerssreading.ui.presenters;

import android.util.Log;

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

    @Override
    public void onAttachView(NewsListView view) {
        mView = view;
        mRepository = new RepositoryImpl(this);
        Log.d("MyFilter", "onAttachView");
        mRepository.getNewsList();
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
        Log.d("MyFilter", "onMenuRequest");
        mView.createMenu(mRepository.getCategoriesList());
    }

    @Override
    public void onBindRepositoryItemViewAtPosition(RepositoryItemView itemView, int position) {
        NewsItem newsItem = mRepository.getNewsItem(position);
        itemView.setTitle(newsItem.getTitle());
        itemView.setDate(newsItem.getDate());
    }

    @Override
    public int getRepositoryItemsCount() {
        return mRepository.getNewsCount();
    }

    @Override
    public void onSelectingFilters(String filter) {
        mRepository.getNewsList(filter);
        mView.updateUI();
    }

    @Override
    public void onRepositoryResponse(List<NewsItem> list, List<String> categoriesList) {
        Log.d("MyFilter", "onRepositoryResponse -> size = " + list.size());
        mView.updateUI();
        Log.d("MyFilter", "onRepositoryResponse");
        mView.createMenu(categoriesList);
    }
}
