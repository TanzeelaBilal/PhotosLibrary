package com.assessment.photoslibrary.data.remote

import com.assessment.photoslibrary.model.DogResponse
import com.assessment.photoslibrary.utils.Constants
import retrofit2.Response
import retrofit2.http.GET

interface DogService {

    @GET(Constants.RANDOM_URL)
    suspend fun getDog(): Response<DogResponse>
}
