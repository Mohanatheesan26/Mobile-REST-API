package com.example.assignment_01

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.ListView
import com.example.assignment_01.posts.api.PostApi
import com.example.assignment_01.posts.models.Post
import com.google.gson.GsonBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val gson = GsonBuilder()
            .create()

        val retrofit = Retrofit.Builder()
            .baseUrl("https://jsonplaceholder.typicode.com/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()

        var postapi = retrofit.create(PostApi::class.java)
        var postcall = postapi.posts;
        postcall.enqueue(object : Callback<List<Post>> {
            override fun onResponse(call: Call<List<Post>>, response: Response<List<Post>>) {

                var postlist  = response.body() as List<Post>
                var post = arrayOfNulls<String>(postlist.size)

                for (i in postlist.indices)
                    post[i] = postlist[i].title

                var adapter = ArrayAdapter<String>(applicationContext, android.R.layout.simple_dropdown_item_1line,post)
                findViewById<ListView>(R.id.listview).adapter = adapter
            }

            override fun onFailure(call: Call<List<Post>>, t: Throwable) {
                Log.d("Error" ,"On Failure.......")
            }

        })
    }
}