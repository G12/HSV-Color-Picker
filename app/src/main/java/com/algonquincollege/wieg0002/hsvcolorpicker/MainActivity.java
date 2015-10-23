package com.algonquincollege.wieg0002.hsvcolorpicker;

import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.PaintDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RectShape;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    RelativeLayout colorPatch;

    SeekBar seekBarHue;
    SeekBar seekBarSaturation;
    SeekBar seekBarValue;
    SeekBar seekBarAlpha;

    TextView textViewHue;
    TextView textViewSaturation;
    TextView textViewValue;
    TextView textViewAlpha;

    final float CYAN = 180;
    final float OPAQUE = 1;
    final float INTENSE = 1;
    float[] _hsv = new float[]{CYAN,OPAQUE,INTENSE};
    float alpha = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        //Just Hide Floating Action Bar for now
        fab.hide();

        //Get the view to display color on
        colorPatch = (RelativeLayout)findViewById(R.id.colorPatch);

        //Create the Gradient Background for Hue SeekBar
        ShapeDrawable.ShaderFactory shaderFactory = new ShapeDrawable.ShaderFactory() {
            @Override
            public Shader resize(int width, int height) {
                LinearGradient linearGradient = new LinearGradient(0, 0, width, height,
                        new int[] {
                                0xFFFF0000,
                                0xFFFFFF00,
                                0xFF00FF00,
                                0xFF00FFFF,
                                0xFF0000FF,
                                0xFFFF00FF,
                                0xFFFF0000 },
                        //if null distribute evenly
                        //null,
                        new float[] {0, 0.20f, 0.333f, 0.50f, 0.667f, 0.80f, 1 },
                        Shader.TileMode.REPEAT);
                return linearGradient;
            }
        };
        PaintDrawable paint = new PaintDrawable();
        paint.setShape(new RectShape());
        paint.setShaderFactory(shaderFactory);

        seekBarHue = (SeekBar) findViewById(R.id.seekBarHue);
        //Set background as constructed above
        seekBarHue.setBackground(paint);
        seekBarHue.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    _hsv[0] = (float) progress;
                    setChanges(false);
                }
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        seekBarSaturation = (SeekBar) findViewById(R.id.seekBarSaturation);
        seekBarSaturation.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if(fromUser) {
                    _hsv[1] = (float)progress/100;
                    setChanges(false);
                }
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        seekBarValue = (SeekBar) findViewById(R.id.seekBarValue);
        seekBarValue.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    _hsv[2] = (float) progress / 100;
                    setChanges(false);
                }
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        seekBarAlpha = (SeekBar) findViewById(R.id.seekBarAlpha);
        seekBarAlpha.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if(fromUser)
                {
                    alpha = (float)progress/100;
                    setChanges(false);
                }
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        colorPatch.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
            Toast.makeText(getBaseContext(), "hue: " + _hsv[0] + (char) 0x00B0 + " saturation: " + _hsv[1] * 100 + "% value: " + _hsv[2] * 100 + "%", Toast.LENGTH_LONG).show();
            return false;
            }
        });

        textViewHue = (TextView) findViewById(R.id.textViewHue);
        textViewSaturation = (TextView) findViewById(R.id.textViewSaturation);
        textViewValue = (TextView) findViewById(R.id.textViewValue);
        textViewAlpha = (TextView) findViewById(R.id.textViewAlpha);

        setChanges(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void colorButtonClick(View v)
    {
        Button btn = (Button)v;
        ColorDrawable buttonColor = (ColorDrawable) btn.getBackground();
        int colorId = buttonColor.getColor();
        alpha = (float)buttonColor.getAlpha()/255;
        Color.colorToHSV(colorId, _hsv);
        Log.v("DEBUG", "hue: " + _hsv[0] + " saturation: " + _hsv[1] + " value: " + _hsv[2]);
        setChanges(true);
    }

    public void setChanges(boolean setSeekBars)
    {
        int colorId = Color.HSVToColor(_hsv);
        Log.v("DEBUG", "colorId: " + colorId + " hue: " + _hsv[0] + " saturation: " + _hsv[1] + " value: " + _hsv[2]);
        colorPatch.setBackgroundColor(colorId);
        colorPatch.setAlpha(alpha);

        if(setSeekBars)
        {
            seekBarHue.setProgress((int)_hsv[0]);
            seekBarSaturation.setProgress((int)(_hsv[1]*100));
            seekBarValue.setProgress((int)(_hsv[2]*100));
            seekBarAlpha.setProgress((int)alpha*100);
        }

        textViewHue.setText("Hue " + (int)(_hsv[0]) + (char) 0x00B0);
        textViewSaturation.setText("Saturation " + (int)(_hsv[1]*100) + "%");
        textViewValue.setText("Value/Lightness " + (int)(_hsv[2]*100) + "%");
        textViewAlpha.setText("Opacity " + (int)(alpha*100) + "%");

    }
}
