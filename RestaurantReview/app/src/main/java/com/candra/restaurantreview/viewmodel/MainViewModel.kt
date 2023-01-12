package com.candra.restaurantreview.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.candra.restaurantreview.Constant.Companion.RESTAURANT_ID
import com.candra.restaurantreview.Event
import com.candra.restaurantreview.model.CustomerReview
import com.candra.restaurantreview.model.PostReviewResponse
import com.candra.restaurantreview.model.Restaurant
import com.candra.restaurantreview.model.RestaurantResponse
import com.candra.restaurantreview.service.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel: ViewModel()
{
    private val _restaurant = MutableLiveData<Restaurant>()
    val restaurant: LiveData<Restaurant> = _restaurant

    private val _listReview = MutableLiveData<List<CustomerReview>>()
    val listReview: LiveData<List<CustomerReview>> = _listReview

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading : LiveData<Boolean> = _isLoading

    private val _snackabarText = MutableLiveData<Event<String>>()
    val snackbarText: LiveData<Event<String>> = _snackabarText

    companion object{
        private const val TAG = "MainViewModel"
    }

    init {
        findRestaurant()
    }

    private fun findRestaurant(){
        _isLoading.value = true
        ApiConfig.getApiService().getRestaurant(RESTAURANT_ID).apply {
            enqueue(object: Callback<RestaurantResponse>{
                override fun onResponse(
                    call: Call<RestaurantResponse>,
                    response: Response<RestaurantResponse>
                ) {
                    _isLoading.value = false
                   response.let {
                       if (it.isSuccessful){
                           it.body()?.also { value ->
                               _restaurant.value = value.restaurant
                               _listReview.value = value.restaurant.customerReviews
                           }
                       }
                   }
                }

                override fun onFailure(call: Call<RestaurantResponse>, t: Throwable) {
                    _isLoading.value = false
                    Log.e(TAG, "onFailure: ${t.message.toString()}", )
                }

            })
        }
    }


    fun postReview(review: String){
        _isLoading.value = true
        ApiConfig.getApiService().postReview(RESTAURANT_ID,"Candra",review).apply {
            enqueue(object: Callback<PostReviewResponse>{
                override fun onResponse(
                    call: Call<PostReviewResponse>,
                    response: Response<PostReviewResponse>
                ) {
                    _isLoading.value = false
                  response.let {
                      if (it.isSuccessful){
                          it.body()?.let { data ->
                              _listReview.value = data.customerReview
                              _snackabarText.value = Event(data.message)
                          }
                      }else{
                          Log.e(TAG, "onResponse: ${it.message()}", )
                      }
                  }
                }

                override fun onFailure(call: Call<PostReviewResponse>, t: Throwable) {
                    _isLoading.value = false
                    Log.e(TAG, "onFailure: ${t.message.toString()}", )
                }

            })
        }

    }

}