package com.wyz.my123;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class Layout_Activity extends AppCompatActivity {

    private int i = 1 ;
    private int a;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_layout);

        LayoutInflater inflater = getLayoutInflater().from(this);
        View view1 = inflater.inflate(R.layout.layout1, null);
        View view2 = inflater.inflate(R.layout.layout2, null);
        View view3 = inflater.inflate(R.layout.layout3, null);

        List<View> viewList = new ArrayList<>();
        viewList.add(view1);
        viewList.add(view2);
        viewList.add(view3);

        ViewPager viewPager = findViewById(R.id.vp);
        Layout_Adapter layout_adapter = new Layout_Adapter(viewList);
        viewPager.setAdapter(layout_adapter);

        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {//当前页
                Log.e("资产", "选择位置：" + position);

                if (position == 0) {
                    /*iv_oval_1.setImageResource(R.drawable.ic_oval_yes);*/
                } else if (position == 1) {
                    /*iv_oval_2.setImageResource(R.drawable.ic_oval_yes);*/
                } else if (position == 2) {
                    /*iv_oval_3.setImageResource(R.drawable.ic_oval_yes);*/
                    Button btn = findViewById(R.id.layout_return);
                    btn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent(Layout_Activity.this, MainActivity.class);
                            startActivity(intent);
                        }
                    });
                    Toast.makeText(Layout_Activity.this, "再次点击屏幕即可退出教程", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

}