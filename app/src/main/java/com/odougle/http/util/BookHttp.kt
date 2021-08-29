package com.odougle.http.util

import java.net.HttpURLConnection
import java.net.URL

object BookHttp {
    val BOOK_JSON_URL = "https://raw.githubusercontent.com/nglauber/" +
            "dominando_android3/master/livros_novatec.json"

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
}