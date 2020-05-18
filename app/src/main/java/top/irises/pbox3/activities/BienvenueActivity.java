package top.irises.pbox3.activities;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Toast;

import org.litepal.LitePal;

import java.security.PrivateKey;
import java.util.Date;

import top.irises.pbox3.R;
import top.irises.pbox3.databinding.ActivityBienvenueBinding;
import top.irises.pbox3.room.domain.Assign;

public class BienvenueActivity extends AppCompatActivity {

    private long currentTime;
    private ActivityBienvenueBinding binding;
    private SharedPreferences shp;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bienvenue);
        this.currentTime = new Date().getTime();
        binding = DataBindingUtil.setContentView(this,R.layout.activity_bienvenue);
        binding.setLifecycleOwner(this);

        shp = getSharedPreferences("conf", MODE_PRIVATE);
        editor = shp.edit();
        binding.button2.setOnClickListener(v->{
            if (binding.keyFirst.getText().toString().equals("") || binding.keyAgain.getText().toString().equals("")){
                Toast.makeText(this,"必须输入密码才能使用!",Toast.LENGTH_SHORT).show();
            }else if(!binding.keyFirst.getText().toString().equals(binding.keyAgain.getText().toString())){
                Toast.makeText(this,"密码不一致",Toast.LENGTH_SHORT).show();
            } else{
                editor.putString("k",binding.keyAgain.getText().toString());
                editor.putBoolean("isFirst",false);
                editor.apply();
                Intent intent = new Intent(this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
