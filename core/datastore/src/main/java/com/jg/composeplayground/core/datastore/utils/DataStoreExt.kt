
package com.jg.composeplayground.core.datastore.utils

import android.util.Log
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import java.io.IOException

/**
 * 빈 값을 반환하는 제네릭 함수
 */
@Suppress("UNCHECKED_CAST")
private inline fun <reified T> getEmptyValue(): T {
    // 기본값이 필요한 타입에 따라 적절한 빈 값을 반환
    return when (T::class) {
        String::class -> "" as T
        else -> null as T
    }
}

/**
 * DataStore Flow 확장 함수
 * 예외 처리와 로깅을 추가합니다.
 */
fun <T> Flow<T>.asDataStoreFlow(tag: String, defaultValue: T? = null): Flow<T> = this.catch { exception ->
    if (exception is IOException) {
        Log.e("DataStore-$tag", "Error reading preferences: ${exception.message}", exception)
        // 기본값이 제공되면 사용하고, 아니면 null 반환 (타입에 맞게 캐스팅)
        emit(defaultValue ?: (null as T))
    } else {
        throw exception
    }
}