package Data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
@Dao
interface profile_table_dao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertProfile(profile:profile_table)
/*
    @Query("SELECT * FROM task_table WHERE user_id =:userId")
    fun getAllTask(userId:String): LiveData<List<task_table>>*/

    @Query("SELECT * FROM profile_table WHERE userId =:idUser")
    fun getUserById(idUser:String): LiveData<profile_table>

    @Query("UPDATE profile_table SET name=:nombre, lastName=:lName, sex=:userSex WHERE userId=:idUser")
    fun updateUser(idUser:String,nombre:String, lName:String,userSex:String)

   /* @Query("DELETE FROM profile_table WHERE userId =:idUser")
    fun deleteUser(idUser:String)*/
}