package stefanserkhir.simplerssreading.ui.presenters.interfaces;

import stefanserkhir.simplerssreading.ui.views.interfaces.BaseView;

public interface BasePresenter {

    BasePresenter onAttachView(BaseView view);

    void onDetachView();
}
