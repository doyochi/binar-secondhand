package id.hikmah.binar.secondhand.daftarjual.helper

data class Resource<out T>(val status: Status, val data: T? = null, val message: String?) {
    companion object {
        fun <T> success(data: T?): Resource<T> = Resource(Status.SUCCESS, data, null)

        fun <T> error (message: String?): Resource<T> = Resource(Status.ERROR, null, message)

        fun <T> loading (data: T?): Resource<T> = Resource(Status.LOADING, data, null)
    }
}
