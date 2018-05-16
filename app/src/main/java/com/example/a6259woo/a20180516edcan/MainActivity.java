package com.example.a6259woo.a20180516edcan;

import android.app.NotificationManager;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;

public class MainActivity extends AppCompatActivity {
    TextView textView;
    String asd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.pongdang);

        pongbang req = pongbang.retrofit.create(pongbang.class);
        Call<ResponseBody> call = req.test();
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    String reponseStr = response.body().string();
                    JSONObject json = new JSONObject(reponseStr);
                    textView.setText("" + json.getString("time"));
                    asd = json.getString("temp");

                } catch (IOException e) {
                    Log.i("asdf", e + "");
                    e.printStackTrace();
                } catch (JSONException e) {
                    Log.i("asdf", e + "");
                    e.printStackTrace();
                }
                NotificationCompat.Builder builder =
                        new NotificationCompat.Builder(MainActivity.this)
                                .setSmallIcon(R.drawable.ic_launcher_background)
                                .setContentTitle("한강가라 ㅉㅉ")
                                .setContentText("asdf");
                NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                notificationManager.notify(0,builder.build());
//                NotificationCompat.Builder builder =
//                        new NotificationCompat.Builder(MainActivity.this)
//                                .setSmallIcon(R.drawable.noti_icon)
//                                .setContentTitle("한강가라 ㅉㅉ")
//                                .setContentText(asd);
////                                .setContentText("" + json.getString("temp"));
//                NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
//                notificationManager.notify(0, builder.build());
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                textView.setText("통신 실패");
                Log.i("asdf", t + "");

            }
        });
    }

    public interface pongbang {
        @GET("/")
        Call<ResponseBody> test();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://hangang.dkserver.wo.tc")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }
}
