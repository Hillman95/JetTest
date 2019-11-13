package com.hillman.jettest.presentation.view.details

import android.graphics.Color
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.hillman.jettest.R
import com.hillman.jettest.presentation.view.utils.CircleTransform
import com.hillman.jettest.presentation.viewmodel.MemberDetailsViewModel
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_details.*

class MemberDetailsActivity: AppCompatActivity(){

    var membersViewModel: MemberDetailsViewModel? = null
    val OFFLINE = "offline"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        membersViewModel = ViewModelProviders.of(this).get(MemberDetailsViewModel::class.java)
        val id = intent.getStringExtra("id")
        membersViewModel!!.getMemberLivedata(id).observe(this, Observer {  member ->
             supportActionBar?.title = member.name
             Picasso.get().load(member.avatarUrl())
                          .error(R.mipmap.ic_launcher)
                          .transform(CircleTransform())
                          .into(avatarView)
             name.text = member.name
             username.text = member?.username
             utcOffset.text = member.utcOffset?.toString()
             status.post {
                 status.text = member.status
                 if (member.status == OFFLINE){
                    status.setTextColor(Color.RED)
                 } else {
                     status.setTextColor(Color.GREEN)
                 }
             }
        })

    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            android.R.id.home -> onBackPressed()
        }
        return super.onOptionsItemSelected(item)

    }

}