package se.mobileinteraction.photogallery.ui.search

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.list_item.view.*
import se.mobileinteraction.photogallery.R
import se.mobileinteraction.photogallery.entities.Category
import se.mobileinteraction.photogallery.utils.load


class SearchAdapter(val onClick: (String, Int) -> Unit) :
    RecyclerView.Adapter<SearchAdapter.SearchViewHolder>() {

    private val list = mutableListOf<Category>()


    fun setData(data: List<Category>) {
        list.clear()
        list.addAll(data)
        notifyDataSetChanged()
    }

    override fun getItemCount() = list.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        SearchViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.list_item,
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        holder.bind(list[position], onClick)
    }

    class SearchViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val title = itemView.findViewById<TextView>(R.id.category_title)
        fun bind(data: Category, onClick: (String , Int) -> Unit) {
            itemView.setOnClickListener {
                onClick(data.id, adapterPosition)
            }

            itemView.showImage.load(data.imageURL)
//            itemView.showImage.setImageDrawable(ContextCompat.getDrawable(itemView.context, data.imageDrawable))
                    //itemView.showImage.setImageResource(data.imageDrawable)
            title.text = data.id

        }
    }
}
