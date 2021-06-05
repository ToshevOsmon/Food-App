package uz.devosmon.foodapp.inteties

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import uz.devosmon.foodapp.inteties.converter.MealListConverter

@Entity(tableName = "Meal")
class Meal(
    @PrimaryKey(autoGenerate = true)
    var id:Int,

    @ColumnInfo(name = "meals")
    @Expose
    @SerializedName("meals")
    @TypeConverters(MealListConverter::class)
    val mealsItems: List<MealsItems>?=null
)
