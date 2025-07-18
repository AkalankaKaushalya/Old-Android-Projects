package com.idnaf.textconverter;

import android.app.Activity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Hashtable;

public class MainActivity extends Activity
{
    private Spinner spMenu;
    private Button btGo;
    private EditText etInput;
    private TextView tvOutput;
    private String StringClean(String input)
    {
        return input.replace(" ","").replace("\t","").replace("\r","").replace("\n","");
    }
    private boolean StringCheckHexString(String input)
    {

        if(input.length() % 2 != 0)
        {
            return false;
        }
        String temp = input;
        for(int i = 0; i < temp.length() ; i+= 2)
        {
            try
            {
                int itemp = Integer.parseInt(temp.substring(i, i+2));
            }
            catch(NumberFormatException e)
            {
                return false;

            }
        }

        return true;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        spMenu = (Spinner) findViewById(R.id.spMenu);
        btGo = (Button) findViewById(R.id.btGo);
        etInput = (EditText) findViewById(R.id.etInput);
        tvOutput = (TextView) findViewById(R.id.tvOutput);


        tvOutput.setOnCreateContextMenuListener(new View.OnCreateContextMenuListener()
        {
            @Override
            public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo)
            {

            }
        });
        btGo.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {

                tvOutput.setText("");
                int menuPosition = spMenu.getSelectedItemPosition();
                String menu = getResources().getStringArray(R.array.menu)[menuPosition];


                String input = etInput.getText().toString();
                StringBuilder output = new StringBuilder();

                switch(menuPosition)
                {
                    // ASCII to hex string
                    case 0:
                        char[] chars = input.toCharArray();
                        for(int i = 0; i < chars.length; i++)
                        {
                            int j = (int) chars[i];
                            output.append(String.format("%02X", j));
                            if(i != (chars.length - 1))
                            {
                                output.append(" ");
                            }
                        }
                        tvOutput.setText(output);

                        break;
                    // Hex string to ASCII
                    case 1:
                        input = StringClean(input).toUpperCase();
                        if(StringCheckHexString(input) == false)
                        {
                            Toast.makeText(getApplicationContext(), "Invalid hex string format", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        for(int i=0;i < input.length(); i+= 2)
                        {
                            String temp = input.substring(i, i + 2);
                            output.append((char) Integer.parseInt(temp, 16));
                        }
                        tvOutput.setText(output);

                        break;
                    // Clean string
                    case 2:
                        tvOutput.setText(StringClean(input));
                        break;
                    // To upper case
                    case 3:
                        tvOutput.setText(input.toUpperCase());
                        break;
                    case 4:
                    // To lower case
                        tvOutput.setText(input.toLowerCase());
                        break;

                }
            }
        });

    }
}
