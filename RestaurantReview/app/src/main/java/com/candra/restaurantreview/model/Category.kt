package com.candra.restaurantreview.model


import com.google.gson.annotations.SerializedName

data class Category(
    @SerializedName("name")
    val name: String
)