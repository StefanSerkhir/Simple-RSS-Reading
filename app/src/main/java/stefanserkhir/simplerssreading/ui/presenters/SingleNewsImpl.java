package stefanserkhir.simplerssreading.ui.presenters;

import stefanserkhir.simplerssreading.ui.presenters.interfaces.SingleNewsPresenter;
import stefanserkhir.simplerssreading.ui.views.interfaces.BaseView;
import stefanserkhir.simplerssreading.ui.views.interfaces.SingleNewsView;

public class SingleNewsImpl implements SingleNewsPresenter {
    private SingleNewsView mView;

    @Override
    public SingleNewsPresenter onAttachView(SingleNewsView view) {
        mView = (SingleNewsView) view;
        return this;
    }

    @Override
    public void onDetachView() {
        mView = null;
    }
}
