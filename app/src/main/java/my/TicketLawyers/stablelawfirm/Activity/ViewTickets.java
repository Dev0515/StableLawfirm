package my.TicketLawyers.stablelawfirm.Activity;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toolbar;

import com.squareup.picasso.Picasso;

import my.TicketLawyers.stablelawfirm.R;

public class ViewTickets extends AppCompatActivity {
String image,desc;
ImageView backarrow,mainticket;
TextView aa;
Toolbar tt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_tickets);
        ActionBar myActionBar = getSupportActionBar();
        myActionBar.hide();
        getWindow().setBackgroundDrawable(getResources().getDrawable(R.drawable.backk));
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        image = extras.getString("Image");
        desc = extras.getString("desc");

        backarrow=(ImageView)findViewById(R.id.backarrow);
        backarrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent d=new Intent(ViewTickets.this,MainActivity.class);
                startActivity(d);
            }
        });
        mainticket=(ImageView)findViewById(R.id.ticket);
        aa=(TextView)findViewById(R.id.descc);
        aa.setText(""+desc);
        Picasso.get()
                .load(image)
                .placeholder(R.drawable.thumbnail)
                .error(R.drawable.thumbnail)
                .into(mainticket); }
}
