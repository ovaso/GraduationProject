package top.irises.pbox3.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.Toast;

import top.irises.pbox3.R;
import top.irises.pbox3.base.Global;
import top.irises.pbox3.databinding.ActivitySettingsBinding;

public class SettingsActivity extends AppCompatActivity {
    private ActivitySettingsBinding binding;
    private SharedPreferences shp;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_settings);
        binding.setLifecycleOwner(this);
        getSupportActionBar().setTitle("设置");
        shp = getSharedPreferences("conf",MODE_PRIVATE);
        editor = shp.edit();

        binding.setSwitchOrder.setChecked(Global.GLOBAL_ORDER);
        binding.setOrderPs.setText(Global.GLOBAL_ORDER?"当前为按照创建日期降序排列(新->旧)":"当前为按照创建日期降序排列(旧->新)");
        binding.setSwitchKey.setChecked(Global.GLOBAL_SHOW_PSW);
        binding.setKeyPs.setText(Global.GLOBAL_SHOW_PSW?"显示":"不显示");
        binding.setSwitchStrict.setChecked(Global.GLOBAL_STRICT);
        binding.setStrictPs.setText(Global.GLOBAL_STRICT?"开启":"关闭");

        binding.setSwitchOrder.setOnCheckedChangeListener((btnView,isChked)->{
            Global.GLOBAL_ORDER = Global.GLOBAL_ORDER?false:true;
            editor.putBoolean("global_order",Global.GLOBAL_ORDER);
            editor.apply();
            binding.setSwitchOrder.setChecked(Global.GLOBAL_ORDER);
            binding.setOrderPs.setText(Global.GLOBAL_ORDER?"当前为按照创建日期降序排列(新->旧)":"当前为按照创建日期升序排列(旧->新)");
        });
        binding.setSwitchKey.setOnCheckedChangeListener((btnView,isChked)->{
            Global.GLOBAL_SHOW_PSW = Global.GLOBAL_SHOW_PSW?false:true;
            editor.putBoolean("show_psw",Global.GLOBAL_SHOW_PSW);
            editor.apply();
            binding.setSwitchKey.setChecked(Global.GLOBAL_SHOW_PSW);
            binding.setKeyPs.setText(Global.GLOBAL_SHOW_PSW?"显示":"不显示");
        });
        binding.setSwitchStrict.setOnCheckedChangeListener((btnView,isChked)->{
            Global.GLOBAL_STRICT = Global.GLOBAL_STRICT?false:true;
            editor.putBoolean("strict",Global.GLOBAL_STRICT);
            editor.apply();
            binding.setSwitchStrict.setChecked(Global.GLOBAL_STRICT);
            binding.setStrictPs.setText(Global.GLOBAL_STRICT?"开启":"关闭");
        });
    }
}
