package com.android_app_project.api;

import com.android_app_project.activity.ForgetPasswordActivity;
import com.android_app_project.entities.Account;
import com.android_app_project.entities.Customer;
import com.android_app_project.model.ForgetPasswordResponse;
import com.android_app_project.model.ResetPasswordRequest;
import com.android_app_project.model.SignupRequest;
import com.android_app_project.model.SignupResponse;
import com.android_app_project.model.UserLoginRequest;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface LoginAPIService {
/*    @GET("categories.php")
    Call<List<Category>> getCategoryAll();

    @GET("category.php")
    Call<Category> getCategory();

    @FormUrlEncoded
    @POST("getcategory.php")
    Call<List<Product>> getProductFromCateID(@Field("idcategory") String cateID);

    @GET("lastproduct.php")
    Call<List<Product>> getLastProduct();

    @FormUrlEncoded
    @POST("registrationapi.php?apicall=login")
    Call<UserLogin> login(@Field("username") String username, @Field("password") String password);

    @Multipart
    @POST("updateimages.php")
    Call<result> upload(@Part("id") RequestBody id, @Part MultipartBody.Part avatar);

    @FormUrlEncoded
    @POST("newmealdetail.php")
    Call<ProductDetailDTO> getProductDetail(@Field("id") String productID);*/
    @POST("login")
    Call<ResponseBody> login(@Body UserLoginRequest userLoginRequest);
    @POST("signup")
    Call<SignupResponse> signup(@Body SignupRequest signupRequest);
    @POST("signup/verify-email")
    Call<ResponseBody> verify(@Query("code") String code, @Query("account") String newAccount, @Query("customer") String newCustomer);
    @POST("forget-password")
    Call<ForgetPasswordResponse> forgotPassword(@Query("email") String email);
    @PUT("reset-password")
    Call<ResponseBody> resetPassword(@Query("reset-password") String resetPasswordRequest,@Query("account") String account);
    @FormUrlEncoded
    @POST("resend-otp")
    Call<ResponseBody> resendOTP(@Field("email") String email, @Field("account") String account);
}
