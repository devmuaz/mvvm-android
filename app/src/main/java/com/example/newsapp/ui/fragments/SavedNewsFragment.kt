package com.example.newsapp.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.newsapp.R
import com.example.newsapp.ui.adapters.NewsAdapter
import com.example.newsapp.ui.MainActivity
import com.example.newsapp.viewmodels.NewsViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.fragment_saved_news.*

class SavedNewsFragment : Fragment(R.layout.fragment_saved_news) {

    private lateinit var viewModel: NewsViewModel
    private lateinit var newsAdapter: NewsAdapter
    private lateinit var parentActivity: MainActivity

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        parentActivity = activity as MainActivity
        viewModel = parentActivity.viewModel
        initNewsRecyclerView()

        viewModel.getSavedArticles().observe(viewLifecycleOwner, {
            newsAdapter.differ.submitList(it)
        })
    }

    private fun initNewsRecyclerView() {
        newsAdapter = NewsAdapter()

//        newsAdapter.onItemClickListener { article ->
//            val bundle = Bundle().apply {
//                putSerializable("article", article)
//                putBoolean("canRemoveArticle", true)
//            }
//            val intent = Intent(activity, ArticleActivity::class.java)
//            intent.putExtras(bundle)
//            startActivity(intent)
//        }

        savedArticlesRecyclerView.apply {
            adapter = newsAdapter
            layoutManager = LinearLayoutManager(activity)

            val bottomNavBar = parentActivity.findViewById<BottomNavigationView>(
                R.id.bottomNavigationView,
            )

            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    if (dy > 0 && bottomNavBar.isShown) {
                        bottomNavBar.visibility = View.GONE
                    } else if (dy < 0) {
                        bottomNavBar.visibility = View.VISIBLE
                    }
                }
            })
        }
    }
}