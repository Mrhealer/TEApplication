package com.healer.dev.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;

public interface BasePresenter {

    void start(@Nullable Bundle extras);

    void destroy();

}
