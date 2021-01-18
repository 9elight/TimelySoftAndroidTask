package geekstudio.com.timelysoftandroidtask

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import geekstudio.com.timelysoftandroidtask.adapter.WordsAdapter
import geekstudio.com.timelysoftandroidtask.models.WordModel
import geekstudio.com.timelysoftandroidtask.utils.Consts
import geekstudio.com.timelysoftandroidtask.utils.NetworkAsync
import org.json.JSONObject

const val WORD_LIST = "wordlist"
const val TRANSLATED_WORD_LIST = "translatedwordlist"

class MainActivity : AppCompatActivity() {
    private val TAG = "MA"
    private lateinit var enterWord: EditText
    private lateinit var addWord: Button
    private lateinit var showMsg: Button
    private lateinit var wordRv: RecyclerView
    private lateinit var adapter: WordsAdapter
    private var wordModelList: MutableList<WordModel> = mutableListOf()
    private var wordList: MutableList<String> = mutableListOf()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initViews()
        setupRecyclerView()
        initListeners()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelableArray(WORD_LIST, wordModelList.toTypedArray())
        Log.e(TAG, "onSaveInstanceState: $wordList")
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        Log.e(TAG, "onRestoreInstanceState:")
        savedInstanceState.getParcelableArray(WORD_LIST)?.let { it ->
            wordModelList = it.map { it as WordModel }.toMutableList()
            adapter.updateList(wordModelList)
        }
        savedInstanceState.getStringArray(TRANSLATED_WORD_LIST)?.toMutableList()?.let {
        }
    }

    private fun initViews() {
        enterWord = findViewById(R.id.et_enter_word)
        addWord = findViewById(R.id.btn_add_word)
        showMsg = findViewById(R.id.btn_show_full_msg)
        wordRv = findViewById(R.id.rv_words_list)
    }

    private fun setupRecyclerView() {
        adapter = WordsAdapter(this::onItemClick)
        wordRv.adapter = adapter
    }

    private fun onItemClick(word: WordModel) {
        if (word.translation.isNotEmpty()) {
            showAlertDialog(word.translation)
        } else {
            adapter.deleteWord(word)
        }
    }

    @Suppress("DEPRECATION")
    private fun translateWord(word: String) {
        val request = NetworkAsync(Consts.URL.plus(word)).execute()
        request.get()?.let {
            if (it.isNotEmpty()) {
                val translation = JSONObject(it).getString("translation")
                val originalWord = JSONObject(it).getString("originalWord")
                val wordModel: WordModel =
                    WordModel(originalWord = originalWord, translation = translation)
                wordModelList.add(wordModel)
                adapter.addWord(wordModel)
            } else {
                adapter.addWord(WordModel(word, ""))
            }
        }
    }

    private fun initListeners() {
        addWord.setOnClickListener {
            val word = enterWord.text.toString()
            if (word.isNotEmpty()) {
                if (!wordList.contains(word)) wordList.add(word)
                translateWord(word)
            }

            enterWord.setText("")
        }
        showMsg.setOnClickListener {
            showAlertDialog(
                wordModelList.map { it.translation }.toString()
                    .replace("[", "")
                    .replace("]", "")
                    .replace(",", "")
            )
        }
    }

    private fun showAlertDialog(message: String) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle(getString(R.string.text_alert_title))
        builder.setMessage(message)
        builder.setPositiveButton(getString(R.string.text_alert_positive)) { dialog, _ ->
            dialog.dismiss()
        }
        builder.show()
    }
}