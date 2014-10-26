package dimcho.proj.stopwatch;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

public class Stopwatch extends Activity {

    private Button startButton;
    private Button stopButton;
    private Button resetButton;
    private Button lapButton;
    private TextView textView;
    private Timer timer;
    private LinearLayout linearLayout;
    private int currentTime = 0;
    private int lapTime = 0;
    private boolean sWatchIsStarted;
    private boolean lapViewExists;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stopwatch);

        startButton = (Button) findViewById(R.id.btn_start);
        stopButton = (Button) findViewById(R.id.btn_stop);
        lapButton = (Button) findViewById(R.id.btn_lap);
        resetButton = (Button) findViewById(R.id.btn_reset);
        textView = (TextView) findViewById(R.id.stopwatch_view);
        textView.setTextSize(55);
    }

/* Handling the configuration change myself because of views inserted
   during run time.(lapDisplay and imageView)

    @Override
    public void onSaveInstanceState(Bundle saveInstanceState){
        saveInstanceState.putInt("currentTime", currentTime);
        saveInstanceState.putInt("lapTime", lapTime);
        saveInstanceState.putBoolean("lapViewExists", lapViewExists);
        saveInstanceState.putBoolean("sWatchIsStarted", sWatchIsStarted);

        super.onSaveInstanceState(saveInstanceState);
    }
    @Override
    public void onRestoreInstanceState(Bundle savedInstanceSate){

        currentTime = savedInstanceSate.getInt("currentTime");
        lapTime = savedInstanceSate.getInt("lapTime");
        lapViewExists = savedInstanceSate.getBoolean("lapViewExists");
        sWatchIsStarted = savedInstanceSate.getBoolean("sWatchIsStarted");

        if(sWatchIsStarted){
            onSWatchStart(view);
        }else{
            textView.setText(TimeFormatUtil.toDisplayString(currentTime));
        }
        super.onRestoreInstanceState(savedInstanceSate);
    }
*/

    public void onSWatchStart(View view) {

        sWatchIsStarted=true;

        startButton.setVisibility(View.GONE);
        stopButton.setVisibility(View.VISIBLE);

        resetButton.setVisibility(View.GONE);
        lapButton.setVisibility(View.VISIBLE);

        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    public void run() {
                        currentTime += 1;
                        lapTime += 1;

                        // update ui
                        textView.setText(TimeFormatUtil.toDisplayString(currentTime));


                    }
                });
            }
        }, 0, 100);
    }

    public void onSWatchStop(View view) {
        sWatchIsStarted=false;
        stopButton.setVisibility(View.GONE);
        startButton.setVisibility(View.VISIBLE);

        lapButton.setVisibility(View.GONE);
        resetButton.setEnabled(true);
        resetButton.setBackgroundResource(R.drawable.btn_lap_states);
        resetButton.setVisibility(View.VISIBLE);

        timer.cancel();
    }

    public void onSWatchReset(View view) {
        timer.cancel();
        currentTime = 0;
        lapTime = 0;
        textView.setText(TimeFormatUtil.toDisplayString(currentTime));
        resetButton.setEnabled(false);
        resetButton.setBackgroundResource(R.drawable.btn_reset_states);

        if (lapViewExists) {
            linearLayout.removeAllViews();
            lapViewExists=false;
        }
    }
    public void onSWatchLap(View view) {

        lapViewExists = true;
        linearLayout = (LinearLayout) findViewById(R.id.layout);

        TextView lapDisplay = new TextView(this);
        ImageView imageView = new ImageView(this);

        lapDisplay.setGravity(Gravity.CENTER);
        lapDisplay.setTextColor(Color.WHITE);
        lapDisplay.setTextSize(30);

        linearLayout.addView(lapDisplay);
        linearLayout.addView(imageView);

        lapDisplay.setText(TimeFormatUtil.toDisplayString(lapTime));
        imageView.setImageResource(R.drawable.divider);
        lapTime = 0;
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.stopwatch, menu);
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


}
