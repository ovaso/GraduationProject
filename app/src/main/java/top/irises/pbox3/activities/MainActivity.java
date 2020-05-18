package top.irises.pbox3.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

import java.util.Timer;
import java.util.TimerTask;

import top.irises.pbox3.R;

public class MainActivity extends AppCompatActivity {
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    private static boolean mBackKeyPressed = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        drawerLayout = findViewById(R.id.id_drawer_layout);
        navigationView = findViewById(R.id.id_navigation_view);

        //drawer行为监听
        navigationView.setNavigationItemSelectedListener(e->{
            switch (e.getItemId()){
                case R.id.drawer_item_home:
                    break;
                case R.id.drawer_item_keys:
                    {
                        Intent intent = new Intent(this,KeychainActivity.class);
                        startActivity(intent);
                    }
                    break;
                case R.id.drawer_item_info:
                    {
                        Intent intent = new Intent(this,InfoActivity.class);
                        startActivity(intent);
                    }
                    break;
                case R.id.drawer_item_settings:
                    {
                        Intent intent = new Intent(this,SettingsActivity.class);
                        startActivity(intent);
                    }
                    break;
                default:
                    break;
            }
            drawerLayout.closeDrawer(GravityCompat.START);
            return true;
        });
    }

    /**
     * 检测drawer是否是打开状态,如果是则关闭
     * 检测退出程序
     */
    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else if(!mBackKeyPressed) {
            Toast.makeText(this, "再次操作将退出", Toast.LENGTH_SHORT).show();
            mBackKeyPressed = true;
            new Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    mBackKeyPressed=false;
                }
            },2000);
        } else {
            super.onBackPressed();
        }
    }
}
