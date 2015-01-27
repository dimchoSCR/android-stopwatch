package dimcho.proj.stopwatch;

import android.animation.LayoutTransition;
import android.app.Activity;
import android.content.Intent;
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
    private Button lapButton;
    private TextView textView;
    private Timer timer;
    private LayoutTransition transition;
    private LinearLayout linearLayout;
    private int currentTime = 0;
    private int lapTime = 0;
    private int lapCounter=0;
    private boolean lapViewExists;
    private boolean isButtonStartPressed = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stopwatch);

        startButton = (Button) findViewById(R.id.btn_start);
        lapButton = (Button) findViewById(R.id.btn_lap);
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
        if (isButtonStartPressed) {
            onSWatchStop();
        } else {
            isButtonStartPressed = true;

            startButton.setBackgroundResource(R.drawable.btn_stop_states);
            startButton.setText(R.string.btn_stop);

            lapButton.setBackgroundResource(R.drawable.btn_lap_states);
            lapButton.setText(R.string.btn_lap);
            lapButton.setEnabled(true);

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
    }

    public void onSWatchStop() {
        startButton.setBackgroundResource(R.drawable.btn_start_states);
        startButton.setText(R.string.btn_start);
        lapButton.setEnabled(true);
        lapButton.setBackgroundResource(R.drawable.btn_lap_states);
        lapButton.setText(R.string.btn_reset);

        isButtonStartPressed = false;
        timer.cancel();
    }

    public void onSWatchReset() {
        timer.cancel();
        currentTime = 0;
        lapTime = 0;
        lapCounter=0;
        textView.setText(TimeFormatUtil.toDisplayString(currentTime));
        lapButton.setEnabled(false);
        lapButton.setText(R.string.btn_lap);
        lapButton.setBackgroundResource(R.drawable.btn_reset_states);

        if (lapViewExists) {
            linearLayout.setLayoutTransition(null);
            linearLayout.removeAllViews();
            lapViewExists = false;
        }
    }

    public void onSWatchLap(View view) {
        if(!isButtonStartPressed){
            onSWatchReset();
        }else {
            lapViewExists = true;
            lapCounter++;

            transition = new LayoutTransition();
            transition.setAnimator(LayoutTransition.CHANGE_APPEARING,null);
            transition.setStartDelay(LayoutTransition.APPEARING,0);

            linearLayout = (LinearLayout) findViewById(R.id.layout);
            linearLayout.setLayoutTransition(transition);

            TextView lapDisplay = new TextView(this);
            ImageView imageView = new ImageView(this);
            imageView.setFocusableInTouchMode(true);

            lapDisplay.setGravity(Gravity.CENTER);
            lapDisplay.setTextColor(Color.WHITE);
            lapDisplay.setTextSize(30);

            linearLayout.addView(lapDisplay);
            linearLayout.addView(imageView);

            imageView.requestFocus();

            lapDisplay.setText(String.format("Lap %d: %s", lapCounter, TimeFormatUtil.toDisplayString(lapTime)));
            imageView.setImageResource(R.drawable.divider);
            lapTime = 0;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.stopwatch, menu);
        return true;
    }

    public boolean onOptionsItemSelected (MenuItem item){
        switch(item.getItemId()){
            case R.id.action_help:
                openHelp();
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void openHelp(){
        Intent intent = new Intent(this,HelpActivity.class);
        startActivity(intent);
    }
}
