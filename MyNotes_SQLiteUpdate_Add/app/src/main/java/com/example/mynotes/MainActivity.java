package com.example.mynotes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    FloatingActionButton fab1,fab2;
    Adapter adapter;
    List<Model> notesList;
    DBConnect dbConnect;
    CoordinatorLayout coordinatorLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recycler_view);
        fab1 = findViewById(R.id.fab_cal);
        fab2 = findViewById(R.id.fab_add);
        coordinatorLayout = findViewById(R.id.layout_main);

        fab1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,CalculatorActivity.class);
                startActivity(intent);
            }
        });

        fab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,AddNotesActivity.class);
                startActivity(intent);
            }
        });

        notesList = new ArrayList<>();

        dbConnect = new DBConnect(this);
        FetchAllNotesFromDatabase();

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new Adapter(this,MainActivity.this,notesList);
        recyclerView.setAdapter(adapter);

        ItemTouchHelper helper = new ItemTouchHelper(callback);
        helper.attachToRecyclerView(recyclerView);
    }

    void FetchAllNotesFromDatabase() {
        Cursor cursor = dbConnect.readAllData();

        if (cursor.getCount() == 0)
        {
            Toast.makeText(this, "No Data to Show", Toast.LENGTH_SHORT).show();
        }
        else
        {
            while (cursor.moveToNext())
            {
                notesList.add(new Model(cursor.getString(0), cursor.getString(1), cursor.getString(2)));
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.options_menu, menu);

        MenuItem searchItem = menu.findItem(R.id.searchbar);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setQueryHint("Search Notes Here");

        SearchView.OnQueryTextListener listener = new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return true;
            }
        };

        searchView.setOnQueryTextListener(listener);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId()==R.id.delete_all_notes)
        {
            deleteAllNotes();
        }
        return super.onOptionsItemSelected(item);
    }

    public void deleteAllNotes() {
        DBConnect db = new DBConnect(MainActivity.this);
        db.deleteAllNotes();

        recreate();
    }

    ItemTouchHelper.SimpleCallback callback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

            int position = viewHolder.getAdapterPosition();
            Model item = adapter.getList().get(position);

            adapter.removeItem(viewHolder.getAdapterPosition());

            Snackbar snackbar = Snackbar.make(coordinatorLayout, "Item Deleted", Snackbar.LENGTH_LONG)
                    .setAction("UNDO", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            adapter.restoreItem(item, position);
                            recyclerView.scrollToPosition(position);
                        }
                    }).addCallback(new BaseTransientBottomBar.BaseCallback<Snackbar>() {
                        @Override
                        public void onDismissed(Snackbar transientBottomBar, int event) {
                            super.onDismissed(transientBottomBar, event);

                            if (!(event==DISMISS_EVENT_ACTION))
                            {
                                DBConnect db = new DBConnect(MainActivity.this);
                                db.deleteSingleItem(item.getId());
                            }
                        }
                    });

            snackbar.setActionTextColor(Color.YELLOW);
            snackbar.show();
        }
    };
}