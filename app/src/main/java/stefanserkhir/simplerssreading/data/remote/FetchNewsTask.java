package stefanserkhir.simplerssreading.data.remote;

import android.os.AsyncTask;

import java.util.List;

public class FetchNewsTask extends AsyncTask<Void, Void, List<String>> {
    public interface DoAfterFetch {
        void doThisAfterFetchTask(List<String> mSetOfCategories);
    }

    DoAfterFetch mDoAfterFetch;

    public FetchNewsTask(DoAfterFetch doAfterFetch) {
        mDoAfterFetch = doAfterFetch;
    }

    @Override
    protected List<String> doInBackground(Void... voids) {
        return new NewsFetcher().fetchNews("https://www.vesti.ru/vesti.rss");
    }

    @Override
    protected void onPostExecute(List<String> setOfCategories) {
        mDoAfterFetch.doThisAfterFetchTask(setOfCategories);
    }
}