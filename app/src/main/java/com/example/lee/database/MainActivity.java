package com.example.lee.database;

import static android.provider.BaseColumns._ID;
//呼叫HELP類的變數
import static com.example.lee.database.DBHelper.EMAIL;
import static com.example.lee.database.DBHelper.NAME;
import static com.example.lee.database.DBHelper.TABLE_NAME;
import static com.example.lee.database.DBHelper.TEL;
import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.view.View.OnClickListener;


public class MainActivity extends Activity {

    private DBHelper dbHelper = null;
    private TextView result = null;
    private Button addbutton = null;
    private Button querybutton = null;
    private EditText nameText = null;
    private EditText telText = null;
    private EditText emailText = null;
    private String name;
    private String tel;
    private String email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //執行help類，會建立demo資料庫和三個欄位
        dbHelper = new DBHelper(this);
        dbHelper.close();
        nameText = (EditText)findViewById(R.id.editText1);
        telText = (EditText)findViewById(R.id.editText2);
        emailText = (EditText)findViewById(R.id.editText3);
        addbutton = (Button)findViewById(R.id.addbutton);
        querybutton = (Button)findViewById(R.id.button);
        addbutton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                add(); //新增資料
            }
        });
        querybutton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                show();
                nameText.setText("");
                telText.setText("");
                emailText.setText("");
            }
        });
        result = (TextView) findViewById(R.id.textview);

    }
    //新增資料
    private void add(){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(NAME,nameText.getText().toString());
        values.put(TEL,telText.getText().toString());
        values.put(EMAIL,emailText.getText().toString());
        db.insert(TABLE_NAME,null,values);
    }
    //顯示資料
    public void show(){
        Cursor cursor = getCursor();
        StringBuilder resultData = new StringBuilder("RESULT: \n");
        while (cursor.moveToNext()){
            int id = cursor.getInt(0);
            String name = cursor.getString(1);
            String tel = cursor.getString(2);
            String email = cursor.getString(3);

            resultData.append(id).append(": ");
            resultData.append(name).append(", ");
            resultData.append(tel).append(", ");
            resultData.append(email).append(", ");
            resultData.append("\n");
        }
        result.setText(resultData);
    }

    public Cursor getCursor(){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String[] columns = {_ID, NAME, TEL, EMAIL};
        Cursor cursor = db.query(TABLE_NAME,columns,null,null,null,null,null);
        startManagingCursor(cursor);
        return cursor;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
