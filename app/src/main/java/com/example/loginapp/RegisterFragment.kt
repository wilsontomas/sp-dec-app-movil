package com.example.loginapp

import Data.profile_table
import android.os.Bundle
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.example.loginapp.databinding.FragmentRegisterBinding
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.squareup.picasso.Picasso
import model.UserData
import services.tasksViewModel
import java.util.*
import java.util.regex.Pattern

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [RegisterFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class RegisterFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var firebaseAuth: FirebaseAuth;
    private  var _binding: FragmentRegisterBinding? =null;
    private val binding get() = _binding!!;
    private lateinit var viewModel:tasksViewModel;
    //private lateinit var firebaseDatabase: FirebaseDatabase;
    private lateinit var view1:View;

    //fields
    private lateinit var usernamev:String;
    private lateinit var nombrev:String;
    private lateinit var apellidov:String;
    private lateinit var sexov:String;
    private lateinit var password:String;
    private lateinit var passwordConfirm:String;

    //end fields
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
        //methods
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        val view = binding.root
        view1 =view;

        firebaseAuth = FirebaseAuth.getInstance();
        //firebaseDatabase = FirebaseDatabase.getInstance();
        binding.backRegisterBtn.setOnClickListener {
            navigateMethod(view,R.id.action_registerFragment_to_loginFragment);
        }
       binding.CrearBtn.setOnClickListener {
           signUp(view);
       }
          //end methods
        viewModel = ViewModelProvider(requireActivity())[tasksViewModel::class.java];
     //   Picasso.get().load(R.drawable.registro1).into(binding.registroimg);
        return view;
    }
    override fun onStart() {
        super.onStart()
        val currentUser = firebaseAuth.currentUser;
        if(currentUser != null){
            navigateMethod(view1,R.id.action_registerFragment_to_menuFragment);
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
         * @return A new instance of fragment RegisterFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            RegisterFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
    private fun validateEmail(email:String):Boolean{
        val pattern: Pattern = Patterns.EMAIL_ADDRESS;
        return pattern.matcher(email).matches();
    }
    private fun navigateMethod(view:View, action:Int){
        Navigation.findNavController(view).navigate(action);
    }

    private fun getFieldsValue(view: View){
        usernamev = binding.userName.text.toString().trim();
        nombrev = binding.nombre.text.toString().trim();
        apellidov = binding.apellido.text.toString().trim();

        val  checked:Boolean = binding.hombre.isChecked;
        if(checked){
            sexov = "hombre";
        }else{
            sexov = "mujer";
        }

        password = binding.password.text.toString().trim();
        passwordConfirm = binding.passwordConfirm.text.toString().trim();

    }
    private fun validatePassword():Boolean{
        if(password != passwordConfirm){
            message("Las claves no coinciden");
            return false;
        }
        if(password.length<6 || passwordConfirm.length<6){
            message("Las claves deben tener al menos 6 caracteres");
            return false;
        }

        return true;
    }
    private fun validateFields(view: View):Boolean{
        getFieldsValue(view);
        if( nombrev.isEmpty() || apellidov.isEmpty()
             || usernamev.isEmpty()
            || sexov.isEmpty()
            ||  password.isEmpty() || passwordConfirm.isEmpty()

        ){
            message("Faltan campos por rellenar");
            return false;
        }
        if(!validateEmail(usernamev)){

            message("El correo debe tener un formato valido");
            return false;
        }

        return true;
    }
    private fun message(msn:String){
        Toast.makeText(activity,msn, Toast.LENGTH_SHORT).show();

    }
    private fun saveOtherInfo(userId:String){

        val userobjectinfo=object {
            var correo=usernamev;
            var nombre =nombrev;
            var apellido=apellidov;

            var sexo=sexov;
        }
        viewModel.addProfile(profile_table(0,userId,nombrev,apellidov,sexov));
 }
    private fun signUp(view:View){
        if(validateFields(view) && validatePassword()){
            val registerActivityObject = MainActivity();
            firebaseAuth.createUserWithEmailAndPassword(usernamev, password).addOnSuccessListener(
                OnSuccessListener{data->
                    val user=firebaseAuth.currentUser?.uid;
                    saveOtherInfo(user!!);
                    message("Usuario creado con exito");
                    navigateMethod(view1,R.id.action_registerFragment_to_menuFragment);
                }).addOnFailureListener(OnFailureListener {

                message("Error al crear o el usuario ya existe");
            });

        }
    }

}