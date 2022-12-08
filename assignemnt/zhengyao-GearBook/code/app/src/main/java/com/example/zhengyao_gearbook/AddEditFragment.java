package com.example.zhengyao_gearbook;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class AddEditFragment extends DialogFragment {
    // defined attributes
    private EditText input_date;
    private EditText input_maker;
    private EditText input_description;
    private EditText input_price;
    private EditText input_comment;
    private OnFragmentInteractionListener listener;
    private Gear gear;

    // interface for adding new gear object
    public interface OnFragmentInteractionListener {
        void onOkPressed(Gear newGear, Integer label);
    }

    // constructor with no parameter
    public AddEditFragment() {
        this.gear = null;
    }

    // constructor with given Gear object as parameter
    public AddEditFragment(Gear gear) {
        this.gear = gear;
    }

    @Override
    // function for checking implementing interface
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener){
            listener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement OnFragmentInteractionListener");
        }
    }

    // check if input date format is like "yyyy-mm-dd"
    public boolean checkDate(String string){
        if (string.length() == 10 && '-' == string.charAt(4) && '-' == string.charAt(7)){
            return true;
        } else {
            return false;
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.input_layout, null);
        // get view by id
        input_date = view.findViewById(R.id.input_date);
        input_maker = view.findViewById(R.id.input_maker);
        input_description = view.findViewById(R.id.input_description);
        input_price = view.findViewById(R.id.input_price);
        input_comment = view.findViewById(R.id.input_comment);

        if (gear != null){
            // if user is editing, display current data
            input_date.setText(gear.getDate());
            input_maker.setText(gear.getMaker());
            input_description.setText(gear.getDescription());
            input_price.setText(gear.getPrice());
            input_comment.setText(gear.getComment());
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        return builder
                .setView(view)
                .setTitle("Add/Edit Gear")
                .setNegativeButton("Cancel", null)
                .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // get user input for each edittext view
                        String inputdate = input_date.getText().toString();
                        String inputmaker = input_maker.getText().toString();
                        String inputdescription = input_description.getText().toString();
                        String inputprice = input_price.getText().toString();
                        String inputcomment = input_comment.getText().toString();

                        // if the input is invalid/empty, popup a message
                        if (TextUtils.isEmpty(inputdate) || TextUtils.isEmpty(inputmaker)
                                || TextUtils.isEmpty(inputdescription) || TextUtils.isEmpty(inputprice)
                                || !checkDate(inputdate) || inputmaker.length() > 20 || inputdescription.length() > 40
                                || inputcomment.length() > 20){
                            Toast.makeText(getActivity(),"Invalid/Empty Input",Toast.LENGTH_SHORT).show();
                        } else {
                            if (gear == null){
                                // if user is adding, generate a new gear and add it into listview
                                listener.onOkPressed(new Gear(inputdate, inputmaker, inputdescription, inputprice, inputcomment), 1);
                            } else {
                                // if user is editing, update the data of current gear
                                gear.setDate(inputdate);
                                gear.setMaker(inputmaker);
                                gear.setDescription(inputdescription);
                                gear.setPrice(inputprice);
                                gear.setComment(inputcomment);
                                listener.onOkPressed(gear, 2);
                            }
                        }
                    }}).create();
    }
}