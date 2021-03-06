package razorpay.example.com.razorpay.helpers

import com.google.gson.Gson

/**
 * Created by Madhu on 24-Apr-2018.
 */
object JsonHelper {
    inline fun <reified T> jsonToKt(jsonString: String): T {
        val gson = Gson()
        return gson.fromJson(jsonString, T::class.java)
    }

    fun KtToJson(obj: Any): String {
        val gson = Gson()
        return gson.toJson(obj)
    }

    inline fun <reified T> jsonToKtList(jsonString: String): List<T> {
        val gson = Gson()
        return gson.fromJson<List<T>>(jsonString, T::class.java)
    }
}