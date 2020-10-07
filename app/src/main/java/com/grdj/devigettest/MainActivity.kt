package com.grdj.devigettest

import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import com.grdj.devigettest.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.android.viewmodel.ext.android.viewModel
import timber.log.Timber

class MainActivity : AppCompatActivity() {
    val viewModel: MainViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        fetchData()
    }

    override fun onResume() {
        super.onResume()
        when(getResources().getConfiguration().orientation){
            Configuration.ORIENTATION_LANDSCAPE -> {
                val paddingDp = applicationContext.getResources().getDimension(R.dimen.left_side_panel_width_open).toInt()
                mainView.setPadding(paddingDp,0,0,0);
                slidingPanel.openPane()
            }
            Configuration.ORIENTATION_PORTRAIT -> {
                slidingPanel.closePane()
            }
        }
    }

    private fun fetchData(){
        viewModel.getRedditPost()
        viewModel.redditPostList.observe(this, Observer {list ->
            list.let {
                Timber.d("RESPONSE, $it")
            }
        })

        viewModel.error.observe(this, Observer{
            if(it){

            }
        })
    }
}