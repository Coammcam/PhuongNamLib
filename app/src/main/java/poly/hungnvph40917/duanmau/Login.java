package poly.hungnvph40917.duanmau;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import poly.hungnvph40917.duanmau.DAO.ThuThuDAO;

public class Login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        EditText edtUser = findViewById(R.id.edtUser);
        EditText edtPass = findViewById(R.id.edtPass);
        Button btnLogin = findViewById(R.id.btnLogin);

        ThuThuDAO thuThuDAO = new ThuThuDAO(this);

        btnLogin.setOnClickListener(view -> {
            String user = edtUser.getText().toString();
            String pass = edtPass.getText().toString();

            if (thuThuDAO.checkLogin(user, pass)) {
                startActivity(new Intent(Login.this, MainActivity.class));

            } else {
                Toast.makeText(Login.this, "user va pass khong dung ", Toast.LENGTH_SHORT).show();
            }

        });

    }
}