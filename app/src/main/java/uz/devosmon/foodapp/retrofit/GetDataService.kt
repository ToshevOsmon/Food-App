package uz.devosmon.foodapp.retrofit

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
import uz.devosmon.foodapp.inteties.Category
import uz.devosmon.foodapp.inteties.Meal
import uz.devosmon.foodapp.inteties.MealResponce

interface GetDataService {

    @GET("categories.php")
    fun getCategoryList():Call<Category>

    @GET("filter.php")
    fun getMealsList(@Query("c") categoryName: String ):Call<Meal>



    @GET("lookup.php")
    fun getSpecificItem(@Query("i") id: String ):Call<MealResponce>

}