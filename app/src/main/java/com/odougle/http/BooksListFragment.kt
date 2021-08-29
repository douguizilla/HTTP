package com.odougle.http

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.odougle.http.databinding.FragmentBooksListBinding

class BooksListFragment : Fragment() {

    private var _binding: FragmentBooksListBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentBooksListBinding.inflate(inflater, container, false)
        return binding.root
    }

}