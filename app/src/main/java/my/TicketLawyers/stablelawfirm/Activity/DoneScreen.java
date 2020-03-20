package my.TicketLawyers.stablelawfirm.Activity;

import android.app.Dialog;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import my.TicketLawyers.stablelawfirm.R;


public class DoneScreen extends AppCompatActivity {
    ImageView backarrow,mainticket;
    TextView tootextgrpname;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_done_screen);
        ActionBar myActionBar = getSupportActionBar();
        myActionBar.hide();
        getWindow().setBackgroundDrawable(getResources().getDrawable(R.drawable.backk));
        backarrow=(ImageView)findViewById(R.id.backarrow);
        tootextgrpname=(TextView)findViewById(R.id.tootextgrpname);
        //tootextgrpname.setText("");
        backarrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent d=new Intent(DoneScreen.this,MainActivity.class);
                startActivity(d);
            }
        });
        final Dialog dialog = new Dialog(DoneScreen.this);
        dialog.setContentView(R.layout.customdialog1);
        dialog.setTitle("Title...");
        final EditText editText=(EditText)dialog.findViewById(R.id.edittext);
        Button dialogButton = (Button) dialog.findViewById(R.id.dialogButtonOK);

        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent d=new Intent(DoneScreen.this,MainActivity.class);
                startActivity(d);
                    dialog.dismiss(); }
        });

        dialog.show();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent d=new Intent(DoneScreen.this,MainActivity.class);
        startActivity(d);
    }
}
