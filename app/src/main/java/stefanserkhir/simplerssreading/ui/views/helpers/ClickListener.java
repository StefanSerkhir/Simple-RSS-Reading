package stefanserkhir.simplerssreading.ui.views.helpers;

import android.view.View;

import stefanserkhir.simplerssreading.ui.presenters.interfaces.NewsListPresenter;

public class ClickListener implements View.OnClickListener {
    private final NewsListPresenter mPresenter;
    private final int mPosition;

    public ClickListener(NewsListPresenter presenter, int position) {
        mPresenter = presenter;
        mPosition = position;
    }

    @Override
    public void onClick(View view) {
        mPresenter.onClick(mPosition);
    }
}
