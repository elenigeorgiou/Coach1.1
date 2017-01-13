package edu.tjhsst.coach11;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;


public class FitTestActivity extends ActionBarActivity {
    private Button start= (Button)findViewById(R.id.start); ;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fit_test);

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               ///START FITNESS TESTING!!!!!!

                //delete the instructions and title
                //start timer - send timmer to phone - have it beep when time is up
                //ten minute fitness test
                //ask for how long they have been sitting --> look at activity log
                // step 1: take resting heart rate: record that

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_fit_test, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
