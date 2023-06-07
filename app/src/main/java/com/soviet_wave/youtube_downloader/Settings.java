package com.soviet_wave.youtube_downloader;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

public class Settings extends AppCompatActivity {
    private Switch debug_sweech;
    private TextView debug_text;
    private Switch material_switch;
    private SharedPreferences pref;
    private SharedPreferences pref_theme;
    private final String save_key = "save_key";
    public static final String APP_PREFERENCES = "settings";
    public static final String MATERIAL_THEME = "theme";





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        pref = getSharedPreferences(MATERIAL_THEME, Context.MODE_PRIVATE);
        boolean material_mode = pref.getBoolean("material_mode",false);
        if (material_mode == false) {
            setTheme(R.style.Theme_YouTube_downloader);


        } else {
            setTheme(R.style.Theme_MaterialYou);
        }
        super.onCreate(savedInstanceState);
        if (material_mode == false){
            setContentView(R.layout.activity_settings);
        }else {
            setContentView(R.layout.activity_settings_material);
        }

        Switch debug_sweech = (Switch) findViewById(R.id.switch_debug);
        Switch material_sweech = (Switch) findViewById(R.id.material_switch);
        TextView debug_text = findViewById(R.id.textView2);
        pref = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
        boolean debug_mode = pref.getBoolean("debug_mode",false);
        pref_theme = getSharedPreferences(MATERIAL_THEME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor_debug = pref.edit();
        SharedPreferences.Editor editor = pref_theme.edit();
        if(debug_mode == true){
            debug_sweech.setChecked(true);
        }else {
            debug_sweech.setChecked(false);
        }
        if(material_mode == true){
            material_sweech.setChecked(true);
        }else {
            material_sweech.setChecked(false);
        }




        debug_sweech.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                // при изменении состояния switch новое значение
                //будет сразу записано в соответствующее "поле" в Ваших SharedPreferences


                if (debug_mode == false) {
                    debug_text.setText("debug_mode is on");
                    editor_debug.putBoolean("debug_mode", true);
                    editor_debug.apply();

                } else {
                    debug_text.setText("debug_mode is off");
                    editor_debug.putBoolean("debug_mode", false);
                    editor_debug.apply();
                }
            }
        });

        material_sweech.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                int android_version = Integer.parseInt(android.os.Build.VERSION.RELEASE);
                boolean version = false;

                if(android_version==12) {
                    Log.d("android_version", "Подходит");
                    version = true;
                }
                else {
                    version = false;
                    Log.d("android_version", "Не подходит");
                    Toast.makeText(Settings.this, getString(R.string.android_ver), Toast.LENGTH_SHORT).show();

                }
                if(version==true) {
                    AlertDialog.Builder reboot_material = new AlertDialog.Builder(Settings.this);
                    reboot_material.setMessage(getString(R.string.rebootM_Text))
                            .setCancelable(false)
                            .setPositiveButton(getString(R.string.rebootM_OK), new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    Log.d("REBOOT_MATERIAL", "OK");
                                    if (material_mode == false) {
                                        editor.putBoolean("material_mode", true);
                                        //debug_text.setText("material_mode is on");
                                        editor.apply();


                                    } else {
                                        editor.putBoolean("material_mode", false);
                                        //debug_text.setText("material_mode is off");
                                        editor.apply();
                                    }
                                }
                            })
                            .setNegativeButton(getString(R.string.rebootM_CANCEL), new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    Log.d("REBOOT_MATERIAL", "Отмена");

                                }
                            });
                    AlertDialog dialog = reboot_material.create();
                    dialog.setTitle(getString(R.string.rebootM_Title));
                    dialog.show();


                }
            }
        });
    }
}