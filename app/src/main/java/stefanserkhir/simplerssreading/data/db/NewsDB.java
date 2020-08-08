package stefanserkhir.simplerssreading.data.db;

import java.util.List;

import io.realm.Realm;
import stefanserkhir.simplerssreading.data.Repository;
import stefanserkhir.simplerssreading.data.db.model.NewsItem;

final class NewsDB implements Repository<NewsItem> {

    public List<NewsItem> get(String filter) {
        Realm realm = Realm.getDefaultInstance();
        if (filter.equals("")) {
            return realm.where(NewsItem.class).findAll();
        } else {
            return realm.where(NewsItem.class).contains("category", filter).findAll();
        }
    }
}
