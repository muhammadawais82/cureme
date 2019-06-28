package com.example.unknown.cureme;


import android.app.AlertDialog;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Random;

import static android.content.Context.VIBRATOR_SERVICE;

public class AutoFragment extends Fragment {


    public AutoFragment() {
        // Required empty public constructor
    }

    View mView;
    DatabaseHelper Mydb;
    //private Button button;
    SessionManager sessionManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        sessionManager = new SessionManager(getActivity());
        mView = inflater.inflate(R.layout.fragment_auto, container, false);
        Button tapp = mView.findViewById(R.id.def_tap);
        Mydb = new DatabaseHelper(getActivity());


        tapp.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(getActivity());

                        DecimalFormat df = new DecimalFormat("#.##");

                        Double temp_level = Double.valueOf(df.format(getRandomDoubleBetweenRange(96.0, 104.0)));

                        final String tempt = String.valueOf(temp_level);
                        final String bloodgroup = String.valueOf(" ");
                        final String sugar = String.valueOf(getRandomNumberInRange(60, 310));
                        final String pulse = String.valueOf(getRandomNumberInRange(60, 110));
                        final String bloodp = String.valueOf(getRandomNumberInRange(70, 220)) + "/" + String.valueOf(getRandomNumberInRange(40, 100));

                        builder.setTitle("Cure Me").setMessage("Temperature: " + tempt + " °F" + "\n" + "Blood Sugar: " + sugar + " mg/dl" + "\n" + "Pulse Rate: " + pulse + " bpm" + "\n" + "Blood Pressure: " + bloodp)
                                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.cancel();
                                    }
                                })
                                .setPositiveButton("Save", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        HashMap<String, String> user = sessionManager.getUserDetails();
                                        String id = user.get(sessionManager.KEY_ID);
                                        String name = user.get(sessionManager.KEY_NAME);
                                        if (Mydb.insertData(id, name, bloodp, sugar, tempt, "", pulse)) {
                                            Toast.makeText(getActivity(), "Data Inserted", Toast.LENGTH_LONG).show();
                                        } else {
                                            Toast.makeText(getActivity(), "Data is not Insert", Toast.LENGTH_LONG).show();
                                        }
                                    }
                                });

                        builder.show();
                    }
                }, 2000);
                return false;
            }
        });
        return mView;
    }

    public static double getRandomDoubleBetweenRange(double min, double max) {
        double x = (Math.random() * ((max - min) + 1)) + min;
        return x;
    }

    private static int getRandomNumberInRange(int min, int max) {

        if (min >= max) {
            throw new IllegalArgumentException("max must be greater than min");
        }

        Random r = new Random();
        return r.nextInt((max - min) + 1) + min;
    }


}