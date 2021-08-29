package com.odougle.http

import android.os.AsyncTask
import android.os.AsyncTask.*
import android.os.AsyncTask.Status.*
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.odougle.http.adapter.BookListAdapter
import com.odougle.http.databinding.FragmentBooksListBinding
import com.odougle.http.model.Book
import com.odougle.http.util.BookHttp
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class BooksListFragment : Fragment(), CoroutineScope {

    private var _binding: FragmentBooksListBinding? = null
    private val binding get() = _binding!!

    private val booksList = mutableListOf<Book>()
    private var adapter: ArrayAdapter<Book>? = null

    private lateinit var job: Job
    private var downloadJob: Job? = null

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
        job = Job()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentBooksListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        adapter = BookListAdapter(requireContext(), booksList)
        binding.listView.emptyView = binding.txtMessage
        binding.listView.adapter = adapter
        if(booksList.isNotEmpty()){
            showProgress(false)
        }else{
            if(downloadJob == null){
                if(BookHttp.hasConnection(requireContext())){
                    startDownloadJson()
                }else{
                    binding.progressBar.visibility = View.GONE
                    binding.txtMessage.setText(R.string.error_no_connection)
                }
            }else if(downloadJob?.isActive == true){
                showProgress(true)
            }
        }
    }

    private fun startDownloadJson() {
        downloadJob = launch {
            showProgress(true)
            val booksTask = withContext(Dispatchers.IO){
                BookHttp.loadBooks()
            }
            updateBookList(booksTask)
            showProgress(false)
        }
    }

    private fun showProgress(show: Boolean) {
        if(show){
            binding.txtMessage.setText(R.string.message_progress)
        }
        binding.txtMessage.visibility = if(show) View.VISIBLE else View.GONE
        binding.progressBar.visibility = if(show) View.VISIBLE else View.GONE
    }

    private fun updateBookList(result: List<Book>?){
        if(result != null){
            booksList.clear()
            booksList.addAll(result)
        }else{
            binding.txtMessage.setText(R.string.error_load_books)
        }
        adapter?.notifyDataSetChanged()
        downloadJob = null
    }


    override fun onDestroy() {
        super.onDestroy()
        job.cancel()
    }
}