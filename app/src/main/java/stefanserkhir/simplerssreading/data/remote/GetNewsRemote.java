package stefanserkhir.simplerssreading.data.remote;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;
import stefanserkhir.simplerssreading.data.mapper.Repository;
import stefanserkhir.simplerssreading.data.remote.api.VestiRuAPI;
import stefanserkhir.simplerssreading.data.remote.model.RSSFeed;

public class GetNewsRemote implements Repository<RSSFeed> {

    public void start(Callback<RSSFeed> callback) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://www.vesti.ru/")
                .addConverterFactory(SimpleXmlConverterFactory.create())
                .build();

        VestiRuAPI vestiRuAPI = retrofit.create(VestiRuAPI.class);

        Call<RSSFeed> call = vestiRuAPI.loadRSSFeed();
        call.enqueue(callback);
    }

    @Override
    public List<RSSFeed> get(String filter) {
        return null;
    }
}