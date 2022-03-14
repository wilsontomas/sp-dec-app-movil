package Data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName="profile_table")
data class profile_table(
    @PrimaryKey(autoGenerate = true)
    val id:Int,
    val userId:String,
    val name:String,
    val lastName:String,
    val sex:String
) {
}