package stefanserkhir.simplerssreading.domain;

import java.util.List;

import stefanserkhir.simplerssreading.data.local.model.NewsItem;
import stefanserkhir.simplerssreading.data.mapper.NewsRepository;

public class Domain implements NewsRepository.RepositoryCallback {

    public interface DomainCallback {
        void onDomainResponse(List<NewsItem> newsList, List<String> categoriesList);
    }

    DomainCallback mDomainCallback;

    public Domain(DomainCallback domainCallback) {
        mDomainCallback = domainCallback;
    }

    public void getNews() {
        new NewsRepository(this).get();
    }

    @Override
    public void onFetchNews(List<NewsItem> newsList) {
        mDomainCallback.onDomainResponse(newsList, NewsRepository.getCategoriesList());
    }
}
