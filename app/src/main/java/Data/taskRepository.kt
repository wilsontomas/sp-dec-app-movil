package Data

import androidx.lifecycle.LiveData

class taskRepository(private val taskDao:task_table_dao) {

    //val getAllTask:LiveData<List<task_table>> = taskDao.getAllTask(userId);

    suspend fun getAllTask(userId:String):LiveData<List<task_table>>{
        return taskDao.getAllTask(userId);
    }
    suspend fun insertTask(taskTable: task_table){
        taskDao.insertTask(taskTable);
    }

    suspend fun getTaskById(taskId:Int):LiveData<task_table>{
       return taskDao.getTaskById(taskId);
    }
    suspend fun updateTask(Id:Int, name:String, estado:Int){
        taskDao.updateTask(Id,name,estado);
    }

    suspend fun deleteTask(Id:Int){
        taskDao.deleteTask(Id);
    }

}