package dimcho.proj.stopwatch;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.TextView;


public class HelpActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);

        TextView textView = (TextView) findViewById(R.id.help_message);

        textView.setText("Created by Dimcho.\n" +
                "\nMore information about the app on: https://github.com/dimchoSCR/android-stopwatch\n"
                +"\nOr you can send me an email: dimchokarpachev@gmail.com");

    }
}
