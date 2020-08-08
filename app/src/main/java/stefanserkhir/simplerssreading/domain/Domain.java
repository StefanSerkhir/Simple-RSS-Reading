package stefanserkhir.simplerssreading.domain;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import stefanserkhir.simplerssreading.data.db.model.NewsItem;

public class Domain {

    public List<String> getCategoriesList() {
        List<String> categoriesList = new ArrayList<>();
        Realm realm = Realm.getDefaultInstance();
        realm.where(NewsItem.class).distinct("category").findAll().forEach(
                category -> {
                    if (!categoriesList.contains(category)) {
                        categoriesList.add(category.getCategory());
                    }
                }
        );
        return categoriesList;
    }
}
