package Data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName="task_table")
data class task_table(
    @PrimaryKey(autoGenerate = true)
    val id:Int,
    val user_id:String,
    val name:String,
    val state:Int
) {
}