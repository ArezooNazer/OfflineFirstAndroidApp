package com.arezoo.offline.data.remote
import android.database.sqlite.SQLiteException
import android.util.Log
import com.google.gson.Gson
import com.google.gson.JsonObject
import okhttp3.ResponseBody
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException
import java.net.SocketTimeoutException

class ErrorHandler {
    private val TAG = "ErrorHandler"

    fun traceErrorException(throwable: Throwable?): ErrorModel {
        Log.d(TAG, "traceErrorException() called with: throwable = $throwable")
        return when (throwable) {
            // if throwable is an instance of HttpException
            // then attempt to parse error data from response body
            is HttpException -> {
                getHttpError(throwable.response()?.errorBody())
            }

            // handle api call timeout error
            is SocketTimeoutException -> {
                ErrorModel(
                    ErrorModel.FAILED_TRY_AGAIN,
                    null,
                    ErrorModel.ErrorStatus.TIMEOUT
                )
            }

            // handle connection error
            is IOException -> {
                ErrorModel(
                    ErrorModel.NO_CONNECTION,
                    null,
                    ErrorModel.ErrorStatus.NO_CONNECTION
                )
            }

            // handle database error
            is SQLiteException -> {
                ErrorModel(
                    throwable.message,
                    null,
                    ErrorModel.ErrorStatus.DATABASE
                )
            }
            else -> ErrorModel(
                ErrorModel.FAILED_TRY_AGAIN,
                null,
                ErrorModel.ErrorStatus.BAD_REQUEST
            )
        }
    }

    /**
     * attempts to parse http response body and get error data from it
     *
     * @param body retrofit response body
     * @return returns an instance of [ErrorModel] with parsed data or NOT_DEFINED status
     */
    private fun getHttpError(body: ResponseBody?): ErrorModel {
        return try {
            // use response body to get error detail
            val result = body?.string()
            Log.d("getHttpError", "getErrorMessage() called with: errorBody = [$result]")
            val json = Gson().fromJson(result, JsonObject::class.java)
            ErrorModel(json.toString(), 400, ErrorModel.ErrorStatus.BAD_REQUEST)
        } catch (e: Throwable) {
            e.printStackTrace()
            ErrorModel(
                message = e.message,
                code = null,
                errorStatus = ErrorModel.ErrorStatus.NOT_DEFINED
            )
        }

    }

    fun parseError(response: Response<*>): ErrorModel {
        if (response.code() >= 500)
            return ErrorModel(
                ErrorModel.FAILED_TRY_AGAIN,
                null,
                ErrorModel.ErrorStatus.BAD_REQUEST
            )

        if (response.code() == 429) {
            return ErrorModel(
                ErrorModel.API_LIMIT_TEXT,
                response.code(),
                ErrorModel.ErrorStatus.API_LIMIT
            )
        }

        Log.d("parseError", "parseError() called with: errorResponse = [${response.errorBody()}]")

        val errorResponse: ResponseBody? = try {
            Gson().fromJson(response.errorBody()?.charStream(), ResponseBody::class.java)
        } catch (e: Exception) {
            null
        }


        return if (errorResponse == null) {
            ErrorModel(
                ErrorModel.FAILED_TRY_AGAIN,
                null,
                ErrorModel.ErrorStatus.BAD_REQUEST
            )
        } else
            ErrorModel(
                errorResponse.string(),
                response.code(),
                null
            )
    }
}