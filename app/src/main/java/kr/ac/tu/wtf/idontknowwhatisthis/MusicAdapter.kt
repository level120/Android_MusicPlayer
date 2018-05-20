package kr.ac.tu.wtf.idontknowwhatisthis

import android.app.Activity

import android.content.Context
import android.graphics.BitmapFactory
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.bumptech.glide.Glide

private val options = BitmapFactory.Options()

data class MusicAdapter(var activity: Activity, var list: List<MusicType>) : BaseAdapter() {
    private val inflater = activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    override fun getCount(): Int {
        return list.size
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItem(position: Int): Any {
        return position
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var convertViewThis = convertView
        if (convertViewThis == null) {
            val layoutParams = LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
            convertViewThis = inflater.inflate(R.layout.itemlist_item, parent, false)
            convertViewThis.layoutParams = layoutParams
        }

        val imageView = convertViewThis?.findViewById(R.id.album) as ImageView
        Glide.with(activity)
                .load(Uri.parse("content://media/external/audio/albumart/${list[position].albumId.toInt()}"))
                .override(48, 48)
                .placeholder(R.mipmap.ic_launcher)
                .error(R.drawable.ic_all_inclusive_black_24dp)
                .into(imageView)


        val title = convertViewThis?.findViewById(R.id.title) as TextView
        val artist = convertViewThis?.findViewById(R.id.artist) as TextView

        title.text = list[position].title
        artist.text = list[position].artist

        return convertViewThis
    }

}