package com.example.newsapp

import android.content.Context
import android.util.Log
import androidx.lifecycle.Observer
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.newsapp.newsDB.NewsDao
import com.example.newsapp.newsDB.NewsItem
import com.example.newsapp.newsapi.NewsApi
import com.example.newsapp.newsapi.NewsResponse
import com.example.newsapp.utility.NotificationUtility
import com.supermario.newsappservice.newsdb.NewsDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Exception

class MyTask(context: Context,workerParameters: WorkerParameters) :CoroutineWorker(context,workerParameters){
    lateinit var newsApi: NewsApi
    lateinit var newsDao: NewsDao
    lateinit var notificationUtility: NotificationUtility
    var palestinianInserted=0
    var worldInserted=0
    var islamicInserted=0
    var entertainmentInserted=0
    companion object{
        public var isRunning=false
        public var newsItemArray=ArrayList<NewsItem>()
        var itemNumber=0

    }
    init {
        newsApi= NewsApi("http://fetchrss.com/rss/")

    }
    override suspend fun doWork(): Result {
        var result= withContext(Dispatchers.IO) {
            try {
                newsDao = NewsDatabase.getDatabase(applicationContext)!!.getNewsDao()
                notificationUtility = NotificationUtility(applicationContext)
                longTask()
                worldNews()
                islamicNews()
                entertainmentNews()
            } catch (e: Exception) {
                return@withContext Result.failure()
            }
            return@withContext Result.success()
        }
        return result
    }
    public suspend fun longTask(){

        withContext(Dispatchers.Main) {
            newsApi.getNews().observeForever(Observer {
                if (it.status == NewsResponse.Result.SUCCESS) {
                     var newsItems = it.response?.channel?.items
                    newsItems?.forEach {
                        Log.d("ttt", it.title!!)
                        GlobalScope.launch(Dispatchers.IO){
                            var news=newsDao.getNewsItems(it.link.toString())
                            if(news.isNullOrEmpty()){
                                if(palestinianInserted==0){
                                    var newsItem= NewsItem.creatFromRSSItem(it)
                                    newsItemArray.add(newsItem)
                                }else{ var newsItem= NewsItem.creatFromRSSItem(it)
                                    newsItemArray.add(newsItem)
                                    var inserted= newsDao.insertNewsItem(newsItem)

                                    if(inserted !=-1L){

                                        Log.d("ttt","inserted to db")

                                        notificationUtility.sendNotification(inserted.toInt(),"أخبار فلسطينية",newsItem.title.toString(),newsItem.link.toString())
                                        // GlobalScope.launch(Dispatchers.Main) {  PalestinianNewsFragment.adapter.notifyDataSetChanged() }
                                        itemNumber=+1
                                    }
                                }

//                                       NewsItem().apply {
//                                       this.link=it.link
//                                       this.title=it.title
//                                       this.pubDate=it.pubDate
//                                       this.category=1
//                                       this.imageUrl=it.mediaContent?.url
//
//                                   }
//




                            }
                        }
                    }


                } else {
                    Log.d("ttt", "Error")
                }
                palestinianInserted=1
            })
        }




    }
    public suspend fun worldNews(){
        withContext(Dispatchers.Main) {
            newsApi.getWorldNews().observeForever(Observer {
                if (it.status == NewsResponse.Result.SUCCESS) {
                    var newsItems = it.response?.channel?.items
                    newsItems?.forEach {
                        GlobalScope.launch(Dispatchers.IO){
                            var news=newsDao.getNewsItems(it.link.toString())
                            if(news.isNullOrEmpty()){
                                if(worldInserted==0){
                                    var newsItem= NewsItem.creatFromRSSItem(it)
                                    newsItemArray.add(newsItem)
                                }else{ var newsItem= NewsItem.creatFromRSSItem(it)
                                    newsItemArray.add(newsItem)
                                    var inserted= newsDao.insertNewsItem(newsItem)

                                    if(inserted !=-1L){

                                        Log.d("ttt","inserted to db")

                                        notificationUtility.sendNotification(inserted.toInt(),"أخبار عالمية",newsItem.title.toString(),newsItem.link.toString())
                                        // GlobalScope.launch(Dispatchers.Main) {  PalestinianNewsFragment.adapter.notifyDataSetChanged() }
                                        itemNumber=+1
                                    }
                                }
                            }
                        }
                    }

                } else {
                    Log.d("ttt", "Error")
                }
                worldInserted=1
            })

        }

    }

    public suspend fun islamicNews(){

        withContext(Dispatchers.Main) {
            newsApi.getIslamicNews().observeForever(Observer {
                if (it.status == NewsResponse.Result.SUCCESS) {
                    var newsItems = it.response?.channel?.items
                    newsItems?.forEach {
                        GlobalScope.launch(Dispatchers.IO){
                            var news=newsDao.getNewsItems(it.link.toString())
                            if(news.isNullOrEmpty()){
                                if(islamicInserted==0){
                                    var newsItem= NewsItem.creatFromRSSItem(it)
                                    newsItemArray.add(newsItem)
                                }else{ var newsItem= NewsItem.creatFromRSSItem(it)
                                    newsItemArray.add(newsItem)
                                    var inserted= newsDao.insertNewsItem(newsItem)

                                    if(inserted !=-1L){

                                        Log.d("ttt","inserted to db")

                                        notificationUtility.sendNotification(inserted.toInt(),"أخبار اسلامية",newsItem.title.toString(),newsItem.link.toString())
                                        // GlobalScope.launch(Dispatchers.Main) {  PalestinianNewsFragment.adapter.notifyDataSetChanged() }
                                        itemNumber=+1
                                    }
                                }
                            }
                        }
                    }

                } else {
                    Log.d("ttt", "Error")
                }
                islamicInserted=1
            })

        }
    }
    public suspend fun entertainmentNews(){
        withContext(Dispatchers.Main) {
            newsApi.getEntertainmentNews().observeForever(Observer {
                if (it.status == NewsResponse.Result.SUCCESS) {
                    var newsItems = it.response?.channel?.items
                    newsItems?.forEach {
                        GlobalScope.launch(Dispatchers.IO){
                            var news=newsDao.getNewsItems(it.link.toString())
                            if(news.isNullOrEmpty()){
                                if(entertainmentInserted==0){
                                    var newsItem= NewsItem.creatFromRSSItem(it)
                                    newsItemArray.add(newsItem)
                                }else{ var newsItem= NewsItem.creatFromRSSItem(it)
                                    newsItemArray.add(newsItem)
                                    var inserted= newsDao.insertNewsItem(newsItem)

                                    if(inserted !=-1L){

                                        Log.d("ttt","inserted to db")

                                        notificationUtility.sendNotification(inserted.toInt(),"أخبار ترفيهية",newsItem.title.toString(),newsItem.link.toString())
                                        // GlobalScope.launch(Dispatchers.Main) {  PalestinianNewsFragment.adapter.notifyDataSetChanged() }
                                        itemNumber=+1
                                    }
                                }
                            }
                        }
                    }

                } else {
                    Log.d("ttt", "Error")
                }
                entertainmentInserted=1
            })

        }

    }



}