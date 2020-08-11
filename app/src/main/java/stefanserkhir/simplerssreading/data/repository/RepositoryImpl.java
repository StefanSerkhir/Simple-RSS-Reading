package stefanserkhir.simplerssreading.data.repository;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.internal.EverythingIsNonNull;
import stefanserkhir.simplerssreading.core.ErrorType;
import stefanserkhir.simplerssreading.data.local.model.NewsItem;
import stefanserkhir.simplerssreading.data.converter.ConverterImpl;
import stefanserkhir.simplerssreading.data.remote.NewsRemote;
import stefanserkhir.simplerssreading.data.remote.model.RSSFeed;
import stefanserkhir.simplerssreading.data.repository.interfaces.Repository;

public class RepositoryImpl implements Callback<RSSFeed>, Repository {

    public interface RepositoryCallback {

        void onRepositoryResponse(List<NewsItem> newsList, List<String> categoriesList);

        void onRepositoryFailure(ErrorType errorType);
    }
    private Realm realm;

    RepositoryCallback mRepositoryCallback;

    public RepositoryImpl(RepositoryCallback repositoryCallback) {
        realm = Realm.getDefaultInstance();
        mRepositoryCallback = repositoryCallback;
    }

    @Override
    public void refreshNewsList() {
        new NewsRemote().start(this);
    }

    @Override
    public void getLocalNewsList() {
        mRepositoryCallback.onRepositoryResponse(
                realm.where(NewsItem.class).findAll(), getCategoriesList());
    }

    @Override
    public List<String> getCategoriesList() {
        List<String> categoriesList = new ArrayList<>();
        for (NewsItem category : realm.where(NewsItem.class).distinct("category").findAll()) {
            if (!categoriesList.contains(category.getCategory())) {
                categoriesList.add(category.getCategory());
            }
        }
        return categoriesList;
    }

    @Override
    public NewsItem getNewsItem(int position) {
        return realm.where(NewsItem.class).findAll().get(position);
    }

    @Override
    public NewsItem getNewsItem(int position, String filter) {
        return realm.where(NewsItem.class)
                .contains("category", filter).findAll().get(position);
    }

    @Override
    public int getNewsCount() {
        return realm.where(NewsItem.class).findAll().size();
    }

    @Override
    public int getNewsCount(String filter) {
        return realm.where(NewsItem.class)
                .contains("category", filter).findAll().size();
    }

    @Override
    @EverythingIsNonNull
    public void onResponse(Call<RSSFeed> call, Response<RSSFeed> response) {
        if (response.isSuccessful()) {
            RSSFeed rssFeed = response.body();
            if ((rssFeed != null ? rssFeed.getNewsList() : null) != null) {
                mRepositoryCallback.onRepositoryResponse(
                        new ConverterImpl().map(rssFeed.getNewsList()), getCategoriesList());
            } else {
                mRepositoryCallback.onRepositoryFailure(ErrorType.EMPTY_NEWS_LIST);
            }
        } else {
            mRepositoryCallback.onRepositoryFailure(ErrorType.UNSUCCESSFUL_RESPONSE);
        }
    }

    @Override
    @EverythingIsNonNull
    public void onFailure(Call<RSSFeed> call, Throwable t) {
        mRepositoryCallback.onRepositoryFailure(ErrorType.FAILED_FETCH);
    }
}
