package com.candra.restaurantreview.model


import com.google.gson.annotations.SerializedName
import retrofit2.http.Field

data class RestaurantResponse(
    @SerializedName("error")
    val error: Boolean,
    @SerializedName("message")
    val message: String,
    @SerializedName("restaurant")
    val restaurant: Restaurant
)

data class PostReviewResponse(

    @field:SerializedName("customerReviews")
    val customerReview: List<CustomerReview>,

    @field:SerializedName("error")
    val error: Boolean,

    @field:SerializedName("message")
    val message: String

)