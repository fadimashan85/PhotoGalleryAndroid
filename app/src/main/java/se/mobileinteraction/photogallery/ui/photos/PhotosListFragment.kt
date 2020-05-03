package se.mobileinteraction.photogallery.ui.photos

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import androidx.transition.TransitionInflater
import kotlinx.android.synthetic.main.fragment_photo_list.*
import kotlinx.android.synthetic.main.item_unsplash_photo.view.*
import kotlinx.android.synthetic.main.list_item.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import se.mobileinteraction.photogallery.MainActivity
import se.mobileinteraction.photogallery.R
import se.mobileinteraction.photogallery.entities.Category
import se.mobileinteraction.photogallery.entities.UnsplashPhoto
import se.mobileinteraction.photogallery.utils.Reselectable
import se.mobileinteraction.photogallery.utils.afterMeasure
import timber.log.Timber


class PhotosListFragment : Fragment(R.layout.fragment_photo_list), Reselectable {

    private val viewModel by viewModel<PhotoListViewModel>()
    private val photoAdapter: PhotoAdapter = PhotoAdapter(null , { isChecked, unsplashPhoto ->
        checkedChangeListener(isChecked, unsplashPhoto)
    }, { view, unsplashPhoto ->
        clickOnItem(view, unsplashPhoto)
    }, {
        viewModel.isFavourited(it)
    })


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val transition =
            TransitionInflater.from(context).inflateTransition(android.R.transition.move)
        sharedElementEnterTransition = transition
        sharedElementReturnTransition = transition
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        context!!.theme.applyStyle(5,true)
        postponeEnterTransition()
        super.onViewCreated(view, savedInstanceState)


        recyclerView.afterMeasure { startPostponedEnterTransition() }
        recyclerView.layoutManager =
            StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        recyclerView.adapter = photoAdapter

        //post -> wait until the recyclerView to completely built then start with this
        //requireActivity -> to check if this fragment is a child for the activity we mentioned to
        recyclerView.post {
            if (requireActivity() is MainActivity) {
                val top = (requireActivity() as MainActivity).recyclerViewPadding()
                recyclerView.setPadding(0, top, 0, 0)
            }
        }

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.photosData.observe(viewLifecycleOwner, Observer { list ->
            Timber.d("${list.size}")
            photoAdapter.submitList(list)


        })
    }

    override fun onReselected() {
        recyclerView.smoothScrollToPosition(0)
    }

    private fun clickOnItem(itemView: View, photoDetails: UnsplashPhoto) {
        with(findNavController()) {

            val imageView = itemView.unsplash_image
            val extras = FragmentNavigatorExtras(imageView to "clicked_photo${photoDetails.id}")
            val arguments = Bundle().apply { putParcelable("photoDetails", photoDetails) }

            navigate(R.id.detailsPageFragment, arguments, null, extras)
        }

//        findNavController().navigate(
//            PhotosListFragmentDirections.actionPhotosListFragmentToDetailsPageFragment(
//                photoDetails
//            ))
    }

    private fun checkedChangeListener(checked: Boolean, photo: UnsplashPhoto) {
        if (checked) {
            viewModel.savePhotoAsFav(photo)
        } else {
            viewModel.deletePhotoFromFav(photo)
        }
    }

    /**
     * here I tried to add animation ti transition fragments
     * **/
//    companion object {
//        fun newInstance(): PhotosListFragment {
//            var fragmentHome = PhotosListFragment()
//            var args = Bundle()
//            fragmentHome.arguments = args
//            return fragmentHome
//        }
//    }
}
