package top.irises.pbox3.activities;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.AsyncListUtil;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Date;

import top.irises.pbox3.R;
import top.irises.pbox3.fragments.Contact;
import top.irises.pbox3.room.domain.Cont;
import top.irises.pbox3.room.repos.ContRepo;
import top.irises.pbox3.utils.DateConverter;

public class AddCont extends AppCompatActivity {
    private ActionBar actionBar;
    private FloatingActionButton fab;
    private Cont cont;
    private ContRepo repo;
    private EditText name,tel,email,ps;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //GLOBAL
        actionBar = getSupportActionBar();
        setContentView(R.layout.activity_add_cont);
        actionBar.setTitle("添加人脉");
        //VARIABLES
        repo = new ContRepo(this);
        //COMPONENTS
        fab = findViewById(R.id.fab_cont_add_done);
        name = findViewById(R.id.add_cont_name);
        tel = findViewById(R.id.add_cont_tel);
        email = findViewById(R.id.add_cont_email);
        ps = findViewById(R.id.add_cont_ps);

        //FALLBACKS



        if(getIntent().getSerializableExtra("cont_item") == null){
            //LOGIC
            cont = new Cont();
            fab.setOnClickListener(v->{
                if (name.getText().toString().trim().equals("")
                        || name.getText() == null
                        || tel.getText().toString().trim().equals("")){
                    Toast.makeText(this,"至少完善姓名和电话信息",Toast.LENGTH_SHORT).show();
                }else{
                    cont.setCreateTime(DateConverter.getTimestamp());
                    repo.insertItem(cont);
                    finish();
                }
            });
            TextWatcher watcher = new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    cont.setName(name.getText().toString());
                    cont.setTel(tel.getText().toString());
                    cont.setEmail(email.getText().toString());
                    cont.setPs(ps.getText().toString());
                }

                @Override
                public void afterTextChanged(Editable s) {
                    cont.setName(name.getText().toString());
                    cont.setTel(tel.getText().toString());
                    cont.setEmail(email.getText().toString());
                    cont.setPs(ps.getText().toString());
                }
            };
            name.addTextChangedListener(watcher);
            tel.addTextChangedListener(watcher);
            email.addTextChangedListener(watcher);
            ps.addTextChangedListener(watcher);
        }else{
            fab.setVisibility(View.INVISIBLE);
            actionBar.setTitle("查看");
            cont = (Cont) getIntent().getSerializableExtra("cont_item");
            name.setText(cont.getName().trim().equals("")?"":cont.getName());
            tel.setText(cont.getTel().trim().equals("")?"":cont.getTel());
            email.setHint("");
            email.setText(cont.getEmail().trim().equals("")?"":cont.getEmail());
            email.setOnClickListener(v->{
                Intent intent = new Intent(Intent.ACTION_SENDTO);
                intent.setData(Uri.parse("mailto:"+cont.getEmail()));
                startActivity(intent);
            });
            ps.setText(cont.getPs().trim().equals("")?"":cont.getPs());
            tel.setOnClickListener(v->{
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:"+cont.getTel()));
                startActivity(intent);
            });
            tel.setKeyListener(null);
            email.setKeyListener(null);
            name.setEnabled(false);
            ps.setEnabled(false);
            /*
            TextWatcher watcher = new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    cont.setName(name.getText().toString());
                    cont.setTel(tel.getText().toString());
                    cont.setEmail(email.getText().toString());
                    cont.setPs(ps.getText().toString());
                }

                @Override
                public void afterTextChanged(Editable s) {
                    cont.setName(name.getText().toString());
                    cont.setTel(tel.getText().toString());
                    cont.setEmail(email.getText().toString());
                    cont.setPs(ps.getText().toString());
                }
            };
            name.addTextChangedListener(watcher);
            tel.addTextChangedListener(watcher);
            email.addTextChangedListener(watcher);
            ps.addTextChangedListener(watcher);

            fab.setOnClickListener(v->{
                if (name.getText().toString().trim().equals("")
                        || name.getText() == null
                        || tel.getText().toString().trim().equals("")){
                    Toast.makeText(this,"至少完善姓名和电话信息",Toast.LENGTH_SHORT).show();
                }else{
                    repo.updateItem(cont);
                    cont.setModifyTime(DateConverter.getTimestamp());
                    Toast.makeText(this,"信息已更改",Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            });
            */
        }
    }
}
