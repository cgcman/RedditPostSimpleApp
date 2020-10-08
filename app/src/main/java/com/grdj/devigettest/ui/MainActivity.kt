package com.grdj.devigettest.ui

import android.content.res.Configuration
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.slidingpanelayout.widget.SlidingPaneLayout
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.grdj.devigettest.R
import com.grdj.devigettest.domain.Children
import com.grdj.devigettest.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.android.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity(), PostItemClickListener {
    val viewModel: MainViewModel by viewModel()

    lateinit var adpt: PostItemAdapter
    var paddingDp: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initViews()
        fetchData()
    }

    override fun onResume() {
        paddingDp = applicationContext.getResources()
            .getDimension(R.dimen.left_side_panel_width_open).toInt()
        super.onResume()
        when(getResources().getConfiguration().orientation){
            Configuration.ORIENTATION_LANDSCAPE -> {
                mainView.let {
                    mainView.setPadding(paddingDp, 0, 0, 0);
                    slidingPanel.openPane()
                }
            }
            Configuration.ORIENTATION_PORTRAIT -> {
                mainView.let {
                    slidingPanel.closePane()
                }
            }
        }
    }

    private fun initViews(){
        adpt = PostItemAdapter(this)
        postList!!.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = adpt
        }
        refreshLayout.setOnRefreshListener {
            listTitle.visibility = View.GONE
            adpt.clearItems()
            viewModel.clearDataFetch(false)
            fetchData()
        }
        refreshLayout!!.isRefreshing = true
        dissmisAll.setOnClickListener {
            adpt.clearItems()
            adpt.notifyDataSetChanged()
        }

        slidingPanel.setPanelSlideListener(object : SlidingPaneLayout.PanelSlideListener {
            override fun onPanelSlide(panel: View, slideOffset: Float) {}
            override fun onPanelOpened(panel: View) {
                paddingDp = applicationContext.getResources()
                    .getDimension(R.dimen.left_side_panel_width_open).toInt()
                mainView.setPadding(paddingDp, 0, 0, 0);
            }
            override fun onPanelClosed(panel: View) {
                paddingDp = applicationContext.getResources()
                    .getDimension(R.dimen.guides_margin).toInt()
                mainView.setPadding(paddingDp, 0, 0, 0);
            }
        })
    }

    private fun fetchData(){
        viewModel.getRedditPost()
        viewModel.redditPostList.observe(this, Observer { list ->
            list.let {
                adpt.setItems(list.data.children as ArrayList)
                adpt.notifyDataSetChanged()
                refreshLayout!!.isRefreshing = false
                listTitle.visibility = View.VISIBLE
            }
        })
    }

    override fun onPostClicked(position: Int) {
        showDetails(adpt.getItem(position))
    }

    override fun onDeleteClicked(position: Int) {
        val itemView = postList!!.findViewHolderForAdapterPosition(position)!!.itemView
        val anim = AnimationUtils.loadAnimation(
            this,
            R.anim.fade_to_left
        )
        itemView.startAnimation(anim)

        Handler().postDelayed({
            //val redditToDelete = posts[position]
            //val task = Runnable { redditDB.redditDAO().deleteReddit(redditToDelete) }
            //excecutor.postTask(task)
            adpt.removeItem(position)
            adpt.notifyDataSetChanged()
        }, anim.duration)
    }

    private fun showDetails(post: Children){
        val options = RequestOptions()
            .placeholder(getProgressDrawable(this))
            .error(R.drawable.no_image_black)

        Glide.with(this)
            .setDefaultRequestOptions(options)
            .load(post.data.thumbnail)
            .into(detailsImage)

        detailsTitle.text = post.data.author
        detailsDescription.text = post.data.title
    }
}