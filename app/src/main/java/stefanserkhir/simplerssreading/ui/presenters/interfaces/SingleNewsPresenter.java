package stefanserkhir.simplerssreading.ui.presenters.interfaces;

import stefanserkhir.simplerssreading.ui.views.interfaces.SingleNewsView;

public interface SingleNewsPresenter extends BasePresenter<SingleNewsView> {

    String getTitle();

    String getLink();

    String getDate();

    String getCategory();

    String getFullText();

    String getImage();
}
