package se.mobileinteraction.photogallery.ui.photos

import android.os.Bundle
import android.view.View
import androidx.core.view.ViewCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import androidx.transition.TransitionInflater
import kotlinx.android.synthetic.main.details_page_fragment.*
import kotlinx.android.synthetic.main.item_unsplash_photo.view.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import se.mobileinteraction.photogallery.R
import se.mobileinteraction.photogallery.entities.UnsplashPhoto
import se.mobileinteraction.photogallery.utils.afterMeasure
import se.mobileinteraction.photogallery.utils.load
import timber.log.Timber


class DetailsPageFragment : Fragment(R.layout.details_page_fragment) {


    private val viewModel: DetailsPageViewModel by viewModel {
        parametersOf(unsplashPhoto)
    }

    private val unsplashPhoto: UnsplashPhoto
        get() = DetailsPageFragmentArgs.fromBundle(arguments!!).photoDetails


    private val detailsPhotoAdapter: DetailsPhotoAdapter =
        DetailsPhotoAdapter({ isChecked, unsplashPhoto ->
            checkedChangeListener(
                isChecked,
                unsplashPhoto
            )
        },
            { view, unsplashPhoto -> clickOnItem(view, unsplashPhoto) },
            { viewModel.isFavourited(it) })

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val transition =
            TransitionInflater.from(context).inflateTransition(android.R.transition.move)
        sharedElementEnterTransition = transition
        sharedElementReturnTransition = transition
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        postponeEnterTransition()
        recyclerView.afterMeasure { startPostponedEnterTransition() }
        recyclerView.adapter = detailsPhotoAdapter
        recyclerView.layoutManager =
            StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)


        onePhoto.aspectRatio = unsplashPhoto.height.toDouble() / unsplashPhoto.width.toDouble()
        unsplashPhoto.urls!!.small?.let {
            onePhoto.load(it)
        }


        root.setOnScrollChangeListener { v, scrollX, scrollY, oldScrollX, oldScrollY ->
            onePhoto.translationY = scrollY.toFloat()
        }

        ViewCompat.setTransitionName(onePhoto, "clicked_photo${unsplashPhoto.id}")
        Timber.e("${ViewCompat.getTransitionName(onePhoto)}")

        favo_switch.setOnCheckedChangeListener { buttonView, isChecked ->
            checkedChangeListener(isChecked, unsplashPhoto)
        }

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        observeData()
    }


    private fun observeData() {

        viewModel.publicProfile.observe(viewLifecycleOwner, Observer {

            it.profile_image.large.let { it1 -> profile_photo.load(it1) }
            username.text = it.username
            user_bio.text = (it.bio + "\n  Down more photos from " + it.username )

        })

        viewModel.userPhotos.observe(viewLifecycleOwner, Observer {
            detailsPhotoAdapter.setData(it)
        })

        viewModel.photo.observe(viewLifecycleOwner, Observer {

            it.urls!!.full?.let { onePhoto.load(it) }
        })
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
