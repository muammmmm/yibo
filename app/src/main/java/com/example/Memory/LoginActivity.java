package com.example.Memory;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.Memory.R;


public class LoginActivity extends AppCompatActivity {
    public static int userID;//主键
    public static String username;
    String name, password;//保存数据
    private EditText edt_username;//用户名字
    private EditText edt_password;//用户密码
    private Button btn_login;
    private Button btn_register;
    //private SQLiteDatabase sqlDate;
    private DBOpenHelper dbOpenHelper;
    @Override

    //初始化各控件
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);
        edt_username =  findViewById(R.id.e_user_name);
        edt_password = findViewById(R.id.e_user_password);
        btn_login =findViewById(R.id.b_btn_login);
        btn_register = findViewById(R.id.b_btn_register);
        dbOpenHelper = new DBOpenHelper(this);//创建对象
        //注册
        btn_register.setOnClickListener(new View.OnClickListener() {//当点击注册时，进入注册界面
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, RegistActivity.class);//跳转
                startActivityForResult(intent,1);//在 RegistActivity.class做一系列操作，在RegistActivity.class做的一系列操作传回到Login.this
            }
        });
        //登入
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {//点击登录
                name = edt_username.getText().toString();//从界面的Text里获得
                password = edt_password.getText().toString();//同理
                if (enter(name, password)) {
                    Toast.makeText(LoginActivity.this, "登录成功" + name, Toast.LENGTH_SHORT).show();
                    new Thread(){//线程
                        public void run() {
                            try {
                                Thread.sleep(1000);//捕捉异常
                                Intent intent1 = new Intent(LoginActivity.this, MainActivity.class);//跳转到主界面
                                startActivity(intent1);//启动MainActivity
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }.start();
                } else if (TextUtils.isEmpty(name) || TextUtils.isEmpty(password) || TextUtils.isEmpty(name) && TextUtils.isEmpty(password)) {
                    Toast.makeText(LoginActivity.this, "请输入用户名或密码", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(LoginActivity.this, "登入失败", Toast.LENGTH_SHORT).show();
                }//判断是否输入或登陆失败
            }
        });
    }

    //判断是否username,password 和数据库的相同 enter()

    public boolean enter(String name, String password) {
        SQLiteDatabase db = dbOpenHelper.getWritableDatabase();//以读写方式读写数据库中的SQLiteDatabase实例
        String sql = "select _id from user where name=? and password=?";
        Cursor cursor = db.rawQuery(sql, new String[]{name, password});//数据库中随机的一行数据
        if (cursor.getCount() != 0) {//返回Cursor 中的行数
            if (cursor.moveToNext()) {//移动光标成功，即找到符合的name，password
                userID = cursor.getInt(cursor.getColumnIndex("_id"));//找到指定列名称的数据
                username = name;
            }
            cursor.close();//关闭游标移动
            return true;
        }
        return false;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {//接收布局里输入的值
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==1 && resultCode==1){
            String name=data.getStringExtra("name");
            String password=data.getStringExtra("password");
            edt_username.setText(name);//存入与数据库中的数据进行比较
            edt_password.setText(password);
        }
    }
}