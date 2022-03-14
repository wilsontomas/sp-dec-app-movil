package Data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.OnConflictStrategy
@Dao
interface task_table_dao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertTask(task:task_table)

    @Query("SELECT * FROM task_table WHERE user_id =:userId")
    fun getAllTask(userId:String):LiveData<List<task_table>>

    @Query("SELECT * FROM task_table WHERE id =:idTask")
    fun getTaskById(idTask:Int):LiveData<task_table>

    @Query("UPDATE task_table SET name=:nombre, state=:estado WHERE id=:idTask")
    fun updateTask(idTask:Int,nombre:String, estado:Int)

    @Query("DELETE FROM task_table WHERE id =:idTask")
    fun deleteTask(idTask:Int)
}