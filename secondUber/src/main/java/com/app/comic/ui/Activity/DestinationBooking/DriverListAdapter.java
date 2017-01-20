package com.app.comic.ui.Activity.DestinationBooking;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.app.comic.R;
import com.app.comic.ui.Model.JSON.Driver;
import com.app.comic.ui.Model.Receive.ListRidesReceive;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

//import static com.app.tbd.ui.Activity.BookingFlight.FlightListFragment.getFareType;
//import static com.app.tbd.ui.Activity.BookingFlight.FlightPriceFragment.resetAdapterFromInside;

public class DriverListAdapter extends BaseAdapter {

    private final Activity context;
    private final List<Driver> obj;
    private ListOfRidesFragment frag;

    public DriverListAdapter(Activity context, ListOfRidesFragment frag, ArrayList<Driver> lists) {
        this.context = context;
        this.obj = lists;
        this.frag = frag;

    }

    @Override
    public int getCount() {
        return obj == null ? 0 : obj.size();
    }

    @Override
    public Object getItem(int position) {
        return obj == null ? null : obj.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    static class ViewHolder {

        @InjectView(R.id.username)
        TextView username;

        @InjectView(R.id.btnViewDriverProfile)
        TextView btnViewDriverProfile;

        @InjectView(R.id.btnSelectDriver)
        TextView btnSelectDriver;

    }

    @Override
    public View getView(final int position, View view, ViewGroup parent) {

        ViewHolder vh;
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.share_ride_driver_list, parent, false);
            vh = new ViewHolder();
            ButterKnife.inject(vh, view);
            view.setTag(vh);
        } else {
            vh = (ViewHolder) view.getTag();
        }

        vh.username.setText(obj.get(position).getUsername());

        vh.btnViewDriverProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                frag.viewDriver(obj.get(position));
            }
        });


        vh.btnSelectDriver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                frag.selectDriver(obj.get(position).getId());
            }
        });

        return view;

    }

}
