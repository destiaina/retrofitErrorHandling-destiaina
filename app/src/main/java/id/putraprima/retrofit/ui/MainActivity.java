package id.putraprima.retrofit.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import id.putraprima.retrofit.ProfileeActivity;
import id.putraprima.retrofit.R;
import id.putraprima.retrofit.RegisterrActivity;
import id.putraprima.retrofit.api.helper.ServiceGenerator;
import id.putraprima.retrofit.api.models.ApiError;
import id.putraprima.retrofit.api.models.ErrorUtils;
import id.putraprima.retrofit.api.models.LoginRequest;
import id.putraprima.retrofit.api.models.LoginResponse;
import id.putraprima.retrofit.api.services.ApiInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    public static final String NAME_KEY="name";
    public static final String VERSION_KEY="version";
    public static final String TOKEN_KEY="token";
    public static final String TOKEN_TYPE="token_type";
    TextView name, version;
    private LoginRequest loginRequest;
    EditText email, password;
    private String token, token_type;
    private SplashActivity splash;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        name=findViewById(R.id.mainTxtAppName);
        version=findViewById(R.id.mainTxtAppVersion);
        email=findViewById(R.id.edtEmail);
        password=findViewById(R.id.edtPassword);
        name.setText(splash.getAppName(MainActivity.this));
        version.setText(splash.getAppVersion(MainActivity.this));
    }

    private void login() {
        ApiInterface service = ServiceGenerator.createService(ApiInterface.class);
        Call<LoginResponse> call = service.login(new LoginRequest(email.getText().toString(),password.getText().toString()));
        call.enqueue(new Callback<LoginResponse>(){
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                Toast.makeText(MainActivity.this, "Berhasil login", Toast.LENGTH_SHORT).show();

                if (response.isSuccessful()){
                    SharedPreferences preference = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                    SharedPreferences.Editor editor = preference.edit();
                    editor.putString("token", response.body().getToken());
                    editor.apply();
                    token=response.body().getToken();
                    token_type=response.body().getToken_type();
                    Intent intent=new Intent(MainActivity.this, ProfileeActivity.class);
                    intent.putExtra(TOKEN_KEY, token);
                    intent.putExtra(TOKEN_TYPE, token_type);
                    startActivity(intent);

                } else if (email.getText().toString().length()==0 ){
                    ApiError error = ErrorUtils.parseError(response);
                    Toast.makeText(MainActivity.this, error.getError().getEmail().get(0), Toast.LENGTH_SHORT).show();

                }else if (password.getText().toString().length()==0){
                    ApiError error = ErrorUtils.parseError(response);

                    Toast.makeText(MainActivity.this, error.getError().getPassword().get(0), Toast.LENGTH_SHORT).show();
                }
                else{
                    ApiError error = ErrorUtils.parseError(response);
                    Toast.makeText(MainActivity.this, error.getError().getEmail().get(0), Toast.LENGTH_SHORT).show();

                }

            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Gagal Mendapatkan Token", Toast.LENGTH_SHORT).show();
            }
        });
    }

    //private boolean validasiInput() {
    //    if(TextUtils.isEmpty(email.getText())){
    //        Toast.makeText(this, "Enter your Email", Toast.LENGTH_SHORT).show();
    //        return false;
    //    } else if(TextUtils.isEmpty(password.getText())){
    //        Toast.makeText(this, "Enter your password", Toast.LENGTH_SHORT).show();
    //        return false;
    //    } else if(!Patterns.EMAIL_ADDRESS.matcher(email.getText().toString()).matches()){
    //        Toast.makeText(this, "Your email address is invalid", Toast.LENGTH_SHORT).show();
    //        return false;
    //    } else{
    //        return true;
    //    }
    //}
    public void prosesLogin(View view) {
        login();
    }

    public void prosesRegister(View view) {
        Intent intent=new Intent(this, RegisterrActivity.class);
        startActivity(intent);
    }
}