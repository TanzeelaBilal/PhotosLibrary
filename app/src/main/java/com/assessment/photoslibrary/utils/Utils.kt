package com.assessment.photoslibrary.utils

import com.assessment.photoslibrary.model.response.Photo

object Utils {

    fun getImageUrl(photo: Photo): String {
        return "https://farm${photo.farm}.staticflickr.com/${photo.server}/${photo.id}_${photo.secret}.jpg"
    }
}