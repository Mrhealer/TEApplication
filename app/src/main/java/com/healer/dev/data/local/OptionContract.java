package com.healer.dev.data.local;

import android.provider.BaseColumns;

public class OptionContract {

    private OptionContract(){
        // To prevent accidental instantiation
    }

    public static final class OptionEntry implements BaseColumns {

        public static final String TABLE_NAME = "options";

        public static final String COLUMN_QUESTION_ID = "question_id";

        public static final String COLUMN_DESCRIPTION = "description";

        public static final String COLUMN_IS_CORRECT = "is_correct";

        public static final String COLUMN_REMARKS = "remarks";

    }

}
