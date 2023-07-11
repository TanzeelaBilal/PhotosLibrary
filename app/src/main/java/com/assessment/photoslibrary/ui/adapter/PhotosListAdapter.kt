package com.assessment.photoslibrary.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import coil.load
import com.assessment.photoslibrary.R
import com.assessment.photoslibrary.model.response.Photo
import com.assessment.photoslibrary.utils.Utils.getImageUrl
import kotlinx.android.synthetic.main.item_photos_listview.view.listview_image
import kotlinx.android.synthetic.main.item_photos_listview.view.listview_item_title


class PhotosListAdapter(context: Context) :
    BaseAdapter() {
    private var photoModel: MutableList<Photo> = mutableListOf()
    private val context: Context

    init {
        this.context = context
    }

    fun addItem(photo: Photo) {
        this.photoModel.add(photo)
        notifyDataSetChanged()
    }

    fun updateList(photo: List<Photo>) {
        this.photoModel = photo as MutableList<Photo>
        notifyDataSetChanged()
    }

    override fun getCount(): Int {
        return photoModel.size
    }

    override fun getItem(position: Int): Any {
        return photoModel[position]
    }

    override fun getItemId(position: Int): Long {
        return 0
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val layoutInflater =
            context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        var holder = Holder()
        var rowView = convertView

        if (rowView == null) {
            rowView =
                layoutInflater.inflate(R.layout.item_photos_listview, parent, false)
            holder.tv = rowView.listview_item_title
            holder.img = rowView.listview_image
            rowView.tag = holder
        } else {
            holder = rowView.tag as Holder
        }


        holder.tv?.text = photoModel[position].title
        holder.img?.load(getImageUrl(photoModel[position]))

        return rowView!!
    }

    class Holder {
        var tv: TextView? = null
        var img: ImageView? = null
    }
}
