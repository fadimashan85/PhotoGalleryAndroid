package se.mobileinteraction.photogallery.entities


data class SearchResultData(

    val total: Int,
    val total_pages: Int,
    val results: List<UnsplashPhoto>
)
