package com.jg.composeplayground.common.common.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavType
import androidx.navigation.navArgument
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

/**
 * Navigation JSON 유틸리티
 *
 * 이 유틸리티는 Navigation Compose에서 복잡한 객체를 JSON 직렬화/역직렬화를 통해
 * 전달하는 데 필요한 함수들을 제공합니다.
 */

// JSON 인코더/디코더 인스턴스
val json = Json {
    ignoreUnknownKeys = true
    isLenient = true
}

/**
 * 문자열을 URL 인코딩하는 확장 함수
 */
fun String.encodeUrlParam(): String = java.net.URLEncoder.encode(this, "UTF-8")

/**
 * 문자열을 URL 디코딩하는 확장 함수
 */
@RequiresApi(Build.VERSION_CODES.O)
fun String.decodeUrlParam(): String = java.net.URLDecoder.decode(this, "UTF-8")

/**
 * 제네릭 객체를 JSON으로 직렬화하여 전달하는 확장 함수
 * @param baseRoute 기본 라우트 경로
 * @param paramName 파라미터 이름 (기본값: "json")
 * @return 완전한 네비게이션 경로
 */
inline fun <reified T> T.toJsonRoute(baseRoute: String, paramName: String = "json"): String {
    val jsonString = json.encodeToString(this)
    val encodedJson = jsonString.encodeUrlParam()
    return "$baseRoute?$paramName=$encodedJson"
}

/**
 * 경로에 JSON 파라미터 플레이스홀더를 추가하는 함수
 * @param baseRoute 기본 라우트 경로
 * @param paramName 파라미터 이름 (기본값: "json")
 * @return 파라미터 플레이스홀더가 추가된 경로
 */
fun createJsonRoutePlaceholder(baseRoute: String, paramName: String = "json"): String {
    return "$baseRoute?$paramName={$paramName}"
}

/**
 * JSON 파라미터를 위한 navArgument 생성 함수
 * @param paramName 파라미터 이름 (기본값: "json")
 * @return NavArgument
 */
fun createJsonNavArgument(paramName: String = "json") = navArgument(paramName) {
    type = NavType.StringType
    nullable = true
}

/**
 * NavBackStackEntry에서 JSON 인자를 추출하고 역직렬화하는 확장 함수
 * @param paramName 파라미터 이름 (기본값: "json")
 * @param defaultValue 기본값 (null인 경우)
 * @return T 타입의 객체
 */
@RequiresApi(Build.VERSION_CODES.O)
inline fun <reified T> NavBackStackEntry.getJsonArg(
    paramName: String = "json",
    defaultValue: T? = null
): T? {
    val jsonString = this.arguments?.getString(paramName)?.decodeUrlParam() ?: return defaultValue
    return try {
        json.decodeFromString<T>(jsonString)
    } catch (e: Exception) {
        defaultValue
    }
}

/**
 * 파라미터를 쿼리 문자열로 변환하는 함수
 * @param params 키-값 쌍의 맵
 * @return 쿼리 문자열 (예: "key1=value1&key2=value2")
 */
fun createQueryString(params: Map<String, Any?>): String {
    return params.entries.mapNotNull { (key, value) ->
        when (value) {
            null -> null
            is String -> "$key=${value.encodeUrlParam()}"
            else -> "$key=$value"
        }
    }.joinToString("&")
}

/**
 * 경로와 쿼리 문자열을 조합하여 완전한 경로를 생성하는 함수
 * @param baseRoute 기본 경로
 * @param params 쿼리 파라미터
 * @return 완전한 경로 (예: "baseRoute?key1=value1&key2=value2")
 */
fun createRouteWithParams(baseRoute: String, params: Map<String, Any?>): String {
    val queryString = createQueryString(params)
    return if (queryString.isNotEmpty()) "$baseRoute?$queryString" else baseRoute
}

/**
 * 경로에 필요한 파라미터 플레이스홀더를 생성하는 함수
 * @param baseRoute 기본 경로
 * @param paramNames 파라미터 이름 목록
 * @return 파라미터가 포함된 경로 (예: "baseRoute?param1={param1}&param2={param2}")
 */
fun createRouteWithParamPlaceholders(baseRoute: String, paramNames: List<String>): String {
    val placeholders = paramNames.joinToString("&") { "$it={$it}" }
    return if (placeholders.isNotEmpty()) "$baseRoute?$placeholders" else baseRoute
}

/**
 * Int 타입 네비게이션 인자를 생성하는 함수
 * @param name 인자 이름
 * @param defaultValue 기본값 (기본값은 0)
 * @return NavArgument
 */
fun createIntNavArgument(name: String, defaultValue: Int = 0) = navArgument(name) {
    type = NavType.IntType
    this.defaultValue = defaultValue
}

/**
 * String 타입 네비게이션 인자를 생성하는 함수
 * @param name 인자 이름
 * @param defaultValue 기본값 (기본값은 빈 문자열)
 * @return NavArgument
 */
fun createStringNavArgument(name: String, defaultValue: String = "") = navArgument(name) {
    type = NavType.StringType
    this.defaultValue = defaultValue
}

/**
 * Boolean 타입 네비게이션 인자를 생성하는 함수
 * @param name 인자 이름
 * @param defaultValue 기본값 (기본값은 false)
 * @return NavArgument
 */
fun createBooleanNavArgument(name: String, defaultValue: Boolean = false) = navArgument(name) {
    type = NavType.BoolType
    this.defaultValue = defaultValue
}

/**
 * NavBackStackEntry에서 Int 값을 가져오는 확장 함수
 * @param key 인자 키
 * @param defaultValue 기본값 (기본값은 0)
 * @return Int 값
 */
fun NavBackStackEntry.getIntArg(key: String, defaultValue: Int = 0): Int {
    return this.arguments?.getInt(key, defaultValue) ?: defaultValue
}

/**
 * NavBackStackEntry에서 String 값을 가져오는 확장 함수
 * @param key 인자 키
 * @param defaultValue 기본값 (기본값은 빈 문자열)
 * @return String 값
 */
fun NavBackStackEntry.getStringArg(key: String, defaultValue: String = ""): String {
    return this.arguments?.getString(key) ?: defaultValue
}

/**
 * NavBackStackEntry에서 URL 디코딩된 String 값을 가져오는 확장 함수
 * @param key 인자 키
 * @param defaultValue 기본값 (기본값은 빈 문자열)
 * @return URL 디코딩된 String 값
 */
@RequiresApi(Build.VERSION_CODES.O)
fun NavBackStackEntry.getDecodedStringArg(key: String, defaultValue: String = ""): String {
    return getStringArg(key, defaultValue).decodeUrlParam()
}

/**
 * NavBackStackEntry에서 Boolean 값을 가져오는 확장 함수
 * @param key 인자 키
 * @param defaultValue 기본값 (기본값은 false)
 * @return Boolean 값
 */
fun NavBackStackEntry.getBooleanArg(key: String, defaultValue: Boolean = false): Boolean {
    return this.arguments?.getBoolean(key, defaultValue) ?: defaultValue
}

/**
 * NavBackStackEntry에서 enum 값을 가져오는 확장 함수
 * @param key 인자 키
 * @param defaultOrdinal 기본 ordinal 값 (기본값은 0)
 * @return E 타입의 enum 값
 */
inline fun <reified E : Enum<E>> NavBackStackEntry.getEnumArg(
    key: String,
    defaultOrdinal: Int = 0
): E {
    val ordinal = getIntArg(key, defaultOrdinal)
    return try {
        enumValues<E>()[ordinal]
    } catch (e: Exception) {
        enumValues<E>()[defaultOrdinal]
    }
} 