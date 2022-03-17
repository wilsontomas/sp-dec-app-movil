package services

import Data.*
import android.app.Application
import android.content.Context
import android.widget.Toast
import androidx.lifecycle.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import model.taskModel
class tasksViewModel(aplication:Application): AndroidViewModel(aplication) {

/*  private var taskSelected=MutableLiveData<Int>();
   private fun setTaskSelected(Id:Int){
       this.taskSelected.value = Id;
   }

 private val tasks: MutableLiveData<List<taskModel>> by lazy {
       MutableLiveData<List<taskModel>>().also {
           loadTasks()
       }
   }

   fun getUsers(): LiveData<List<taskModel>> {
       return tasks
   }

   private fun loadTasks() {

       // Do an asynchronous operation to fetch users.
   }*/


   private val repositoryTask:task_table_dao;
   private val repositoryProfile:profile_table_dao;
    //private val taskList=MutableLiveData<List<task_table?>>();
    lateinit var taskId:Number;
     lateinit var selectedTask:task_table;
    lateinit var tasklists:List<task_table>;
   // var selectedtask:MutableLiveData<task_table?> = selectedTask

     lateinit var selectedProfile:profile_table;

   init{
       val taskDao = taskDatabase.getDatabase(aplication).taskDao();
       repositoryTask = taskDao;
       val taskProfile = taskDatabase.getDatabase(aplication).profileDao();
       repositoryProfile = taskProfile;
   }
//profile
   fun addProfile(profile:profile_table){
      viewModelScope.launch(Dispatchers.IO) {
          repositoryProfile.insertProfile(profile);
      }
   }
/*
   fun getProfile(id:String){
       viewModelScope.launch(Dispatchers.IO) {
           selectedProfile.postValue( repositoryProfile.getUserById(id).value);

          // selectedprofile.postValue(selectedProfile.value);
       }
   }*/
    fun getProfileData(id:String):LiveData<profile_table>{
        return repositoryProfile.getUserById(id);
    }
   fun updateProfile(idUser:String,name:String, lName:String,userSex:String){
       viewModelScope.launch(Dispatchers.IO) {
           repositoryProfile.updateUser(idUser,name,lName,userSex);
       }
   }
   //end profile
   //tasks
   fun addTask(taskTable: task_table){
       viewModelScope.launch(Dispatchers.IO) {
           repositoryTask.insertTask(taskTable);
       }
   }
   fun getAllTasks(userId:String):LiveData<List<task_table>>{
          return repositoryTask.getAllTask(userId);
   }

   fun updateTask(Id:Int, name:String, estado:String){
       viewModelScope.launch(Dispatchers.IO) {
           repositoryTask.updateTask(Id,name,estado);
       }
   }
   fun deleteTask(Id:Number){
       viewModelScope.launch(Dispatchers.IO) {
           repositoryTask.deleteTask(Id.toInt());
       }
   }
   //end tasks
}