package stefanserkhir.simplerssreading.repository.remote.api;

import retrofit2.Call;
import retrofit2.http.GET;
import stefanserkhir.simplerssreading.repository.remote.model.RSSFeed;

public interface VestiRuAPI {

    @GET("vesti.rss")
    Call<RSSFeed> loadRSSFeed();
}