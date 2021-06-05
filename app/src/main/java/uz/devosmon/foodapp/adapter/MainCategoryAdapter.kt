package uz.devosmon.foodapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.item_rv_main_category.view.*
import uz.devosmon.foodapp.R
import uz.devosmon.foodapp.inteties.CategoryItems
import uz.devosmon.foodapp.inteties.Recipies
import java.util.*
import kotlin.collections.ArrayList

class MainCategoryAdapter : RecyclerView.Adapter<MainCategoryAdapter.ItemViewHolder>(), Filterable {

    var listener: OnItemClickListener? = null

    var context: Context? = null
    var arrMainCategory = ArrayList<CategoryItems>()
    var exampleList = ArrayList<CategoryItems>()

    class ItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {

        context = parent.context
        return ItemViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_rv_main_category, parent, false)
        )
    }

    fun setData(arrData: List<CategoryItems>) {
        arrMainCategory = arrData as ArrayList<CategoryItems>
        exampleList = arrMainCategory
    }


    fun setClickListener(listener1: OnItemClickListener) {
        listener = listener1
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {

        holder.itemView.dish_name.text = exampleList[position].strCategory

        Glide.with(context!!).load(exampleList[position].strCategoryThumb)
            .into(holder.itemView.img_dish)

        holder.itemView.rootView.setOnClickListener {
            listener!!.onClick(exampleList[position].strCategory)
        }


    }

    override fun getItemCount(): Int {
        return exampleList.size
    }

    interface OnItemClickListener {
        fun onClick(categoryName: String)
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(p0: CharSequence?): FilterResults {

                val charSearch = p0.toString()
                if (charSearch.isEmpty()) {
                    exampleList = arrMainCategory
                } else {
                    var resultList = ArrayList<CategoryItems>()
                    for (raw in arrMainCategory) {

                        if (raw.strCategory.toLowerCase(Locale.ROOT).contains(
                                charSearch.toLowerCase(
                                    Locale.ROOT
                                )
                            )
                        ) {
                            resultList.add(raw)
                        }
                    }
                    exampleList = resultList
                }
                val filterResults = FilterResults()
                filterResults.values = exampleList
                return filterResults

            }

            @Suppress("UNCHECKED_CAST")
            override fun publishResults(p0: CharSequence?, p1: FilterResults?) {

                exampleList = p1?.values as ArrayList<CategoryItems>
                notifyDataSetChanged()

            }

        }
    }

}