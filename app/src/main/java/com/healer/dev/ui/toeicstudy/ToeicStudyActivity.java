package com.healer.dev.ui.toeicstudy;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.LayoutTransition;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.healer.dev.R;
import com.healer.dev.data.local.DatabaseManager;
import com.healer.dev.data.models.TopicModel;
import com.healer.dev.data.models.WordModel;
import com.healer.dev.ui.admob.FullAdActivity;
import com.healer.dev.utils.Constants;

import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ToeicStudyActivity extends AppCompatActivity {

    private static final int COUNT_INDEX_MAX = 12;
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_name_topic)
    TextView tvNameTopic;
    @BindView(R.id.tv_example_tran)
    TextView tvExampleTran;
    @BindView(R.id.tv_example)
    TextView tvExample;
    @BindView(R.id.iv_word)
    ImageView ivWord;
    @BindView(R.id.tv_type)
    TextView tvType;
    @BindView(R.id.tv_explain)
    TextView tvExplain;
    @BindView(R.id.tv_idont_know)
    TextView tvIdontKnow;
    @BindView(R.id.tv_iknow)
    TextView tvIknow;
    @BindView(R.id.cl_detail_part)
    ConstraintLayout clDetailPart;
    @BindView(R.id.tv_origin)
    TextView tvOrigin;
    @BindView(R.id.tv_pronun)
    TextView tvPronun;
    @BindView(R.id.tv_details)
    TextView tvDetails;
    @BindView(R.id.cv_word)
    CardView cvWord;
    @BindView(R.id.rl_background)
    RelativeLayout rlBackground;
    TopicModel topicModel;
    @BindView(R.id.im_Speak)
    ImageView im_Speak;
    @BindView(R.id.cl_full)
    ConstraintLayout clFull;

    WordModel wordModel;
    int preId = -1;
    AnimatorSet animatorSet;
    private TextToSpeech mTxSpeech;
    private int mCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_toeic_study);

        ButterKnife.bind(this);

        setupUI();

        loadData();
        initTxSpeech();

    }

    private void loadData() {
        topicModel = (TopicModel) getIntent().getSerializableExtra(Constants.EXTRA_STRING_DEFAULT);
        tvNameTopic.setText(topicModel.name);
        rlBackground.setBackgroundColor(Color.parseColor(topicModel.color));

        wordModel = DatabaseManager.getInstance(this).getRandomWord(topicModel.id, preId);
        preId = topicModel.id;
        tvOrigin.setText(wordModel.origin);
        tvPronun.setText(wordModel.pronunciation);
        tvType.setText(wordModel.type);
        tvExample.setText(wordModel.example);
        tvExampleTran.setText(wordModel.example_translation);
        Glide.with(this).load(wordModel.image_url).
                apply(new RequestOptions().placeholder(R.drawable.loading).
                        error(R.drawable.ic_error)).into(ivWord);
    }

    private void initTxSpeech() {
        mTxSpeech = new TextToSpeech(this, status -> {
            if (status == TextToSpeech.SUCCESS) {
                int result = mTxSpeech.setLanguage(Locale.ENGLISH);
                if (result == TextToSpeech.LANG_MISSING_DATA ||
                        result == TextToSpeech.LANG_NOT_SUPPORTED) {
                    Toast.makeText(getApplicationContext(), getString(R.string.language_not_support), Toast.LENGTH_SHORT).show();
                }
            } else {
                mTxSpeech.setPitch(0.6f);
                mTxSpeech.setSpeechRate(1.0f);
            }
        });
    }

    /**
     * speak text when user click item speak
     *
     * @param text
     */
    private void speakText(String text) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mTxSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, null, null);
        } else {
            mTxSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, null);
        }
    }

    /**
     * binding data
     */
    private void setupUI() {
        ButterKnife.bind(this);
        if (getSupportActionBar() != null) getActionBar().hide();
    }

    @OnClick({R.id.tv_idont_know, R.id.tv_iknow, R.id.iv_back, R.id.tv_details, R.id.im_Speak})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_idont_know:
                nextWord(false);
                break;
            case R.id.tv_iknow:
                nextWord(true);
                break;
            case R.id.iv_back:
                onBackPressed();
                break;
            case R.id.tv_details:
                clFull.setLayoutTransition(new LayoutTransition());
                changeStatues(false);
                break;
            case R.id.im_Speak:
                speakText(wordModel.origin);
                break;
            default:
                break;
        }
    }

    private void changeStatues(boolean isExpanded) {
        if (isExpanded) {
            tvDetails.setVisibility(View.VISIBLE);
            clDetailPart.setVisibility(View.GONE);
        } else {
            tvDetails.setVisibility(View.GONE);
            clDetailPart.setVisibility(View.VISIBLE);
        }
    }

    private void nextWord(final boolean isKnown) {
        mCount++;
        if (mCount == COUNT_INDEX_MAX) {
            Intent intent = new Intent(this, FullAdActivity.class);
            startActivity(intent);
        } else {
            setAnimation(R.animator.animation_move_to_left);
            animatorSet.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);

                    DatabaseManager.getInstance(ToeicStudyActivity.this).updateWordLevel(wordModel, isKnown);
                    changeStatues(true);
                    loadData();

                    clFull.setLayoutTransition(null);

                    setAnimation(R.animator.animation_move_from_right);
                }
            });
        }

    }

    private void setAnimation(int animation) {
        animatorSet = (AnimatorSet) AnimatorInflater.loadAnimator(this, animation);
        animatorSet.setTarget(cvWord);
        animatorSet.start();
    }

    @Override
    protected void onResume() {
        mCount = 0;
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        if (mTxSpeech != null) {
            mTxSpeech.stop();
            mTxSpeech.shutdown();
        }
        super.onDestroy();
    }
}
