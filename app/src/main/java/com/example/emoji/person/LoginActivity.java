package com.example.emoji.person;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.emoji.R;
import com.example.emoji.base.BaseActivity;
import com.example.emoji.data.bmob.MyUser;
import com.example.emoji.utils.ToastUtil;
import com.example.media.utils.StringUtils;

public class LoginActivity extends BaseActivity<PersonViewModel> implements View.OnClickListener {
    private View registerView;
    private View loginView;
    private EditText lName;
    private EditText lPsw;
    private Button btLogin;
    private Button btLRegister;

    private EditText rName;
    private EditText rPsw;
    private EditText rConPsw;
    private Button btRegister;
    private MutableLiveData<MyUser> liveData;
    private MutableLiveData<Boolean> registerLiveData;//登录是否成功

    @Override
    public void initViewModel() {
        viewModel = new ViewModelProvider(this).get(PersonViewModel.class);
        liveData = viewModel.getUserMutableLiveData();
        registerLiveData = viewModel.getBooleanMutableLiveData();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_register_login;
    }

    @Override
    protected void initView() {
        registerView = findViewById(R.id.register_view);
        loginView = findViewById(R.id.login_view);
        lName = findViewById(R.id.lName);
        lPsw = findViewById(R.id.lPsw);
        btLogin = findViewById(R.id.login);
        btLRegister = findViewById(R.id.lRegister);

        rName = findViewById(R.id.rName);
        rPsw = findViewById(R.id.rPsw);
        rConPsw = findViewById(R.id.rCon_psw);
        btRegister = findViewById(R.id.register);

        loginView.setVisibility(View.VISIBLE);
        registerView.setVisibility(View.INVISIBLE);

        btLogin.setOnClickListener(this);
        btLRegister.setOnClickListener(this);
        btRegister.setOnClickListener(this);
    }

    @Override
    public void initData() {

        liveData.observe(this, myUser -> {
            if (myUser != null) {
                finish();
            }
        });

      registerLiveData.observe(this,isRegister ->{
          //注册成功
          if (isRegister){
//              loginView.setVisibility(View.VISIBLE);
//              registerView.setVisibility(View.INVISIBLE);
              finish();
          }
      });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.login:
                String userName = lName.getText().toString();
                String password = lPsw.getText().toString();
                MyUser mu = new MyUser();
                mu.setUsername(userName);
                mu.setPassword(password);
                viewModel.login(mu);
                break;
            case R.id.lRegister:
                loginView.setVisibility(View.INVISIBLE);
                registerView.setVisibility(View.VISIBLE);
                break;
            case R.id.register:
                String rUserName = rName.getText().toString();
                String rPassword = rPsw.getText().toString();
                String rConPassword = rConPsw.getText().toString();
                if (!StringUtils.isEmpty(rUserName)){
                    if (rPassword.equals(rConPassword)){
                        MyUser newUser = new MyUser();
                        newUser.setUsername(rUserName);
                        newUser.setPassword(rPassword);
                        viewModel.register(newUser);
                    }else {
                        ToastUtil.toastShort("两次输入的密码不一致！！！");
                    }
                }else {
                    ToastUtil.toastShort("用户名不能为空！！！");
                }
                break;
        }
    }
}