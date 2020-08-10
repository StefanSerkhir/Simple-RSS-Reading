package stefanserkhir.simplerssreading.data.repository.interfaces;

import java.util.List;

import stefanserkhir.simplerssreading.data.local.model.NewsItem;

public interface Repository {

    void refreshNewsList();

    void getNewsList();

    List<NewsItem> getNewsList(String filter);

    List<String> getCategoriesList();

    NewsItem getNewsItem(int position);

    int getNewsCount();
}
