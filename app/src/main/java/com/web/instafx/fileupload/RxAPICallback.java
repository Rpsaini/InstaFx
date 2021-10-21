package com.web.instafx.fileupload;

public interface RxAPICallback<P> {
    void onSuccess(P t);

    void onFailed(Throwable throwable);
}

