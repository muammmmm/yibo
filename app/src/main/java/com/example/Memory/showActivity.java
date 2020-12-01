package com.example.Memory;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.Memory.R;

public class showActivity extends AppCompatActivity {//修改日记内容
    private EditText mtitle;
    private EditText mtext;
    private TextView time;
    private DBOpenHelper myDB;
    private SQLiteDatabase msql;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);
        //修改页面各控件
        mtitle=(EditText) findViewById(R.id.showtitle);
        mtext=(EditText) findViewById(R.id.showtext);
        time=(TextView)findViewById(R.id.showtime);
        myDB=new DBOpenHelper(this);

        msql=myDB.getWritableDatabase();

        mtitle.setText(getIntent().getStringExtra("title"));
        mtext.setText(getIntent().getStringExtra("content"));
        time.setText(getIntent().getStringExtra("time"));
    }
    //删除 delete

    public void delete(View v) {
        int id = getIntent().getIntExtra("ID",0);
        msql.delete("note","ID = " + id,null);
        finish();

    }
    //修改 update

    public void update(View v){//修改谁的日记，以id为基数修改
        int id = getIntent().getIntExtra("ID",0);
        String content=mtext.getText().toString();//获取之前写的内容
        msql.execSQL("UPDATE note SET content=? WHERE ID = ?",
                new String[]{content,id+""});
        String title=mtitle.getText().toString();
        msql.execSQL("UPDATE note SET title=? WHERE ID = ?",
                new String[]{title,id+""});
        finish();
    }
    //关闭

    public void goBack(View v) {
        finish();
    }

}


