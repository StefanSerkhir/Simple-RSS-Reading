package stefanserkhir.simplerssreading.ui.presenters;

import android.util.Log;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import stefanserkhir.simplerssreading.data.local.model.NewsItem;
import stefanserkhir.simplerssreading.data.remote.GetNewsRemote;
import stefanserkhir.simplerssreading.data.remote.model.RSSFeed;
import stefanserkhir.simplerssreading.domain.Domain;
import stefanserkhir.simplerssreading.ui.presenters.interfaces.NewsListPresenter;
import stefanserkhir.simplerssreading.ui.views.interfaces.NewsListView;

public class NewsListImpl implements NewsListPresenter, Domain.DomainCallback {
    private NewsListView mView;


    @Override
    public NewsListPresenter onAttachView(NewsListView view) {
        mView = view;
        mView.updateUI("");
        return this;
    }

    @Override
    public void onDetachView() {
        mView = null;
    }

    @Override
    public void onDataRequest() {
        new Domain(this).getNews();
    }

    @Override
    public void onSelectingFilters(String filter) {
        mView.updateUI(filter);
    }

    @Override
    public void onDomainResponse(List<NewsItem> newsList, List<String> categoriesList) {
        mView.updateUI("");
        mView.createMenu(categoriesList);
    }
}
