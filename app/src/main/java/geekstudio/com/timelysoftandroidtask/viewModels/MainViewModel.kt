package geekstudio.com.timelysoftandroidtask.viewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import geekstudio.com.timelysoftandroidtask.utils.Consts
import geekstudio.com.timelysoftandroidtask.utils.NetworkAsync
import org.json.JSONObject

class MainViewModel : ViewModel(){
    val wordList: MutableList<String> = mutableListOf()
    val translatedWordList: MutableList<String> = mutableListOf()
    val translatedWordLiveData: MutableLiveData<String> = MutableLiveData()

    fun makeGetRequest(word: String){
        val request = NetworkAsync(Consts.URL.plus(word)).execute()
        request.get()?.let {
            if (it.isEmpty()){

            }else{
                val translatedWord = JSONObject(it).getString("translation")
                translatedWordLiveData.value = translatedWord
            }
        }
    }
}