package com.example.zhengyao_gearbook;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import java.util.ArrayList;

public class GearAdapter extends ArrayAdapter {
    // defined attributes
    private ArrayList<Gear> gears;
    private Context context;

    // constructor of GearAdapter
    public GearAdapter(Context context, ArrayList<Gear> gears){
        super(context, 0, gears);
        this.gears = gears;
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;

        if (view == null){
            // inflate gearlist_layout
            view = LayoutInflater.from(context).inflate(R.layout.gearlist_layout, parent, false);
        }

        Gear gear = gears.get(position);

        // get views by id
        TextView gear_date = view.findViewById(R.id.gear_date);
        TextView gear_maker = view.findViewById(R.id.gear_maker);
        TextView gear_description = view.findViewById(R.id.gear_description);
        TextView gear_price = view.findViewById(R.id.gear_price);
        TextView gear_comment = view.findViewById(R.id.gear_comment);

        // set text for each view
        gear_date.setText("Date: " + gear.getDate());
        gear_maker.setText(gear.getMaker());
        gear_description.setText("Description: " + gear.getDescription());
        gear_price.setText("$ " + gear.getPrice());
        gear_comment.setText(gear.getComment());

        return view;
    }
}