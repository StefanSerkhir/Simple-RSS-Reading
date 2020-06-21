package stefanserkhir.simplerssreading;

import android.util.Xml;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

import io.realm.Realm;

public class NewsFetcher {

    public void fetchNews(String stringURL) {
        HttpsURLConnection connection = null;
        try {
            URL url = new URL(stringURL);
            connection = (HttpsURLConnection) url.openConnection();

            InputStream in = connection.getInputStream();
            if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                throw new IOException(connection.getResponseMessage() + ": with " + stringURL);
            }

            parseNews(in);
        } catch (XmlPullParserException | IOException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }

    private void parseNews(InputStream in) throws XmlPullParserException, IOException {
        XmlPullParser parser = Xml.newPullParser();
        parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
        parser.setInput(in, null);
        NewsItem newsToBeAdded = null;
        List<NewsItem> newsListToBeAdded = new ArrayList<>();
        while (parser.getEventType() != XmlPullParser.END_DOCUMENT) {
            switch (parser.getEventType()) {
                case XmlPullParser.START_TAG:
                    if (parser.getName().equals("item")){
                        newsToBeAdded = new NewsItem();
                    }

                    if (newsToBeAdded != null) {
                        if (parser.getName().equals("title")) {
                            newsToBeAdded.setTitle(parser.nextText());
                        }
                        if (parser.getName().equals("pubDate")) {
                            newsToBeAdded.setDate(parser.nextText());
                        }
                        if (parser.getName().equals("category")) {
                            newsToBeAdded.setCategory(parser.nextText());
                        }
                        if (parser.getName().equals("enclosure")) {
                            newsToBeAdded.setImage(parser.getAttributeValue(0));
                        }
                        if (parser.getName().equals("yandex:full-text")) {
                            newsToBeAdded.setFullText(parser.nextText());
                        }
                        break;
                    }
                    break;
                case XmlPullParser.END_TAG:
                    if (parser.getName().equals("item") && newsToBeAdded != null) {
                        newsListToBeAdded.add(newsToBeAdded);
                    }
                    break;
                default:
                    break;
            }
            parser.next();
        }
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        realm.deleteAll();
        realm.copyToRealm(newsListToBeAdded);
        realm.commitTransaction();
        realm.close();
    }
}
