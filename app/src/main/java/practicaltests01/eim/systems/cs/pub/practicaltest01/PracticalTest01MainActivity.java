package practicaltests01.eim.systems.cs.pub.practicaltest01;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class PracticalTest01MainActivity extends AppCompatActivity {

    private EditText leftEditText;
    private EditText rightEditText;

    private Button leftButton;
    private Button rightButton;

    private Button navigateToSecondaryActivityButton;

    private IntentFilter intentFilter = new IntentFilter();

    private ButtonClickListener buttonClickListener = new ButtonClickListener();

    private class ButtonClickListener implements View.OnClickListener {

        @Override
        public void onClick(View view) {

            int leftCount = Integer.parseInt(leftEditText.getText().toString());
            int rightCount = Integer.parseInt(rightEditText.getText().toString());

            if (leftCount + rightCount > 10) {
                Intent intent = new Intent(getApplicationContext(), PracticalTest01Service.class);
                intent.putExtra("edit_left_text", leftCount);
                intent.putExtra("edit_right_text", rightCount);
                getApplicationContext().startService(intent);
            }

            if (view.getId() == R.id.left_button) {
                String leftText = leftEditText.getText().toString();
                leftEditText.setText(String.valueOf(Integer.parseInt(leftText) + 1));

            } else if (view.getId() == R.id.right_button) {
                String rightText = rightEditText.getText().toString();
                rightEditText.setText(String.valueOf(Integer.parseInt(rightText) + 1));
            } else if (view.getId() == R.id.navigate_to_second_activity) {

                Intent intent = new Intent(getApplicationContext(), PracticalTest01SecondaryActivity.class);
                int suma = leftCount + rightCount;
                intent.putExtra("sum_text", suma);
                startActivityForResult(intent, 1);
            }
        }
    }

    protected void onDestroy() {
        Intent intent = new Intent(this, PracticalTest01Service.class);
        stopService(intent);
        super.onDestroy();
    }

    private MessageBroadcastReceiver messageBroadcastReceiver = new MessageBroadcastReceiver();
    private class MessageBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d("received ", intent.getStringExtra("message"));
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(messageBroadcastReceiver, intentFilter);
    }

    @Override
    protected void onPause() {
        unregisterReceiver(messageBroadcastReceiver);
        super.onPause();
    }

    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putString("edit_left_text", leftEditText.getText().toString());
        savedInstanceState.putString("edit_right_text", rightEditText.getText().toString());
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        if (savedInstanceState.containsKey("edit_left_text")) {
            leftEditText.setText(savedInstanceState.getString("edit_left_text"));
        } else {
            leftEditText.setText("0");
        }
        if (savedInstanceState.containsKey("edit_right_text")) {
            rightEditText.setText(savedInstanceState.getString("edit_right_text"));
        } else {
            rightEditText.setText("0");
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if (requestCode == 1) {
            Toast.makeText(this, "Activity returned with " + resultCode, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        leftEditText = (EditText) findViewById(R.id.edit_left_text);
        rightEditText = (EditText) findViewById(R.id.edit_right_text);

        leftButton = (Button) findViewById(R.id.left_button);
        rightButton = (Button) findViewById(R.id.right_button);
        navigateToSecondaryActivityButton = (Button) findViewById(R.id.navigate_to_second_activity);

        leftButton.setOnClickListener(buttonClickListener);
        rightButton.setOnClickListener(buttonClickListener);
        navigateToSecondaryActivityButton.setOnClickListener(buttonClickListener);

        for (int index = 0; index < Constants.actionTypes.length; index++) {
            intentFilter.addAction(Constants.actionTypes[index]);
        }

        if (savedInstanceState != null) {
            if (savedInstanceState.containsKey("edit_left_text")) {
                leftEditText.setText(savedInstanceState.getString("edit_left_text"));
            } else {
                leftEditText.setText("0");
            }
            if (savedInstanceState.containsKey("edit_right_text")) {
                rightEditText.setText(savedInstanceState.getString("edit_right_text"));
            } else {
                rightEditText.setText("0");
            }
        } else {
            leftEditText.setText("0");
            rightEditText.setText("0");
        }

    }
}