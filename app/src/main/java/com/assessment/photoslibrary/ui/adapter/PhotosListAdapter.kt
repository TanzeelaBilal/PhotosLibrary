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
import com.assessment.photoslibrary.model.response.PhotoModel
import kotlinx.android.synthetic.main.item_photos_listview.view.listview_image
import kotlinx.android.synthetic.main.item_photos_listview.view.listview_item_title


class PhotosListAdapter(context: Context, photoModel: List<PhotoModel>) :
    BaseAdapter() {
    private val photoModel: List<PhotoModel>
    private val context: Context

    init {
        this.photoModel = photoModel
        this.context = context
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

    fun getImageUrl(photo: PhotoModel): String {
        return "https://farm${photo.farm}.staticflickr.com/${photo.server}/${photo.id}_${photo.secret}.jpg"
    }

    class Holder {
        var tv: TextView? = null
        var img: ImageView? = null
    }
}
