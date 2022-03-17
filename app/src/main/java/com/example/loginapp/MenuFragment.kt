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
    private lateinit var lista:List<task_table>;
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

        viewModel.getAllTasks(firebaseAuth.currentUser?.uid!!).observe(viewLifecycleOwner, Observer {
            if(!it.isNullOrEmpty()){
                updateUi(it);
            }else{
                Toast.makeText(view1.context,"No hay tareas para mostrar",Toast.LENGTH_SHORT).show();
            }
        })


        //viewModel.getProfile(firebaseAuth.currentUser?.uid!!);
     /*   viewModel.selectedProfile.observe(viewLifecycleOwner, Observer {
            Toast.makeText(view.context,it.name,Toast.LENGTH_LONG).show();
        })*/
        viewModel.getProfileData(firebaseAuth.currentUser?.uid!!).observe(viewLifecycleOwner,
            Observer {
                if(it !=null){
                    viewModel.selectedProfile =it;
                    //Toast.makeText(view.context,it.name,Toast.LENGTH_LONG).show();
                   // Toast.makeText(view.context,viewModel.selectedProfile.lastName,Toast.LENGTH_LONG).show();
                }

            })

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

    private fun updateUi(listado:List<task_table>){
        if(!listado.isNullOrEmpty()){
        lista=listado
        val adapter = TaskAdapter(lista);
        adapter.setOnItemClickListener(object :TaskAdapter.onItemClickListener{
            override fun itemClick(id: Number) {

                viewModel.taskId = id;
                Navigation.findNavController(view1).navigate(R.id.action_menuFragment_to_taskDetailFragment);
                //Toast.makeText(view1.context,id.toString(),Toast.LENGTH_LONG).show();
            }

        });
        binding.taskRecycler.adapter = adapter;
            binding.taskRecycler.layoutManager = LinearLayoutManager(view1.context);
        }

    }

}