package com.am.task.remote.network

import com.fasterxml.jackson.core.JsonProcessingException
import org.json.JSONObject
import retrofit2.Response
import java.net.UnknownHostException
import java.util.regex.Pattern

@Suppress("unused")
sealed class ApiResponse<T> {
    companion object {
        fun <T> create(error: Throwable): ApiErrorResponse<T> {
            if(error is UnknownHostException)
                return ApiErrorResponse(error.message ?: "network error", UNABLE_TO_FIND_SERVER)
            if(error is JsonProcessingException)
                return ApiErrorResponse(error.message ?: "parsing error", RESPONSE_PARSING_ERROR)

            return ApiErrorResponse(error.message ?: "unknown error",UNKNOWN_ERROR)
        }

        fun <T> create(response: Response<T>): ApiResponse<T> {
            return if (response.isSuccessful) {
                val body = response.body()
                if (body == null || response.code() == 204) {
                    ApiEmptyResponse()
                } else {
                    ApiSuccessResponse(
                        body = body,
                        linkHeader = response.headers().get("link")
                    )
                }
            } else {
                val msg = response.errorBody()?.string()
                val errorMsg = if (msg.isNullOrEmpty()) {
                    response.message()
                } else {
                    parseErrors(msg)
                }
                ApiErrorResponse(errorMsg ?: "unknown error",response.code())
            }
        }

        private fun parseErrors(msg: String?): String? {
            try {
                val v = JSONObject(msg)
                val arr = v.getJSONObject("errors")

                var str = ""
                for (key in arr.keys()) {
                    val ar = arr.getJSONArray(key)
                    for (item in 0 until ar.length()) {
                        str = str + ar.get(item) + "\n"
                    }
                }

                return str
            } catch (e: Exception) {
                return msg
            }
        }

        //ERROR codes
        const val UNAUTHORIZED_ACCESS_ERROR = 401
        const val MISSING_PERMISSION_ERROR = 403
        const val REQUEST_ERROR = 400
        const val SERVER_ERROR = 500
        const val RESOURCE_NOT_FOUND_ERROR = 404
        const val UNABLE_TO_FIND_SERVER = -1
        const val RESPONSE_PARSING_ERROR = -2
        const val UNKNOWN_ERROR = -3
    }
}


class ApiEmptyResponse<T> : ApiResponse<T>()

data class ApiSuccessResponse<T>(
    val body: T,
    val links: Map<String, String>
) : ApiResponse<T>() {
    constructor(body: T, linkHeader: String?) : this(
        body = body,
        links = linkHeader?.extractLinks() ?: emptyMap()
    )

    companion object {
        private val LINK_PATTERN = Pattern.compile("<([^>]*)>[\\s]*;[\\s]*rel=\"([a-zA-Z0-9]+)\"")

        private fun String.extractLinks(): Map<String, String> {
            val links = mutableMapOf<String, String>()
            val matcher = LINK_PATTERN.matcher(this)

            while (matcher.find()) {
                val count = matcher.groupCount()
                if (count == 2) {
                    links[matcher.group(2)] = matcher.group(1)
                }
            }
            return links
        }

    }
}

data class ApiErrorResponse<T>(val errorMessage: String, val errorCode: Int) : ApiResponse<T>()