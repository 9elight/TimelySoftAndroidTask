package geekstudio.com.timelysoftandroidtask.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import geekstudio.com.timelysoftandroidtask.R
import geekstudio.com.timelysoftandroidtask.models.WordModel

class WordsAdapter(val onItemClick: (WordModel) -> Unit) :
    RecyclerView.Adapter<WordsAdapter.WordsViewHolder>() {
    private var wordsList: MutableList<WordModel> = mutableListOf()

    fun addWord(word: WordModel) {
        this.wordsList.add(word)
        notifyDataSetChanged()
    }

    fun deleteWord(word: WordModel) {
        this.wordsList.remove(word)
        notifyDataSetChanged()
    }

    fun updateList(list: MutableList<WordModel>) {
        this.wordsList = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WordsViewHolder {
        return WordsViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_word, parent, false)
        )
    }

    override fun onBindViewHolder(holder: WordsViewHolder, position: Int) {
        holder.onBind(wordsList[position].originalWord)
    }

    override fun getItemCount(): Int {
        return wordsList.size
    }


    inner class WordsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val wordTextView: TextView = itemView.findViewById(R.id.tv_word)
        fun onBind(word: String) {
            wordTextView.text = word
            itemView.setOnClickListener {
                onItemClick(wordsList[adapterPosition])
            }
        }

    }
}

