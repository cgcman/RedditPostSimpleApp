package com.grdj.devigettest.ui

import android.content.res.Configuration
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.slidingpanelayout.widget.SlidingPaneLayout
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.grdj.devigettest.R
import com.grdj.devigettest.domain.Children
import com.grdj.devigettest.util.Constants.CLOSE
import com.grdj.devigettest.util.Constants.EMPTY_STRING
import com.grdj.devigettest.util.Constants.OPEN
import com.grdj.devigettest.util.Constants.ZERO
import com.grdj.devigettest.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.post_item.view.*
import org.koin.android.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity(), PostItemClickListener {
    val viewModel: MainViewModel by viewModel()

    lateinit var adpt: PostItemAdapter
    var paddingLeft: Int = 0
    var paddingRight: Int = 0
    var isAnimationOver = true
    var animationState = OPEN

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initViews()
        fetchData()
    }

    override fun onResume() {
        paddingLeft = applicationContext.getResources()
            .getDimension(R.dimen.left_side_panel_width_open).toInt()
        paddingRight = applicationContext.getResources()
            .getDimension(R.dimen.guides_margin).toInt()
        super.onResume()
        when(getResources().getConfiguration().orientation){
            Configuration.ORIENTATION_LANDSCAPE -> {
                mainView.let {
                    mainView.setPadding(paddingLeft, 0, paddingRight, 0);
                    slidingPanel.openPane()
                    animationState = OPEN
                }
            }
            Configuration.ORIENTATION_PORTRAIT -> {
                mainView.let {
                    slidingPanel.closePane()
                    animationState = CLOSE
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
            viewModel.dataIsFetch = false
            fetchData()
        }
        refreshLayout!!.isRefreshing = true
        dissmisAll.setOnClickListener {
            adpt.clearItems()
            adpt.notifyDataSetChanged()
        }

        slidingPanel.setPanelSlideListener(object : SlidingPaneLayout.PanelSlideListener {
            override fun onPanelSlide(panel: View, slideOffset: Float) {
                when(animationState){
                    CLOSE -> {
                        if(isAnimationOver){
                            val anim = AnimationUtils.loadAnimation(
                                applicationContext,
                                R.anim.translate_right
                            )
                            listContainer.startAnimation(anim)
                            isAnimationOver = false
                        }
                    }
                    else -> {
                        if(isAnimationOver){
                            val anim = AnimationUtils.loadAnimation(
                                applicationContext,
                                R.anim.translate_left
                            )
                            listContainer.startAnimation(anim)
                            isAnimationOver = false
                        }
                    }
                }
            }
            override fun onPanelOpened(panel: View) {
                mainView.setPadding(paddingLeft, 0, paddingRight, 0);
                animationState = OPEN
                isAnimationOver = true
            }
            override fun onPanelClosed(panel: View) {
                mainView.setPadding(paddingRight, 0, paddingRight, 0);
                animationState = CLOSE
                isAnimationOver = true
            }
        })
    }

    private fun fetchData(){
        viewModel.getRedditPost()
        viewModel.redditPostList.observe(this, Observer { list ->
            list.let {
                adpt.setItems(list as ArrayList<Children>)
                adpt.notifyDataSetChanged()
                refreshLayout!!.isRefreshing = false
                listTitle.visibility = View.VISIBLE
            }
        })

        viewModel.error.observe(this, Observer {
            if(it){
                refreshLayout!!.isRefreshing = false
            }
        })

        viewModel.lastSaveData.observe(this, Observer {
            showDetails(it)
        })

        viewModel.message.observe(this, Observer {
            showMessages(it)
        })
    }

    override fun onPostClicked(position: Int) {
        val itemView = postList!!.findViewHolderForAdapterPosition(position)!!.itemView
        itemView.visited.visibility = View.GONE
        viewModel.persistData(adpt.getItem(position))
    }

    override fun onDeleteClicked(position: Int) {
        val itemView = postList!!.findViewHolderForAdapterPosition(position)!!.itemView
        val anim = AnimationUtils.loadAnimation(
            this,
            R.anim.fade_to_left
        )
        itemView.startAnimation(anim)

        Handler().postDelayed({
            viewModel.deletePost(adpt.getItem(position).data)
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

    private fun showMessages(message: String){
        if(!message.length.equals(ZERO)){
            Toast.makeText(getApplication(), message, Toast.LENGTH_SHORT).show()
            viewModel.message.value = EMPTY_STRING
        }
    }
}