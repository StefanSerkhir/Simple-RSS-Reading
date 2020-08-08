package stefanserkhir.simplerssreading.data;

import android.util.Log;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import stefanserkhir.simplerssreading.data.remote.FetchNewsTask.DoAfterFetch;
import stefanserkhir.simplerssreading.data.remote.GetNewsRemote;
import stefanserkhir.simplerssreading.data.remote.model.RSSFeed;

public class NewsRepository implements Callback<RSSFeed>, DoAfterFetch {

    public void get() {
        new GetNewsRemote().start(this);
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

    @Override
    public void doThisAfterFetchTask(List<String> mSetOfCategories) {

    }
}
