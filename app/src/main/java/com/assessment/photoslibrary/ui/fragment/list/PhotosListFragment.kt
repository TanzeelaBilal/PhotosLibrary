package com.assessment.photoslibrary.ui.fragment.list

import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.graphics.drawable.toBitmap
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import coil.ImageLoader
import coil.request.Disposable
import coil.request.ImageRequest
import com.assessment.photoslibrary.R
import com.assessment.photoslibrary.model.response.PhotoModel
import com.assessment.photoslibrary.ui.adapter.PhotosListAdapter
import com.assessment.photoslibrary.utils.NetworkResult
import com.assessment.photoslibrary.viewmodel.list.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_photos_list.list_view
import kotlinx.android.synthetic.main.fragment_photos_list.pbLoader
import java.io.File

@AndroidEntryPoint
class PhotosListFragment : Fragment() {

    private lateinit var disposable: Disposable
    private var photosListResponse: List<PhotoModel>? = null
    private val viewModel by viewModels<MainViewModel>()

    companion object {
        fun newInstance() = PhotosListFragment()
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fetchData()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_photos_list, container, false)
    }

    private fun fetchResponse() {
        viewModel.fetchPhotosResponse()
        pbLoader.visibility = View.VISIBLE
    }


    private fun fetchData() {
        fetchResponse()
        viewModel.response.observe(this) { response ->
            when (response) {
                is NetworkResult.Success -> {
                    response.data?.let {
                        photosListResponse = response.data.photos.photo
                        list_view.adapter =
                            photosListResponse?.let { it1 ->
                                PhotosListAdapter(
                                    requireContext(),
                                    it1
                                )
                            }
                    }
                    pbLoader.visibility = View.GONE
                }

                is NetworkResult.Error -> {
                    pbLoader.visibility = View.GONE
                    Toast.makeText(
                        activity,
                        response.message,
                        Toast.LENGTH_SHORT
                    ).show()
                    response.message?.let { Log.e("error", it) }
                }

                is NetworkResult.Loading -> {
                    pbLoader.visibility = View.VISIBLE
                }
            }
        }
    }

    private fun downloadImage(url: String?) {
        url?.let {
            /*val di = this.getExternalFilesDir(Environment.DIRECTORY_PICTURES)!!.absolutePath + "/" +
                    resources.getString(R.string.dogs) + "/"*/

            /* val path = getExternalFilesDir(Environment.DIRECTORY_PICTURES)*/
            val dirPath = Environment.getExternalStorageDirectory().absolutePath + "/" +
                    resources.getString(R.string.app_name) + "/"

            val dir = File(dirPath)

            val fileName: String = url.substring(url.lastIndexOf('/') + 1)

            val imageLoader = ImageLoader(this.requireActivity())
            val request = ImageRequest.Builder(this.requireActivity())
                .data(url)
                .target { drawable ->
                    viewModel.downloadImage(drawable.toBitmap(), dir, fileName)
                }
                .build()
            disposable = imageLoader.enqueue(request)
        }
    }

    private fun observeDownloadResponse() {
        viewModel.downloadResponse.observe(this) { response ->
            if (response) {
                Toast.makeText(activity, "Saved !!", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(activity, "Unable to save image !!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onDestroy() {
        disposable.dispose()
        super.onDestroy()
    }

}