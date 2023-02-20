package com.example.activityt;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.widget.HorizontalScrollView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements SensorEventListener {
    MediaPlayer mediaPlayer;
    SensorManager sensorManager;
    Sensor s;
    TextView textView;
    HorizontalScrollView hView;
    AudioManager audioManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.infoXYZ);

        sensorManager = (SensorManager) getSystemService(this.SENSOR_SERVICE);



        s = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);

        if(s != null){
            Toast.makeText(this, "GYROSCOPE Sensor is found ☺", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this, "GYROSCOPE Sensor is not found!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        audioManager = (AudioManager) getSystemService(this.AUDIO_SERVICE);
        if(sensorEvent.sensor.getType() == Sensor.TYPE_GYROSCOPE){
            int x = (int ) sensorEvent.values[0];
            int y = (int ) sensorEvent.values[1];
            int z = (int ) sensorEvent.values[2];
            if (y >= 0){
                int maxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_RING);
                audioManager.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
                audioManager.setStreamVolume(AudioManager.STREAM_RING, maxVolume, AudioManager.FLAG_SHOW_UI + AudioManager.FLAG_PLAY_SOUND);
            } else{
                audioManager.setRingerMode(AudioManager.RINGER_MODE_VIBRATE);
            }
            textView.setText("X: "+x+"\nY: "+y+"\nZ: "+z);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(this, s, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }
}