package com.odougle.http.util

import android.content.Context
import android.net.ConnectivityManager
import com.odougle.http.model.Book
import org.json.JSONObject
import java.io.IOException
import java.net.HttpURLConnection
import java.net.URL

object BookHttp {
    val BOOK_JSON_URL = "https://raw.githubusercontent.com/nglauber/" +
            "dominando_android3/master/livros_novatec.json"

    @Throws(IOException::class)
    private fun connect(urlAddress: String) : HttpURLConnection{
        val second = 1000
        val url = URL(urlAddress)
        val connection = (url.openConnection() as HttpURLConnection).apply {
            readTimeout = 10 * second
            connectTimeout = 15 * second
            requestMethod = "GET"
            doInput = true
            doOutput = false
        }
        connection.connect()
        return connection
    }

    fun hasConnection(context: Context) : Boolean{
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val info = cm.activeNetworkInfo
        return info != null && info.isConnected
    }

    fun loadBooks() : List<Book>?{
        try {
            val connection = connect(BOOK_JSON_URL)
            val responseCode = connection.responseCode
            if(responseCode == HttpURLConnection.HTTP_OK){
                val inputStream = connection.inputStream
                val json = JSONObject(streamToString(inputStream))
                return readBooksFromJson(json)
            }
        }catch (e: Exception){
            e.printStackTrace()
        }
        return null
    }
}