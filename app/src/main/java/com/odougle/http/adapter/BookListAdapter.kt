package com.odougle.http.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.odougle.http.R
import com.odougle.http.model.Book

class BookListAdapter(
    context: Context,
    books: List<Book>
) : ArrayAdapter<Book>(context, 0, books) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val book = getItem(position)
        val holder: ViewHolder
        val view: View

        if(convertView == null){
            view = LayoutInflater.from(context).inflate(R.layout.item_book, parent, false)
            holder = ViewHolder(view)
            view.tag = holder
        }else{
            view = convertView
            holder = view.tag as ViewHolder
        }
        book?.let {
            Glide.with(context).load(book.coverUrl).into(holder.imgCapa)
            holder.txtTitulo.text = book.title
            holder.txtAutores.text = book.author
            holder.txtAno.text = book.year.toString()
            holder.txtPaginas.text = context.getString(R.string.n_paginas, book.pages)

        }
        return view
    }

    internal class ViewHolder(view: View){
        var imgCapa: ImageView = view.findViewById(R.id.imgCover)
        var txtTitulo: TextView = view.findViewById(R.id.txtTitle)
        var txtAutores: TextView = view.findViewById(R.id.txtAuthors)
        var txtPaginas: TextView = view.findViewById(R.id.txtPages)
        var txtAno: TextView = view.findViewById(R.id.txtYear)
    }
}