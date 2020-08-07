package stefanserkhir.simplerssreading.ui.presenters.interfaces;

public interface NewsListPresenter extends BasePresenter {

    void onDataRequest();

    void onSelectingFilters(String filter);
}
