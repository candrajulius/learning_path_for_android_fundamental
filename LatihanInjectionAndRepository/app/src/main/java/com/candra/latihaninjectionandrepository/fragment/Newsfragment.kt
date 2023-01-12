package com.candra.latihaninjectionandrepository.fragment

import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridLayout
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.candra.latihaninjectionandrepository.BuildConfig
import com.candra.latihaninjectionandrepository.adapter.NewsAdapter
import com.candra.latihaninjectionandrepository.databinding.NewsFragmentBinding
import com.candra.latihaninjectionandrepository.entity.NewsEntity
import com.candra.latihaninjectionandrepository.helper.Result
import com.candra.latihaninjectionandrepository.viewmodel.NewsViewModel
import com.candra.latihaninjectionandrepository.viewmodel.factory.ViewModelFactory

class Newsfragment: Fragment()
{
    private var tabName: String? = null

    private var _binding: NewsFragmentBinding? = null
    private val binding get() = _binding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = NewsFragmentBinding.inflate(layoutInflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tabName = arguments?.getString(ARG_TAB)

        val factory: ViewModelFactory = ViewModelFactory.getInstance(requireActivity())
        val viewModel: NewsViewModel by viewModels {
            factory
        }

        /*
        Di sini kita melakukan pengecekan, apabila data sudah disimpan, langkah selanjutnya adalah dihapus. Sebaliknya, jika data belum disimpan, langkah selanjutnya adalah menyimpan data tersebut.
         */
        val newsAdapter = NewsAdapter { news ->
            if (news.isBookmarked){
                viewModel.deleteNews(news)
            } else {
                viewModel.saveNews(news)
            }
        }

        if (tabName == TAB_NEWS) {
            setDataToTabNews(viewMdoel = viewModel,mainAdapter = newsAdapter )
        } else if (tabName == TAB_BOOKMARK) {
            setDataToTabBookMark(viewModel,newsAdapter)
        }

        setAdapterInHere(newsAdapter = newsAdapter)

    }

    private fun setDataToTabBookMark(viewModel: NewsViewModel,mainAdapter: NewsAdapter){
        viewModel.getBookmarkedNews().observe(viewLifecycleOwner){
            showProgress(false)
            it.let { mainAdapter.submitList(it) }
        }
    }

    private fun setDataToTabNews(viewMdoel: NewsViewModel,mainAdapter: NewsAdapter){
        viewMdoel.getHeadlineNews().observe(viewLifecycleOwner){
            it?.let { dataResult ->
                when(dataResult){
                    is Result.Loading -> {
                        showProgress(true)
                    }
                    is Result.Success -> {
                        showProgress(false)
                        val newsData = dataResult.data
                        mainAdapter.submitList(newsData)
                    }
                    is Result.Error -> {
                        showProgress(false)
                        Toast.makeText(requireActivity(),"Terjadi kesalahan ${dataResult.error}"
                        ,Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }



    private fun setAdapterInHere(newsAdapter: NewsAdapter){
        binding?.rvNews?.apply {
            if (context.applicationContext.resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE){
                layoutManager = GridLayoutManager(requireActivity(),2)
            }else{
                layoutManager = LinearLayoutManager(requireActivity())
            }
            setHasFixedSize(true)
            adapter = newsAdapter
        }
    }



    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun showProgress(show: Boolean){
        if(show){
            binding?.progressBar?.visibility = View.VISIBLE
        }else{
            binding?.progressBar?.visibility = View.GONE
        }
    }

    companion object {
        const val ARG_TAB = "tab_name"
        const val TAB_NEWS = "news"
        const val TAB_BOOKMARK = "bookmark"
    }
}