package com.diegop.appoffline.utils

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.diegop.appoffline.R
import com.diegop.appoffline.domain.model.Issue
import kotlinx.android.synthetic.main.item_issue.view.*

class IssuesAdapter : ListAdapter<Issue, IssuesAdapter.IssueViewHolder>(IssueDiff()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
            IssueViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_issue, parent, false))

    override fun onBindViewHolder(holder: IssueViewHolder, position: Int) = holder.bind(getItem(position))

    inner class IssueViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(issue: Issue) {
            itemView.issueTitle.text = issue.title
            itemView.issueBody.text = issue.body
        }
    }

    class IssueDiff : DiffUtil.ItemCallback<Issue>() {
        override fun areItemsTheSame(oldItem: Issue, newItem: Issue): Boolean = oldItem == newItem
        override fun areContentsTheSame(oldItem: Issue, newItem: Issue): Boolean = oldItem == newItem
    }
}
