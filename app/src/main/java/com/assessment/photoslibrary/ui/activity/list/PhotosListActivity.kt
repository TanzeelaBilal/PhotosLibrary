package com.assessment.photoslibrary.ui.activity.list

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.assessment.photoslibrary.R
import com.assessment.photoslibrary.databinding.ActivityPhotosListBinding
import com.assessment.photoslibrary.ui.fragment.list.PhotosListFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PhotosListActivity : AppCompatActivity() {

    private lateinit var _binding: ActivityPhotosListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityPhotosListBinding.inflate(layoutInflater)
        setContentView(_binding.root)
        if (savedInstanceState == null) {
           changeFragment(PhotosListFragment.newInstance(resources.getString(R.string.title)))
        }
    }

    fun changeFragment(fragment : Fragment){
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, fragment)
            .addToBackStack(null)
            .commit()
    }
}
