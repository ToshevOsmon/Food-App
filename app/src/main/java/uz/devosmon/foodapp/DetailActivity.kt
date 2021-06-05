package uz.devosmon.foodapp


import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_detail.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import uz.devosmon.foodapp.inteties.Category
import uz.devosmon.foodapp.inteties.Meal
import uz.devosmon.foodapp.inteties.MealResponce
import uz.devosmon.foodapp.retrofit.GetDataService
import uz.devosmon.foodapp.retrofit.RetrofitClient

var youtubeLink = ""

class DetailActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        var id = intent.getStringExtra("id")

        getCategories(id)

        imgToolBarBtnBack.setOnClickListener {
            finish()
        }
        btnYouTube.setOnClickListener {
var uri:Uri = Uri.parse(youtubeLink)

            startActivity(Intent(Intent.ACTION_VIEW,uri))

        }
    }


    fun getCategories(id: String) {

        val service = RetrofitClient.retrofitInstance!!.create(GetDataService::class.java)
        val call = service.getSpecificItem(id)
        call.enqueue(object : Callback<MealResponce> {
            override fun onResponse(
                call: Call<MealResponce>,
                response: Response<MealResponce>
            ) {

                Glide.with(this@DetailActivity).load(response.body()!!.mealsEntity[0].strMealThumb)
                    .into(imgItem)

                tvCategory.text = response.body()!!.mealsEntity[0].strMeal

                tvInstructions.text = response.body()!!.mealsEntity[0].strInstructions

                tvIngredients.text = response.body()!!.mealsEntity[0].strIngredient1


                if (response.body()!!.mealsEntity[0].strYoutube != null) {
                    youtubeLink = response.body()!!.mealsEntity[0].strYoutube
                } else {
                    btnYouTube.visibility = View.GONE
                }
            }

            override fun onFailure(call: Call<MealResponce>, t: Throwable) {

                Toast.makeText(this@DetailActivity, t.localizedMessage, Toast.LENGTH_SHORT).show()

            }
        })
    }


}
