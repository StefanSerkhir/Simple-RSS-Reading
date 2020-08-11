package stefanserkhir.simplerssreading.data.remote.model;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Path;
import org.simpleframework.xml.Root;

import java.util.List;

@Root(name = "rss", strict = false)
public class RSSFeed {
    @ElementList(name = "item", inline = true)
    @Path("channel")
    private List<SingleNews> newsList;

    public List<SingleNews> getNewsList() {
        return newsList;
    }
}
