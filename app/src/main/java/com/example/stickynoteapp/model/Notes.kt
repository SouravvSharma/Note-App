package com.example.stickynoteapp.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize


@Entity(tableName ="Notes")
@Parcelize
data class Notes (
    @PrimaryKey(autoGenerate = true)
    var id:Int? = null,
    var title:String,
    var subTitle:String,
    var note: String,
    var date: String
):Parcelable