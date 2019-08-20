package team.app.base.data

enum class Status {
    LOADING,
    SUCCESS,
    ERROR,
    COMPLETE
}

class DataResult<T> private constructor(val status: Status, val data: T?, val error: Throwable?) {
    companion object {
        fun <T> loading(): DataResult<T> = DataResult(Status.LOADING, null, null)

        fun <T> success(data: T?): DataResult<T> = DataResult(Status.SUCCESS, data, null)

        fun <T> error(throwable: Throwable?): DataResult<T> = DataResult(Status.ERROR, null, throwable)

        fun <T> complete(): DataResult<T> = DataResult(Status.COMPLETE, null, null)
    }
}

