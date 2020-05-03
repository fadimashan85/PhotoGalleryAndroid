package se.mobileinteraction.photogallery.entities

import retrofit2.http.Url
import se.mobileinteraction.photogallery.R
import java.net.URL


data class Category(val id: String, val imageURL:String)

//object Supplier {
//    val categoryList = listOf(
//        Category("wallpapers", R.drawable.wallpapers),
//        Category("Textures & Patterns", R.drawable.textures),
//        Category("CurrentEvents", R.drawable.currentevents),
//        Category("Architecture", R.drawable.architecture),
//        Category("Business & Work", R.drawable.business),
//        Category("Film", R.drawable.film),
//        Category("Animals", R.drawable.animals),
//        Category("Travel", R.drawable.travel),
//        Category("Fashion", R.drawable.fashion),
//        Category("Food_Drink", R.drawable.food_drink),
//        Category("Spirituality", R.drawable.spirituality),
//        Category("Experimental", R.drawable.experimental),
//        Category("People", R.drawable.people),
//        Category("Health", R.drawable.health),
//        Category("Art_Culture", R.drawable.art_culture)
//
//    )

object Supplier {
    val categoryList = listOf(
        Category("wallpapers", "https://images.unsplash.com/photo-1578605002661-69450a97476d?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=668&q=80"),
        Category("Textures & Patterns","https://images.unsplash.com/photo-1494858392419-ab8a2e6f0826?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=800&q=60"),
        Category("CurrentEvents", "https://images.unsplash.com/photo-1575898200535-44a375c4b3b0?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=668&q=80"),
        Category("Architecture", "https://images.unsplash.com/photo-1579349223046-05b7b823fe96?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=800&q=60"),
        Category("Business & Work", "https://images.unsplash.com/photo-1574656117531-9d86a7aec8a4?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=800&q=60"),
        Category("Film", "https://images.unsplash.com/photo-1563177474-140d23d3bb7d?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=500&q=60"),
        Category("Animals", "https://images.unsplash.com/photo-1576581531862-3cd33b8a2db6?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=500&q=60"),
        Category("Travel","https://images.unsplash.com/photo-1504626113995-a0e5d91f925d?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=500&q=60"),
        Category("Fashion", "https://images.unsplash.com/photo-1579954568250-8b7abc7743e7?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=500&q=60"),
        Category("Food_Drink", "https://images.unsplash.com/photo-1568315482487-1ebc0984a955?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=500&q=60"),
        Category("Spirituality", "https://images.unsplash.com/photo-1572541933966-ccaf082a9d9c?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=500&q=60"),
        Category("Experimental","https://images.unsplash.com/photo-1579776704607-f98aafccb24c?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=634&q=80"),
        Category("People", "https://images.unsplash.com/photo-1577280807586-fee733d503f1?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=500&q=60"),
        Category("Health", "https://images.unsplash.com/photo-1579316964606-7f55c2294dce?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=500&q=60"),
        Category("Art_Culture", "https://images.unsplash.com/photo-1514410432920-a6ed15a94c7c?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=500&q=60")

    )
}
