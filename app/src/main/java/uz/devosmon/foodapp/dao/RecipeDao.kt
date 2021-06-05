package uz.devosmon.foodapp.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import uz.devosmon.foodapp.inteties.CategoryItems
import uz.devosmon.foodapp.inteties.Meal
import uz.devosmon.foodapp.inteties.MealsItems

@Dao
interface RecipeDao {

    @Query("SELECT * from categoryitems order by id desc")
    suspend fun getAllCategory(): List<CategoryItems>

    @Insert(onConflict =  OnConflictStrategy.REPLACE)
    fun insertCategory(categoryItems: CategoryItems?)


    @Insert(onConflict =  OnConflictStrategy.REPLACE)
    fun insertMeals(mealsItems: MealsItems?)



    @Query("DELETE from categoryitems")
    suspend fun clearDb()


    @Query("SELECT * from mealsitems where categoryName=:categoryName order by id desc")
    suspend fun getAllMeal(categoryName:String): List<MealsItems>

}