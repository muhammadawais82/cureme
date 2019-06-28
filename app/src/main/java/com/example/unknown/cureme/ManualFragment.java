package com.example.unknown.cureme;


import android.database.Cursor;
import android.icu.text.SymbolTable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.HashMap;



public class ManualFragment extends Fragment {


    public ManualFragment() {
        // Required empty public constructor
    }

    DatabaseHelper myDb;
    EditText pat_id, pat_name, p_blood, pat_sug, pat_temp, pat_bp, pat_hb;
    Button btnAddData;
    Button btnViewData;
    double num_sug,num_temp, num_bp ,num_hb;
    View mView;
    SessionManager sessionManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        sessionManager = new SessionManager(getActivity());
        mView = inflater.inflate(R.layout.fragment_manual, container, false);
        myDb = new DatabaseHelper(getActivity());
        pat_id = (EditText) mView.findViewById(R.id.p_id);
        pat_name = (EditText) mView.findViewById(R.id.p_name);
        p_blood = (EditText) mView.findViewById(R.id.p_blood);
        pat_sug = (EditText) mView.findViewById(R.id.p_sugar);
        pat_temp = (EditText) mView.findViewById(R.id.p_temp);
        pat_bp = (EditText) mView.findViewById(R.id.p_bp);
        pat_hb = (EditText) mView.findViewById(R.id.p_hb);
        btnAddData = (Button) mView.findViewById(R.id.btn_AddData);
        btnViewData = (Button) mView.findViewById(R.id.btn_ViewData);
        HashMap<String, String> user = sessionManager.getUserDetails();
        String id = user.get(sessionManager.KEY_ID);
        String name = user.get(sessionManager.KEY_NAME);
        pat_id.setText(id);
        pat_name.setText(name);
     /*   pat_id.setEnabled(false);
        pat_name.setEnabled(false);*/
        //show error message
        /*pat_name.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (pat_name.getText().length()<4){
                    pat_name.setError("You must enter valid name");
                }

            }
        });*/
        pat_temp.addTextChangedListener(textWatcher);
        AddData();
        ViewAll();
        return mView;

    }


    TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (pat_temp.getText().length() > 0) {
                Double num = Double.parseDouble(pat_temp.getText().toString());
                if (num >= 96.0 && num <= 104.9) {
                    //save the number
                    num_temp =  num;
                } else {
                    pat_temp.setError("Enter Value between 96.0 to 104.9");
                }
            }
                /*if (pat_sug.getText().length() > 0) {
                    int num1 = Integer.parseInt(pat_temp.getText().toString());
                    if (num1 >= 60 && num1 <= 310) {
                        //save the number
                        num_sug = num1;
                    } else {
                        pat_sug.setError("Enter Value between 60 to 310");
                    }
                }
*/

            }


        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    public void ViewAll() {
        btnViewData.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Cursor res = myDb.getAllData();
                        if (res.getCount() == 0) {
                            //show message
                            ShowMassage("Sorry", "Nothing to Show");
                            return;
                        }
                        StringBuffer buffer = new StringBuffer();
                        while (res.moveToNext()) {
                            buffer.append("ID : " + res.getString(1) + "\n");
                            buffer.append("Name : " + res.getString(2) + "\n");
                            buffer.append("Blood Group : " + res.getString(3) + "\n");
                            buffer.append("Sugar : " + res.getString(4) + " mg/dl" + "\n");
                            buffer.append("Temperature : " + res.getString(5) + " °F" + "\n");
                            buffer.append("Blood Pressure : " + res.getString(6) + " SYS / DIA" + "\n");
                            buffer.append("HeartBeat : " + res.getString(7) + " BPM" + "\n");
                        }
                        ShowMassage("Data", buffer.toString());
                    }
                }
        );
    }

    public void ShowMassage(String title, String Messsage) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(Messsage);
        builder.show();
    }

    public void AddData() {
        btnAddData.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (p_blood.getText().toString().length() <= 1 || p_blood.getText().toString().length() > 3)  {
                            //Toast.makeText(getActivity(), "Enter correct blood group with sign", Toast.LENGTH_SHORT).show();
                            p_blood.setError("Enter correct blood group with + or - sign");
                        } else {
                            if (pat_sug.getText().toString().length() <= 1 || pat_sug.getText().toString().length() > 3) {
                                //Toast.makeText(getActivity(), "Must enter a value between 60 to 300 of sugar", Toast.LENGTH_SHORT).show();
                                pat_sug.setError("Must enter a value between 60 to 300 of sugar");
                            } else {
                                if (pat_temp.getText().toString().length() <= 1 || pat_temp.getText().toString().length() >= 5) {
                                    //Toast.makeText(getActivity(), "Enter temperature in Farnhight", Toast.LENGTH_SHORT).show();
                                    pat_temp.setError("Enter temperature in Farnhight");
                                } else {
                                    if (pat_bp.getText().toString().length() <= 5 ||pat_bp.getText().toString().length() > 9) {
                                        //Toast.makeText(getActivity(), "Please Enter Blood Pressure in Sys / Dia", Toast.LENGTH_SHORT).show();
                                        pat_bp.setError("Please Enter Blood Pressure in Sys / Dia, with - or / sign");
                                    } else {
                                        if (pat_hb.getText().toString().length() < 2) {
                                            //Toast.makeText(getActivity(), "Enter HeartBeat in BPM", Toast.LENGTH_SHORT).show();
                                            pat_hb.setError("Enter HeartBeat in BPM between 50 to 110");
                                        } else {
                                            if (myDb.insertData(pat_id.getText().toString(), pat_name.getText().toString(), p_blood.getText().toString(), pat_sug.getText().toString(), pat_temp.getText().toString(), pat_bp.getText().toString(), pat_hb.getText().toString())) {
                                                Toast.makeText(getActivity(), "Data Inserted", Toast.LENGTH_LONG).show();
                                            } else {
                                                Toast.makeText(getActivity(), "Data is not Inserted check errors", Toast.LENGTH_LONG).show();
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
        );
    }
}