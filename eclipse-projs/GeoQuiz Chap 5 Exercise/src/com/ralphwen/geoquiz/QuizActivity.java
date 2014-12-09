package com.ralphwen.geoquiz;

import java.util.HashSet;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class QuizActivity extends ActionBarActivity {

	// private static final String TAG = "QuizActivity";
	private static final String KEY_INDEX = "index";
	private static final String KEY_IS_CHEATER = "is_cheater";
	private static final String KEY_CHEATED_QUESTION_SET = "cheated_question_set";

	private Button mTrueButton;
	private Button mFalseButton;
	private Button mCheatButton;
	private Button mNextButton;
	private TextView mQuestionTextView;

	private boolean mIsCheater;
	private HashSet<Integer> mCheatedQuestionSet = new HashSet<Integer>();

	private TrueFalse[] mQuestionBank = new TrueFalse[] {
			new TrueFalse(R.string.question_oceans, true),
			new TrueFalse(R.string.question_mideast, false),
			new TrueFalse(R.string.question_africa, false),
			new TrueFalse(R.string.question_americas, true),
			new TrueFalse(R.string.question_asia, true) };

	private int mCurrentIndex = 0;

	private void updateQuestion() {
		// Log.d(TAG, "Updating question text for question #" + mCurrentIndex,
		// new Exception());
		int question = mQuestionBank[mCurrentIndex].getQuestion();
		mQuestionTextView.setText(question);
	}

	private void checkAnswer(boolean userPressedTrue) {
		boolean answerIsTrue = mQuestionBank[mCurrentIndex].isTrueQuestion();

		int messageResId = 0;

		if (mIsCheater) {
			messageResId = R.string.judgement_toast;
		} else {
			if (userPressedTrue == answerIsTrue) {
				messageResId = R.string.correct_toast;
			} else {
				messageResId = R.string.incorrect_toast;
			}
		}

		Toast.makeText(this, messageResId, Toast.LENGTH_SHORT).show();
	}

	@SuppressWarnings("unchecked")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// Log.d(TAG, "onCreate(Bundle) called");
		setContentView(R.layout.activity_quiz);

		mQuestionTextView = (TextView) findViewById(R.id.question_text_view);

		mTrueButton = (Button) findViewById(R.id.true_button);
		mTrueButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				checkAnswer(true);
			}
		});

		mFalseButton = (Button) findViewById(R.id.false_button);
		mFalseButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				checkAnswer(false);
			}
		});

		mNextButton = (Button) findViewById(R.id.next_button);
		mNextButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				mCurrentIndex++;
				mCurrentIndex %= mQuestionBank.length;
				mIsCheater = false;
				if (mCheatedQuestionSet.contains(mCurrentIndex))
					mIsCheater = true;
				updateQuestion();
			}
		});

		mCheatButton = (Button) findViewById(R.id.cheat_button);
		mCheatButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent i = new Intent(QuizActivity.this, CheatActivity.class);
				boolean answerIsTrue = mQuestionBank[mCurrentIndex]
						.isTrueQuestion();
				i.putExtra(CheatActivity.EXTRA_ANSWER_IS_TRUE, answerIsTrue);
				// startActivity(i);
				startActivityForResult(i, 0);
			}
		});

		if (savedInstanceState != null) {
			mCurrentIndex = savedInstanceState.getInt(KEY_INDEX, 0);
			mIsCheater = savedInstanceState.getBoolean(KEY_IS_CHEATER, false);
			mCheatedQuestionSet = (HashSet<Integer>) savedInstanceState
					.get(KEY_CHEATED_QUESTION_SET);
		}

		updateQuestion();
	}

	@Override
	public void onSaveInstanceState(Bundle savedInstanceState) {
		super.onSaveInstanceState(savedInstanceState);
		// Log.i(TAG, "onSaveInstanceState");
		savedInstanceState.putInt(KEY_INDEX, mCurrentIndex);
		savedInstanceState.putBoolean(KEY_IS_CHEATER, mIsCheater);
		savedInstanceState.putSerializable(KEY_CHEATED_QUESTION_SET,
				mCheatedQuestionSet);
	}

	@Override
	public void onStart() {
		super.onStart();
		// Log.d(TAG, "onStart() called");
	}

	@Override
	public void onPause() {
		super.onPause();
		// Log.d(TAG, "onPause() called");
	}

	@Override
	public void onResume() {
		super.onResume();
		// Log.d(TAG, "onResume() called");
	}

	@Override
	public void onStop() {
		super.onStop();
		// Log.d(TAG, "onStop() called");
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		// Log.d(TAG, "onDestroy() called");
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.quiz, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (data == null)
			return;
		mIsCheater = data.getBooleanExtra(CheatActivity.EXTRA_ANSWER_SHOWN,
				false);
		if (mIsCheater)
			mCheatedQuestionSet.add(mCurrentIndex);
	}
}
