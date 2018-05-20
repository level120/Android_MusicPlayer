package kr.ac.tu.wtf.idontknowwhatisthis

import android.Manifest.permission.READ_EXTERNAL_STORAGE
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.support.v4.app.ActivityCompat
import android.widget.ListView

/* http://blog.naver.com/PostView.nhn?blogId=tkddlf4209&logNo=220746210643&categoryNo=41&parentCategoryNo=0&viewDate=&currentPage=1&postListTopCurrentPage=1&from=postView */
class MainActivity : AppCompatActivity() {

    private var itemList: ListView? = null

    var musicList: ArrayList<MusicType> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        getMusicList()
        itemList = findViewById(R.id.itemList)

        itemList?.adapter = MusicAdapter(this, musicList)
        itemList?.setOnItemClickListener { _, _, position, _ ->
            val intent = Intent(this, MusicActivity::class.java)
            intent.putExtra("position", position)
            intent.putExtra("playlist", musicList)
            startActivity(intent)
        }
    }

    fun getMusicList() {

        if (Build.VERSION.SDK_INT > 22) {
            if (checkSelfPermission(READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, arrayOf(READ_EXTERNAL_STORAGE), 1)
            }
        }


        var projection = arrayOf(
                MediaStore.Audio.Media._ID,
                MediaStore.Audio.Media.ALBUM_ID,
                MediaStore.Audio.Media.TITLE,
                MediaStore.Audio.Media.ARTIST)

        var cursor = contentResolver.query(
                MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                projection,
                null,
                null,
                null)

        while (cursor.moveToNext()) {
            musicList.add(
                    MusicType(
                            cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media._ID)),
                            cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM_ID)),
                            cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.TITLE)),
                            cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST))
                    )
            )
        }

        cursor.close()
    }
}
