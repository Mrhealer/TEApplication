package com.healer.dev.ui.about;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.healer.dev.BuildConfig;
import com.healer.dev.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Class display about from Dev.
 */
public class AboutActivity extends AppCompatActivity {

    private static final String VERION_APP = "Phiên Bản Hiện Tại : ";
    private static final String REVIEW_APP_URL = "https://play.google.com/store/apps/details?id=com.healer.dev";
    private static final String CHECK_VERSION_APP_URL = "https://play.google.com/store/apps/details?id=com.healer.dev";
    private static final String FANPAGE_APP_URL = "https://www.facebook.com/ToeicTranningTnut/";
    private static final String CONTACT_SUPPORT_URL = "https://facebook.com/iukylong";
    @BindView(R.id.img_back_about)
    public ImageView mBack;
    @BindView(R.id.teapp_version)
    public TextView mTxtVersion;
    @BindView(R.id.review_App)
    public TextView mReviewApp;
    @BindView(R.id.fanpage_Facebook)
    public TextView mFanpageFacebook;
    @BindView(R.id.contact_Support)
    public TextView mContactSupport;
    private AdView mAdView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        ButterKnife.bind(this);
        MobileAds.initialize(this, getString(R.string.admob_app_id));
        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        mTxtVersion.setText(VERION_APP + BuildConfig.VERSION_NAME);
    }

    /**
     * butterknife click.
     *
     * @param view
     */
    @OnClick({R.id.img_back_about, R.id.teapp_version, R.id.review_App, R.id.fanpage_Facebook, R.id.contact_Support})
    public void onViewClicked(View view) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        switch (view.getId()) {
            case R.id.img_back_about:
                onBackPressed();
                break;
            case R.id.review_App:
                intent.setData(Uri.parse(REVIEW_APP_URL));
                startActivity(intent);
                break;
            case R.id.check_Version:
                intent.setData(Uri.parse(CHECK_VERSION_APP_URL));
                startActivity(intent);
                break;
            case R.id.fanpage_Facebook:
                intent.setData(Uri.parse(FANPAGE_APP_URL));
                startActivity(intent);
                break;
            case R.id.contact_Support:
                intent.setData(Uri.parse(CONTACT_SUPPORT_URL));
                startActivity(intent);
                break;
            default:
                break;
        }
    }
}
