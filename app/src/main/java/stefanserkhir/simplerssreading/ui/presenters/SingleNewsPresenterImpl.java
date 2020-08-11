package stefanserkhir.simplerssreading.ui.presenters;

import java.util.Map;

import stefanserkhir.simplerssreading.core.KeyExtra;
import stefanserkhir.simplerssreading.ui.presenters.interfaces.SingleNewsPresenter;
import stefanserkhir.simplerssreading.ui.views.interfaces.SingleNewsView;

public class SingleNewsPresenterImpl implements SingleNewsPresenter {
    private static Map<KeyExtra, String> sKitExtra;
    private SingleNewsView mView;

    public static void setKitExtra(Map<KeyExtra, String> kitExtra) {
        sKitExtra = kitExtra;
    }

    @Override
    public void onAttachView(SingleNewsView view) {
        mView = view;
        mView.updateUI();
    }

    @Override
    public void onDetachView() {
        mView = null;
    }

    @Override
    public String getTitle() {
        return sKitExtra.get(KeyExtra.TITLE);
    }

    @Override
    public String getLink() {
        return sKitExtra.get(KeyExtra.LINK);
    }

    @Override
    public String getDate() {
        return sKitExtra.get(KeyExtra.DATE);
    }

    @Override
    public String getCategory() {
        return sKitExtra.get(KeyExtra.CATEGORY);
    }

    @Override
    public String getFullText() {
        return sKitExtra.get(KeyExtra.FULL_TEXT);
    }

    @Override
    public String getImage() {
        return sKitExtra.get(KeyExtra.IMAGE);
    }
}
