package top.irises.pbox3.activities;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import top.irises.pbox3.R;
import top.irises.pbox3.room.domain.Pay;
import top.irises.pbox3.room.repos.PayRepo;
import top.irises.pbox3.utils.DateConverter;

public class AddPay extends AppCompatActivity {

    private ActionBar actionBar;
    private EditText expand,ps;
    private FloatingActionButton fab;
    private Pay pay;
    private TextWatcher watcher;
    private PayRepo repo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_pay);
        actionBar = getSupportActionBar();
        actionBar.setTitle("添加账单");
        repo = new PayRepo(this);
        pay = new Pay();
        fab = findViewById(R.id.fab_pay_add_done);
        expand = findViewById(R.id.add_pay_expand);
        ps = findViewById(R.id.add_pay_ps);

        //FALLBACKS
        watcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                pay.setPs(ps.getText().toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        };
        ps.addTextChangedListener(watcher);

        fab.setOnClickListener(v->{
            if (expand.getText().toString() == null || expand.getText().toString().trim().equals("")){
                Toast.makeText(this,"请输入金额,再执行操作",Toast.LENGTH_SHORT).show();
            }else {
                pay.setExpend(Double.valueOf(expand.getText().toString()));
                pay.setCreateTime(DateConverter.getTimestamp());
                repo.insertItem(pay);
                finish();
            }

        });



    }

    public void initGLOBAL(){
        actionBar = getSupportActionBar();
        actionBar.setTitle("添加账单");
    }
    public void initComponents(){
        fab = findViewById(R.id.fab_pay_add_done);
        expand = findViewById(R.id.add_pay_expand);
        ps = findViewById(R.id.add_pay_ps);
    }
    public void initVar(){
    }
}
