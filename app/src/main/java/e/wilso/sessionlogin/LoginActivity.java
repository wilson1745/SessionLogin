package e.wilso.sessionlogin;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

   private Button login, register;
   private EditText etEmail, etPass;
   private DbHelper openHelper;
   private Session session;

   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_login);

      openHelper = DbHelper.getInstance(this);

      session = new Session(this);
      login = (Button)findViewById(R.id.btnLogin);
      register = (Button)findViewById(R.id.btnReg);
      etEmail = (EditText)findViewById(R.id.etEmail);
      etPass = (EditText)findViewById(R.id.etPass);

      login.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
            login();
         }
      });

      register.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
            startActivity(new Intent(LoginActivity.this,RegisterActivity.class));
         }
      });

      if(session.loggedin()) {
         startActivity(new Intent(LoginActivity.this,MainActivity.class));
         finish();
      }
   }

   private void login() {
      String email = etEmail.getText().toString();
      String pass = etPass.getText().toString();

      if(openHelper.getUser(email,pass)) {
         session.setLoggedin(true);
         startActivity(new Intent(LoginActivity.this, MainActivity.class));
         finish();
      }
      else {
         Toast.makeText(getApplicationContext(), "Wrong email/password",Toast.LENGTH_SHORT).show();
      }
   }
}
