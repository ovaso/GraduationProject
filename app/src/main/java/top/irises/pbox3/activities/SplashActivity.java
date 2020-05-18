package top.irises.pbox3.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ImageView;

import top.irises.pbox3.R;
import top.irises.pbox3.base.Global;

public class SplashActivity extends AppCompatActivity {
    ImageView splashImage;
    SharedPreferences shp;
    SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        splashImage = findViewById(R.id.splash_img);
        shp = getSharedPreferences("conf",MODE_PRIVATE);
        editor = shp.edit();
        boolean isFirst = shp.getBoolean("isFirst",true);
        int dbVersion = shp.getInt("iiDataBaseVersion", 0);
        if (isFirst) {

            //修改初始值,默认设置并写入shp
            editor.putBoolean("global_order",false);
            editor.putBoolean("show_psw",false);
            editor.putBoolean("strict",false);
            editor.apply();

            //加载默认设置
            Global.GLOBAL_ORDER = shp.getBoolean("global_order", false);
            Global.GLOBAL_SHOW_PSW = shp.getBoolean("show_psw",false);
            Global.GLOBAL_STRICT = shp.getBoolean("strict",false);
            Intent intent = new Intent(this,BienvenueActivity.class);
            startActivity(intent);
            finish();
        }else{
            //这里做加载设置信息
            Global.GLOBAL_ORDER = shp.getBoolean("global_order", false);
            Global.GLOBAL_SHOW_PSW = shp.getBoolean("show_psw",false);
            Global.GLOBAL_STRICT = shp.getBoolean("strict",false);
            Intent intent = new Intent(this,MainActivity.class);
            startActivity(intent);
            finish();
        }
    }
}
