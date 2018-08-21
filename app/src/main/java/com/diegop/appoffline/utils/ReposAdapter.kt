package com.diegop.appoffline.utils

import android.support.v7.recyclerview.extensions.ListAdapter
import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.diegop.appoffline.R
import com.diegop.appoffline.domain.model.Repo
import kotlinx.android.synthetic.main.item_repo.view.*

class ReposAdapter(private val onClick: (Repo) -> Unit) : ListAdapter<Repo, ReposAdapter.RepoViewHolder>(RepoDiff()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
            RepoViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_repo, parent, false))

    override fun onBindViewHolder(holder: RepoViewHolder, position: Int) = holder.bind(getItem(position))

    inner class RepoViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(repo: Repo) {
            itemView.setOnClickListener { onClick(repo) }
            itemView.repoName.text = repo.name
            itemView.repoDescription.text = repo.description
        }
    }

    class RepoDiff : DiffUtil.ItemCallback<Repo>() {
        override fun areItemsTheSame(oldItem: Repo?, newItem: Repo?) = oldItem?.name == newItem?.name
        override fun areContentsTheSame(oldItem: Repo?, newItem: Repo?) = oldItem == newItem
    }
}
