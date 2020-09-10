package com.example.newsapp.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.newsapp.R
import com.example.newsapp.adapters.CategoriesAdapter
import com.example.newsapp.adapters.NewsAdapter
import com.example.newsapp.ui.ArticleActivity
import com.example.newsapp.ui.MainActivity
import com.example.newsapp.utils.Constants.categories
import com.example.newsapp.utils.Resource
import com.example.newsapp.viewmodels.NewsViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.fragment_breaking_news.*

class BreakingNewsFragment : Fragment(R.layout.fragment_breaking_news) {

    private lateinit var viewModel: NewsViewModel
    private lateinit var newsAdapter: NewsAdapter
    private lateinit var categoriesAdapter: CategoriesAdapter
    private lateinit var parentActivity: MainActivity

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        parentActivity = activity as MainActivity
        viewModel = parentActivity.viewModel
        initCategoriesRecyclerView()
        initNewsRecyclerView()

        viewModel.newsData.observe(viewLifecycleOwner, { response ->
            when (response) {
                is Resource.Success -> {
                    progressBarStatus(false)
                    tryAgainStatus(false)
                    newsAdapter.differ.submitList(response.data!!.articles)
                }
                is Resource.Error -> {
                    tryAgainStatus(true, response.message!!)
                    progressBarStatus(false)
                }
                is Resource.Loading -> {
                    tryAgainStatus(false)
                    progressBarStatus(true)
                }
            }
        })

        tryAgainButton.setOnClickListener { viewModel.getBreakingNews() }
    }

    private fun initCategoriesRecyclerView() {
        categoriesAdapter = CategoriesAdapter(categories)

        categoriesAdapter.onItemClickListener { viewModel.getBreakingNews(it) }

        categoriesRecyclerView.apply {
            adapter = categoriesAdapter
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        }
    }

    private fun initNewsRecyclerView() {
        newsAdapter = NewsAdapter()

        newsAdapter.onItemClickListener { article ->
            val bundle = Bundle().apply { putSerializable("article", article) }
            val intent = Intent(activity, ArticleActivity::class.java)
            intent.putExtras(bundle)
            startActivity(intent)
        }

        breakingNewsRecyclerView.apply {
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

    private fun progressBarStatus(status: Boolean) {
        if (status) progressBar.visibility = View.VISIBLE
        else progressBar.visibility = View.GONE
    }

    private fun tryAgainStatus(status: Boolean, message: String = "message") {
        if (status) {
            tryAgainMessage.text = message
            tryAgainLayout.visibility = View.VISIBLE
        } else {
            tryAgainLayout.visibility = View.GONE
        }
    }
}
