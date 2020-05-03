package se.mobileinteraction.photogallery.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.fragment_about.*
import se.mobileinteraction.photogallery.R

class AboutFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_about, container, false)
    }

    override fun onStart() {
        super.onStart()
        licensesButton.setOnClickListener {
            with(findNavController()) {
                val destination = AboutFragmentDirections.actionAboutFragmentToLicencesMenu()
                navigate(destination)
            }
        }
    }
}
