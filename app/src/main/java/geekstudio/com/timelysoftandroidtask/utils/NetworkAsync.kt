package geekstudio.com.timelysoftandroidtask.utils

import android.os.AsyncTask
import android.util.Log

@Suppress("DEPRECATION")
class NetworkAsync(private val url: String) : AsyncTask<String?,String?,String?>(){

    override fun doInBackground(vararg params: String?): String? {
        return RequestHelper.getRequest(url)
    }

    override fun onPostExecute(result: String?) {
        super.onPostExecute(result)
        Log.e("TAG", "onPostExecute: $result", )
    }
}