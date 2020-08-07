package stefanserkhir.simplerssreading.data.db.model;

import io.realm.RealmObject;
import io.realm.annotations.Required;

public class NewsItem extends RealmObject  {
    @Required
    private String title;
    @Required
    private String fullText;
    @Required
    private String category;
    @Required
    private String date;
    private String image;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getFullText() {
        return fullText;
    }

    public void setFullText(String fullText) {
        this.fullText = fullText;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
