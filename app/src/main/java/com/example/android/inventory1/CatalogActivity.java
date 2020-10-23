package com.example.android.inventory1;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.android.inventory1.EditorActivity;
import com.example.android.inventory1.R;
import com.example.android.inventory1.data.VeggieContract.VeggieEntry;
import com.example.android.inventory1.data.VeggieDbHelper;

/**
 * Displays list of vegetables that were entered and stored in the app.
 */
public class CatalogActivity extends AppCompatActivity {

    /**
     * Database helper that provides access to the database
     */
    private com.example.android.inventory1.data.VeggieDbHelper mDbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catalog);

        // Setup Floating Action Button to open EditorActivity
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CatalogActivity.this, EditorActivity.class);
                startActivity(intent);
            }
        });

        // To access the database, instantiate the subclass of SQLiteOpenHelper
        // and pass the context, which is the current activity.
        mDbHelper = new VeggieDbHelper(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        displayDatabaseInfo();
    }

    /**
     * Temporary helper method to display information in the onscreen TextView about the state of
     * the veggies database.
     */
    private void displayDatabaseInfo() {
        // Create and/or open a database to read from it
        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        // Define a projection that specifies which columns from the database
        // that will be used after this query.
        String[] projection = {
                VeggieEntry._ID,
                VeggieEntry.COLUMN_VEGGIE_NAME,
                VeggieEntry.COLUMN_VEGGIE_PRICE,
                VeggieEntry.COLUMN_VEGGIE_QUANTITY,
                VeggieEntry.COLUMN_VEGGIE_SUPPLIER_NAME,
                VeggieEntry.COLUMN_VEGGIE_SUPPLIER_PHONE};

        // Perform a query on the veggies table
        Cursor cursor = db.query(
                VeggieEntry.TABLE_NAME,   // The table to query
                projection,            // The columns to return
                null,                  // The columns for the WHERE clause
                null,                  // The values for the WHERE clause
                null,                  // Don't group the rows
                null,                  // Don't filter by row groups
                null);                   // The sort order

        TextView displayView = (TextView) findViewById(R.id.text_view_veggie);

        try {
            // Create a header in the Text View that looks like this:
            //
            // The veggies table contains <number of rows in Cursor> veggies.
            // _id - name - price - quantity - supplierName - supplierPhone
            //
            // In the while loop below, iterate through the rows of the cursor and display
            // the information from each column in this order.
            displayView.setText("The veggies table contains " + cursor.getCount() + " veggies.\n\n");
            displayView.append(VeggieEntry._ID + " - " +
                    VeggieEntry.COLUMN_VEGGIE_NAME + " - " +
                    VeggieEntry.COLUMN_VEGGIE_PRICE + " - " +
                    VeggieEntry.COLUMN_VEGGIE_QUANTITY + " - " +
                    VeggieEntry.COLUMN_VEGGIE_SUPPLIER_NAME + " - " +
                    VeggieEntry.COLUMN_VEGGIE_SUPPLIER_PHONE + "\n");

            // Figure out the index of each column
            int idColumnIndex = cursor.getColumnIndex(VeggieEntry._ID);
            int nameColumnIndex = cursor.getColumnIndex(VeggieEntry.COLUMN_VEGGIE_NAME);
            int priceColumnIndex = cursor.getColumnIndex(VeggieEntry.COLUMN_VEGGIE_PRICE);
            int quantityColumnIndex = cursor.getColumnIndex(VeggieEntry.COLUMN_VEGGIE_QUANTITY);
            int supplierNameColumnIndex = cursor.getColumnIndex(VeggieEntry.COLUMN_VEGGIE_SUPPLIER_NAME);
            int supplierPhoneColumnIndex = cursor.getColumnIndex(VeggieEntry.COLUMN_VEGGIE_SUPPLIER_PHONE);

            // Iterate through all the returned rows in the cursor
            while (cursor.moveToNext()) {
                // Use that index to extract the String or Int value of the word
                // at the current row the cursor is on.
                int currentID = cursor.getInt(idColumnIndex);
                String currentName = cursor.getString(nameColumnIndex);
                int currentPrice = cursor.getInt(priceColumnIndex);
                int currentQuantity = cursor.getInt(quantityColumnIndex);
                String supplierName = cursor.getString(supplierNameColumnIndex);
                String supplierPhone = cursor.getString(supplierPhoneColumnIndex);
                // Display the values from each column of the current row in the cursor in the TextView
                displayView.append(("\n" + currentID + " - " +
                        currentName + " - " +
                        currentPrice + " - " +
                        currentQuantity + " - " +
                        supplierName + " - " +
                        supplierPhone));
            }
        } finally {
            // Always close the cursor when you're done reading from it. This releases all its
            // resources and makes it invalid.
            cursor.close();
        }
    }

    /**
     * Helper method to insert hardcoded pet data into the database. For debugging purposes only.
     */
    private void insertVeggie() {
        // Gets the database in write mode
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        // Create a ContentValues object where column names are the keys,
        // and carrot's veggies attributes are the values.
        ContentValues values = new ContentValues();
        values.put(VeggieEntry.COLUMN_VEGGIE_NAME, "Carrot");
        values.put(VeggieEntry.COLUMN_VEGGIE_PRICE, "$4");
        values.put(VeggieEntry.COLUMN_VEGGIE_QUANTITY, 3);
        values.put(VeggieEntry.COLUMN_VEGGIE_SUPPLIER_NAME, "Whole Foods");
        values.put(VeggieEntry.COLUMN_VEGGIE_SUPPLIER_PHONE, "(800) 123-4567");

        // Insert a new row for carrot in the database, returning the ID of that new row.
        // The first argument for db.insert() is the veggies table name.
        // The second argument provides the name of a column in which the framework
        // can insert NULL in the event that the ContentValues is empty (if
        // this is set to "null", then the framework will not insert a row when
        // there are no values).
        // The third argument is the ContentValues object containing the info for carrot.
        long newRowId = db.insert(VeggieEntry.TABLE_NAME, null, values);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu options from the res/menu/menu_catalog.xml file.
        // This adds menu items to the app bar.
        getMenuInflater().inflate(R.menu.menu_catalog, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // User clicked on a menu option in the app bar overflow menu
        switch (item.getItemId()) {
            // Respond to a click on the "Insert dummy data" menu option
            case R.id.action_insert_dummy_data:
                insertVeggie();
                displayDatabaseInfo();
                return true;
            // Respond to a click on the "Delete all entries" menu option
            case R.id.action_delete_all_entries:
                // Do nothing for now
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}