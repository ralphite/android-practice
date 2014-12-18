package com.ralphwen.runtracker;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

/**
 * Created by Yadong on 12/18/2014.
 * ralph.wen@gmail.com
 * Project: RunTracker
 */
public class RunListFragment extends ListFragment {

    private RunDatabaseHelper.RunCursor mRunCursor;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mRunCursor = RunManager.get(getActivity()).queryRuns();
    }

    @Override
    public void onDestroy() {
        mRunCursor.close();
        super.onDestroy();
    }

    private static class RunCursorAdapter extends CursorAdapter {

        private RunDatabaseHelper.RunCursor mRunCursor;

        public RunCursorAdapter(Context context, RunDatabaseHelper.RunCursor c) {
            super(context, c, 0);
            mRunCursor = c;
        }

        @Override
        public View newView(Context context, Cursor cursor, ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater)
                    context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            return inflater.inflate(android.R.layout.simple_list_item_1, parent, false);
        }

        @Override
        public void bindView(View view, Context context, Cursor cursor) {
            Run run = mRunCursor.getRun();

            TextView startDateTextView = (TextView)view;
            String cellText = context.getString(R.string.cell_text, run.getStartDate());
            startDateTextView.setText(cellText);
        }
    }
}
