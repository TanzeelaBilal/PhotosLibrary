package com.assessment.photoslibrary.utils

class Constants {

    companion object {
        const val FLICKR_API_KEY = "fee10de350d1f31d5fec0eaf330d2dba"
        const val BASE_URL = "http://yuriy.me/rakuten-rewards/"
        const val RECENT_PHOTOS_URL =
            "photos.json?method=flickr.photos.getRecent&page=1&format=json&nojsoncallback=true&safe_search=true&api_key=$FLICKR_API_KEY"
    }
}