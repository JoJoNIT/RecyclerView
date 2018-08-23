package com.wzlab.recyclerview;

import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.nfc.Tag;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.lang.reflect.Field;

public class TimePickerActivity extends AppCompatActivity {
private static final String TAG ="TimePickerActivity";
private TimePicker tp1,tp2;
private RecyclerView mRecyclerView;
    private TextView mTvpop;
    private CustomPopWindow mCustomPopWindow;
    String[] label = {"重复","自定义","工作日"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_picker);
        mTvpop = (TextView) findViewById(R.id.tv_pop);
        mTvpop.setOnClickListener(new View.OnClickListener() {
                                      @Override
                                      public void onClick(View view) {
                                          showPopMenu();
                                      }


                                  });
//        mRecyclerView = findViewById(R.id.recyclerView);
//        mRecyclerView.setLayoutManager(new LinearLayoutManager(TimePickerActivity.this));
//        mRecyclerView.setAdapter(new LinearAdapter(TimePickerActivity.this, label,new LinearAdapter.OnItemClickListener() {
//            @Override
//            public void onClick(int pos) {
//                Toast.makeText(TimePickerActivity.this, "" + label[pos], Toast.LENGTH_SHORT).show();
//            }
//        }));
//        mRecyclerView.addItemDecoration(new MyDecoration());//分割线设置


        tp1 = (TimePicker) findViewById(R.id.tp1);
        tp1.setIs24HourView(true);
        tp2 = (TimePicker) findViewById(R.id.tp2);
        tp2.setIs24HourView(true);
        tp1.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker timePicker, int i, int i1) {
                Log.e(TAG, "onTimeChanged: " + i);
                //    Toast.makeText(getApplicationContext(),i,Toast.LENGTH_LONG).show();
            }
        });
        Resources systemResources = Resources.getSystem();
        int hourNumberPickerId = systemResources.getIdentifier("hour", "id", "android");
        int minuteNumberPickerId = systemResources.getIdentifier("minute", "id", "android");


        NumberPicker hourNumberPicker = (NumberPicker) tp1.findViewById(hourNumberPickerId);
        NumberPicker minuteNumberPicker = (NumberPicker) tp1.findViewById(minuteNumberPickerId);
        setNumberPickerDivider(hourNumberPicker);
        setNumberPickerDivider(minuteNumberPicker);
        NumberPicker hourNumberPicker2 = (NumberPicker) tp2.findViewById(hourNumberPickerId);
        NumberPicker minuteNumberPicker2 = (NumberPicker) tp2.findViewById(minuteNumberPickerId);
        setNumberPickerDivider(hourNumberPicker2);
        setNumberPickerDivider(minuteNumberPicker2);
    }
        private void showPopMenu(){
            View contentView = LayoutInflater.from(this).inflate(R.layout.pop_menu,null);


            RecyclerView mRvLabels = contentView.findViewById(R.id.recyclerView);
            mRvLabels.setLayoutManager(new LinearLayoutManager(this));
            LinearAdapter linearAdapter = new LinearAdapter(getApplicationContext(), label, new LinearAdapter.OnItemClickListener() {
                @Override
                public void onClick(int pos) {
                    mTvpop.setText(label[pos]);
                    Toast.makeText(getApplicationContext(),label[pos],Toast.LENGTH_SHORT).show();
                }
            });
            mRvLabels.setAdapter(linearAdapter);

            handleLogic(contentView);
            mCustomPopWindow=new CustomPopWindow.PopupWindowBuilder(this)
                    .setView(contentView)
                    .create()
                    .showAtLocation(findViewById(R.id.ll_root), Gravity.BOTTOM,0,0);
        }
        private void handleLogic(View contentView){
            View.OnClickListener listener = new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            };

        }







//    class MyDecoration extends RecyclerView.ItemDecoration {
//        @Override
//        public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
//            super.getItemOffsets(outRect, view, parent, state);
//            outRect.set(0, 0, 0, getResources().getDimensionPixelOffset(R.dimen.dividerHeight));
//        }


        private void setNumberPickerDivider(NumberPicker numberPicker) {
            final int count = numberPicker.getChildCount();
            for (int i = 0; i < count; i++) {
                try {
                    Field dividerField = numberPicker.getClass().getDeclaredField("mSelectionDivider");
                    dividerField.setAccessible(true);
                    ColorDrawable colorDrawable = new ColorDrawable(
                            ContextCompat.getColor(this, android.R.color.holo_blue_light));
                    dividerField.set(numberPicker, colorDrawable);
                    numberPicker.invalidate();
                } catch (NoSuchFieldException | IllegalAccessException | IllegalArgumentException e) {
                    Log.w("setNumberPickerTxtClr", e);
                }
            }
        }
    }

