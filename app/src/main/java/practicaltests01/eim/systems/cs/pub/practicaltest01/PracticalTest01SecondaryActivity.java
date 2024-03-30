package practicaltests01.eim.systems.cs.pub.practicaltest01;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class PracticalTest01SecondaryActivity extends AppCompatActivity {

    EditText sumEditText;
    Button okButton;
    Button cancelButton;

    private ButtonClickListener buttonClickListener = new ButtonClickListener();
    private class ButtonClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            if (view.getId() == R.id.ok_button) {
                setResult(RESULT_OK, null);
                finish();
            } else if (view.getId() == R.id.cancel_button) {
                setResult(RESULT_CANCELED, null);
                finish();
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practical_test01_secondary);

        sumEditText = (EditText) findViewById(R.id.sum_text);
        okButton = (Button) findViewById(R.id.ok_button);
        cancelButton = (Button) findViewById(R.id.cancel_button);

        Intent intent = getIntent();
        if (intent != null && intent.getExtras().containsKey("sum_text")) {
            int nr = intent.getIntExtra("sum_text", -1);
            sumEditText.setText(String.valueOf(nr));
        }

        okButton.setOnClickListener(buttonClickListener);
        cancelButton.setOnClickListener(buttonClickListener);
    }

}
