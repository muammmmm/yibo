package com.example.Memory;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.Memory.R;

import java.text.SimpleDateFormat;
import java.util.Date;

public class AddContentActivity extends AppCompatActivity {
    private EditText mt;//标题
    private EditText met;//内容
    private DBOpenHelper myDB;
    private SQLiteDatabase mysqldb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //实例化各控件
        setContentView(R.layout.activity_add_content);
        mt=findViewById(R.id.title);
        met=findViewById(R.id.text);
        myDB=new DBOpenHelper(this);
        mysqldb=myDB.getWritableDatabase();
    }
    public void save(View v) {//保存
        DdAdd();
        finish();
    }
    public void cancle(View v) {//返回
        mt.setText("");//重置为空
        met.setText("");

        finish();
    }
    public void DdAdd(){//存入数据库
        ContentValues cv = new ContentValues();
        cv.put("title",mt.getText().toString());//放入
        cv.put("content",met.getText().toString());
        cv.put("time",getTime());
        cv.put("_id", LoginActivity.userID);//获取作者id
        mysqldb.insert("note",null,cv);//插入数据
    }
    public static String getTime(){//获得时间
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
        Date date = new Date();
        String str=sdf.format(date);//格式化字符串
        return str;
    }


}
