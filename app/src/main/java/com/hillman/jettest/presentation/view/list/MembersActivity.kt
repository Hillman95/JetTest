package com.hillman.jettest.presentation.view.list

import android.content.res.Configuration
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.hillman.jettest.R
import com.hillman.jettest.presentation.viewmodel.MembersViewModel
import kotlinx.android.synthetic.main.activity_main.*
import com.hillman.jettest.presentation.view.utils.EndlessScroll
import com.hillman.jettest.presentation.viewmodel.MembersViewModelFactory


class MembersActivity: AppCompatActivity(){


    private lateinit var membersViewModel: MembersViewModel
    var membersAdapter: MembersAdapter? = null
    private var layoutManager: LinearLayoutManager? = null



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        membersViewModel = ViewModelProviders.of(this, MembersViewModelFactory(this)).get(MembersViewModel::class.java)
        prepareRecyclerView()
        getMembers()

        swiperefresh.setOnRefreshListener {
            membersViewModel.refreshMembers()
        }

    }

    private fun getMembers() {
        showLoading(true)
        membersViewModel.members.observe(this, Observer {  members ->
            membersAdapter?.submitList(members)
            showLoading(false)
        })
    }


    private fun prepareRecyclerView() {
        membersAdapter = MembersAdapter()
        layoutManager = LinearLayoutManager(this)
        layoutManager!!.scrollToPositionWithOffset(membersViewModel.getCurrentPos(),
            membersViewModel.getCurrentOffset())
        recyclerView.layoutManager = layoutManager
        recyclerView.itemAnimator = DefaultItemAnimator()
        recyclerView.adapter = membersAdapter

        recyclerView.addOnScrollListener(object : EndlessScroll(layoutManager!!) {
            override fun onLoadMore() {
                if (!membersViewModel.isLoading) {
                    showLoading(true)
                    membersViewModel.loadMembers()
                    showLoading(true)
                }
            }
        })
    }

    override fun onPause() {
        super.onPause()
        layoutManager?.apply {
            membersViewModel.saveCurrentPos(findFirstCompletelyVisibleItemPosition())
            membersViewModel.saveCurrentOffset(recyclerView?.getChildAt(0)?.top ?:0)
        }
    }


    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        swiperefresh.isRefreshing = membersViewModel.isLoading

    }

    private fun showLoading(show: Boolean){
        membersViewModel.isLoading = show
        swiperefresh.isRefreshing = show
    }
}


