package com.example.converion;
import android.annotation.SuppressLint;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.DigitsKeyListener;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private RadioGroup RG;
    private Button Convert;
    private TextView decimalT,hexT,binT,octT;
    private RadioButton decimalR,hexR,binR,octR;
    private EditText editing;
    private int inputType;
     @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RG = findViewById(R.id.radiog);
        Convert = (Button)findViewById(R.id.Convert);

        decimalT = (TextView)findViewById(R.id.ad);
        hexT = (TextView)findViewById(R.id.ah);
        binT = (TextView)findViewById(R.id.ab);
        octT = (TextView)findViewById(R.id.ao);

        decimalR = (RadioButton)findViewById(R.id.D);
        hexR = (RadioButton)findViewById(R.id.H);
        binR = (RadioButton)findViewById(R.id.B);
        octR = (RadioButton)findViewById(R.id.O);

        editing = (EditText)findViewById(R.id.Editing);
        inputType = 10;
        decimalR.setChecked(true);
        hexR.setOnClickListener(this);
        binR.setOnClickListener(this);
        octR.setOnClickListener(this);
        decimalR.setOnClickListener(this);
        Convert.setOnClickListener(this);
        editing.setKeyListener(DigitsKeyListener.getInstance("0123456789abcdef"));

        RG.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {

            RadioButton radioButton = (RadioButton) findViewById(checkedId);
            //    Toast.makeText(getApplicationContext(), radioButton.getText(), Toast.LENGTH_LONG).show();

            if (radioButton.getText().equals("B")) {
                editing.setKeyListener(DigitsKeyListener.getInstance("01"));

            } else if (radioButton.getText().equals("D")) {
                editing.setKeyListener(DigitsKeyListener.getInstance("0123456789"));
            } else if (radioButton.getText().equals("O")) {
                editing.setKeyListener(DigitsKeyListener.getInstance("01234567"));
            } else if (radioButton.getText().equals("H")) {
                 editing.setKeyListener(DigitsKeyListener.getInstance("0123456789abcdefABCDEF"));
            }
        editing.setText("");
            }


    });

        editing.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void afterTextChanged(Editable editable)
            {
                if(editing.length() == 0) {
                    Convert.setEnabled(false);
                    decimalR.setEnabled(false);
                    hexR.setEnabled(false);
                    binR.setEnabled(false);
                    octR.setEnabled(false);
                    decimalT.setText("empty");
                    hexT.setText("empty");
                    octT.setText("empty");
                    binT.setText("empty");
                } else {
                    Convert.setEnabled(true);
                    decimalR.setEnabled(true);
                    hexR.setEnabled(true);
                    binR.setEnabled(true);
                    octR.setEnabled(true);
                }

                Convert.callOnClick();
            }
        });

    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.H) {
            inputType = 16;
            Convert.callOnClick();
        }
        else if(view.getId() == R.id.B) {
            inputType = 2;
            Convert.callOnClick();
        }
        else if(view.getId() == R.id.D) {
            inputType = 10;
            Convert.callOnClick();
        }
        else if(view.getId() == R.id.O) {
            inputType = 8;
            Convert.callOnClick();
        }
        else if(view.getId() == R.id.Convert) {
            if(editing.length()!= 0) {
                String s = editing.getText().toString();
                switch (inputType){
                    case 10:
                        editing.setKeyListener(DigitsKeyListener.getInstance("0123456789"));
                        decimalT.setText(s);
                        hexT.setText("" + Long.toHexString(Long.valueOf(s)));
                        binT.setText("" + Long.toBinaryString(Long.valueOf(s)));
                        octT.setText(""+ Long.toOctalString(Long.valueOf(s)));
                        break;
                    case 16:
                        editing.setKeyListener(DigitsKeyListener.getInstance("0123456789abcdef"));
                        decimalT.setText("" + Long.parseLong(s,16) );
                        hexT.setText(s);
                        binT.setText("" + Long.toBinaryString(Long.parseLong(s, 16)));
                        octT.setText("" + Long.toOctalString(Long.parseLong(s, 16)));
                        break;
                    case 8:
                        try {
                            editing.setKeyListener(DigitsKeyListener.getInstance("01234567"));
                            decimalT.setText("" + Long.parseLong(s,8) );
                            hexT.setText("" + Long.toHexString(Long.parseLong(s,8)));
                            binT.setText("" + Long.toBinaryString(Long.parseLong(s, 8)));
                            octT.setText(s);

                        }catch (Exception ex){
                            Toast.makeText(this,"Just allowded: 0 => 7",Toast.LENGTH_LONG).show();
                        }
                        break;
                    case 2:
                        try {
                            editing.setKeyListener(DigitsKeyListener.getInstance("01"));
                            decimalT.setText("" + Long.parseLong(s,2) );
                            hexT.setText("" + Long.toHexString(Long.parseLong(s,2)));
                            binT.setText(s);
                            octT.setText("" + Long.toOctalString(Long.parseLong(s, 2)));
                        }catch (Exception e){
                            Toast.makeText(this,"Just allowded: ( 0 or 1)",Toast.LENGTH_LONG).show();
                        }
                        break;

                } //end of switch
            } //end of if

        } // end of else if
    } //end of onClick
}
