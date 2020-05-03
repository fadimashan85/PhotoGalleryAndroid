package se.mobileinteraction.photogallery.ui.search

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import kotlinx.android.synthetic.main.item_unsplash_photo.*
import kotlinx.android.synthetic.main.search_result_fragment.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import se.mobileinteraction.photogallery.R
import se.mobileinteraction.photogallery.entities.Supplier
import se.mobileinteraction.photogallery.entities.UnsplashPhoto
import se.mobileinteraction.photogallery.ui.photos.PhotoAdapter
import se.mobileinteraction.photogallery.utils.afterMeasure
import timber.log.Timber


class SearchResultFragment : Fragment(R.layout.search_result_fragment) {


    private val viewModel: SearchResultViewModel by viewModel {
        parametersOf(SearchResultFragmentArgs.fromBundle(arguments!!).searchQuery)

    }

    private val categoyHeaderPosition: Int
        get() = SearchResultFragmentArgs.fromBundle(arguments!!).position

    //    private val itemOffsetsParams = ItemOffsetsParams()
    private val photoAdapter: PhotoAdapter by lazy {
        PhotoAdapter(
            if (categoyHeaderPosition > -1) Supplier.categoryList[categoyHeaderPosition] else null,
            { isChecked, unsplashphoto ->
                checkedChangeListener(isChecked, unsplashphoto)
            }, { view, unsplash ->
                clickOnItem(view, unsplash)
            }, {
                viewModel.isFavourited(it)
            })
    }

//    fun getItemOffsets(outRect: Rect,
//                                view: View,
//                                parent: RecyclerView,
//                                state: RecyclerView.State) {
//        if (parent.getChildAdapterPosition(view) < 0) {
//            outRect.setEmpty()
//            return
//        }
//
//        determineItemOffsetsParams(view, parent, state, itemOffsetsParams)
//    }
//
//    private fun determineItemOffsetsParams(view: View,
//                                           parent: RecyclerView,
//                                           state: RecyclerView.State,
//                                           itemOffsetsParams: ItemOffsetsRequestBuilder.ItemOffsetsParams
//    ) {
//        val layoutManager = parent.layoutManager
//
//        when (layoutManager) {
//            null -> throw IllegalArgumentException("RecyclerView without layout manager")
//            is StaggeredGridLayoutManager -> {
//                val layoutParams = view.layoutParams as StaggeredGridLayoutManager.LayoutParams
//
//                // could write some logic to determine and cache group index for each item
//                // could access to Span object in item layout params through reflection
//
//                itemOffsetsParams.apply {
//                    spanIndex = layoutParams.spanIndex
//                    groupIndex = 0
//                    spanSize = if (layoutParams.isFullSpan) layoutManager.spanCount else 2
//                    spanCount = layoutManager.spanCount
//                    groupCount = 1
//                    isLayoutVertical = (layoutManager.orientation == OrientationHelper.VERTICAL)
//                    isLayoutReverse = layoutManager.reverseLayout
//                }
//            }
//        }
//    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        searchRV.afterMeasure { startPostponedEnterTransition() }

//        val layoutManager: GridLayoutManager = GridLayoutManager(context,2)
//        layoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup(){
//            override fun getSpanSize(position: Int): Int {
//                return when(position){
//                    0 -> 2
//                    else -> 1
//                }
//            }
//        }

        val layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
//        val layoutParams = StaggeredGridLayoutManager.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT)
//            layoutParams.isFullSpan


//        val layoutParams = view.layoutParams

//                itemOffsetsParams.apply {
//                    spanIndex = 0
//                    groupIndex = 0
//                    spanSize =  layoutManager.spanCount
//                    spanCount = layoutManager.spanCount
//                    groupCount = 1
//                    isLayoutVertical = (layoutManager.orientation == OrientationHelper.VERTICAL)
//                    isLayoutReverse = layoutManager.reverseLayout
//                }


//        searchRV.layoutManager =
//            StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        searchRV.setHasFixedSize(true)
        searchRV.layoutParams
        searchRV.layoutManager = layoutManager


        searchRV.adapter = photoAdapter


    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.searchData.observe(viewLifecycleOwner, Observer { list ->
            Timber.d("${list.size}")
            photoAdapter.submitList(list)
        })


    }

    private fun clickOnItem(itemView: View, photoDetails: UnsplashPhoto) {
        findNavController().navigate(
            SearchResultFragmentDirections.actionSearchResultFragmentToDetailsPageFragment(
                photoDetails
            )
        )
    }

    private fun checkedChangeListener(checked: Boolean, photo: UnsplashPhoto) {

        photo_fav_switch.setOnCheckedChangeListener { buttonView, isChecked ->
            if (checked) {
                viewModel.savePhotoAsFav(photo)
            } else {
                viewModel.deletePhotoFromFav(photo)
            }
        }

    }


}

//data class ItemOffsetsParams(
//    /* Item data */
//    var spanIndex: Int  = 0,
//    var groupIndex: Int = 0,
//    var spanSize: Int   = 1,
//    /* Layout manager data */
//    var spanCount: Int  = 1,
//    var groupCount: Int = 1,
//    var isLayoutVertical: Boolean = true,
//    var isLayoutReverse: Boolean  = false
//)
