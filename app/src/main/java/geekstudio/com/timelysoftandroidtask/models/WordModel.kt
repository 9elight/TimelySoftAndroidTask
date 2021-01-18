package geekstudio.com.timelysoftandroidtask.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class WordModel(
    var originalWord: String,
    var translation: String
) : Parcelable