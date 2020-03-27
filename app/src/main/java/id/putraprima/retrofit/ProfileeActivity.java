package id.putraprima.retrofit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import java.util.List;
import id.putraprima.retrofit.api.helper.ServiceGenerator;
import id.putraprima.retrofit.api.models.Data;
import id.putraprima.retrofit.api.models.LoginResponse;
import id.putraprima.retrofit.api.models.Profile;
import id.putraprima.retrofit.api.services.ApiInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileeActivity extends AppCompatActivity {

    public static final String TOKEN_KEY="token";
    public static final String TOKEN_TYPE="token_type";
    public static final String NAME_KEY="name";
    public static final String EMAIL_KEY="email";
    public static final String ID_KEY="id";
    private static SharedPreferences preference;
    private String nameText, emailText;
    TextView id, name, email;
    private String token, token_type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profilee);
        id=findViewById(R.id.idText);
        name=findViewById(R.id.nameText);
        email=findViewById(R.id.emailText);
        Bundle extras=getIntent().getExtras();
        if (extras!=null){
            token=extras.getString(TOKEN_KEY);
            token_type=extras.getString(TOKEN_TYPE);
        }
        showProfile();
    }

    private void showProfile() {
        ApiInterface service = ServiceGenerator.createService(ApiInterface.class);
        Call<Data<Profile>> call = service.getProfile(token_type+" "+token);
        call.enqueue(new Callback<Data<Profile>>() {

            @Override
            public void onResponse(Call<Data<Profile>> call, Response<Data<Profile>> response) {
                if (response.body()!=null){
                    String idText=response.body().data.id;
                    String nameText=response.body().data.name;
                    String emailText=response.body().data.email;
                    id.setText(idText);
                    name.setText(nameText);
                    email.setText(emailText);
                }
            }

            @Override
            public void onFailure(Call<Data<Profile>> call, Throwable t) {
                Toast.makeText(ProfileeActivity.this, "Gagal Menampilkan Profile", Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void prosesUpdate(View view) {
        Toast.makeText(ProfileeActivity.this,"profile", Toast.LENGTH_SHORT).show();
        Intent intent=new Intent(ProfileeActivity.this, UpdateActivity.class);
        intent.putExtra(TOKEN_TYPE, token_type);
        intent.putExtra(TOKEN_KEY, token);
        intent.putExtra(NAME_KEY, nameText );
        intent.putExtra(EMAIL_KEY, emailText);
        startActivity(intent);
    }

}