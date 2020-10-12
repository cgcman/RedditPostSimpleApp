package com.grdj.devigettest.ui

import android.content.res.Resources
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.grdj.devigettest.R
import com.grdj.devigettest.domain.Children
import com.grdj.devigettest.util.Constants.A_DAY_IN_HOURS
import com.grdj.devigettest.util.Constants.A_MONTH
import com.grdj.devigettest.util.Constants.A_YEAR
import com.grdj.devigettest.util.Constants.DAYS
import com.grdj.devigettest.util.Constants.HOURS
import com.grdj.devigettest.util.Constants.MINUTES
import com.grdj.devigettest.util.Constants.MONTHS
import com.grdj.devigettest.util.Constants.SIXTY
import com.grdj.devigettest.util.Constants.YEARS
import kotlinx.android.synthetic.main.post_item.view.*
import java.util.concurrent.TimeUnit

class PostItemAdapter (private val listener: PostItemClickListener) : RecyclerView.Adapter<PostItemAdapter.MyViewHolder>() {

    private var items: ArrayList<Children>

    init {
        this.items = ArrayList<Children>()
    }

    fun setItems(items: ArrayList<Children>) {
        this.items = items
    }

    fun getItems(): ArrayList<Children> {
        return this.items
    }

    fun clearItems() {
        this.items = ArrayList<Children>()
    }

    fun getItem(position: Int): Children {
        return this.items.get(position)
    }

    fun removeItem(position: Int) {
        this.items.removeAt(position)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        val viewHolderView = inflater.inflate(R.layout.post_item, parent, false)
        return MyViewHolder(viewHolderView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val post = items[position]
        holder.fetch(post, listener)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        fun fetch(post: Children, listener: PostItemClickListener) {
            val res: Resources = itemView.resources
            val options = RequestOptions()
                .placeholder(getProgressDrawable(itemView.context))
                .error(R.drawable.no_image_white)

            Glide.with(itemView.context)
                .setDefaultRequestOptions(options)
                .load(post.data.thumbnail)
                .into(itemView.thumb)

            itemView.title.text = post.data.author
            itemView.time.text = convertToDate(res, post.data.created.toLong())
            itemView.descrption.text = post.data.title
            itemView.comments.text = "${post.data.num_comments} ${res.getString(R.string.comments_string)}"

            itemView.clickGroup.setAllOnClickListener(View.OnClickListener {
                listener.onPostClicked(adapterPosition)
            })

            itemView.dissmisGroup.setAllOnClickListener(View.OnClickListener {
                listener.onDeleteClicked(adapterPosition)
            })
        }

        private fun convertToDate(res: Resources, itemTime: Long): String {

            val currentMillis = System.currentTimeMillis()
            val itemMillis = TimeUnit.SECONDS.toMillis(itemTime)
            val diffTime = currentMillis - itemMillis

            if (itemTime > SIXTY) {
                val timeInMinutes = TimeUnit.MILLISECONDS.toMinutes(diffTime)
                if (timeInMinutes > SIXTY) {
                    val timeInHours = TimeUnit.MILLISECONDS.toHours(diffTime)
                    if (timeInHours > A_DAY_IN_HOURS) {
                        val timeInDays = TimeUnit.MILLISECONDS.toDays(diffTime)
                        if (timeInDays > A_MONTH) {
                            val timeInMonths = timeInDays / A_MONTH
                            if (timeInMonths > A_YEAR) {
                                val timeInYears = timeInMonths / A_YEAR
                                return res.getString(R.string.item_time, timeInYears, YEARS)
                            } else {
                                return res.getString(R.string.item_time, timeInMonths, MONTHS)
                            }
                        } else {
                            return res.getString(R.string.item_time, timeInDays, DAYS)
                        }
                    } else {
                        return res.getString(R.string.item_time, timeInHours, HOURS)
                    }
                } else {
                    return res.getString(R.string.item_time, timeInMinutes, MINUTES)
                }
            } else {
                return res.getString(R.string.item_moment)
            }
        }
    }
}