package stefanserkhir.simplerssreading.data.mapper;

import java.util.ArrayList;
import java.util.List;

import stefanserkhir.simplerssreading.core.Mapper;
import stefanserkhir.simplerssreading.data.local.model.NewsItem;
import stefanserkhir.simplerssreading.data.remote.model.SingleNews;

public class MapperImpl implements Mapper<NewsItem, SingleNews> {

    @Override
    public List<NewsItem> map(List<SingleNews> list) {
        List<NewsItem> convertedList = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            NewsItem newsItem = new NewsItem();

            newsItem.setTitle(list.get(i).getTitle());
            newsItem.setLink(list.get(i).getLink());
            newsItem.setDate(list.get(i).getDate());
            newsItem.setFullText(list.get(i).getFullText());
            newsItem.setCategory(list.get(i).getCategory());
            newsItem.setImage(list.get(i).getImage());

            convertedList.add(newsItem);
        }
        return convertedList;
    }
}
