package com.example.personal_items;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import static com.example.personal_items.PersonalDBHelper.DATABASE_NAME;

public class MainActivity extends AppCompatActivity {
    private SQLiteDatabase mDatabase;
    private PersonalAdapter mAdapter;
    private EditText mEditTextName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setTitle("Personal Items");

        PersonalDBHelper dbHelper = new PersonalDBHelper(this);
        mDatabase = dbHelper.getWritableDatabase();

        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new PersonalAdapter(this, getAllItems());
        recyclerView.setAdapter(mAdapter);

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }
            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                removeItem((long) viewHolder.itemView.getTag());
            }
        }).attachToRecyclerView(recyclerView);


        mEditTextName = findViewById(R.id.edittext_name);

        Button buttonAdd = findViewById(R.id.button_add);

        buttonAdd.setOnClickListener(v -> addItem());


        Button buttonSend = findViewById(R.id.button_send);
        buttonSend.setOnClickListener(v -> {
            Intent i = new Intent(Intent.ACTION_SEND);
            i.putExtra(Intent.EXTRA_EMAIL, new String[] {"example@domain.com"});
            i.putExtra(Intent.EXTRA_SUBJECT, "BackUp Of Personal Details");
            i.putExtra(Intent.EXTRA_TEXT, "Attached below is a back up of your personal details");
            i.setType("application/octet-stream");
            i.putExtra(Intent.EXTRA_STREAM,(getDatabasePath(DATABASE_NAME)));
            startActivity(Intent.createChooser(i, "Send e-mail"));

        });
    }

    private void addItem() {
        if (mEditTextName.getText().toString().trim().length() == 0) {
            return;
        }
        String name = mEditTextName.getText().toString();
        ContentValues cv = new ContentValues();
        cv.put(PersonalContract.PersonalEntry.COLUMN_NAME, name);
        mDatabase.insert(PersonalContract.PersonalEntry.TABLE_NAME, null, cv);
        mAdapter.swapCursor(getAllItems());

        mEditTextName.getText().clear();
    }
    private void removeItem(long id) {
        mDatabase.delete(PersonalContract.PersonalEntry.TABLE_NAME,
                PersonalContract.PersonalEntry._ID + "=" + id, null);
        mAdapter.swapCursor(getAllItems());
    }


    private Cursor getAllItems() {
        return mDatabase.query(
                PersonalContract.PersonalEntry.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                PersonalContract.PersonalEntry.COLUMN_TIMESTAMP + " DESC"
        );
    }

}