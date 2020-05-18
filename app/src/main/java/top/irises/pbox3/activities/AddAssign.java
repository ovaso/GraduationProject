package top.irises.pbox3.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProvider;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.ParseException;
import java.util.Calendar;

import top.irises.pbox3.R;
import top.irises.pbox3.base.Global;
import top.irises.pbox3.room.domain.Assign;
import top.irises.pbox3.utils.DateConverter;
import top.irises.pbox3.vms.AddPageViewModel;
import top.irises.pbox3.vms.AssignmentViewModel;

public class AddAssign extends AppCompatActivity {

    private Calendar ca;
    private DatePickerDialog datePickerDialog1;
    private DatePickerDialog datePickerDialog2;
    private TimePickerDialog timePickerDialog1;
    private TimePickerDialog timePickerDialog2;

    private int mYear,mMonth,mDay,tH,tM;
    private boolean flag = true;
    private String initTime;
    private MutableLiveData<String> startTime;
    private MutableLiveData<String> endTime;
    private FloatingActionButton fab;
    private Assign assign;
    private AddPageViewModel mViewmodel;
    private AssignmentViewModel assignViewModel;

    TextView st;
    TextView et;
    TextView title;
    TextView content;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_assign);
        assign = (Assign)getIntent().getSerializableExtra("assign_item");
        mViewmodel = new ViewModelProvider(this).get(AddPageViewModel.class);
        if (assign != null){
            setContentView(R.layout.activity_show_assign);
            getSupportActionBar().setTitle("查看");
            st = findViewById(R.id.add_assign_start_time);
            et = findViewById(R.id.add_assign_end_datetime);
            title = findViewById(R.id.add_assign_title_tv);
            fab = findViewById(R.id.add_fab);
            content = findViewById(R.id.add_assign_content_tv);
            fab.setVisibility(View.INVISIBLE);
            title.setText(assign.getTitle());
            title.setKeyListener(null);
            content.setText(assign.getContent());
            content.setSingleLine(false);
            content.setKeyListener(null);
            if (assign.getStartTime() != 0){
                st.setText(DateConverter.getDateFromTimestamp(assign.getStartTime()));
            }else{
                st.setVisibility(View.INVISIBLE);
            }
            if (assign.getEndTime() != 0){
                et.setText(DateConverter.getDateFromTimestamp(assign.getEndTime()));
            }else{
                et.setVisibility(View.INVISIBLE);
            }
        }else {
            setContentView(R.layout.activity_add_assign);
            assignViewModel = new ViewModelProvider(this).get(AssignmentViewModel.class);
            startTime = mViewmodel.getStartTime();
            startTime = mViewmodel.getStartTime();
            endTime = mViewmodel.getEndTime();
            assign = new Assign();
            st = findViewById(R.id.add_assign_start_time);
            et = findViewById(R.id.add_assign_end_datetime);
            title = findViewById(R.id.add_assign_title_tv);
            content = findViewById(R.id.add_assign_content_tv);
            fab = findViewById(R.id.add_fab);
            setDatePicker();
            timePickerDialog1 = new TimePickerDialog(this,new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                    tH = hourOfDay;
                    tM = minute;
                    initTime = getDatetime(mYear,mMonth,mDay,tH,tM);
                    mViewmodel.getStartTime().setValue(initTime);
                }
            },tH,tM,true);
            datePickerDialog1 = new DatePickerDialog(this,new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                    mYear = year;
                    mMonth = month+1;
                    mDay = dayOfMonth;
                    timePickerDialog1.updateTime(ca.get(Calendar.HOUR_OF_DAY),ca.get(Calendar.MINUTE));
                    timePickerDialog1.show();
                }
            },mYear, mMonth, mDay);
            timePickerDialog2 = new TimePickerDialog(this,new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                    tH = hourOfDay;
                    tM = minute;
                    initTime = getDatetime(mYear,mMonth,mDay,tH,tM);
                    mViewmodel.getEndTime().setValue(initTime);
                }
            },tH,tM,true);
            datePickerDialog2 = new DatePickerDialog(this,new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                    mYear = year;
                    mMonth = month+1;
                    mDay = dayOfMonth;
                    timePickerDialog2.updateTime(ca.get(Calendar.HOUR_OF_DAY),ca.get(Calendar.MINUTE));
                    timePickerDialog2.show();
                }
            },mYear, mMonth, mDay);

            getSupportActionBar().setTitle("添加记录");

            st.setOnClickListener(v->{
                datePickerDialog1.show();
            });
            et.setOnClickListener(v->{
                datePickerDialog2.show();
            });

            startTime.observe(this,s->{
                st.setText(startTime.getValue());
                try {
                    assign.setStartTime(DateConverter.getTimestamp(mYear,mMonth,mDay,tH,tM));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            });
            endTime.observe(this,s->{
                et.setText(endTime.getValue());
                try {
                    assign.setEndTime(DateConverter.getTimestamp(mYear,mMonth,mDay,tH,tM));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            });

            title.addTextChangedListener(watcher);
            content.addTextChangedListener(watcher);

            fab.setOnClickListener(v->{
                if (assign.getTitle() == null || assign.getTitle().trim().equals("")) {
                    Toast.makeText(this,"插入未通过,请输入标题并重新添加",Toast.LENGTH_SHORT).show();
                }else if (Global.GLOBAL_STRICT) {
                    if (assign.getStartTime() == 0l || assign.getEndTime() == 0l ) {
                        Toast.makeText(this,"严格模式下需要设置开始和结束时间",Toast.LENGTH_SHORT).show();
                    }else if(assign.getStartTime()>assign.getEndTime()) {
                        Toast.makeText(this,"结束日期需要在开始日期之后",Toast.LENGTH_SHORT).show();
                    }else{
                        assign.setCreateTime(DateConverter.getTimestamp());
                        mViewmodel.addAssign(assign);
                        finish();
                    }
                }else{
                    assign.setCreateTime(DateConverter.getTimestamp());
                    mViewmodel.addAssign(assign);
                    finish();
                }
            });
        }
    }

    TextWatcher watcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            assign.setTitle(title.getText().toString());
            assign.setContent(content.getText().toString());
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    private void setDatePicker(){
        this.ca = Calendar.getInstance();
        mYear = ca.get(Calendar.YEAR);
        mMonth = ca.get(Calendar.MONTH);
        mDay = ca.get(Calendar.DAY_OF_MONTH);
    }
    private String getDatetime(int year,int month,int day,int hourOfDay,int minute){
        return String.valueOf(year)+"年"
                +String.valueOf(month)+"月"
                +String.valueOf(day)+"日 "
                +String.valueOf(hourOfDay)+"点"
                +String.valueOf(minute)+"分";
    }
}
