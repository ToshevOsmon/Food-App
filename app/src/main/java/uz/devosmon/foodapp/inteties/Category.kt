package uz.devosmon.foodapp.inteties

import androidx.room.*
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import uz.devosmon.foodapp.inteties.converter.CategoryListConverter

@Entity(tableName = "Category")
data class Category(

    @PrimaryKey(autoGenerate = true)
    var id:Int,

    @ColumnInfo(name = "categoryItems")
    @Expose
    @SerializedName("categories")
    @TypeConverters(CategoryListConverter::class)

    val categorieitems: List<CategoryItems>?=null
)

