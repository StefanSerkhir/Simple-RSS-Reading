package stefanserkhir.simplerssreading.core;

import stefanserkhir.simplerssreading.R;

public enum ErrorType {
    EMPTY_NEWS_LIST(R.string.error_empty_list),
    UNSUCCESSFUL_RESPONSE(R.string.error_unsuccessful),
    FAILED_FETCH(R.string.error_failed);

    private int resourceStringId;

    ErrorType(int resourceStringId) { this.resourceStringId = resourceStringId; }

    public int getResourceStringId() { return resourceStringId; }
}
