package kr.ac.tu.wtf.idontknowwhatisthis

import java.io.Serializable

data class MusicType(
        var id:String,
        var albumId:String,
        var title:String,
        var artist:String
): Serializable {
    override fun toString():String =
            "Music id : $id, albumId : $albumId, title : $title, artist : $artist"
}