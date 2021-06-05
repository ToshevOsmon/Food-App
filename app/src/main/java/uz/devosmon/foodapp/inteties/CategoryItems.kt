package uz.devosmon.foodapp.inteties

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

@Entity(tableName = "CategoryItems")
data class CategoryItems(

    @PrimaryKey(autoGenerate = true)
    var id: Int,

    @Expose
    @SerializedName("idCategory")
    @ColumnInfo(name = "idcategory")
    val idCategory: String,

    @ColumnInfo(name = "strcategory")
    @Expose
    @SerializedName("strCategory")

    val strCategory: String,

    @ColumnInfo(name = "strcategorydescription")
    @Expose
    @SerializedName("strCategoryDescription")

    val strCategoryDescription: String,

    @ColumnInfo(name = "strcategorythumb")
    @Expose
    @SerializedName("strCategoryThumb")

    val strCategoryThumb: String
)