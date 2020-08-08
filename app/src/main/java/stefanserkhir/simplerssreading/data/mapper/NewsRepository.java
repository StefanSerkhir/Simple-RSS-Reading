package stefanserkhir.simplerssreading.data.mapper;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import stefanserkhir.simplerssreading.data.local.model.NewsItem;
import stefanserkhir.simplerssreading.data.remote.GetNewsRemote;
import stefanserkhir.simplerssreading.data.remote.model.RSSFeed;

public class NewsRepository implements Callback<RSSFeed> {

    public interface RepositoryCallback {
        void onFetchNews(List<NewsItem> list);
    }

    RepositoryCallback mRepositoryCallback;

    public NewsRepository(RepositoryCallback repositoryCallback) {
        mRepositoryCallback = repositoryCallback;
    }

    public static List<String> getCategoriesList() {
        List<String> categoriesList = new ArrayList<>();
        Realm realm = Realm.getDefaultInstance();
        realm.where(NewsItem.class).distinct("category").findAll().forEach(
                category -> {
                    if (!categoriesList.contains(category)) {
                        categoriesList.add(category.getCategory());
                    }
                }
        );
        return categoriesList;
    }

    public void get() {
        new GetNewsRemote().start(this);
    }

    @Override
    public void onResponse(Call<RSSFeed> call, Response<RSSFeed> response) {
        if (response.isSuccessful()) {
            RSSFeed rssFeed = response.body();
            if ((rssFeed != null ? rssFeed.getNewsList() : null) != null) {
                mRepositoryCallback.onFetchNews(new MapperImpl().map(rssFeed.getNewsList()));
            } else {
               onFailure(call, new Throwable("Failed in response method"));
            }
        }
    }

    @Override
    public void onFailure(Call<RSSFeed> call, Throwable t) {
        Log.d("MyFilter", "\n Failed ");
    }
}
