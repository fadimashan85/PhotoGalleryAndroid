package se.mobileinteraction.photogallery.ui.photos


import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Switch
import android.widget.ToggleButton
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.RecyclerView
import se.mobileinteraction.photogallery.R
import se.mobileinteraction.photogallery.entities.UnsplashPhoto
import se.mobileinteraction.photogallery.utils.load


class DetailsPhotoAdapter(val onChecked: (Boolean, UnsplashPhoto) -> Unit ,val onClick: (View, UnsplashPhoto) -> Unit , val isFavourited: (String) -> Boolean) :
    RecyclerView.Adapter<DetailsPhotoAdapter.DetailsPhotoViewHolder>() {

    private val list = mutableListOf<UnsplashPhoto>()



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        DetailsPhotoViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_unsplash_photo,
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: DetailsPhotoViewHolder, position: Int) {
        val userPhotos = list[position]
        holder.bind(userPhotos, onChecked, onClick, isFavourited)

    }

    class DetailsPhotoViewHolder( val view: View) : RecyclerView.ViewHolder(view) {
        val imageView = itemView.findViewById<AspectRatioImageView>(R.id.unsplash_image)
        val favoPhoto = itemView.findViewById<ToggleButton>(R.id.photo_fav_switch)

        fun bind( data: UnsplashPhoto,onChecked: (Boolean,UnsplashPhoto)-> Unit , onClick: (View, UnsplashPhoto) -> Unit, isFavourited: (String)-> Boolean) {
            ViewCompat.setTransitionName(imageView, "clicked_photo${data.id}")
            itemView.setBackgroundColor(Color.parseColor(data.color))
            imageView.aspectRatio = data.height.toDouble() / data.width.toDouble()
            favoPhoto.isChecked = isFavourited(data.id)
            favoPhoto.setOnCheckedChangeListener { buttonView, isChecked ->
                    onChecked(isChecked,data )
            }


            data.urls!!.regular?.let { url ->
                imageView.load(url)
            }

            itemView.setOnClickListener {
                onClick(imageView, data)
            }
        }
    }

    fun setData(data: List<UnsplashPhoto>) {
        list.clear()
        list.addAll(data)

        notifyDataSetChanged()
    }


    override fun getItemCount(): Int {
        return list.size
    }


}
