package com.diegop.appoffline.ui.detail

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import com.diegop.appoffline.R
import com.diegop.appoffline.utils.IssuesAdapter
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_detail.*
import javax.inject.Inject

class DetailActivity : AppCompatActivity() {

    @Inject
    lateinit var factory: DetailViewModel.Factory
    lateinit var viewModel: DetailViewModel
    private val adapter = IssuesAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        viewModel = ViewModelProviders.of(this, factory).get(DetailViewModel::class.java)
        viewModel.issuesData.observe(this, Observer {
            adapter.submitList(it)
        })
        viewModel.errorData.observe(this, Observer {
            Snackbar.make(repoIssues, "Error: $it", Snackbar.LENGTH_LONG).show()
        })

        val user = intent.getStringExtra("user")
        val repository = intent.getStringExtra("name")

        repoIssues.layoutManager = LinearLayoutManager(this)
        repoIssues.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
        repoIssues.adapter = adapter

        viewModel.getIssues(user, repository)
    }
}
