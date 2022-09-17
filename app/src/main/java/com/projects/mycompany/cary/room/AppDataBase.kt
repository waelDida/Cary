package com.projects.mycompany.cary.room

import android.content.Context
import androidx.room.*
import com.projects.mycompany.cary.converter.Converter
import com.projects.mycompany.cary.models.CareGiver
import com.projects.mycompany.cary.models.CareSeeker

@Database(entities = [CareSeeker::class,CareGiver::class],version = 1, exportSchema = false)
@TypeConverters(Converter::class)
abstract class AppDataBase : RoomDatabase(){

    companion object {
        @Volatile
        private var INSTANCE: AppDataBase? = null

        fun getInstance(context: Context): AppDataBase {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        AppDataBase::class.java,
                        "cary_data_base"
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
    abstract val seekerDao: SeekerDao
    abstract val giverDao: GiverDao
}
