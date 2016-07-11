package es.source.code.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.regex.Pattern;

import es.source.code.conf.SCOSConf;
import es.source.code.model.User;

public class LoginOrRegister extends AppCompatActivity {
    private EditText edtUsername;
    private EditText edtPassword;
    private Button btnLogin;
    private Button btnReturn;
    private Button btnRegister;
    private String username = null;
    private String password = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_or_register);

        edtUsername = (EditText) findViewById(R.id.edt_username);
        edtPassword = (EditText) findViewById(R.id.edt_password);

        //Button 事件
        btnLogin = (Button) findViewById(R.id.btn_login);
        btnReturn = (Button) findViewById(R.id.btn_return);
        btnRegister = (Button) findViewById(R.id.btn_register);

        btnLogin.setOnClickListener(loginClickListener);
        btnReturn.setOnClickListener(returnClickListener);
        btnRegister.setOnClickListener(registerClickListener);
    }

    private View.OnClickListener loginClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            username = edtUsername.getText().toString();
            password = edtPassword.getText().toString();
            Pattern pattern = Pattern.compile("[a-zA-Z0-9]{6,20}");
            if ("".equals(username)) {
                edtUsername.setError("用户名不为空!");
            } else if (!pattern.matcher(username).matches()) {
                edtUsername.setError("输入内容不符合规则");
            } else {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        String params = "username=" + username + "&" + "password=" + password;
                        String path = SCOSConf.SERVER + "?" + params;

                        try {
                            URL url = new URL(path);
                            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                            conn.connect();

                            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                            if (in.ready()) {
                                String echo = in.readLine();
                                if ("1".equals(echo)) {
                                    User loginUser = new User();
                                    loginUser.setUserName(username);
                                    loginUser.setPassword(password);
                                    loginUser.setOldUser(true);

                                    Intent intent = new Intent(LoginOrRegister.this, MainScreen.class);
                                    Bundle bundle = new Bundle();
                                    bundle.putString("Login", "LoginSuccess");
                                    intent.putExtras(bundle);
                                    intent.putExtra("LoginUser", loginUser);
                                    startActivity(intent);
                                }
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
            }
        }
    };

    private View.OnClickListener returnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(LoginOrRegister.this, MainScreen.class);
            Bundle bundle = new Bundle();
            bundle.putString("Return", "Return");
            intent.putExtras(bundle);
            startActivity(intent);
        }
    };

    private View.OnClickListener registerClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            username = edtUsername.getText().toString();
            password = edtPassword.getText().toString();
            Pattern pattern = Pattern.compile("[a-zA-Z0-9]{6,20}");
            if ("".equals(username)) {
                edtUsername.setError("用户名不为空!");
            } else if (!pattern.matcher(username).matches()) {
                edtUsername.setError("输入内容不符合规则");
            } else {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        String params = "username=" + username + "&" + "password=" + password;
                        String path = SCOSConf.SERVER + "?" + params;

                        try {
                            URL url = new URL(path);
                            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                            conn.connect();

                            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                            if (in.ready()) {
                                String echo = in.readLine();
                                if ("1".equals(echo)) {
                                    User loginUser = new User();
                                    loginUser.setUserName(username);
                                    loginUser.setPassword(password);
                                    loginUser.setOldUser(true);

                                    Intent intent = new Intent(LoginOrRegister.this, MainScreen.class);
                                    Bundle bundle = new Bundle();
                                    bundle.putString("Register", "RegisterSuccess");
                                    intent.putExtras(bundle);
                                    startActivity(intent);
                                }
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
            }
        }
    };
}
