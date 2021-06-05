package uz.devosmon.foodapp

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.ConnectivityManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import pub.devrel.easypermissions.AppSettingsDialog
import pub.devrel.easypermissions.EasyPermissions
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import uz.devosmon.foodapp.database.RecipiesDatabase
import uz.devosmon.foodapp.inteties.Category
import uz.devosmon.foodapp.inteties.Meal
import uz.devosmon.foodapp.inteties.MealsItems
import uz.devosmon.foodapp.retrofit.GetDataService
import uz.devosmon.foodapp.retrofit.RetrofitClient
import java.util.jar.Manifest


//Food App create by Toshev Osmon

class SplashActivity : BaseActivity(), EasyPermissions.RationaleCallbacks,
    EasyPermissions.PermissionCallbacks {

    private var READ_STORAGE_PERMISSION = 123

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



        readStorageTask()

        btnGetStart.setOnClickListener {

            isConnection()

        }
    }

    private fun isConnection() {
        val manager =
            applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = manager.activeNetworkInfo

        if (networkInfo != null) {
            if (networkInfo.type == ConnectivityManager.TYPE_WIFI) {

                var intent = Intent(this, HomeActivity::class.java)
                startActivity(intent)
                finish()
            }
            else if (networkInfo.type == ConnectivityManager.TYPE_MOBILE){

                var intent = Intent(this, HomeActivity::class.java)
                startActivity(intent)
                finish()

            }

        }
        else{
            val dialog = Dialog(this)
dialog.setContentView(R.layout.alter_dialog_layout)

            dialog.setCanceledOnTouchOutside(false)
            dialog.window!!.setLayout(WindowManager.LayoutParams.WRAP_CONTENT,
            WindowManager.LayoutParams.WRAP_CONTENT)

            dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

            dialog.show()




        }


    }


    fun getCategories() {

        val service = RetrofitClient.retrofitInstance!!.create(GetDataService::class.java)
        val call = service.getCategoryList()
        call.enqueue(object : Callback<Category> {
            override fun onResponse(
                call: Call<Category>,
                response: Response<Category>
            ) {


                for (arr in response.body()!!.categorieitems!!) {
                    getMeal(arr.strCategory)
                }
                insertDataIntroRoomDb(response.body())

            }

            override fun onFailure(call: Call<Category>, t: Throwable) {

                Toast.makeText(this@SplashActivity, t.localizedMessage, Toast.LENGTH_SHORT).show()

            }
        })
    }

    fun getMeal(categoryName: String) {

        val service = RetrofitClient.retrofitInstance!!.create(GetDataService::class.java)
        val call = service.getMealsList(categoryName)
        call.enqueue(object : Callback<Meal> {
            override fun onResponse(
                call: Call<Meal>,
                response: Response<Meal>
            ) {

                insertMealDataIntroRoomDb(categoryName, response.body())

            }

            override fun onFailure(call: Call<Meal>, t: Throwable) {

                loader.visibility = View.INVISIBLE

                Toast.makeText(this@SplashActivity, t.localizedMessage, Toast.LENGTH_SHORT).show()

            }
        })
    }

    fun insertDataIntroRoomDb(category: Category?) {

        launch {
            this.let {
                for (arr in category!!.categorieitems!!) {

                    RecipiesDatabase.getDatabase(this@SplashActivity)
                        .recipeDao().insertCategory(arr)

                }

            }
        }

    }


    fun insertMealDataIntroRoomDb(categoryName: String, meal: Meal?) {

        launch {
            this.let {


                for (arr in meal!!.mealsItems!!) {

                    var mealItemModel = MealsItems(
                        arr.id,
                        arr.idMeal,
                        categoryName,
                        arr.strMeal,
                        arr.strMealThumb
                    )
                    RecipiesDatabase.getDatabase(this@SplashActivity)
                        .recipeDao().insertMeals(mealItemModel)
                    Log.d("TTTT", arr.toString())

                }

                btnGetStart.visibility = View.VISIBLE
            }
        }

    }


    fun clearDatabase() {

        launch {
            this.let {
                RecipiesDatabase.getDatabase(this@SplashActivity).recipeDao().clearDb()
            }

        }

    }

    private fun hasReadStoragePermission(): Boolean {

        return EasyPermissions.hasPermissions(
            this,
            android.Manifest.permission.READ_EXTERNAL_STORAGE
        )
    }

    private fun readStorageTask() {
        if (hasReadStoragePermission()) {
            clearDatabase()
            getCategories()

        } else {

            EasyPermissions.requestPermissions(
                this,
                "his app needs access to you storage",
                READ_STORAGE_PERMISSION,
                android.Manifest.permission.READ_EXTERNAL_STORAGE
            )

        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)

    }

    override fun onRationaleAccepted(requestCode: Int) {


    }

    override fun onRationaleDenied(requestCode: Int) {


    }

    override fun onPermissionsGranted(requestCode: Int, perms: MutableList<String>) {

    }

    override fun onPermissionsDenied(requestCode: Int, perms: MutableList<String>) {

        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            AppSettingsDialog.Builder(this)
                .build().show()
        }

    }


}
