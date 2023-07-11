package com.assessment.photoslibrary.model.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

data class PhotosListResponse(
    @SerializedName("photos")
    val photos: Photos
)

data class Photos(
    @SerializedName("page")
    val page: Int,
    @SerializedName("pages")
    val pages: Int,
    @SerializedName("perpage")
    val perpage: Int,
    @SerializedName("total")
    val total: Int,
    @SerializedName("photo")
    val photo: List<Photo>
)

@Parcelize
data class Photo(
    @SerializedName("id")
    val id: String,
    @SerializedName("owner")
    val owner: String,
    @SerializedName("secret")
    val secret: String,
    @SerializedName("server")
    val server: String,
    @SerializedName("farm")
    val farm: String,
    @SerializedName("title")
    val title: String,
    @SerializedName("ispublic")
    val ispublic: Int,
    @SerializedName("isfriend")
    val isfriend: Int,
    @SerializedName("isfamily")
    val isfamily: Int

) : Parcelable
