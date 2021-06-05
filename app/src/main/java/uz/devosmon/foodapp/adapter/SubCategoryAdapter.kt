package uz.devosmon.foodapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.item_rv_main_category.view.*
import kotlinx.android.synthetic.main.item_rv_sub_category.view.*
import uz.devosmon.foodapp.R
import uz.devosmon.foodapp.inteties.MealsItems
import uz.devosmon.foodapp.inteties.Recipies

class SubCategoryAdapter : RecyclerView.Adapter<SubCategoryAdapter.ItemViewHolder>() {

    var listener:OnItemClickListener? = null

    var context: Context?=null
    var arrSubCategory = ArrayList<MealsItems>()

    class ItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        context = parent.context
        return ItemViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_rv_sub_category, parent, false)
        )
    }

    fun setData(arrData: List<MealsItems>) {
        arrSubCategory = arrData as ArrayList<MealsItems>
    }


    fun setClickListener(listener1: OnItemClickListener){
        listener = listener1
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {

holder.itemView.textName.text = arrSubCategory[position].strMeal

        Glide.with(context!!).load(arrSubCategory[position].strMealThumb).into(holder.itemView.img_dishs)


        holder.itemView.rootView.setOnClickListener {
            listener!!.onClick(arrSubCategory[position].idMeal)
        }
    }

    override fun getItemCount(): Int {
        return arrSubCategory.size
    }


    interface OnItemClickListener{
        fun onClick(id: String)
    }

}