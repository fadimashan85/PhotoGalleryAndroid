package se.mobileinteraction.photogallery.ui.favourites


import android.os.Bundle
import android.view.View
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import kotlinx.android.synthetic.main.favourites_list_fragment.*
import kotlinx.android.synthetic.main.item_unsplash_photo.view.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import se.mobileinteraction.photogallery.R
import se.mobileinteraction.photogallery.entities.UnsplashPhoto
import se.mobileinteraction.photogallery.ui.photos.DetailsPhotoAdapter


class FavouritesListFragment : Fragment(R.layout.favourites_list_fragment) {

    private val viewModel by viewModel<FavouritesListViewModel>()
    private val adapter: DetailsPhotoAdapter =
        DetailsPhotoAdapter({ isChecked, unsplashPhoto ->
            checkedChangeListener(isChecked, unsplashPhoto)

        }, { view, unsplashPhoto ->

            clickOnItem(view, unsplashPhoto)
        }, { viewModel.isFavourited(it) })

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        observeData()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView.layoutManager =
            StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        recyclerView.adapter = adapter

        adapter.setData(viewModel.readPhotoListCache())

    }

    private fun observeData() {

    }

    private fun clickOnItem(itemView: View, photoDetails: UnsplashPhoto) {
        with(findNavController()) {

            val imageView = itemView.unsplash_image
            val extras = FragmentNavigatorExtras(imageView to "clicked_photo${photoDetails.id}")
            val arguments = Bundle().apply { putParcelable("photoDetails", photoDetails) }

            navigate(R.id.detailsPageFragment, arguments, null, extras)
        }
    }

    private fun checkedChangeListener(checked: Boolean, photo: UnsplashPhoto) {
        if (checked) {
            viewModel.savePhotoAsFav(photo)
        } else {
            viewModel.deletePhotoFromFav(photo)
        }
    }

}
