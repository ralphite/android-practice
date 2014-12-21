package com.ralphwen.geoquiz;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by Yadong on 12/21/2014.
 * ralph.wen@gmail.com
 * Project: GeoQuiz
 */
public class CheatActivity extends ActionBarActivity {

    //intent extras
    public static final String EXTRA_ANSWER_IS_TRUE =
            "com.ralphwen.geoquiz.answer_is_true";
    public static final String EXTRA_ANSWER_IS_SHOWN =
            "com.ralphwen.geoquiz.answer_is_shown";

    //bundle keys
    private static final String KEY_ANSWER_IS_SHOWN = "answer_is_shown";

    private boolean mAnswerIsTrue;
    private boolean mAnswerIsShown;

    private TextView mAnswerTextView;
    private Button mShowAnswerButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cheat);

        if(savedInstanceState != null) {
            mAnswerIsShown = savedInstanceState.getBoolean(KEY_ANSWER_IS_SHOWN, false);
        }
        setAnswerResult(mAnswerIsShown);

        mAnswerIsTrue = getIntent().getBooleanExtra(EXTRA_ANSWER_IS_TRUE, false);

        mAnswerTextView = (TextView) findViewById(R.id.answer_textView);
        if (mAnswerIsShown) {
            showAnswer();
        }

        mShowAnswerButton = (Button) findViewById(R.id.showAnswerButton);
        mShowAnswerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAnswer();

                mAnswerIsShown = true;
                setAnswerResult(true);
            }
        });
    }

    private void showAnswer() {
        if(mAnswerIsTrue) {
            mAnswerTextView.setText(R.string.true_button);
        } else {
            mAnswerTextView.setText(R.string.false_button);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putBoolean(KEY_ANSWER_IS_SHOWN, mAnswerIsShown);
    }

    private void setAnswerResult(boolean answerIsShown) {
        Intent data = new Intent();
        data.putExtra(EXTRA_ANSWER_IS_SHOWN, answerIsShown);
        setResult(RESULT_OK, data);
    }
}
