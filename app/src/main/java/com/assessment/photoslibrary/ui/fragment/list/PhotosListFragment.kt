package com.assessment.photoslibrary.ui.fragment.list

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver.OnGlobalLayoutListener
import android.view.animation.TranslateAnimation
import android.widget.AbsListView
import android.widget.AbsListView.OnScrollListener
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.ListFragment
import androidx.fragment.app.viewModels
import com.assessment.photoslibrary.R
import com.assessment.photoslibrary.model.response.PhotoModel
import com.assessment.photoslibrary.ui.activity.list.PhotosListActivity
import com.assessment.photoslibrary.ui.adapter.PhotosListAdapter
import com.assessment.photoslibrary.ui.fragment.detailspage.PhotoDetailsFragment
import com.assessment.photoslibrary.utils.NetworkResult
import com.assessment.photoslibrary.viewmodel.list.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_photos_list.footer
import kotlinx.android.synthetic.main.fragment_photos_list.pbLoader


@AndroidEntryPoint
class PhotosListFragment : ListFragment() {

    private var photosListResponse: List<PhotoModel>? = null
    private val viewModel by viewModels<MainViewModel>()
    private var mCachedVerticalScrollRange = 0
    private var mQuickReturnHeight = 0

    private val STATE_ONSCREEN = 0
    private val STATE_OFFSCREEN = 1
    private val STATE_RETURNING = 2
    private var mState = STATE_ONSCREEN
    private var mScrollY = 0
    private var mMinRawY = 0
    private var anim: TranslateAnimation? = null

    companion object {
        private const val TITLE = "TITLE"

        fun newInstance(title: String) = PhotosListFragment().apply {
            arguments = Bundle().apply {
                putString(TITLE, title)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        setCallBack()
        return inflater.inflate(R.layout.fragment_photos_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setListViewHeader()
        setFooterText()
        fetchData()
    }

    private fun fetchData() {
        fetchResponse()
        viewModel.response.observe(this) { response ->
            when (response) {
                is NetworkResult.Success -> {
                    response.data?.let {
                        photosListResponse = response.data.photos.photo
                        //setListViewHeader()
                        listView.adapter =
                            photosListResponse?.let { it1 ->
                                PhotosListAdapter(
                                    requireContext(),
                                    it1
                                )
                            }
                        setListViewListener()
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

    private fun fetchResponse() {
        viewModel.fetchPhotosResponse()
        pbLoader.visibility = View.VISIBLE
    }

    private fun setListViewHeader() {
        var mListView = listView

        val header: View = layoutInflater.inflate(R.layout.item_header_listview, null)
        mListView.addHeaderView(header)
        mListView.viewTreeObserver.addOnGlobalLayoutListener(
            OnGlobalLayoutListener {
                mQuickReturnHeight = header.height
                mListView.computeScroll()
                mCachedVerticalScrollRange = mListView.height
            })
        mListView.setOnScrollListener(object : OnScrollListener {
            @SuppressLint("NewApi")
            override fun onScroll(
                view: AbsListView?, firstVisibleItem: Int,
                visibleItemCount: Int, totalItemCount: Int
            ) {
                mScrollY = 0
                var translationY = 0

                mScrollY = mListView.scrollY

                val rawY: Int = (header.top
                        - Math.min(
                    mCachedVerticalScrollRange
                            - mListView.height, mScrollY
                ))
                when (mState) {
                    STATE_OFFSCREEN -> {
                        if (rawY <= mMinRawY) {
                            mMinRawY = rawY
                        } else {
                            mState = STATE_RETURNING
                        }
                        translationY = rawY
                    }

                    STATE_ONSCREEN -> {
                        if (rawY < -mQuickReturnHeight) {
                            mState = STATE_OFFSCREEN
                            mMinRawY = rawY
                        }
                        translationY = rawY
                    }

                    STATE_RETURNING -> {
                        translationY = rawY - mMinRawY - mQuickReturnHeight
                        if (translationY > 0) {
                            translationY = 0
                            mMinRawY = rawY - mQuickReturnHeight
                        }
                        if (rawY > 0) {
                            mState = STATE_ONSCREEN
                            translationY = rawY
                        }
                        if (translationY < -mQuickReturnHeight) {
                            mState = STATE_OFFSCREEN
                            mMinRawY = rawY
                        }
                    }
                }
                /** this can be used if the build is below honeycomb  */
                if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.HONEYCOMB) {
                    anim = TranslateAnimation(
                        0f, 0f, translationY.toFloat(),
                        translationY.toFloat()
                    )
                    anim?.fillAfter = true
                    anim?.duration = 0
                    header.startAnimation(anim)
                } else {
                    header.translationY = translationY.toFloat()
                }
            }

            override fun onScrollStateChanged(view: AbsListView?, scrollState: Int) {}
        })
    }

    private fun setListViewListener() {
        listView.setOnItemClickListener { parent, view, position, id ->
            photosListResponse?.get(position)
                ?.let {
                    PhotoDetailsFragment.newInstance(it)
                }
                ?.let { (activity as PhotosListActivity).changeFragment(it) }
        }
    }

    private fun setFooterText() {
        footer.text = arguments?.getString("TITLE")
    }

    private fun setCallBack() {
        // Create the callback
        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                requireActivity().finish()
            }
        }

        // Add the callback to the activity's OnBackPressedDispatcher
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)

    }
}