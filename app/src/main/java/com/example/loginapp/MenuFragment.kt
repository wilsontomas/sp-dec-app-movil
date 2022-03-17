package com.example.loginapp

import Data.task_table
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.loginapp.databinding.FragmentMenuBinding
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.FirebaseDatabase
import model.UserData
import model.taskModel
import services.TaskAdapter
import services.tasksViewModel

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [MenuFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MenuFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    //private lateinit var firebaseDatabase: FirebaseDatabase;
    private  lateinit var firebaseAuth: FirebaseAuth;
    private  var _binding:FragmentMenuBinding? =null;
    private val binding get() = _binding!!;
    private lateinit var viewModel: tasksViewModel;
    private lateinit var lista:MutableList<taskModel>;
    //private lateinit var firebaseDatabase: FirebaseDatabase;
    private lateinit var view1:View;
    private lateinit var userData: UserData;




    //btn
    private lateinit var logOutBtn:Button;
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
        // Inflate the layout for this fragment
        firebaseAuth = FirebaseAuth.getInstance();
        _binding = FragmentMenuBinding.inflate(inflater, container, false)
        val view = binding.root
         view1 =view;
        binding.plus.setOnClickListener {
            Navigation.findNavController(view1).navigate(R.id.action_menuFragment_to_addTaskFragment);
        }
        binding.profile.setOnClickListener {
            Navigation.findNavController(view1).navigate(R.id.action_menuFragment_to_profileFragment);
        }

        viewModel = ViewModelProvider(requireActivity())[tasksViewModel::class.java];
        viewModel.getAllTasks(firebaseAuth.currentUser?.uid!!);
        viewModel.tasklists.observe(this, Observer {
            updateUi(it);
        });
       /* if(!viewModel.taskList.){
            val datos = viewModel.taskList.value;

            datos?.forEach { it->
                lista.add(taskModel(it.id,it.name,it.state))
            }
        }*/

       /* var lista = mutableListOf<taskModel>(
            taskModel(1,"wilson1","pendiente"),
            taskModel(2,"wilson2","cancelado"),
            taskModel(3,"wilson3","completado"),
            taskModel(4,"wilson4","pendiente"),
            taskModel(5,"wilson5","cancelado"),
            taskModel(6,"wilson6","completado"),
            taskModel(7,"wilson7","pendiente"),
            taskModel(8,"wilson8","cancelado"),
            taskModel(9,"wilson9","completado")
        )*/


        binding.taskRecycler.layoutManager = LinearLayoutManager(view.context);
        //initialization

        //firebaseDatabase = FirebaseDatabase.getInstance();

        binding.logOutBtnAction.setOnClickListener {
            logOut(view);
        }
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
         * @return A new instance of fragment MenuFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            MenuFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    private fun logOut(view: View){
        firebaseAuth.signOut();
        Navigation.findNavController(view).navigate(R.id.action_menuFragment_to_loginFragment);
    }
    private fun getSoloData(data:DataSnapshot,keyName:String):String{
        return keyName+": "+data.child(keyName).value.toString();
    }
    private fun setUsetData(data:DataSnapshot){
/*
        view1.findViewById<TextView>(R.id.menuNombre).setText(getSoloData(data,"nombre"));
        view1.findViewById<TextView>(R.id.menuApellido).setText(getSoloData(data,"apellido"));
        view1.findViewById<TextView>(R.id.menuTelefono).setText(getSoloData(data,"telefono"));
        view1.findViewById<TextView>(R.id.menuSexo).setText(getSoloData(data,"sexo"));
       */
    }
    private fun updateUi(listado:List<task_table?>){
        if(!listado.isNullOrEmpty()){


        listado.forEach{ls->
            lista.add(taskModel(ls?.id!!,ls?.name!!,ls?.state!!));}

        val adapter = TaskAdapter(lista);
        adapter.setOnItemClickListener(object :TaskAdapter.onItemClickListener{
            override fun itemClick(id: Number) {
                //Toast.makeText(view.context,"el id es: "+id,Toast.LENGTH_LONG).show();
                viewModel.getProfile(firebaseAuth.currentUser?.uid!!);
                Navigation.findNavController(view1).navigate(R.id.action_menuFragment_to_taskDetailFragment);

            }

        });
        binding.taskRecycler.adapter = adapter;
        }

    }

}