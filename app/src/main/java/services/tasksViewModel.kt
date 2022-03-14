package services

import Data.*
import android.app.Application
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
   private lateinit var taskList:MutableLiveData<List<task_table>>;
   private lateinit var selectedTask:MutableLiveData<task_table>;
   private lateinit var selectedProfile:MutableLiveData<profile_table>;
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

   fun getProfile(id:String){
       viewModelScope.launch(Dispatchers.IO) {
           selectedProfile.value = repositoryProfile.getUserById(id).value;
       }
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
   fun getAllTasks(userId:String){
       viewModelScope.launch(Dispatchers.IO) {
           taskList.value = repositoryTask.getAllTask(userId).value;
       }

   }
   fun getTaskById(taskId:Int){
       viewModelScope.launch(Dispatchers.IO) {
          selectedTask.value= repositoryTask.getTaskById(taskId).value;
       }

   }
   fun updateTask(Id:Int, name:String, estado:Int){
       viewModelScope.launch(Dispatchers.IO) {
           repositoryTask.updateTask(Id,name,estado);
       }
   }
   fun deleteTask(Id:Int){
       viewModelScope.launch(Dispatchers.IO) {
           repositoryTask.deleteTask(Id);
       }
   }
   //end tasks
}