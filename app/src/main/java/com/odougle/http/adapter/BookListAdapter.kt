package com.odougle.http.adapter

import android.content.Context
import android.widget.ArrayAdapter
import com.odougle.http.model.Book

class BookListAdapter(
    context: Context,
    books: List<Book>
) : ArrayAdapter<Book>(context, 0, books) {

}