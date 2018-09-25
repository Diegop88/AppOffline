package com.diegop.appoffline.ui.main

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.diegop.appoffline.R
import com.diegop.appoffline.ui.detail.DetailActivity
import com.diegop.appoffline.utils.ReposAdapter
import com.google.android.material.snackbar.Snackbar
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class MainActivity : AppCompatActivity() {
    @Inject
    lateinit var factory: MainViewModel.Factory
    private lateinit var viewModel: MainViewModel

    private val adapter = ReposAdapter {
        val intent = Intent(this, DetailActivity::class.java)
        intent.putExtra("user", it.user)
        intent.putExtra("name", it.name)
        startActivity(intent)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = ViewModelProviders.of(this, factory).get(MainViewModel::class.java)
        viewModel.userData.observe(this, Observer { adapter.submitList(it) })
        viewModel.errorData.observe(this, Observer { Snackbar.make(search, "Error: $it", Snackbar.LENGTH_LONG).show() })

        search.setOnClickListener { viewModel.getRepo(githubUser.text.toString()) }
        userRepos.layoutManager = LinearLayoutManager(this)
        userRepos.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
        userRepos.adapter = adapter
    }
}
