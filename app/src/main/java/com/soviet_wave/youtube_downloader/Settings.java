package com.soviet_wave.youtube_downloader;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ScrollView;
import android.widget.Switch;
import android.widget.TextView;

public class Settings extends AppCompatActivity {
    private Switch debug_sweech;
    private TextView debug_text;
    private SharedPreferences pref;
    private final String save_key = "save_key";
    public static final String APP_PREFERENCES = "settings";




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        Switch debug_sweech = (Switch) findViewById(R.id.switch_debug);
        TextView debug_text = findViewById(R.id.textView2);
        pref = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
        boolean debug_mode = pref.getBoolean("debug_mode",false);
        SharedPreferences.Editor editor = pref.edit();
        if(debug_mode == true){
            debug_sweech.setChecked(true);
        }else {
            debug_sweech.setChecked(false);
        }


        debug_sweech.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                // при изменении состояния switch новое значение
                //будет сразу записано в соответствующее "поле" в Ваших SharedPreferences


                    if (debug_mode == false) {
                        debug_text.setText("debug_mode is on");
                        editor.putBoolean("debug_mode", true);
                        editor.apply();

                    } else {
                        debug_text.setText("debug_mode is off");
                        editor.putBoolean("debug_mode", false);
                        editor.apply();
                    }
            }
        });
    }
}