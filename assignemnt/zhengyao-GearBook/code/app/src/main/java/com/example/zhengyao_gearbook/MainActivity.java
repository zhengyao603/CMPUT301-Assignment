package com.example.zhengyao_gearbook;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.TextView;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity implements AddEditFragment.OnFragmentInteractionListener {
    // declare variables
    ArrayList<Gear> gear_datalist = new ArrayList<>();
    ListView gear_list;
    GearAdapter gear_adapter;
    Button add_button;
    TextView total_price;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // get views by ids
        gear_list = findViewById(R.id.gear_list);
        add_button = findViewById(R.id.add_button);
        total_price = findViewById(R.id.total_price);
        // initialize GearAdapter
        gear_adapter = new GearAdapter(this, gear_datalist);
        gear_list.setAdapter(gear_adapter);


        //  1⃣ Add button popup an input AlertDialog which is responsible for adding new gear
        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // generate new fragment instance with no parameters
                new AddEditFragment().show(getSupportFragmentManager(), "Add_Gear");
            }
        });


        //  2⃣ One single click on a certain item popup detailed information AlertDialog
        gear_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                View detail_view = LayoutInflater.from(MainActivity.this).inflate(R.layout.detail_layout, null);
                // get view by id
                final TextView detail_date = detail_view.findViewById(R.id.detail_date);
                final TextView detail_maker = detail_view.findViewById(R.id.detail_maker);
                final TextView detail_description = detail_view.findViewById(R.id.detail_description);
                final TextView detail_price = detail_view.findViewById(R.id.detail_price);
                final TextView detail_comment = detail_view.findViewById(R.id.detail_comment);
                // display the data for each attribute of a gear
                detail_date.setText("Date: " + gear_datalist.get(position).getDate());
                detail_maker.setText("Maker: " + gear_datalist.get(position).getMaker());
                detail_description.setText("Description: " + gear_datalist.get(position).getDescription());
                detail_price.setText("Price: " + gear_datalist.get(position).getPrice());
                detail_comment.setText("Comment: " + gear_datalist.get(position).getComment());

                builder.setNegativeButton("Cancel", null);

                //  2⃣ . 1⃣  Edit button on the detailed information page allows you to edit that gear data
                builder.setPositiveButton("Edit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // generate new fragment instance with current gear object as parameter
                        new AddEditFragment(gear_datalist.get(position)).show(getSupportFragmentManager(), "Edit_Gear");
                    }
                });
                // show the detailed information AlertDialog
                builder.setView(detail_view).show();
            }
        });


        //  3⃣  Long time clicking an item allows you to delete that item by the popup menu
        gear_list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                // build the popup menu
                PopupMenu popup_delete = new PopupMenu(MainActivity.this, view);
                popup_delete.getMenuInflater().inflate(R.menu.popup_layout, popup_delete.getMenu());
                popup_delete.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    // click on the menu item deletes current gear object
                    public boolean onMenuItemClick(MenuItem item) {
                        gear_datalist.get(position).subtract();
                        gear_datalist.remove(position);
                        gear_adapter.notifyDataSetChanged();
                        // update total price
                        total_price.setText("Total Price: $" + Gear.getCount().toString());
                        return false;
                    }
                });
                popup_delete.show();
                return true;
            }
        });
    }

    @Override
    // interface allows adding/editing new gear
    public void onOkPressed(Gear newGear, Integer label) {
        if (label == 1){
            // add gear
            gear_adapter.add(newGear);
            gear_adapter.notifyDataSetChanged();
        } else if (label == 2){
            // edit gear
            gear_adapter.notifyDataSetChanged();
        }
        // update total price
        total_price.setText("Total Price: $" + Gear.getCount().toString());
    }
}