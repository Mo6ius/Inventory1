package com.example.android.inventory1.data;

import android.provider.BaseColumns;

/**
 * API Contract for the Inventory1 app.
 */
public final class VeggieContract {

    // To prevent someone from accidentally instantiating the contract class,
    // give it an empty constructor.
    private VeggieContract() {
    }

    /**
     * Inner class that defines constant values for the vegetables database table.
     * Each entry in the table represents a single vegetable.
     */
    public static final class VeggieEntry implements BaseColumns {

        /**
         * Name of database table for vegetables
         */
        public final static String TABLE_NAME = "veggies";

        /**
         * Unique ID number for the vegetable (only for use in the database table).
         * <p>
         * Type: INTEGER
         */
        public final static String _ID = BaseColumns._ID;

        /**
         * Name of the vegetable.
         * <p>
         * Type: TEXT
         */
        public final static String COLUMN_VEGGIE_NAME = "name";

        /**
         * Price of the vegetable.
         * <p>
         * Type: INTEGER
         */
        public final static String COLUMN_VEGGIE_PRICE = "price";

        /**
         * Quantity of the vegetable.
         * <p>
         * Type: INTEGER
         */
        public final static String COLUMN_VEGGIE_QUANTITY = "quantity";

        /**
         * Name of supplier
         * <p>
         * Type: STRING
         */
        public final static String COLUMN_VEGGIE_SUPPLIER_NAME = "supplierName";

        /**
         * Phone of supplier
         * <p>
         * Type: STRING
         */
        public final static String COLUMN_VEGGIE_SUPPLIER_PHONE = "supplierPhone";
    }

}