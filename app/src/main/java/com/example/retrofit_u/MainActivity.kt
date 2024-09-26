package com.example.retrofit_u

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.liveData
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val retroobject = RetrofitInstance.getRetrofitInstance().create(Albumservice::class.java)
        //getallresponse(retroobject)
        //getresponsewithparameter(retroobject)
        //getresponseusingquery(retroobject)
        postAlbum(retroobject)


    }

    private fun getallresponse(retroobject: Albumservice){

        val responseLiveData: LiveData<Response<Album>> = liveData {
            val response = retroobject.getAlbums()
            emit(response)
        }

        responseLiveData.observe(this, Observer { response ->
            if (response.isSuccessful) {
                val albumlist = response.body()?.listIterator()
                if (albumlist != null) {
                    while (albumlist.hasNext()) {
                        val albumitems = albumlist.next()
                        val result = " " + "Album title :${albumitems.title}\n" +
                                " " + "id :${albumitems.id}\n" +
                                " " + "user id :${albumitems.userId}\n\n\n"

                        val textview = findViewById<TextView>(R.id.textView)
                        textview.append(result)
                    }
                }
            } else {
                Log.e("Error", "Failed to fetch albums: ${response.errorBody()?.string()}")
            }
        })

    }

    private fun getresponsewithparameter(retroobject: Albumservice){
        val pathresponse: LiveData<Response<AlbumItem>> = liveData {
            val response1 = retroobject.getAlbum(3)
            emit(response1)
        }

        pathresponse.observe(this, Observer { response ->
            if (response.isSuccessful) {
                val title = response.body()?.title.toString()
                Toast.makeText(this, title, Toast.LENGTH_SHORT).show()
            } else {
                Log.e("Error", "Failed to fetch album: ${response.errorBody()?.string()}")
            }
        })

    }

    private fun getresponseusingquery(retroobject: Albumservice){
        val queryresponse: LiveData<Response<Album>> = liveData {
            val response2 = retroobject.getSortedAlbums(userId = 3)
            emit(response2)
        }
        queryresponse.observe(this, Observer {
            if (it.isSuccessful){
                val albumlist = it.body()?.listIterator()
                if (albumlist != null) {
                    while (albumlist.hasNext()) {
                        val albumitems = albumlist.next()
                        val result = " " + "Album title :${albumitems.title}"
                        val textview = findViewById<TextView>(R.id.textView)
                        textview.append(result)
                    }
                }

            }
        })

    }

    private fun postAlbum(retroobject: Albumservice){
        val album = AlbumItem(0, "My title", 3)
        val postresponse: LiveData<Response<AlbumItem>> = liveData {
            val response3 = retroobject.postAlbum(album)
            emit(response3)
        }

        postresponse.observe(this,Observer{
                val albumitem = it.body()
                val result = " " + "Album title :${albumitem?.title}\n" +
                             " " + "id :${albumitem?.id}\n" +
                             " " + "user id :${albumitem?.userId}\n\n"
                val textview = findViewById<TextView>(R.id.textView)
                textview.append(result)

        })
    }
}