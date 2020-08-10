package stefanserkhir.simplerssreading.data.repository.interfaces;

import java.util.List;

import stefanserkhir.simplerssreading.data.local.model.NewsItem;

public interface Repository {

    void refreshNewsList();

    void getLocalNewsList();

    List<String> getCategoriesList();

    NewsItem getNewsItem(int position);

    NewsItem getNewsItem(int position, String filter);

    int getNewsCount();

    int getNewsCount(String filter);
}
