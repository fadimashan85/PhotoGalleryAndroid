package se.mobileinteraction.photogallery.ui.map

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.ToggleButton
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.RecyclerView
import se.mobileinteraction.photogallery.R
import se.mobileinteraction.photogallery.entities.UnsplashPhoto
import se.mobileinteraction.photogallery.ui.photos.AspectRatioImageView
import se.mobileinteraction.photogallery.utils.load


class MapsAdapter(
    val onChecked: (Boolean, UnsplashPhoto) -> Unit,
    val onClick: (String) -> Unit,
    val isFavourited: (String) -> Boolean
) :
    RecyclerView.Adapter<MapsAdapter.PhotoViewHolder>() {

    private val list = mutableListOf<UnsplashPhoto>()

    fun setData(data: List<UnsplashPhoto>) {
        list.clear()
        list.addAll(data)

        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        PhotoViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_maps_photo,
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: PhotoViewHolder, position: Int) {
        val unsplash = list[position]
        holder.bind(unsplash, onChecked, onClick, isFavourited)
        ViewCompat.setTransitionName(holder.itemView,"clicked_photo")
    }


    override fun getItemCount() = list.size

    class PhotoViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val imageView = itemView.findViewById<AspectRatioImageView>(R.id.unsplash_image)
        fun bind(
            data: UnsplashPhoto,
            onChecked: (Boolean, UnsplashPhoto) -> Unit,
            onClick: (String) -> Unit,
            isFavourited: (String) -> Boolean
        ) {
            ViewCompat.setTransitionName(imageView, "clicked_photo${data.id}")
            itemView.setBackgroundColor(Color.parseColor(data.color))
            imageView.aspectRatio = (data.height.toDouble() / data.width.toDouble())
            data.urls!!.small?.let { url -> imageView.load(url) }


            data.urls!!.regular?.let { url ->
                imageView.load(url)
            }

            itemView.setOnClickListener {
                onClick(data.id)
            }

        }
    }
}
