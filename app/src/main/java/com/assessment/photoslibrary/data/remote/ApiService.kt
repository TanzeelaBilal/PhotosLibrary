package com.assessment.photoslibrary.data.remote

import com.assessment.photoslibrary.model.response.PhotosListResponse
import com.assessment.photoslibrary.utils.Constants
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET(Constants.RECENT_PHOTOS_URL)
    suspend fun getRecentPhotos(
        @Query("method") method: String = "flickr.photos.getRecent",
        @Query("api_key") apiKey: String = Constants.FLICKR_API_KEY,
        @Query("format") format: String = "json",
        @Query("nojsoncallback") noJsonCallback: Boolean = true,
        @Query("safe_search") safe_search: Boolean = true,
        @Query("per_page") perPage: Int = Constants.PER_PAGE_LIMIT,
        @Query("page") page: Int
    ): Response<PhotosListResponse>
}
