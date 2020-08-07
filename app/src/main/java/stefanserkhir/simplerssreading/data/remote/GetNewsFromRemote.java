package stefanserkhir.simplerssreading.data.remote;

import android.util.Log;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;
import stefanserkhir.simplerssreading.data.remote.api.VestiRuAPI;
import stefanserkhir.simplerssreading.data.remote.model.RSSFeed;

public class GetNewsFromRemote implements Callback<RSSFeed> {
    static final String BASE_URL = "https://www.vesti.ru/";

    public void start() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(SimpleXmlConverterFactory.create())
                .build();

        VestiRuAPI vestiRuAPI = retrofit.create(VestiRuAPI.class);

        Call<RSSFeed> call = vestiRuAPI.loadRSSFeed();
        call.enqueue(this);
    }

    @Override
    public void onResponse(Call<RSSFeed> call, Response<RSSFeed> response) {
        if (response.isSuccessful()) {
            String T = "MyFilter";
            RSSFeed rssFeed = response.body();
            int i = 1;
            rssFeed.getNewsList().forEach(
                    article -> {
                        Log.d(T, "\n =============== # " + i + " ===============");
                        Log.d(T, "Title: " + article.getTitle() + " Link: " + article.getLink());
                        Log.d(T, "PubDate: " + article.getPubDate());
                        Log.d(T, "Category: " + article.getCategory());
                        Log.d(T, "FullText: " + article.getFullText());
                        Log.d(T, "URL: " + article.getEnclosure());
                    }
            );
        }
    }

    @Override
    public void onFailure(Call<RSSFeed> call, Throwable t) {
        String T = "MyFilter";
        Log.d(T, "\n Failed ");
    }
}