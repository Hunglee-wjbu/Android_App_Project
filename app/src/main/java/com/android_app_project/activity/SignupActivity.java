package com.android_app_project.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.android_app_project.R;
import com.android_app_project.Utils.Constants;
import com.android_app_project.Utils.SharePrefManager;
import com.android_app_project.api.LoginAPIService;
import com.android_app_project.api.RetrofitClient;
import com.android_app_project.databinding.ActivitySignupBinding;
import com.android_app_project.model.SignupRequest;
import com.android_app_project.model.SignupResponse;
import com.android_app_project.model.UserLoginResponse;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignupActivity extends AppCompatActivity {

    private ActivitySignupBinding binding;

    LoginAPIService loginApiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignupBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.idLoginTextView.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
            startActivity(intent);
            finish();
        });
        binding.idSignUpButton.setOnClickListener(v -> {
            Signup();
        });
    }

    private void Signup() {
        String firstName = binding.idFirstNameEditText.getText().toString().trim();
        String lastName = binding.idLastNameEditText.getText().toString().trim();
        String username = binding.idUsernameEditText.getText().toString().trim();
        String password = binding.idPasswordEditText.getText().toString().trim();
        String email = binding.idEmailEditText.getText().toString().trim();
        if (!validate(firstName,lastName,username,password,email)) {
            return;
        }
        else {
            SignupRequest signupRequest = new SignupRequest(firstName,lastName,username,password,email);

            Log.d("loggs", "userLogin: " + Constants.URL_REGISTRATION);
            loginApiService = RetrofitClient.getInstance().getRetrofit(Constants.URL_REGISTRATION).create(LoginAPIService.class);
            loginApiService.signup(signupRequest).enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    try {
                        if(response.isSuccessful()){
                            Log.i("logg", "raw: " + response.raw());
                            assert response.body() != null;
                            SignupResponse signupResponse = new SignupResponse(response.body().string());
                            if(signupResponse.getMessage()==null)
                                return;
                            if(signupResponse.getMessage().isEmpty() ){
                                Toast.makeText(getApplicationContext(),"Đăng ký không thành công",Toast.LENGTH_SHORT).show();
                                return;
                            } else if (signupResponse.getMessage().contains("successfully")) {
                                Intent intent = new Intent(getApplicationContext(),SignupVerifyEmailActivity.class);
                                startActivity(intent);
                            } else {
                                Toast.makeText(getApplicationContext(),signupResponse.getMessage(),Toast.LENGTH_SHORT).show();
                                return;
                            }
                        }
                        else {
                            Toast.makeText(getApplicationContext(),response.message() , Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    Log.d("logg",t.getMessage());
                }
            });

        }

    }

    private boolean validate(String firstName,String lastName,String username,String password, String email) {
        boolean valid = true;
        Log.d("TAG", "validate: "+email);
        if (firstName.isEmpty())
        {
            binding.idFirstNameEditText.setError(getString(R.string.enteryourname));
            valid = false;
        } else
        {
            binding.idFirstNameEditText.setError(null);
        }
        if (lastName.isEmpty())
        {
            binding.idLastNameEditText.setError(getString(R.string.enteryourname));
            valid = false;
        } else
        {
            binding.idLastNameEditText.setError(null);
        }

        if (username.isEmpty())
        {
            binding.idUsernameEditText.setError(getString(R.string.enteryourname));
            valid = false;
        } else
        {
            binding.idUsernameEditText.setError(null);
        }

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches())
        {
            binding.idEmailEditText.setError(getString(R.string.validemail));
            valid = false;
        } else
        {
            binding.idEmailEditText.setError(null);
        }

        if (password.isEmpty() || password.length() < 6 || password.length() > 12)
        {
            binding.idPasswordEditText.setError(getString(R.string.validpassword));
            valid = false;
        } else
        {
            binding.idPasswordEditText.setError(null);
        }

        return valid;
    }
}