package com.healer.dev.ui.about;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.healer.dev.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Class display about from Dev
 */
public class AboutActivity extends AppCompatActivity {

    @BindView(R.id.img_back_about)
    public ImageView mBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        ButterKnife.bind(this);
    }

    /**
     * butterknife click
     *
     * @param view
     */
    @OnClick({R.id.img_back_about})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_back_about:
                onBackPressed();
                break;
            default:
                break;
        }
    }
}
