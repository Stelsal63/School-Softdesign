package com.softdesign.school;

import android.content.res.Resources;
import android.os.Build;
import android.os.PersistableBundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;
import android.support.v7.widget.Toolbar;

import com.softdesign.school.utils.Lg;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    public static  final String VISIBLE_KEY = "visible";
    public static  final String STATUS_BAR_KEY = "sbColor";
    public static  final String TOOLBAR_KEY = "tbColor";

    private int mColor;

    CheckBox mCheckBox;

    EditText mEditText;
    EditText mEditText2;

    Toolbar mToolbar;

    private Button mButtonRed;
    private Button mButtonBlue;
    private Button mButtonGreen;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Lg.e(this.getLocalClassName(), "=============================");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setTitle("School Lesson 1");

        Lg.e(this.getLocalClassName(), "on create");
        mCheckBox = (CheckBox) findViewById(R.id.checkBox);
        mCheckBox.setOnClickListener(this);
        mEditText = (EditText) findViewById(R.id.editText);
        mEditText2 = (EditText) findViewById(R.id.editText2);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mButtonBlue = (Button) findViewById(R.id.button_blue);
        mButtonBlue.setOnClickListener(this);
        mButtonGreen = (Button) findViewById(R.id.button_green);
        mButtonGreen.setOnClickListener(this);
        mButtonRed = (Button) findViewById(R.id.button_red);
        mButtonRed.setOnClickListener(this);

        setupToolbar();

    }

    private void setupToolbar(){
        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeAsUpIndicator(R.drawable.ic_menu_24dp);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home){
            Toast.makeText(this, "Menu click", Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.checkBox:
                Toast.makeText(this, "Click!", Toast.LENGTH_SHORT).show();
                if (mCheckBox.isChecked()){
                    mEditText2.setVisibility(View.INVISIBLE);
                } else {
                    mEditText2.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.button_blue:
                setBackgroundAndStatusBarColor(R.color.blue,R.color.darkblue);
                break;
            case R.id.button_green:
                setBackgroundAndStatusBarColor(R.color.green, R.color.darkgreen);
                break;
            case R.id.button_red:
                setBackgroundAndStatusBarColor(R.color.red,R.color.darkred);
                break;

        }
    }

    private void setBackgroundAndStatusBarColor(int tbColor, int sbColor) {
        mToolbar.setBackgroundResource(tbColor);
        mColor = tbColor;
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(getResources().getColor(sbColor));
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        Lg.e(this.getLocalClassName(), "on start");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Lg.e(this.getLocalClassName(), "on resume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Lg.e(this.getLocalClassName(), "on pause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Lg.e(this.getLocalClassName(), "on stop");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Lg.e(this.getLocalClassName(), "on restart");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Lg.e(this.getLocalClassName(), "on destroy");
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Lg.e(this.getLocalClassName(), "on save instance state");
        outState.putBoolean(VISIBLE_KEY, mEditText2.getVisibility() == View.VISIBLE);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            outState.putInt(STATUS_BAR_KEY, getWindow().getStatusBarColor());
        outState.putInt(TOOLBAR_KEY, mColor);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Lg.e(this.getLocalClassName(), "on restore instance state");
        int visibleState = savedInstanceState.getBoolean(VISIBLE_KEY) ? View.VISIBLE : View.INVISIBLE;

        int tbColor =savedInstanceState.getInt(TOOLBAR_KEY);
        mColor = tbColor;
        mToolbar.setBackgroundResource(tbColor);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            int sbColor = savedInstanceState.getInt(STATUS_BAR_KEY);
            getWindow().setStatusBarColor(sbColor);
        }
        mEditText2.setVisibility(visibleState);
    }
}
