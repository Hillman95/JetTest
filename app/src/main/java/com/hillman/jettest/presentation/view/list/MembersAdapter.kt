package com.hillman.jettest.presentation.view.list

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.hillman.jettest.R
import com.hillman.jettest.data.entity.Member
import com.hillman.jettest.presentation.view.details.MemberDetailsActivity
import com.hillman.jettest.presentation.view.utils.CircleTransform
import com.squareup.picasso.Picasso


class MembersAdapter : PagedListAdapter<Member, MembersAdapter.MembersViewHolder>(diffCallback){

    private lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MembersViewHolder {
        context = parent.context
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_member, parent, false)
        return MembersViewHolder(view)
    }

    override fun onBindViewHolder(holder: MembersViewHolder, position: Int) {
        val member = getItem(position)
        holder.bind(member)
    }

    companion object{
        private val diffCallback = object : DiffUtil.ItemCallback<Member>() {
            override fun areItemsTheSame(oldItem: Member, newItem: Member): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Member, newItem: Member): Boolean =
                oldItem == newItem
        }
    }

    inner class MembersViewHolder(view: View) : RecyclerView.ViewHolder(view){
        private val avatarView : AppCompatImageView = view.findViewById(R.id.avatar)
        private val nameView: TextView = view.findViewById(R.id.name)

        fun bind(member: Member?) {
            nameView.text = member?.name
            Picasso.get().load(member?.avatarUrl())
                .error(R.mipmap.ic_launcher_round)
                .transform(CircleTransform())
                .into(avatarView)
            itemView.setOnClickListener {
                context.startActivity(Intent(context, MemberDetailsActivity::class.java)
                    .putExtra("id", member?.id))
            }
        }
    }
}