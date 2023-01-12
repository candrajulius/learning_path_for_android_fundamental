package com.candra.restaurantreview.model


import com.google.gson.annotations.SerializedName

data class CustomerReview(
    @SerializedName("date")
    val date: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("review")
    val review: String
)