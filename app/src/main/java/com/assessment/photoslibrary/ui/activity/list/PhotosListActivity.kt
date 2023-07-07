package com.assessment.photoslibrary.ui.activity.list

import android.os.Bundle
import android.os.Environment
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.graphics.drawable.toBitmap
import coil.ImageLoader
import coil.load
import coil.request.Disposable
import coil.request.ImageRequest
import coil.transform.RoundedCornersTransformation
import com.assessment.photoslibrary.R
import com.assessment.photoslibrary.databinding.ActivityMainBinding
import com.assessment.photoslibrary.databinding.ActivityPhotosListBinding
import com.assessment.photoslibrary.ui.fragment.list.PhotosListFragment
import com.assessment.photoslibrary.utils.NetworkResult
import com.assessment.photoslibrary.viewmodel.list.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.io.File

@AndroidEntryPoint
class PhotosListActivity : AppCompatActivity() {

    private lateinit var _binding: ActivityPhotosListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityPhotosListBinding.inflate(layoutInflater)
        setContentView(_binding.root)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, PhotosListFragment.newInstance())
                .commitNow()
        }
    }
}
