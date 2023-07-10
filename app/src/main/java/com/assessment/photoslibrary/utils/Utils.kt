package com.assessment.photoslibrary.utils

import com.assessment.photoslibrary.model.response.PhotoModel

object Utils {

    fun getImageUrl(photo: PhotoModel): String {
        return "https://farm${photo.farm}.staticflickr.com/${photo.server}/${photo.id}_${photo.secret}.jpg"
    }
}