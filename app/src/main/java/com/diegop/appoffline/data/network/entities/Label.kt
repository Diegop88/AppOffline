package com.diegop.appoffline.data.network.entities

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
class Label {
    @PrimaryKey
    @SerializedName("id")
    var id: Int? = null
    @SerializedName("url")
    var url: String? = null
    @SerializedName("name")
    var name: String? = null
    @SerializedName("color")
    var color: String? = null
    @SerializedName("default")
    var _default: Boolean? = null
}
