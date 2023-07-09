package com.assessment.photoslibrary.data.remote

import com.assessment.photoslibrary.model.response.PhotosListResponse
import com.assessment.photoslibrary.utils.Constants
import com.assessment.photoslibrary.utils.Constants.Companion.FLICKR_API_KEY
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {

    @GET("photos.json?method=flickr.photos.search&format=json&nojsoncallback=1&text=dogs&api_key=$FLICKR_API_KEY")
    suspend fun getPhotosList(): Response<PhotosListResponse>
}
