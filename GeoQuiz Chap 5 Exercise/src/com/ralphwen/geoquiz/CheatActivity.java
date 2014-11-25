package com.ralphwen.geoquiz;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class CheatActivity extends Activity {

	public static final String EXTRA_ANSWER_IS_TRUE = "com.ralphwen.geoquiz.answer_is_true";
	public static final String EXTRA_ANSWER_SHOWN = "com.ralphwen.geoquiz.answer_shown";

	private static final String KEY_IS_CHEATER = "is_cheater";
	
	private boolean mAnswerIsTrue;
	private boolean mAnswerIsShown;

	private TextView mAnswerTextView;
	private Button mShowAnswer;

	private void setAnswerShownResult(boolean isAnswerShown) {
		Intent data = new Intent();
		data.putExtra(EXTRA_ANSWER_SHOWN, isAnswerShown);
		setResult(RESULT_OK, data);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_cheat);

		mAnswerIsShown = false;
		if (savedInstanceState != null) {
			mAnswerIsShown = savedInstanceState.getBoolean(KEY_IS_CHEATER,
					false);
		}
		setAnswerShownResult(mAnswerIsShown);

		mAnswerIsTrue = getIntent().getBooleanExtra(EXTRA_ANSWER_IS_TRUE, true);

		mAnswerTextView = (TextView) findViewById(R.id.answerTextView);
		if (mAnswerIsShown) {
			setAnswerText();
		}

		mShowAnswer = (Button) findViewById(R.id.showAnswerButton);
		mShowAnswer.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				setAnswerText();

				mAnswerIsShown = true;
				setAnswerShownResult(mAnswerIsShown);
			}
		});
	}

	@Override
	public void onSaveInstanceState(Bundle savedInstanceState) {
		super.onSaveInstanceState(savedInstanceState);
		savedInstanceState.putBoolean(KEY_IS_CHEATER, mAnswerIsShown);
	}

	private void setAnswerText() {
		if (mAnswerIsTrue) {
			mAnswerTextView.setText(R.string.true_button);
		} else {
			mAnswerTextView.setText(R.string.false_button);
		}
	}
}
