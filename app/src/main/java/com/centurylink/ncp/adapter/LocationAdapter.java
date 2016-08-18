package com.centurylink.ncp.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.centurylink.ncp.R;
import com.centurylink.ncp.models.MarkerModel;

import java.util.List;


/**
 * Created by AB65363 on 7/28/2016.
 */
public class LocationAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private Context mContext;
    private List<MarkerModel> all_marker;

    public LocationAdapter(Context context, List<MarkerModel> all_marker){

        this.mContext = context;
        this.all_marker = all_marker;

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.marker_description, new LinearLayout(mContext));
        RecyclerView.ViewHolder holder = new LocationHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        LocationHolder mHolder = (LocationHolder)holder;

        mHolder.main_text.setText(all_marker.get(position).getMain_text());
        mHolder.sub_text.setText(all_marker.get(position).getSub_text());
        mHolder.desc_text.setText(all_marker.get(position).getDesc_text());

    }

    @Override
    public int getItemCount() {
        return all_marker.size();
    }


    private class LocationHolder extends RecyclerView.ViewHolder{

        TextView main_text,sub_text,desc_text;

        public LocationHolder(View itemView) {
            super(itemView);

            main_text = (TextView)itemView.findViewById(R.id.main_text);
            sub_text = (TextView)itemView.findViewById(R.id.sub_text);
            desc_text = (TextView)itemView.findViewById(R.id.desc_text);

        }


    }


}
