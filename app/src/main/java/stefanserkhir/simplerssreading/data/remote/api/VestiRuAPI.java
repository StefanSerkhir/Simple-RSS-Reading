package stefanserkhir.simplerssreading.data.remote.api;

import retrofit2.Call;
import retrofit2.http.GET;
import stefanserkhir.simplerssreading.data.remote.model.RSSFeed;

public interface VestiRuAPI {

    @GET("vesti.rss")
    Call<RSSFeed> loadRSSFeed();
}