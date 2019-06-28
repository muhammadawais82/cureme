package com.example.unknown.cureme;

import android.app.SearchManager;
import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import java.util.ArrayList;

public class DoctorActivity extends AppCompatActivity {
    DatabaseHelper myDb;
    RecyclerView recyclerView;
    LinearLayoutManager linearLayoutManager;
    ArrayList<MyModelClass> list = new ArrayList<>();
    MyAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor);
        myDb = new DatabaseHelper(this);
        recyclerView = findViewById(R.id.RecyclerView);
        recyclerView.hasFixedSize();
        linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        adapter = new MyAdapter(this, list);
        recyclerView.setAdapter(adapter);
        loadData();
    }

    public void ShowMassage(String title, String Messsage) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(Messsage);
        builder.show();
    }

    private void loadData() {
        Cursor res = myDb.getAllData();
        if (res.getCount() == 0) {
            //show message
            ShowMassage("Error", "Nothing Found");
            return;
        }
        //StringBuffer buffer = new StringBuffer();
        while (res.moveToNext()) {
            /*buffer.append("ID : " + res.getString(1) + "\n");
            buffer.append("Name : " + res.getString(2) + "\n");
            buffer.append("Blood Group : " + res.getString(3) + "\n");
            buffer.append("Sugar : " + res.getString(4) + "\n");
            buffer.append("Temperature : " + res.getString(5) + "\n");
            buffer.append("Blood Pressure : " + res.getString(6) + "\n");
            buffer.append("HeartBeat : " + res.getString(7) + "\n");*/
            list.add(new MyModelClass(res.getString(1), res.getString(2), res.getString(3), res.getString(4), res.getString(5), res.getString(6), res.getString(7)));
        }
        adapter.notifyDataSetChanged();
        //ShowMassage("Data", buffer.toString());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_doc, menu);
        SearchView searchView;
        MenuItem searchMenuItem;
        searchMenuItem = menu.findItem(R.id.action_search);
        SearchManager searchManager = (SearchManager) getApplicationContext().getSystemService(SEARCH_SERVICE);
        searchView = null;
        if (searchMenuItem != null) {
            searchView = (SearchView) searchMenuItem.getActionView();
            searchView.getMaxWidth();
            searchView.setQueryHint("Search...");
            //searchView.setInputType(InputType.TYPE_TEXT_FLAG_CAP_SENTENCES);
        }
        if (searchView != null) {
            searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
            searchView.setSubmitButtonEnabled(true);
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String newText) {
                    adapter.getFilter().filter(newText);
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    adapter.getFilter().filter(newText);
                    return false;
                }
            });
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.signout) {
            SessionManager sessionManager = new SessionManager(this);
            sessionManager.logoutUser();
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}