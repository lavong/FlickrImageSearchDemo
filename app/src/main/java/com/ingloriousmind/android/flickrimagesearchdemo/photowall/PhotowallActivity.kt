package com.ingloriousmind.android.flickrimagesearchdemo.photowall

import android.os.Bundle
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ingloriousmind.android.flickrimagesearchdemo.R
import com.ingloriousmind.android.flickrimagesearchdemo.photowall.model.PhotoItem
import timber.log.Timber


class PhotowallActivity : AppCompatActivity(), PhotowallContract.View {

    private val presenter: PhotowallPresenter by lazy { PhotowallPresenter() }
    private lateinit var recycler: RecyclerView
    private lateinit var adapter: PhotowallAdapter
    private lateinit var searchView: SearchView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_photo_wall)
        initViews()
        presenter.bindView(this)
    }

    private fun initViews() {
        recycler = findViewById(R.id.photowall_recycler)
        adapter = PhotowallAdapter(object : PhotowallAdapter.ClickHandler {
            override fun onPhotoClicked(photoItem: PhotoItem, position: Int) {
                presenter.onPhotoItemClicked(photoItem)
            }
        })
        recycler.adapter = adapter
        recycler.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val layoutManager = recyclerView.layoutManager as GridLayoutManager
                val visibleCount = layoutManager.childCount
                val totalCount = layoutManager.itemCount
                val firstVisible = layoutManager.findFirstVisibleItemPosition()
                val shouldLoadMore = firstVisible + visibleCount >= totalCount
                if (shouldLoadMore) {
                    presenter.onScrolledToEnd(totalCount)
                }
            }
        })
    }

    override fun onDestroy() {
        if (isFinishing) {
            presenter.unbindView()
        }
        super.onDestroy()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.photowall, menu)

        searchView = menu.findItem(R.id.action_search).actionView as SearchView
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                searchView.onActionViewCollapsed()
                presenter.onSearchQuery(query)
                return true
            }

            override fun onQueryTextChange(query: String): Boolean = false
        })

        return super.onCreateOptionsMenu(menu)
    }

    override fun showPhotoItems(photoItems: List<PhotoItem>) {
        Timber.v("displaying ${photoItems.size} items")
        adapter.setPhotoItems(photoItems)
    }

    override fun updateSearchQuery(query: String) {
        searchView.setQuery(query, false)
    }

}
