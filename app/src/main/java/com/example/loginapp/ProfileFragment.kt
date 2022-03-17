package com.example.loginapp

import Data.profile_table
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import com.example.loginapp.databinding.FragmentMenuBinding
import com.example.loginapp.databinding.FragmentProfileBinding
import services.tasksViewModel

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ProfileFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ProfileFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var profileId:profile_table;
    private  var _binding: FragmentProfileBinding? =null;
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
        super.onCreateView(inflater, container, savedInstanceState)
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        val view = binding.root
        view1 = view;
        viewModel = ViewModelProvider(requireActivity())[tasksViewModel::class.java];
       /* viewModel.selectedProfile.observe(viewLifecycleOwner, Observer {
            if(it !=null){
                binding.profileNombre.setText(profileId.name);
                binding.profileApellido.setText(profileId.lastName);
                Toast.makeText(view.context,profileId.userId,Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(view.context,"Errro al cargar usuario",Toast.LENGTH_SHORT).show();
            }
        })*/
        binding.profileNombre.setText(viewModel.selectedProfile.name);
        binding.profileApellido.setText(viewModel.selectedProfile.lastName);
        if(viewModel.selectedProfile.sex=="hombre"){
            binding.profileRadioSexo1.isChecked =true;
        }else{
            binding.profileRadioSexo2.isChecked =true;
        }
        profileId = viewModel.selectedProfile;
    binding.editActionProfile.setOnClickListener {
        editarProfile();
        Toast.makeText(view.context,"Se actualizo el perfil",Toast.LENGTH_SHORT).show();
       // Navigation.findNavController(view).navigate(R.id.action_profileFragment_to_menuFragment);
    }
    binding.backProfileBtn.setOnClickListener {
        Navigation.findNavController(view).navigate(R.id.action_profileFragment_to_menuFragment);
    }
        // Inflate the layout for this fragment
        return view;
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
         * @return A new instance of fragment ProfileFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ProfileFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    fun editarProfile(){
        if(binding.profileNombre.text.isNullOrEmpty() || binding.profileApellido.text.isNullOrEmpty()){
            Toast.makeText(view1.context,"Debe llenar los datos del usuario",Toast.LENGTH_SHORT).show()
        }else{
            var sexo:String;
            if(binding.profileRadioSexo1.isChecked){
                sexo="hombre";
            }else{
                sexo="mujer";
            }
            viewModel.updateProfile(profileId.userId,binding.profileNombre.text.toString(),binding.profileApellido.text.toString(),sexo);
        }
    }
}