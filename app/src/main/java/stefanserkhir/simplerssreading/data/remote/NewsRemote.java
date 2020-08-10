package stefanserkhir.simplerssreading.data.remote;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;
import stefanserkhir.simplerssreading.data.remote.api.VestiRuApi;
import stefanserkhir.simplerssreading.data.remote.model.RSSFeed;

public class NewsRemote {

    private VestiRuApi vestiRuAPI;

    public NewsRemote() {
        //noinspection deprecation
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://www.vesti.ru/")
                .addConverterFactory(SimpleXmlConverterFactory.create())
                .build();

        vestiRuAPI = retrofit.create(VestiRuApi.class);
    }

    public void start(Callback<RSSFeed> callback) {
        Call<RSSFeed> call = vestiRuAPI.loadRSSFeed();
        call.enqueue(callback);
    }
}