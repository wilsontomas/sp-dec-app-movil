package Data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [task_table::class,profile_table::class], version = 1, exportSchema = false)
abstract class taskDatabase:RoomDatabase() {

    abstract  fun taskDao():task_table_dao;
    abstract  fun profileDao():profile_table_dao;

    companion object{
        @Volatile
        private var INSTANCE:taskDatabase?=null;

        fun getDatabase(context:Context):taskDatabase{
            val tempInstance = INSTANCE;
            if(tempInstance != null){
                return tempInstance;
            }
            synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    taskDatabase::class.java,
                    "task_database"
                ).build();
                INSTANCE=instance;
                return instance;
            }
        }

    }
}