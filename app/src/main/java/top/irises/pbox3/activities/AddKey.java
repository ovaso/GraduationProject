package top.irises.pbox3.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.DatePicker;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.ParseException;
import java.util.Calendar;

import top.irises.pbox3.R;
import top.irises.pbox3.databinding.ActivityAddKeyBinding;
import top.irises.pbox3.room.domain.Ke;
import top.irises.pbox3.utils.DateConverter;
import top.irises.pbox3.vms.AddKeyViewModel;

public class AddKey extends AppCompatActivity {

    private ActivityAddKeyBinding binding;
    private int mYear,mMonth,mDay,tH,tM;
    private Calendar ca;
    private DatePickerDialog datePickerDialog;
    private TimePickerDialog timePickerDialog;
    private String initTime;
    private AddKeyViewModel mViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_add_key);
        binding.setLifecycleOwner(this);
        getSupportActionBar().setTitle("添加记录");
        mViewModel = new ViewModelProvider(this).get(AddKeyViewModel.class);
        Ke ke = new Ke();

        setDatePicker();

        datePickerDialog = new DatePickerDialog(this,new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                mYear = year;
                mMonth = month+1;
                mDay = dayOfMonth;
                timePickerDialog.updateTime(ca.get(Calendar.HOUR_OF_DAY),ca.get(Calendar.MINUTE));
                timePickerDialog.show();
            }
        },mYear, mMonth, mDay);
        timePickerDialog = new TimePickerDialog(this,new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                tH = hourOfDay;
                tM = minute;
                initTime = getDatetime(mYear,mMonth,mDay,tH,tM);
                mViewModel.getExpiredTime().setValue(initTime);
            }
        },tH,tM,true);


        TextWatcher watcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                ke.setPs(binding.keAddPs.getText().toString());
                ke.setAccount(binding.keAddAcc.getText().toString());
                ke.setKey(binding.keAddKey.getText().toString());
                ke.setType(binding.keAddType.getText().toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        };

        binding.keAddPs.addTextChangedListener(watcher);
        binding.keAddAcc.addTextChangedListener(watcher);
        binding.keAddKey.addTextChangedListener(watcher);
        binding.keAddType.addTextChangedListener(watcher);

        binding.keAddExpire.setOnClickListener(v->{
            datePickerDialog.show();
        });

        mViewModel.getExpiredTime().observe(this,time->{
            binding.keAddExpire.setText(time);
        });

        binding.floatingActionButton.setOnClickListener(v->{

            if((ke.getAccount() == null||ke.getKey() == null)||(ke.getAccount().trim().equals("")||ke.getKey().trim().equals(""))){
                Toast.makeText(this,"至少设置账号密码",Toast.LENGTH_SHORT).show();
            }else{
                try {
                    ke.setExpires(DateConverter.getTimestamp(mYear,mMonth,mDay,tH,tM));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                ke.setCreateTime(DateConverter.getTimestamp());
                mViewModel.insertItem(ke);
                finish();
            }
        });
    }

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
