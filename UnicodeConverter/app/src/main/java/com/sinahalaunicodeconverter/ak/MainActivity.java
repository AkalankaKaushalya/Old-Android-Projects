package com.sinahalaunicodeconverter.ak;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.sinahalaunicodeconverter.ak.adapter.FontAdapter;
import com.sinahalaunicodeconverter.ak.model.Font;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
     RecyclerView fontsRV;
     ArrayList<Font> fontsItems = new ArrayList<>();
     EditText fontet;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fontsRV = findViewById(R.id.fontRc);
        fontet = findViewById(R.id.NameEt);

        if (fontsItems.isEmpty()) {

            final Font f1 = new Font("Bubble");
            final Font f2 = new Font("Currency");
            final Font f3 = new Font("Magic");
            final Font f4 = new Font("Knight");
            final Font f5 = new Font("Antrophobia");
            final Font f6 = new Font("Fancy style 1");
            final Font f7 = new Font("Fancy style 2");
            final Font f8 = new Font("Fancy style 3");
            final Font f9 = new Font("Fancy style 4");
            final Font f10 = new Font("Fancy style 5");
            final Font f11 = new Font("Fancy style 6");
            final Font f12 = new Font("Fancy style 7");
            final Font f13 = new Font("Fancy style 8");
            final Font f14 = new Font("Fancy style 9");
            final Font f15 = new Font("Fancy style 10");
            final Font f16 = new Font("Fancy style 11");
            final Font f17 = new Font("Fancy style 12");
            final Font f18 = new Font("Fancy style 13");
            final Font f19 = new Font("Fancy style 14");
            final Font f20 = new Font("Fancy style 15");
            final Font f21 = new Font("Aries");
            final Font f22 = new Font("Taurus");
            final Font f23 = new Font("Gemini");
            final Font f24 = new Font("Cancer");
            final Font f25 = new Font("Leo");
            final Font f26 = new Font("Virgo");
            final Font f27 = new Font("Libra");
            final Font f28 = new Font("Scorpius");
            final Font f29 = new Font("Sagittarius");
            final Font f30 = new Font("Capricorn");
            final Font f31 = new Font("Aquarius");
            final Font f32 = new Font("Pisces");
            final Font f33 = new Font("Senol");

            fontsItems.add(f1);
            fontsItems.add(f2);
            fontsItems.add(f3);
            fontsItems.add(f4);
            fontsItems.add(f5);
            fontsItems.add(f6);
            fontsItems.add(f7);
            fontsItems.add(f8);
            fontsItems.add(f9);
            fontsItems.add(f10);
            fontsItems.add(f11);
            fontsItems.add(f12);
            fontsItems.add(f13);
            fontsItems.add(f14);
            fontsItems.add(f15);
            fontsItems.add(f16);
            fontsItems.add(f17);
            fontsItems.add(f18);
            fontsItems.add(f19);
            fontsItems.add(f20);
            fontsItems.add(f21);
            fontsItems.add(f22);
            fontsItems.add(f23);
            fontsItems.add(f24);
            fontsItems.add(f25);
            fontsItems.add(f26);
            fontsItems.add(f27);
            fontsItems.add(f28);
            fontsItems.add(f29);
            fontsItems.add(f30);
            fontsItems.add(f31);
            fontsItems.add(f32);
            fontsItems.add(f33);

        } else {
            fontsItems.clear();
            final Font f1 = new Font("Bubble");
            final Font f2 = new Font("Currency");
            final Font f3 = new Font("Magic");
            final Font f4 = new Font("Knight");
            final Font f5 = new Font("Antrophobia");
            final Font f6 = new Font("Fancy style 1");
            final Font f7 = new Font("Fancy style 2");
            final Font f8 = new Font("Fancy style 3");
            final Font f9 = new Font("Fancy style 4");
            final Font f10 = new Font("Fancy style 5");
            final Font f11 = new Font("Fancy style 6");
            final Font f12 = new Font("Fancy style 7");
            final Font f13 = new Font("Fancy style 8");
            final Font f14 = new Font("Fancy style 9");
            final Font f15 = new Font("Fancy style 10");
            final Font f16 = new Font("Fancy style 11");
            final Font f17 = new Font("Fancy style 12");
            final Font f18 = new Font("Fancy style 13");
            final Font f19 = new Font("Fancy style 14");
            final Font f20 = new Font("Fancy style 15");
            final Font f21 = new Font("Aries");
            final Font f22 = new Font("Taurus");
            final Font f23 = new Font("Gemini");
            final Font f24 = new Font("Cancer");
            final Font f25 = new Font("Leo");
            final Font f26 = new Font("Virgo");
            final Font f27 = new Font("Libra");
            final Font f28 = new Font("Scorpius");
            final Font f29 = new Font("Sagittarius");
            final Font f30 = new Font("Capricorn");
            final Font f31 = new Font("Aquarius");
            final Font f32 = new Font("Pisces");
            final Font f33 = new Font("Senol");

            fontsItems.add(f1);
            fontsItems.add(f2);
            fontsItems.add(f3);
            fontsItems.add(f4);
            fontsItems.add(f5);
            fontsItems.add(f6);
            fontsItems.add(f7);
            fontsItems.add(f8);
            fontsItems.add(f9);
            fontsItems.add(f10);
            fontsItems.add(f11);
            fontsItems.add(f12);
            fontsItems.add(f13);
            fontsItems.add(f14);
            fontsItems.add(f15);
            fontsItems.add(f16);
            fontsItems.add(f17);
            fontsItems.add(f18);
            fontsItems.add(f19);
            fontsItems.add(f20);
            fontsItems.add(f21);
            fontsItems.add(f22);
            fontsItems.add(f23);
            fontsItems.add(f24);
            fontsItems.add(f25);
            fontsItems.add(f26);
            fontsItems.add(f27);
            fontsItems.add(f28);
            fontsItems.add(f29);
            fontsItems.add(f30);
            fontsItems.add(f31);
            fontsItems.add(f32);
            fontsItems.add(f33);
        }



        //fontsRV = view.findViewById(R.id.recycle_view_FF);

        final FontAdapter adapter = new FontAdapter(fontsItems, this);
        fontsRV.setLayoutManager(new LinearLayoutManager(this));
        fontsRV.setAdapter(adapter);

        fontet.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @SuppressLint("NotifyDataSetChanged")
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String editTextStr = fontet.getText().toString();

                if (editTextStr.isEmpty() || editTextStr.equals(" ")) {
                    editTextStr = "Name";
                }

                for (int item = 0; item < fontsItems.size(); item++) {
                    fontsItems.get(item).setPreviewText(editTextStr);
                    adapter.notifyDataSetChanged();


                }
            }
        });


    }
}