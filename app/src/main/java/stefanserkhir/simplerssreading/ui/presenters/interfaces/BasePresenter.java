package stefanserkhir.simplerssreading.ui.presenters.interfaces;

import stefanserkhir.simplerssreading.ui.views.interfaces.BaseView;

public interface BasePresenter<T extends BaseView> {

    BasePresenter<T> onAttachView(T view);

    void onDetachView();
}
