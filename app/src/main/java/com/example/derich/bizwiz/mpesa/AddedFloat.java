package com.example.derich.bizwiz.mpesa;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.derich.bizwiz.PreferenceHelper;
import com.example.derich.bizwiz.R;
import com.example.derich.bizwiz.sql.DatabaseHelper;
import com.example.derich.bizwiz.utils.DateAndTime;

import static com.example.derich.bizwiz.sql.DatabaseHelper.COLUMN_ADDED_CASH;
import static com.example.derich.bizwiz.sql.DatabaseHelper.COLUMN_ADDED_FLOAT;
import static com.example.derich.bizwiz.sql.DatabaseHelper.COLUMN_CLOSING_CASH;
import static com.example.derich.bizwiz.sql.DatabaseHelper.COLUMN_COMMENT;
import static com.example.derich.bizwiz.sql.DatabaseHelper.COLUMN_MPESA_STATUS;
import static com.example.derich.bizwiz.sql.DatabaseHelper.COLUMN_OPENING_CASH;
import static com.example.derich.bizwiz.sql.DatabaseHelper.COLUMN_OPENING_FLOAT;
import static com.example.derich.bizwiz.sql.DatabaseHelper.COLUMN_REDUCTED_CASH;
import static com.example.derich.bizwiz.sql.DatabaseHelper.COLUMN_REDUCTED_FLOAT;
import static com.example.derich.bizwiz.sql.DatabaseHelper.COLUMN_TRANSACTION_STATUS;
import static com.example.derich.bizwiz.sql.DatabaseHelper.DATE_MILLIS;
import static com.example.derich.bizwiz.sql.DatabaseHelper.TABLE_MPESA;
import static com.example.derich.bizwiz.sql.DatabaseHelper.TABLE_TRANSACTIONS;
import static com.example.derich.bizwiz.sql.DatabaseHelper.TIME_OF_TRANSACTION;
import static com.example.derich.bizwiz.sql.DatabaseHelper.TRANSACTION_DATE;
import static com.example.derich.bizwiz.sql.DatabaseHelper.TRANSACTION_TYPE;
import static com.example.derich.bizwiz.sql.DatabaseHelper.TRANSACTION_USER;
import static com.example.derich.bizwiz.utils.DateAndTime.currentDateandTime;
import static com.example.derich.bizwiz.utils.DateAndTime.getDate;

public class AddedFloat extends AppCompatActivity {
    EditText amount, comments;
    DatabaseHelper myDb;
    Button btn_insert;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_added_float);
        btn_insert = findViewById(R.id.button_added_float);
        amount = findViewById(R.id.editText_added_float);
        comments= findViewById(R.id.editText_added_float_comment);
        addedFloat();
    }

    private void addedFloat() {
        btn_insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String addedAmount = amount.getText().toString().trim();
                final String comment = comments.getText().toString().trim();


                if (!(addedAmount.isEmpty()))
                {
                    AlertDialog.Builder builder
                            = new AlertDialog
                            .Builder(AddedFloat.this);

                    builder.setMessage("Do you want to insert Added float of " + addedAmount + " Ksh ?");


                    builder.setTitle("Alert !");
                    builder.setCancelable(false);
                    builder
                            .setPositiveButton(
                                    "Yes",
                                    new DialogInterface
                                            .OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog,
                                                            int which)
                                        {
                                            Integer addedCash = Integer.valueOf(addedAmount);
                                            long timeMillis = System.currentTimeMillis();
                                            insert( getDate(),addedCash, comment);
                                            amount.setText("");
                                            comments.setText("");
                                            Toast.makeText(AddedFloat.this,"Added successfully",Toast.LENGTH_SHORT).show();

                                        }
                                    });
                    builder
                            .setNegativeButton(
                                    "No",
                                    new DialogInterface
                                            .OnClickListener() {

                                        @Override
                                        public void onClick(DialogInterface dialog,
                                                            int which)
                                        {
                                            dialog.cancel();
                                        }
                                    });

                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                }
                else{
                    Toast.makeText(AddedFloat.this,"Amount cannot be empty",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void insert(String date,Integer addedCash, String comment) {

        String type = "A float of " + addedCash + " Ksh was added.";
        //Your DB Helper
        SQLiteOpenHelper dbHelper = new DatabaseHelper(AddedFloat.this);
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        ContentValues contentValue = new ContentValues();
        ContentValues contentValue1 = new ContentValues();
        contentValue.put(DATE_MILLIS, date);
        contentValue.put(COLUMN_ADDED_FLOAT, addedCash);
        contentValue.put(TIME_OF_TRANSACTION, DateAndTime.currentTimeOfAdd);
        contentValue.put(COLUMN_COMMENT,comment);
        contentValue.put(COLUMN_MPESA_STATUS, 0);

        contentValue.put(COLUMN_ADDED_CASH, 0);
        contentValue.put(COLUMN_CLOSING_CASH, 0);
        contentValue.put(COLUMN_OPENING_CASH, 0);
        contentValue.put(COLUMN_OPENING_FLOAT, 0);
        contentValue.put(COLUMN_REDUCTED_CASH, 0);
        contentValue.put(COLUMN_REDUCTED_FLOAT, 0);

        contentValue1.put(TRANSACTION_TYPE, type);
        contentValue1.put(TRANSACTION_DATE,currentDateandTime);
        contentValue1.put(TRANSACTION_USER, PreferenceHelper.getUsername());
        contentValue1.put(COLUMN_TRANSACTION_STATUS, 0);

        //insert data in DB
        database.insert(TABLE_MPESA,null,contentValue);
        database.insert(TABLE_TRANSACTIONS,null,contentValue1);

        //Close the DB connection.
        database.close();

    }
}
