package com.wyz.my123;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NavUtils;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private Dialog dialog;
    private View inflate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageButton imageButtonStart = findViewById(R.id.imageButton_start);
        imageButtonStart.setOnClickListener(this);

        ImageButton imageButton_phb = findViewById(R.id.imageButton_phb);
        imageButton_phb.setOnClickListener(this);

        ImageButton imageButton_history = findViewById(R.id.imageButton_history);
        imageButton_history.setOnClickListener(this);

        ImageButton imageButton_set = findViewById(R.id.setting_button);
        imageButton_set.setOnClickListener(this);

        ImageButton imageButton_prompt = findViewById(R.id.imageButton_prompt);
        imageButton_prompt.setOnClickListener(this);

        ImageButton btn = findViewById(R.id.setting_button);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                show_dialog();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        String welcome = "欢迎 !" + getIntent().getStringExtra("displayname");
        Toast toast = Toast.makeText(this, welcome, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER,0,0);
        toast.show();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.imageButton_start:
                Intent intent = new Intent(this,PracticeActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_bottom_in,R.anim.slide_top_out);
                break;
            case R.id.imageButton_phb:
                Intent intent_phb = new Intent(this,PHB_Activity.class);
                startActivity(intent_phb);
                overridePendingTransition(R.anim.slide_left_in,R.anim.slide_right_out);
                break;
            case R.id.imageButton_history:
                Intent intent_his = new Intent(this,RecordActivity.class);
                startActivity(intent_his);
                overridePendingTransition(R.anim.slide_right_in,R.anim.slide_left_out);
                break;
            case R.id.setting_button:
                Intent intent_set = new Intent(this,SetActivity.class);
                startActivity(intent_set);
                overridePendingTransition(R.anim.slide_left_in,R.anim.slide_right_out);
            case R.id.imageButton_prompt:
                Intent intent_prompt = new Intent(this,Layout_Activity.class);
                startActivity(intent_prompt);
                overridePendingTransition(R.anim.slide_right_in,R.anim.slide_left_out);
           /* case R.id.btn_logout:
                Intent intent1 = new Intent(this,.class);
                startActivity(intent1);
                overridePendingTransition(R.anim.slide_left_in,R.anim.slide_right_out);*/
        }
    }

    private void show_dialog(){
        dialog = new Dialog(this, R.style.ActionSheetDialogStyle);
        //填充对话框的布局
        inflate = LayoutInflater.from(this).inflate(R.layout.activity_set, null);
        //获取控件
        Button btn_logout = inflate.findViewById(R.id.btn_logout);
        Button btn_return = inflate.findViewById(R.id.btn_return);
        //将布局设置给Dialog
        dialog.setContentView(inflate);
        //获取当前Activity所在的窗体
        Window dialogWindow = dialog.getWindow();
        //设置Dialog从窗体底部弹出
        dialogWindow.setGravity(Gravity.CENTER);
        //获得窗体的属性
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        lp.y = 0;//设置Dialog距离底部的距离
        //宽度填充当前布局文件宽度
        lp.width = WindowManager.LayoutParams.WRAP_CONTENT;
        //将属性设置给窗体
        dialogWindow.setAttributes(lp);
        dialog.show();//显示对话框

        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, PHB_Activity.class);
                startActivity(intent);
            }
        });

        btn_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

    }
}