package com.example.anative;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Application;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    // Used to load the 'anative' library on application startup.
    static {
        System.loadLibrary("anative");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

//        anr();
//        crash();
//        exit();
//        process();
    }

    private void anr() {
        Button anrSleep = new Button(getBaseContext());
        anrSleep.setText("anr-sleep");
        anrSleep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "anr: anr-sleep");
                try {
                    Thread.sleep(20000);
                } catch (InterruptedException e) {
                    Log.d(TAG, "anr: anr-sleep", e);
                }
            }
        });
        ((ViewGroup) findViewById(R.id.main_content)).addView(anrSleep);

        Button anrWait = new Button(getBaseContext());
        anrWait.setText("anr-wait");
        anrWait.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "anr: anr-wait");
                synchronized (this) {
                    try {
                        wait(20000);
                    } catch (InterruptedException e) {
                        Log.d(TAG, "anr: anr-wait", e);
                    }
                }
            }
        });
        ((ViewGroup) findViewById(R.id.main_content)).addView(anrWait);
    }

    private void crash() {
        Button crash = new Button(getBaseContext());
        crash.setText("crash");
        crash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "crash: ");
                Object object = null;
                object.equals(new Object());
            }
        });
        ((ViewGroup) findViewById(R.id.main_content)).addView(crash);
    }

    private void exit() {
        Button anrSleep = new Button(getBaseContext());
        anrSleep.setText("exit");
        anrSleep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "exit: ");
                System.exit(0);
            }
        });
        ((ViewGroup) findViewById(R.id.main_content)).addView(anrSleep);
    }

    /**
     * A native method that is implemented by the 'anative' native library,
     * which is packaged with this application.
     */
    public native String stringFromJNI();

    public static class MainApplication extends Application {
        @Override
        public void onCreate() {
            super.onCreate();
            Log.d(TAG, "Application onCreate start");
            try {
                Thread.sleep(16000);
            } catch (InterruptedException e) {
                Log.d(TAG, "anr: anr-sleep", e);
            }
            Log.d(TAG, "Application onCreate end");
        }
    }
}