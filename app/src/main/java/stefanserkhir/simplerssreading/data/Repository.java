package stefanserkhir.simplerssreading.data;

import java.util.List;

import stefanserkhir.simplerssreading.data.remote.model.RSSFeed;

public interface Repository<T> {

    List<T> get(String filter);

/*    List<NewsItem> getNewsRealm();

    List<NewsItem> getNewsRealm(String filter);

    List<RSSFeed> getNewsRemote();

    List<RSSFeed> getNewsRemote(String filter);*/
}
