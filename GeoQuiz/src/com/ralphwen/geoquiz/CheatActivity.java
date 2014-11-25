package com.ralphwen.geoquiz;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class CheatActivity extends Activity {

	public static final String EXTRA_ANSWER_IS_TRUE = "com.ralphwen.geoquiz.answer_is_true";
	public static final String EXTRA_ANSWER_SHOWN = "com.ralphwen.geoquiz.answer_shown";
	private boolean mAnswerIsTrue;

	private TextView mAnswerTextView;
	private TextView mCompileVersionTextView;
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

		setAnswerShownResult(false);

		mAnswerIsTrue = getIntent().getBooleanExtra(EXTRA_ANSWER_IS_TRUE, true);

		mAnswerTextView = (TextView) findViewById(R.id.answerTextView);
		mCompileVersionTextView = (TextView) findViewById(R.id.compileVersionTextView);
		mCompileVersionTextView.setText("API level "
				+ String.valueOf(Build.VERSION.SDK_INT));

		mShowAnswer = (Button) findViewById(R.id.showAnswerButton);
		mShowAnswer.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if (mAnswerIsTrue) {
					mAnswerTextView.setText(R.string.true_button);
				} else {
					mAnswerTextView.setText(R.string.false_button);
				}

				setAnswerShownResult(true);
			}
		});
	}
}
