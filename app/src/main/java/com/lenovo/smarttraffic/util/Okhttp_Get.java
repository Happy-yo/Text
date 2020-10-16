package com.lenovo.smarttraffic.util;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.TreeMap;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class Okhttp_Get {
    private static JSONObject jsonObject = new JSONObject();
    private static OkHttpClient client = new OkHttpClient();
    private static MediaType JSON = MediaType.parse("application/json;charset=utf-8");
    public static void http(TreeMap<String,Object> map ,String path,Get_Dat get_dat){
        for(String key:map.keySet()){
            try {
                jsonObject.put(key,map.get(key));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        RequestBody body = RequestBody.create(JSON,String.valueOf(jsonObject));
        Request.Builder builder = new Request.Builder();
        builder.url(path);
        builder.post(body);
        Request request = builder.build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                get_dat.fail();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String js = response.body().string();
                Log.d("AAAAAA", "onResponse: "+js);
                try {
                    JSONObject jsonObject = new JSONObject(js);
                    get_dat.get(jsonObject);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });



    }
     public interface Get_Dat{
         void get(JSONObject json);
         void fail();

}

}
