package com.candra.restaurantreview.model


import com.google.gson.annotations.SerializedName

data class Restaurant(
    @SerializedName("categories")
    val categories: List<Category>,
    @SerializedName("customerReviews")
    val customerReviews: List<CustomerReview>,
    @SerializedName("description")
    val description: String,
    @SerializedName("id")
    val id: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("pictureId")
    val pictureId: String,
    @SerializedName("rating")
    val rating: Double
)