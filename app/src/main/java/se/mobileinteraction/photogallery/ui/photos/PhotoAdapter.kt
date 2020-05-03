package se.mobileinteraction.photogallery.ui.photos

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.ToggleButton
import androidx.core.view.ViewCompat
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import kotlinx.android.synthetic.main.header_item.view.*
import se.mobileinteraction.photogallery.R
import se.mobileinteraction.photogallery.entities.Category
import se.mobileinteraction.photogallery.entities.UnsplashPhoto
import se.mobileinteraction.photogallery.utils.load


class PhotoAdapter(
    val category: Category? = null,
    val onChecked: (Boolean, UnsplashPhoto) -> Unit,
    val onClick: (View, UnsplashPhoto) -> Unit,
    val isFavorited: (String) -> Boolean
) :
    PagedListAdapter<UnsplashPhoto, RecyclerView.ViewHolder>(COMPARATOR) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return (
                if (viewType == HEADER_TYPE) {
                    HeaderViewHolder(
                        LayoutInflater.from(parent.context).inflate(
                            R.layout.header_item,
                            parent,
                            false
                        )
                    )


                } else {
                    PhotoViewHolder(
                        LayoutInflater.from(parent.context).inflate(
                            R.layout.item_unsplash_photo,
                            parent,
                            false
                        )
                    )
                })
    }


    override fun getItemViewType(position: Int): Int {
        return if (position == 0 && category != null) HEADER_TYPE else ITEM_TYPE
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        if (holder is PhotoViewHolder) {
            getItem(position)?.let {
                holder.bind(it, onClick, onChecked, isFavorited)

            }

        } else if (holder is HeaderViewHolder)


            category?.let {
                holder.bind(it)

            }


    }


    class HeaderViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val imageHeaderView = itemView.findViewById<ImageView>(R.id.header_photo)
        val txtHeaderView = itemView.findViewById<TextView>(R.id.header_text)

        fun bind(photo: Category) {
            ViewCompat.setTransitionName(imageHeaderView, "clicked_photo${photo.id}")
            itemView.header_photo.load(photo.imageURL)
            txtHeaderView.text = photo.id

        }

    }

    class PhotoViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val imageView = itemView.findViewById<AspectRatioImageView>(R.id.unsplash_image)
        val txtView = itemView.findViewById<TextView>(R.id.user_photograph)
        val favSwitch = itemView.findViewById<ToggleButton>(R.id.photo_fav_switch)


        fun bind(
            data: UnsplashPhoto, onClick: (View, UnsplashPhoto) -> Unit,
            onChecked: (Boolean, UnsplashPhoto) -> Unit,
            isFavorited: (String) -> Boolean
        ) {
            ViewCompat.setTransitionName(imageView, "clicked_photo${data.id}")
            itemView.setBackgroundColor(Color.parseColor(data.color))
            imageView.aspectRatio = data.height.toDouble() / data.width.toDouble()
            data.urls!!.small?.let { url -> imageView.load(url) }
            txtView.text = data.user!!.name


            favSwitch.isChecked = isFavorited(data.id)
            itemView.setOnClickListener {
                onClick(imageView, data)
            }

            favSwitch.setOnCheckedChangeListener { buttonView, isChecked ->
                onChecked(isChecked, data)
            }
        }
    }

    companion object {

        val HEADER_TYPE = 0
        val ITEM_TYPE = 1

        val COMPARATOR = object : DiffUtil.ItemCallback<UnsplashPhoto>() {
            override fun areContentsTheSame(
                oldItem: UnsplashPhoto,
                newItem: UnsplashPhoto
            ): Boolean =
                oldItem == newItem

            override fun areItemsTheSame(oldItem: UnsplashPhoto, newItem: UnsplashPhoto): Boolean =
                oldItem == newItem
        }
    }
}