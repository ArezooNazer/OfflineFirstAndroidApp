package com.arezoo.offline.data.remote

data class ErrorModel(
    val message: String?,
    val code: Int?,
    var errorStatus: ErrorStatus?
) {

    companion object {
        const val FAILED_TRY_AGAIN = "Error! please try later"
        const val NO_CONNECTION = "Internet Connection Error"
        const val API_LIMIT_TEXT = "Sorry! You reached API limit"
    }

    /**
     * various error status to know what happened if something goes wrong with a repository call
     */
    enum class ErrorStatus {
        /**
         * error in connecting to repository (Server or Database)
         */
        NO_CONNECTION,
        /**
         * error in getting value (Json Error, Server Error, etc)
         */
        BAD_REQUEST,
        /**
         * Time out  error
         */
        TIMEOUT,
        /**
         * no data available in repository
         */
        EMPTY_RESPONSE,
        /**
         * an unexpected error
         */
        NOT_DEFINED,
        /**
         * bad credential
         */
        UNAUTHORIZED,
        /**
         * Database Errors
         */
        DATABASE,
        /**
         * Foursquare API Limit
         */
        API_LIMIT

    }
}