package com.example.loginapp

import android.os.Bundle
import android.telephony.SmsManager
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.navigation.Navigation
import com.example.loginapp.databinding.FragmentForgotBinding
import com.example.loginapp.databinding.FragmentLoginBinding
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.FirebaseDatabase
import model.UserData
import java.util.regex.Pattern

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [forgotFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class forgotFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var _binding:FragmentForgotBinding?=null;
    private val binding get() = _binding!!;
    private lateinit var login:Button;
    private lateinit var register:Button;
    private lateinit var recover:Button;
    private lateinit var firebaseAuth:FirebaseAuth;
    private lateinit var firebaseDatabase:FirebaseDatabase;
    private lateinit var view1:View;
    private lateinit var username:String;

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
        _binding = FragmentForgotBinding.inflate(inflater,container,false);
        val view = binding.root;

        view1=view;
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        binding.forgotLoginBtn.setOnClickListener {
            navigateMethod(view,R.id.action_forgotFragment_to_loginFragment)
        }

        binding.forgotRegisterBtn.setOnClickListener {
            navigateMethod(view,R.id.action_forgotFragment_to_registerFragment)
        }

        binding.forgotBtnAction2.setOnClickListener {
            sendEmail(view);
        }


        // Inflate the layout for this fragment


        return view;
    }
    override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.

        if(firebaseAuth.currentUser != null){
            navigateMethod(view1,R.id.action_forgotFragment_to_menuFragment);
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
         * @return A new instance of fragment forgotFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            forgotFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    private fun navigateMethod(view:View,action:Int){
        Navigation.findNavController(view).navigate(action);

    }
    private fun message(msg:String){
        Toast.makeText(activity,msg,Toast.LENGTH_LONG).show();

    }
    private fun validateEmail(email:String):Boolean{
        val pattern:Pattern = Patterns.EMAIL_ADDRESS;
        return pattern.matcher(email).matches();
    }
    private fun validateUsername(view: View):Boolean{
        val userNameValidate = view.findViewById<EditText>(R.id.forgotUserName).text.toString().trim();
        if(userNameValidate.isEmpty()){
            message("Debe agregar el correo del usuario");

            return false;
        }
        if(!validateEmail(userNameValidate)){
            message("Ingrese un correo con formato valido");

            return false;
        }
        username=userNameValidate;
        return true;
    }



    private fun sendEmail(view: View){
        if(validateUsername(view)){
     firebaseAuth.sendPasswordResetEmail(username).addOnSuccessListener(OnSuccessListener{
        message("Se ha enviado un correo de recuperacion de contraseña, revise su correo");
         navigateMethod(view,R.id.action_forgotFragment_to_loginFragment);
    }).addOnFailureListener(OnFailureListener {
        data->message(data.toString())
    })
        }
           // message("Algo fallo con el correo");
    }

}