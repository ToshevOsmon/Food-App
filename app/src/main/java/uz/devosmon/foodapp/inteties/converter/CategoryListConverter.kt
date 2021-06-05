package uz.devosmon.foodapp.inteties.converter

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import uz.devosmon.foodapp.inteties.Category
import uz.devosmon.foodapp.inteties.CategoryItems

class CategoryListConverter {


    @TypeConverter
    fun formCategoryList(category: List<CategoryItems>):String?{

        if (category==null){
            return  null
        }
        else{
            val gson = Gson()
            val type = object : TypeToken<CategoryItems>(){

            }.type

            return gson.toJson(category,type )
        }
    }

@TypeConverter
fun toCategoryList(categoryString: String):List<CategoryItems>?{


    if (categoryString==null){
        return null
    }
    else{
        val gson= Gson()
        val type = object :TypeToken<CategoryItems>(){

        }.type
        return gson.fromJson(categoryString,type)
    }
}
}


