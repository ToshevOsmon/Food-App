package uz.devosmon.foodapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.coroutines.launch
import uz.devosmon.foodapp.adapter.MainCategoryAdapter
import uz.devosmon.foodapp.adapter.SubCategoryAdapter
import uz.devosmon.foodapp.database.RecipiesDatabase
import uz.devosmon.foodapp.inteties.Category
import uz.devosmon.foodapp.inteties.CategoryItems
import uz.devosmon.foodapp.inteties.MealsItems
import uz.devosmon.foodapp.inteties.Recipies
import java.util.*
import kotlin.collections.ArrayList

class HomeActivity : BaseActivity() {

    var arrMainCategory = ArrayList<CategoryItems>()
    var arrSubCategory = ArrayList<MealsItems>()


    var mainCategoryAdapter = MainCategoryAdapter()
    var subCategoryAdapter = SubCategoryAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        getDataFromDb()

        mainCategoryAdapter.setClickListener(onClicked)
        subCategoryAdapter.setClickListener(onClicked1)



        search.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {

                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {

                mainCategoryAdapter.filter.filter(newText)
                return true

            }
        })

    }

    private val onClicked = object : MainCategoryAdapter.OnItemClickListener {
        override fun onClick(categoryName: String) {
            getMealDataFromDb(categoryName)
        }
    }

    private val onClicked1 = object : SubCategoryAdapter.OnItemClickListener {
        override fun onClick(id: String) {

            var intent = Intent(this@HomeActivity, DetailActivity::class.java)
            intent.putExtra("id", id)
            startActivity(intent)

        }
    }

    private fun getDataFromDb() {

        launch {
            this.let {

                var cat =
                    RecipiesDatabase.getDatabase(this@HomeActivity).recipeDao().getAllCategory()

                arrMainCategory = cat as ArrayList<CategoryItems>

                arrMainCategory.reverse()

                getMealDataFromDb(arrMainCategory[0].strCategory)

                mainCategoryAdapter.setData(arrMainCategory)

                rv_main_category.layoutManager =
                    LinearLayoutManager(this@HomeActivity, LinearLayoutManager.HORIZONTAL, false)
                rv_main_category.adapter = mainCategoryAdapter


            }


        }

    }

    private fun getMealDataFromDb(categoryName: String) {

        tvCategory.text = "$categoryName Category"

        launch {
            this.let {

                var cat =
                    RecipiesDatabase.getDatabase(this@HomeActivity).recipeDao()
                        .getAllMeal(categoryName)

                arrSubCategory = cat as ArrayList<MealsItems>

                arrSubCategory.reverse()

                subCategoryAdapter.setData(arrSubCategory)

                rv_sub_category.layoutManager =
                    LinearLayoutManager(this@HomeActivity)
                rv_sub_category.adapter = subCategoryAdapter


            }


        }

    }

}
