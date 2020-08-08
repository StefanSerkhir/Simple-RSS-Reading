package stefanserkhir.simplerssreading.ui.presenters;

import android.util.Log;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import stefanserkhir.simplerssreading.data.remote.GetNewsRemote;
import stefanserkhir.simplerssreading.data.remote.model.RSSFeed;
import stefanserkhir.simplerssreading.data.remote.FetchNewsTask;
import stefanserkhir.simplerssreading.domain.Domain;
import stefanserkhir.simplerssreading.ui.presenters.interfaces.NewsListPresenter;
import stefanserkhir.simplerssreading.ui.views.interfaces.NewsListView;

public class NewsListImpl implements NewsListPresenter, FetchNewsTask.DoAfterFetch, Callback<RSSFeed> {
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
        new GetNewsRemote().start(this);
        new FetchNewsTask(this).execute();
    }

    @Override
    public void onSelectingFilters(String filter) {
        mView.updateUI(filter);
    }

    @Override
    public void doThisAfterFetchTask(List<String> setOfCategories) {
        mView.updateUI("");
        mView.createMenu(new Domain().getCategoriesList());
    }

    @Override
    public void onResponse(Call<RSSFeed> call, Response<RSSFeed> response) {
        if (response.isSuccessful()) {
            RSSFeed rssFeed = response.body();
            int i = 1;
            Log.d("MyFilter", "Title: " + rssFeed.getChannelTitle());
        }
    }

    @Override
    public void onFailure(Call<RSSFeed> call, Throwable t) {
        Log.d("MyFilter", "\n Failed ");
    }
}
