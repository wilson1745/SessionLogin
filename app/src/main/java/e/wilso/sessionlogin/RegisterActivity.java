package e.wilso.sessionlogin;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity {

   private Button reg;
   private TextView tvLogin;
   private EditText etEmail, etPass;
   private DbHelper openHelper;

   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_register);

      openHelper = DbHelper.getInstance(this);

      reg = (Button)findViewById(R.id.btnReg);
      tvLogin = (TextView)findViewById(R.id.tvLogin);
      etEmail = (EditText)findViewById(R.id.etEmail);
      etPass = (EditText)findViewById(R.id.etPass);

      reg.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
            register();
         }
      });

      tvLogin.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
            startActivity(new Intent(RegisterActivity.this,LoginActivity.class));
            finish();
         }
      });
   }

   private void register() {
      String email = etEmail.getText().toString();
      String pass = etPass.getText().toString();

      if(email.isEmpty() && pass.isEmpty()) {
         displayToast("Username/password field empty");
      }
      else {
         openHelper.addUser(email,pass);
         displayToast("User registered");
         finish();
      }
   }

   private void displayToast(String message) {
      Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
   }
}
