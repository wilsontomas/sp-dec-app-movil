package services

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import model.taskModel
class tasksViewModel: ViewModel() {
   private var taskSelected=MutableLiveData<Int>();

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
    }
    private fun setTaskSelected(Id:Int){
        this.taskSelected.value = Id;
    }
}