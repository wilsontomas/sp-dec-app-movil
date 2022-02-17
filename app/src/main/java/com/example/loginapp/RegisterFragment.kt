package com.example.loginapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.RadioButton
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.Navigation
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.database.FirebaseDatabase
import model.UserData
import java.util.*

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
    private lateinit var firebaseDatabase: FirebaseDatabase;
    private lateinit var view1:View;
    //btns
    private lateinit var signUpBtn:Button;
    private lateinit var backBtn:Button;
    //end btns
    //fields
    private lateinit var usernamev:String;
    private lateinit var nombrev:String;
    private lateinit var apellidov:String;
    private lateinit var telefonov:String;
    private lateinit var correov:String;
    private lateinit var fechav:String;
    private lateinit var sexov:String;
    private lateinit var paisv:String;
    private lateinit var provinciav:String;
    private lateinit var direccionv:String;
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
        val view =inflater.inflate(R.layout.fragment_register, container, false);
        view1 =view;
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();

        backBtn = view.findViewById(R.id.backRegisterBtn);
        backBtn.setOnClickListener {
            navigateMethod(view,R.id.action_registerFragment_to_loginFragment);
        }

        signUpBtn = view.findViewById(R.id.CrearBtn);
        signUpBtn.setOnClickListener {
            signUp(view);
        }

        //end methods

        return view;
    }
    override fun onStart() {
        super.onStart()
        val currentUser = firebaseAuth.currentUser;
        if(currentUser != null){
            navigateMethod(view1,R.id.action_registerFragment_to_menuFragment);
        }
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
    private fun navigateMethod(view:View, action:Int){
        Navigation.findNavController(view).navigate(action);
    }
    private fun getTextView(view: View,id:Int):String{
        return view.findViewById<TextView>(id).text.toString().trim();
    }
    private fun getFieldsValue(view: View){
        usernamev = getTextView(view,R.id.userName);
        nombrev = getTextView(view,R.id.nombre);
        apellidov = getTextView(view,R.id.apellido);
        telefonov = getTextView(view,R.id.telefono);
        correov = getTextView(view,R.id.correo);
        fechav =getTextView(view,R.id.fecha);
        val checked:Boolean = view.findViewById<RadioButton>(R.id.hombre).isChecked;
        if(checked){
            sexov = "hombre";
        }else{
            sexov = "mujer";
        }

        paisv = getTextView(view,R.id.pais);
        provinciav = getTextView(view,R.id.provincia);
        direccionv = getTextView(view,R.id.direccion);
        password = getTextView(view,R.id.password);
        passwordConfirm = getTextView(view,R.id.passwordConfirm);

    }
    private fun validatePassword():Boolean{
        if(password == passwordConfirm){

            return true;
        }
        Toast.makeText(activity,"Las claves no coinciden", Toast.LENGTH_SHORT).show();
        return false;
    }
    private fun validateFields(view: View):Boolean{
        getFieldsValue(view);
        if(usernamev.isEmpty() || nombrev.isEmpty() || apellidov.isEmpty()
            || telefonov.isEmpty() || correov.isEmpty() || fechav.isEmpty()
            || sexov.isEmpty() || paisv.isEmpty() || provinciav.isEmpty()
            || direccionv.isEmpty() || password.isEmpty() || passwordConfirm.isEmpty()

        ){
            Toast.makeText(activity,"Faltan campos por rellenar", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
    private fun saveOtherInfo(userId:String){


        val userobjectinfo=object {
            var username=usernamev;
            var nombre =nombrev;
            var apellido=apellidov;
            var telefono=telefonov;
            var correo= correov;
            var fecha=fechav;
            var sexo=sexov;
            var pais=paisv;
            var provincia=provinciav;
            var direccion=direccionv;
        }


       firebaseDatabase.getReference("usersDb").child(userId).setValue(userobjectinfo).addOnFailureListener {
           Toast.makeText(activity,"No se pudieron guardar los datos del usuario", Toast.LENGTH_SHORT).show();
       }

    }
    private fun signUp(view:View){
        if(validateFields(view) && validatePassword()){
            val registerActivityObject = MainActivity();
            firebaseAuth.createUserWithEmailAndPassword("$usernamev@gmail.com", password)
                .addOnCompleteListener(registerActivityObject,OnCompleteListener
                    { task: Task<AuthResult> -> if(task.isSuccessful){
                        val user=firebaseAuth.currentUser?.uid;
                        saveOtherInfo(user!!);
                        navigateMethod(view1,R.id.action_registerFragment_to_menuFragment);
                    }else{
                        Toast.makeText(activity,"Error al crear o el usuario ya existe ${task.exception?.message}", Toast.LENGTH_SHORT).show();

                    } });
        }else{
            Toast.makeText(activity,"algo ocurrio", Toast.LENGTH_SHORT).show();
        }
    }

}