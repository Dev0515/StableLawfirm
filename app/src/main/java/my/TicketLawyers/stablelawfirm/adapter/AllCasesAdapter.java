package my.TicketLawyers.stablelawfirm.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import my.TicketLawyers.stablelawfirm.Activity.ViewTickets;

import my.TicketLawyers.stablelawfirm.R;
import my.TicketLawyers.stablelawfirm.model.DataModel1;
import com.squareup.picasso.Picasso;

import java.util.List;

public class AllCasesAdapter extends RecyclerView.Adapter<AllCasesAdapter.MyViewHolder> {

    private List<DataModel1> dataSet;
   public Context ctx;

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView textViewName;
        TextView textViewVersion;
        ImageView imageViewIcon;
        RelativeLayout rel;

        public MyViewHolder(View itemView) {
            super(itemView);
            this.textViewName = (TextView) itemView.findViewById(R.id.textViewName);
            this.textViewVersion = (TextView) itemView.findViewById(R.id.textViewVersion);
            this.imageViewIcon = (ImageView) itemView.findViewById(R.id.imageView);
            this.rel=(RelativeLayout)itemView.findViewById(R.id.rellayout); }
    }

    public AllCasesAdapter(List<DataModel1> data, Context ctx ){
        this.dataSet = data;
        this.ctx=ctx;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent,
                                           int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.test, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int listPosition) {

        TextView textViewName = holder.textViewName;
        TextView textViewVersion = holder.textViewVersion;
        ImageView imageView1 = holder.imageViewIcon;
        imageView1.setTag(R.id.imageView, 1);
        textViewName.setText(dataSet.get(listPosition).getDesc());
        Log.e("22", "onResponse: "+dataSet.get(listPosition).getImage() );
        Picasso.get()
                .load(dataSet.get(listPosition).getImage())
                .placeholder(R.drawable.thumbnail)
                .error(R.drawable.thumbnail)
                .into(imageView1);
        holder.rel.setOnClickListener(new View.OnClickListener() {
          @Override
              public void onClick(View v)
          {
              Intent aa=new Intent(ctx, ViewTickets.class);
              aa.putExtra("desc",dataSet.get(listPosition).getDesc());
              aa.putExtra("Image",dataSet.get(listPosition).getImage());
              ctx.startActivity(aa);
          }
   }
        );
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }
}

