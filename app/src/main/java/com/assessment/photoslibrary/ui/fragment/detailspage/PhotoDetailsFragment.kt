package com.assessment.photoslibrary.ui.fragment.detailspage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import coil.load
import com.assessment.photoslibrary.R
import com.assessment.photoslibrary.model.response.PhotoModel
import com.assessment.photoslibrary.ui.activity.list.PhotosListActivity
import com.assessment.photoslibrary.ui.fragment.list.PhotosListFragment
import com.assessment.photoslibrary.utils.Utils
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_details_photo.imgMain
import kotlinx.android.synthetic.main.fragment_details_photo.tvFarm
import kotlinx.android.synthetic.main.fragment_details_photo.tvId
import kotlinx.android.synthetic.main.fragment_details_photo.tvIsFamily
import kotlinx.android.synthetic.main.fragment_details_photo.tvIsFriend
import kotlinx.android.synthetic.main.fragment_details_photo.tvIsPublic
import kotlinx.android.synthetic.main.fragment_details_photo.tvOwner
import kotlinx.android.synthetic.main.fragment_details_photo.tvSecret
import kotlinx.android.synthetic.main.fragment_details_photo.tvServer
import kotlinx.android.synthetic.main.fragment_details_photo.tvTitle


@AndroidEntryPoint
class PhotoDetailsFragment : Fragment() {

    var photoModel: PhotoModel? = null

    companion object {
        private const val PHOTO_MODEL = "PHOTO_MODEL"

        fun newInstance(photoModel: PhotoModel) = PhotoDetailsFragment().apply {
            arguments = Bundle().apply {
                putParcelable(PHOTO_MODEL, photoModel)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        photoModel = arguments?.getParcelable("PHOTO_MODEL")
        setCallBack()
        return inflater.inflate(R.layout.fragment_details_photo, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setData()
    }

    private fun setData() {
        photoModel?.let {
            imgMain.load(Utils.getImageUrl(it))
            tvId.text = "ID : " + it.id
            tvTitle.text =  it.title
            tvFarm.text = "Farm : " + it.farm
            tvSecret.text = "Secret : " + it.secret
            tvServer.text = "Server : " + it.server
            tvOwner.text = "Owner : " + it.owner
            tvIsFamily.text = "Family : " + getYesNo(it.isfamily)
            tvIsFriend.text = "Friend : " + getYesNo(it.isfriend)
            tvIsPublic.text = "Public : " + getYesNo(it.ispublic)
        }
    }

    private fun getYesNo(num: Int): String {
        return if (num == 1)
            "Yes"
        else
            "No"
    }

    private fun setCallBack() {
        // Create the callback
        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                photoModel?.title?.let {
                    PhotosListFragment.newInstance(it)
                }?.let { (activity as PhotosListActivity).changeFragment(it) }
            }
        }

        // Add the callback to the activity's OnBackPressedDispatcher
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)
    }
}