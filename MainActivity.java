package com.apps.bluj.binaryapp;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    TextView topLabel;
    TextView bottomLabel;
    EditText topTextBox;
    EditText bottomTextBox;
    Button encodeButton;
    Button decodeButton;
    Button swapButton;
    Button clearButton;
    Button shareButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        topLabel = (TextView) findViewById(R.id.topLabel);
        bottomLabel = (TextView) findViewById(R.id.bottomLabel);
        topTextBox = (EditText) findViewById(R.id.topTextBox);
        bottomTextBox = (EditText) findViewById(R.id.bottomTextBox);
        encodeButton = (Button) findViewById(R.id.encodeButton);
        decodeButton = (Button) findViewById(R.id.decodeButton);
        swapButton = (Button) findViewById(R.id.swapButton);
        clearButton = (Button) findViewById(R.id.clearButton);
        shareButton = (Button) findViewById(R.id.shareButton);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

    static String toBinaryByte(int n) {
        String bin = toBinary(n);

        while (bin.length() < 8) {
            bin = "0" + bin;
        }
        return bin;
    }

    static String toBinary(int n) {
        if (n == 0) {
            return "0";
        }
        String binary = "";
        while (n > 0) {
            int remainder = n % 2;
            binary = remainder + binary;
            n /= 2;
        }
        return binary;
    }

    static String decodeBinary(String bin) {
        String message = "";
        for (int i = 0; i + 7 < bin.length(); i = i + 8){
            String currentByte = bin.substring(i, i + 8);
            Character c = (char) toDecimal(currentByte);
            message += c;
        }
        return message;
    }

    static int toDecimal(String bin) {
        int binary = Integer.parseInt(bin);
        int decimal = 0;
        int power = 0;

        while (binary > 0) {
            decimal += (int) ((binary % 10) * Math.pow(2, power));
            binary /= 10;
            power++;
        }
        return decimal;
    }

    static String encodeBinary(String message) {
        String bin = "";
        char[] messArray = message.toCharArray();

        for (int i = 0; i < messArray.length; i++) {
            int temp = messArray[i];
            bin += toBinaryByte(temp);
        }
        return bin;
    }

    public void encodeBinary(View view) {
        String message = topTextBox.getText().toString();
        bottomTextBox.setText(encodeBinary(message));
        encodeButton.setVisibility(View.GONE);
        clearButton.setVisibility(View.VISIBLE);
        swapButton.setVisibility(View.GONE);
        shareButton.setVisibility(View.VISIBLE);
    }

    public void Share(View view) {
        String binaryString = bottomTextBox.getText().toString();
        ClipboardManager clipboard = (ClipboardManager)
                getSystemService(Context.CLIPBOARD_SERVICE);

        ClipData clip = ClipData.newPlainText("simple text", binaryString);
        clipboard.setPrimaryClip(clip);

        Context context = getApplicationContext();
        String text = "Copied to clipboard you nerd...";
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }

    public void clear(View view) {
        topTextBox.setText("");
        bottomTextBox.setText("");
        clearButton.setVisibility(View.GONE);
        shareButton.setVisibility(View.INVISIBLE);
        swapButton.setVisibility(View.VISIBLE);

        String currentLabel = topLabel.getText().toString().toLowerCase();

        switch (currentLabel) {
            case "text":
                encodeButton.setVisibility(View.VISIBLE);
                break;

            case "binary":
                decodeButton.setVisibility(View.VISIBLE);
                break;
        }
    }

    public void swap(View view) {
        String currentLabel = topLabel.getText().toString().toLowerCase();

        switch (currentLabel) {
            case "text":
                encodeButton.setVisibility(View.GONE);
                decodeButton.setVisibility(View.VISIBLE);
                topLabel.setText("Binary");
                bottomLabel.setText("Text");
                break;

            case "binary":
                decodeButton.setVisibility(View.GONE);
                encodeButton.setVisibility(View.VISIBLE);
                topLabel.setText("Text");
                bottomLabel.setText("Binary");
        }
    }

    public void decodeBinary(View view) {
        String message = topTextBox.getText().toString();
        bottomTextBox.setText(decodeBinary(message));
        decodeButton.setVisibility(View.GONE);
        clearButton.setVisibility(View.VISIBLE);
        swapButton.setVisibility(View.GONE);
        shareButton.setVisibility(View.VISIBLE);
    }
}
