package com.example.loginapp

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.Navigation
import com.example.loginapp.databinding.FragmentLoginBinding
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import java.util.regex.Pattern

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


/**
 * A simple [Fragment] subclass.
 * Use the [LoginFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class LoginFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private  var _binding: FragmentLoginBinding? =null;
    private val binding get() = _binding!!;
    private lateinit var gmail:String;
    private lateinit var clave:String;
    private lateinit var loginBtn: Button;
    private lateinit var registerBtn: Button;
    private lateinit var forgotBtn: Button;
    private lateinit var firebaseAuth: FirebaseAuth;
    private lateinit var view1:View;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }

    }
    override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.

        if(firebaseAuth.currentUser != null){
            navigateMethod(view1,R.id.action_loginFragment_to_menuFragment);
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        firebaseAuth = FirebaseAuth.getInstance();
        _binding = FragmentLoginBinding.inflate(inflater,container,false);
        val view = binding.root;
        view1 = view;
        //methods here
        binding.loginBtnAction.setOnClickListener {
            logIn(view);
        }
        binding.registerTextBtn.setOnClickListener {
            navigateMethod(view,R.id.action_loginFragment_to_registerFragment);
        }
        binding.forgotTextBtn.setOnClickListener {
            navigateMethod(view,R.id.action_loginFragment_to_forgotFragment);
        }

        //end of methods

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
         * @return A new instance of fragment LoginFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            LoginFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    private fun validateCredential(view: View): Boolean {
        val nombreRef = view.findViewById<TextView>(R.id.loginUserName);
        gmail = nombreRef.text.toString().trim();

        val claveRef = view.findViewById<TextView>(R.id.loginPassword);
        clave = claveRef.text.toString().trim();
        if (gmail.isEmpty() || clave.isEmpty()) {

            message("Se deben llenar todos los campos");
            return false;
        }
        if(!validateEmail(gmail)){

            message("Se deben ingresar un correo valido");
            return false;
        }
        return true;
    }

    private fun validateEmail(email:String):Boolean{
        val pattern: Pattern = Patterns.EMAIL_ADDRESS;
        return pattern.matcher(email).matches();
    }
    private fun navigateMethod(view:View,action:Int){
        Navigation.findNavController(view).navigate(action);

    }
    private fun message(msn:String){
        Toast.makeText(
            activity,
            msn,
            Toast.LENGTH_SHORT
        ).show()
    }
    private fun logIn(view: View) {
        if (validateCredential(view)) {
            val mainObject =MainActivity();

            firebaseAuth.signInWithEmailAndPassword(gmail, clave)
                .addOnCompleteListener(mainObject, OnCompleteListener { task: Task<AuthResult> ->
                    if (task.isSuccessful) {
                        message("Acaba de iniciar sesion");
                        navigateMethod(view,R.id.action_loginFragment_to_menuFragment);

                    } else {


                        message("Usuario o clave incorrectos");
                    }

                })
        }
    }
}