package geekstudio.com.timelysoftandroidtask.utils

import android.util.Log
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

object RequestHelper {
    const val GET : String = "GET"
    const val TAG : String = "RequestHandler"

    @Throws(IOException::class)
    fun getRequest(url: String) : String{
        val obj = URL(url)
        val con = obj.openConnection() as HttpURLConnection
        con.requestMethod = GET
        val responseCode = con.responseCode
        Log.d(TAG, "getRequest response code: $responseCode ")
        return if (responseCode == HttpURLConnection.HTTP_OK){
            val `in` =
                BufferedReader(InputStreamReader(con.inputStream))
            var inputLine: String?
            val response = StringBuffer()
            while (`in`.readLine().also { inputLine = it } != null) {
                response.append(inputLine)
            }
            `in`.close()
            response.toString()
        }else{
            ""
        }
    }
}