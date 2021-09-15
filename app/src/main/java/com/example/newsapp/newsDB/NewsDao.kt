package com.example.newsapp.newsDB

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface NewsDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    public suspend fun insertNewsItem(newsItem: NewsItem):Long
    @Query("select * from newsitem order by pubDate DESC limit :limit")
    public suspend fun getNewsItems(limit:Int):List<NewsItem>
    @Query("select * from newsitem where link=:link")
    public suspend fun getNewsItems(link:String?):List<NewsItem>

}