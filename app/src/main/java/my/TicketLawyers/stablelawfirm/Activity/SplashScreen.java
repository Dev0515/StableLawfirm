package my.TicketLawyers.stablelawfirm.Activity;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ProgressBar;

import my.TicketLawyers.stablelawfirm.R;


public class SplashScreen extends AppCompatActivity {
     ProgressBar pgsBar;
    private int i = 0;

    private Handler hdlr = new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        pgsBar = (ProgressBar) findViewById(R.id.pBar);

                i = pgsBar.getProgress();
                new Thread(new Runnable() {
                    public void run() {
                        while (i < 100) {
                            i += 5;

                            hdlr.post(new Runnable() {
                                public void run() {
                                    pgsBar.setProgress(i); }
                            });
                            try {

                                Thread.sleep(100);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                        Intent intent=new Intent(SplashScreen.this,MainActivity.class);
                        startActivity(intent);
                    }
                }).start();
            }

    }


