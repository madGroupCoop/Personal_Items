package com.example.personal_items;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

public class PersonalAdapter extends RecyclerView.Adapter<PersonalAdapter.PersonalViewHolder> {
    private Context mContext;
    private Cursor mCursor;
    public PersonalAdapter(Context context, Cursor cursor) {
        mContext = context;
        mCursor = cursor;
    }
    public class PersonalViewHolder extends RecyclerView.ViewHolder {
        public TextView nameText;
        public PersonalViewHolder(View itemView) {
            super(itemView);
            nameText = itemView.findViewById(R.id.textview_name_item);
        }
    }
    @Override
    public PersonalViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.personal_item, parent, false);
        return new PersonalViewHolder(view);
    }
    @Override
    public void onBindViewHolder(PersonalViewHolder holder, int position) {
        if (!mCursor.moveToPosition(position)) {
            return;
        }
        String name = mCursor.getString(mCursor.getColumnIndex(PersonalContract.PersonalEntry.COLUMN_NAME));
        long id = mCursor.getLong(mCursor.getColumnIndex(PersonalContract.PersonalEntry._ID));

        holder.itemView.setTag(id);
        holder.nameText.setText(name);
    }
    @Override
    public int getItemCount() {
        return mCursor.getCount();
    }
    public void swapCursor(Cursor newCursor) {
        if (mCursor != null) {
            mCursor.close();
        }
        mCursor = newCursor;
        if (newCursor != null) {
            notifyDataSetChanged();
        }
    }
}
