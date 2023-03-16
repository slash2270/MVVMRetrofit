package com.example.mvvmretrofit.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.mvvmretrofit.R
import com.example.mvvmretrofit.activity.MainActivity
import com.example.mvvmretrofit.activity.WebActivity
import com.example.mvvmretrofit.bean.GithubSearchBean

class PagingAdapter(private val activity: MainActivity) : PagingDataAdapter<GithubSearchBean, PagingAdapter.ViewHolder>(diff) {

    companion object {
        private val diff = object : DiffUtil.ItemCallback<GithubSearchBean>() {
            override fun areItemsTheSame(oldItem: GithubSearchBean, newItem: GithubSearchBean): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: GithubSearchBean, newItem: GithubSearchBean): Boolean {
                return oldItem == newItem
            }
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name: TextView = itemView.findViewById(R.id.name_text)
        val description: TextView = itemView.findViewById(R.id.description_text)
        val starCount: TextView = itemView.findViewById(R.id.star_count_text)
        val view: LinearLayout = itemView.findViewById(R.id.ll_root)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.github_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        if (item != null) {
            holder.name.text = item.name
            holder.description.text = item.description
            holder.starCount.text = item.starCount.toString()
            holder.view.setOnClickListener {
                val intent = Intent(activity, WebActivity::class.java)
                intent.putExtra("url", item.htmlUrl)
                activity.startActivity(intent)
            }
        }
    }

}