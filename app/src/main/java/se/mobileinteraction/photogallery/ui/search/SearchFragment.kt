package se.mobileinteraction.photogallery.ui.search

import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.chip.Chip
import kotlinx.android.synthetic.main.search_fragment.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import se.mobileinteraction.photogallery.R
import se.mobileinteraction.photogallery.entities.Supplier
import se.mobileinteraction.photogallery.utils.ScreenUtils


class SearchFragment : Fragment(R.layout.search_fragment) {

    private val viewModel: SearchViewModel by viewModel()
    private val adapter = SearchAdapter { query, position ->
        searchWithQuery(query, position)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView.layoutManager = LinearLayoutManager(context).apply {
            orientation = LinearLayoutManager.HORIZONTAL
        }
        recyclerView.adapter = adapter

        adapter.setData(Supplier.categoryList)

        Supplier.categoryList.map { it.id }.forEach { query ->
            val chip = Chip(requireContext())
            chip.id = View.generateViewId()
            chip.text = query
            chip.setOnClickListener { searchWithQuery(query) }
            chip_group.addView(chip)
        }


        go_map.setOnClickListener {
            findNavController().navigate(SearchFragmentDirections.actionSearchFragmentToMapsFragment())
        }


        chip_group.setChipSpacing(ScreenUtils.dipsToPixel(8f).toInt())

        search_bar.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }

            override fun onQueryTextSubmit(query: String?): Boolean {
                //(query?.let)if query not null so do this (searchWithQuery(it))

                if (query != null) {
                    searchWithQuery(query)
                }

                return false
            }
        })
    }

    private fun searchWithQuery(query: String, position: Int = -1) {
        findNavController().navigate(
            SearchFragmentDirections.actionSearchFragmentToSearchResultFragment(
                query, position
            )
        )
    }
}
