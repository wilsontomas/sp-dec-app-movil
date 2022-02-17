package com.example.loginapp

import android.os.Bundle
import android.telephony.SmsManager
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.navigation.Navigation
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.FirebaseDatabase
import model.UserData

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
    private lateinit var login:Button;
    private lateinit var register:Button;
    private lateinit var recover:Button;
    private lateinit var firebaseAuth:FirebaseAuth;
    private lateinit var firebaseDatabase:FirebaseDatabase;
    private lateinit var view1:View;
    private lateinit var username:String;
    private lateinit var userNumber:String;
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
        val view =inflater.inflate(R.layout.fragment_forgot, container, false);
        view1=view;
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        login = view.findViewById(R.id.forgotLoginBtn);
        login.setOnClickListener {
            navigateMethod(view,R.id.action_forgotFragment_to_loginFragment)
        }

        register = view.findViewById(R.id.forgotRegisterBtn);
        register.setOnClickListener { navigateMethod(view,R.id.action_forgotFragment_to_registerFragment) }

        recover = view.findViewById(R.id.forgotBtnAction2);
        recover.setOnClickListener {
            getUserNumber();
           // navigateMethod(view,R.id.action_forgotFragment_to_registerFragment)
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
    private fun messaje(msg:String){
        Toast.makeText(activity,msg,Toast.LENGTH_LONG).show();

    }
    private fun validateUsername(view: View):Boolean{
        val userNameValidate = view.findViewById<EditText>(R.id.forgotUserName).text.toString().trim();
        if(userNameValidate.isEmpty()){
            messaje("Debe agregar el nombre de usuario");

            return false;
        }
        username=userNameValidate;
        return true;
    }

    private fun getSoloData(data:DataSnapshot,keyName:String):String{
        return data.child(keyName).value.toString();
    }
    private fun getUserNumber(){
       if(validateUsername(view1)){
            firebaseDatabase.reference
           .child("usersDb").orderByChild("username").equalTo(username)
                .get().addOnSuccessListener(
                    OnSuccessListener { data:DataSnapshot->
                        if(data.hasChildren()){
                        data.children.forEach { dataSnapshot ->
                            val numero = dataSnapshot.child("telefono").value.toString();
                            //messaje(numero);
                            //userNumber = numero;
                            sendMessage(numero);
                        }}else{messaje("No se encontro el usuario");}
                }).addOnFailureListener(OnFailureListener {
                    messaje("Algo salio mal");
                })
        }


    }

    private fun sendMessage(numero:String){
        try{
            val smsManager:SmsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(numero,null,"Prueba",null,null);
        }catch (error:Exception){
            messaje(error.toString());
           messaje("Numero invalido p fuera de servicio");
        }


    }

}