package sumagoscope.madipt.b2volleydemo;

import android.os.Bundle;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ArrayList<Post> list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        list=new ArrayList<>();
        getDataFromServer();

    }

    private void getDataFromServer() {

        RequestQueue requestQueue= Volley.newRequestQueue(this);
        String url="https://jsonplaceholder.typicode.com/posts";

        StringRequest stringRequest=new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                //Log.d("mytag",""+response);

                try {
                    JSONArray jsonArray=new JSONArray(response);
                    for(int i=0;i<jsonArray.length();i++)
                    {
                        Post post=new Post();
                        JSONObject jsonObject=jsonArray.getJSONObject(i);
                        post.setId(jsonObject.getInt("id"));
                        post.setTitle(jsonObject.getString("title"));
                        post.setBody(jsonObject.getString("body"));
                        post.setUserId(jsonObject.getInt("userId"));
                        list.add(post);

                    }

                    Log.d("mytag",""+list.size());

                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("mytag",""+error.getMessage(),error);
                error.printStackTrace();
            }
        });

        requestQueue.add(stringRequest);

    }
}