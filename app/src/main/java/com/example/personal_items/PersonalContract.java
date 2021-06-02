package com.example.personal_items;

import android.provider.BaseColumns;

public class PersonalContract {
    private PersonalContract() {
    }
    public static final class PersonalEntry implements BaseColumns {
        public static final String TABLE_NAME = "personalList";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_TIMESTAMP = "timestamp";
    }

}
