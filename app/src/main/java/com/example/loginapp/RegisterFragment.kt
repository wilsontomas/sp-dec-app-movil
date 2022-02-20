package com.example.loginapp

import android.os.Bundle
import android.util.Patterns
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
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.database.FirebaseDatabase
import model.UserData
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
    private fun validateEmail(email:String):Boolean{
        val pattern: Pattern = Patterns.EMAIL_ADDRESS;
        return pattern.matcher(email).matches();
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
            || telefonov.isEmpty() || usernamev.isEmpty() || fechav.isEmpty()
            || sexov.isEmpty() || paisv.isEmpty() || provinciav.isEmpty()
            || direccionv.isEmpty() || password.isEmpty() || passwordConfirm.isEmpty()

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
            var telefono=telefonov;
            var fecha=fechav;
            var sexo=sexov;
            var pais=paisv;
            var provincia=provinciav;
            var direccion=direccionv;
        }


       firebaseDatabase.getReference("usersDb").child(userId).setValue(userobjectinfo).addOnFailureListener {
           message("No se pudieron guardar los datos del usuario");
       }

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
                    error->message(error.toString())
                message("Error al crear o el usuario ya existe");
            });

        }
    }

}