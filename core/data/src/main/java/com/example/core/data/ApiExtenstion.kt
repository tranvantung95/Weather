package com.example.core.data

import com.example.core.data.remote.utils.ResponseErrorHandler
import com.example.core.domain.AppResult
import com.example.core.domain.BusinessModel
import com.example.core.domain.ErrorEntity
import com.example.core.domain.ErrorType
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException
import java.net.SocketTimeoutException

inline fun <Model, ModelEntity> safeApiCall(
    crossinline mapper: (ModelEntity) -> Model,
    crossinline callFunction: suspend () -> ModelEntity,
    defaultDispatcher: CoroutineDispatcher? = null
): Flow<AppResult<Model>> = flow {
    val response = withContext(defaultDispatcher ?: Dispatchers.IO) { callFunction.invoke() }
    emit(AppResult.Success<Model>(mapper.invoke(response)) as AppResult<Model>)
}.retryWithPolicy(WeatherRetryPolicy()).catch { e ->
    e.printStackTrace()
    emit(
        when (e) {
            is HttpException -> {
                val body = e.response()?.errorBody()
                AppResult.Error(
                    ErrorEntity(
                        code = e.response()?.code(),
                        message = ResponseErrorHandler.getErrorMessage(body),
                        errorType = ErrorType.NETWORK
                    )
                )
            }

            is SocketTimeoutException -> AppResult.Error(
                ErrorEntity(
                    code = null,
                    message = "TimeOut",
                    errorType = ErrorType.TIMEOUT
                )
            )

            is IOException -> AppResult.Error(
                ErrorEntity(
                    code = null,
                    message = "IO Error",
                    errorType = ErrorType.IO
                )
            )

            else -> AppResult.Error(
                ErrorEntity(
                    code = null,
                    message = "Unknown Error",
                    errorType = ErrorType.UNKNOWN
                )
            )
        }
    )
}.flowOn(defaultDispatcher ?: Dispatchers.Main)
