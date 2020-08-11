package stefanserkhir.simplerssreading.data.remote.model;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Namespace;
import org.simpleframework.xml.Path;
import org.simpleframework.xml.Root;

@Root(name = "item", strict = false)
public class SingleNews {

    @Element(name = "title")
    private String title;

    @Element(name = "link")
    private String link;

    @Element(name = "pubDate")
    private String date;

    @Element(name = "category")
    private String category;

    @Namespace(reference = "yandex")
    @Element(name = "full-text")
    private String fullText;

    @Attribute(name = "url")
    @Path("enclosure")
    private String image;

    public String getTitle() {
        return title;
    }

    public String getLink() {
        return link;
    }

    public String getDate() {
        return date;
    }

    public String getCategory() {
        return category;
    }

    public String getFullText() {
        return fullText;
    }

    public String getImage() {
        return image;
    }
}