package com.example.loginapp

import Data.task_table
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.example.loginapp.databinding.FragmentAddTaskBinding
import com.example.loginapp.databinding.FragmentProfileBinding
import com.google.firebase.auth.FirebaseAuth
import com.squareup.picasso.Picasso
import services.tasksViewModel

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [addTaskFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class addTaskFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private  lateinit var firebaseAuth: FirebaseAuth;
    private  var _binding: FragmentAddTaskBinding? =null;
    private val binding get() = _binding!!;
    private lateinit var view1:View;
    private lateinit var viewModel: tasksViewModel;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAddTaskBinding.inflate(inflater, container, false)
        val view = binding.root
        view1 = view;
        firebaseAuth = FirebaseAuth.getInstance();
        viewModel = ViewModelProvider(requireActivity())[tasksViewModel::class.java];
        // Inflate the layout for this fragment
        binding.addActionTask.setOnClickListener {
            addTarea();
        }
        binding.backTaskAddBtn.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_addTaskFragment_to_menuFragment);
        }
       // Picasso.get().load(R.drawable.taskicon).into(binding.taskImg);
        return view;
    }
    override fun onStart() {
        super.onStart()
        if(firebaseAuth.currentUser == null){
            Navigation.findNavController(view1).navigate(R.id.action_menuFragment_to_loginFragment);
        }
        //getUserData();
        //showTextValues(view1);
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null;
    }
    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment addTaskFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            addTaskFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
    fun addTarea(){
        if(binding.taskNombreDescAdd.text.isNullOrEmpty()){
            Toast.makeText(view1.context,"Debe llenar la descripcion", Toast.LENGTH_SHORT).show();
        }else{
            var estado="";
            if(binding.completadoRadioAdd.isChecked){
                estado = "completado";
            }else if(binding.canceladoRadioAdd.isChecked){
                estado = "cancelado";
            }else{
                estado = "pendiente";
            }
            viewModel.addTask(task_table(0,viewModel.selectedProfile.userId,binding.taskNombreDescAdd.text.toString(), estado));
            Toast.makeText(view1.context,"Tarea creada", Toast.LENGTH_SHORT).show();
            Navigation.findNavController(view1).navigate(R.id.action_addTaskFragment_to_menuFragment);
        }
    }


}