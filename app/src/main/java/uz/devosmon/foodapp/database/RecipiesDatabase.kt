package uz.devosmon.foodapp.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import uz.devosmon.foodapp.dao.RecipeDao
import uz.devosmon.foodapp.inteties.*
import uz.devosmon.foodapp.inteties.converter.CategoryListConverter
import uz.devosmon.foodapp.inteties.converter.MealListConverter

@Database(entities = [Recipies::class,CategoryItems::class,Category::class,Meal::class,MealsItems::class],version = 1,exportSchema = false)
@TypeConverters(CategoryListConverter::class,MealListConverter::class)
abstract class RecipiesDatabase:RoomDatabase() {

    companion object{

        var recipiesDatabase:RecipiesDatabase?=null

        @Synchronized
        fun getDatabase(context: Context):RecipiesDatabase{
            if (recipiesDatabase == null){
                recipiesDatabase = Room.databaseBuilder(
                    context,
                    RecipiesDatabase::class.java,
                    "recipe.db"
                )
                    .allowMainThreadQueries()
                    .build()
            }
            return recipiesDatabase!!
        }
    }

    abstract fun recipeDao():RecipeDao
}