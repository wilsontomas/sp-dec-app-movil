package services

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.loginapp.R
import model.taskModel

class TaskAdapter(
    var tareas:List<taskModel>
): RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {

    inner class TaskViewHolder(itemView:View):RecyclerView.ViewHolder(itemView)


   // private lateinit var mListener:
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.task_item,parent,false);
        return TaskViewHolder(view);
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        holder.itemView.findViewById<TextView>(R.id.nombreItem).text = tareas[position].nombre;
        when(tareas[position].estado){
           "pendiente"->holder.itemView.findViewById<ImageView>(R.id.imgState).setImageResource(R.drawable.pendding);
            "completado"->holder.itemView.findViewById<ImageView>(R.id.imgState).setImageResource(R.drawable.complete);
            "cancelado"->holder.itemView.findViewById<ImageView>(R.id.imgState).setImageResource(R.drawable.cancel);
            else -> {
                print("Imagen no encontrada");
            }
        }


    }

    override fun getItemCount(): Int {
       return tareas.size;
    }
}