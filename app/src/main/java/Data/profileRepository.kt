package Data

import androidx.lifecycle.LiveData

class profileRepository(private val profileDao:profile_table_dao) {
    suspend fun insertProfile(profile:profile_table){
        profileDao.insertProfile(profile);
    }

    suspend fun getUserById(idUser:String): LiveData<profile_table> {
        return profileDao.getUserById(idUser);
    }

    suspend fun updateUser(idUser:String,name:String, lName:String,userSex:String){
        profileDao.updateUser(idUser,name,lName,userSex);
    }
}
