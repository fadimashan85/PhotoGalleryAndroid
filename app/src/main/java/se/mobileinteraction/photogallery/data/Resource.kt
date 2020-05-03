package se.mobileinteraction.photogallery.data

sealed class Resource<T>
data class Success<T>(val data: T) : Resource<T>()
data class Failed<T>(val throwable: Throwable) : Resource<T>()
class Loading<T> : Resource<T>() {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        return true
    }

    override fun hashCode(): Int {
        return javaClass.hashCode()
    }
}
