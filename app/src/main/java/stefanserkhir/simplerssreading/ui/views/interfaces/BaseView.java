package stefanserkhir.simplerssreading.ui.views.interfaces;

import java.util.Map;

import stefanserkhir.simplerssreading.core.KeyExtra;

public interface BaseView {

    void updateUI();

    void openNewScreen(Map<KeyExtra, String> kitExtra);
}
