package com.example.loginapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.Navigation
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
 * Use the [MenuFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MenuFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    //private lateinit var firebaseDatabase: FirebaseDatabase;
    private  lateinit var firebaseAuth: FirebaseAuth;
    private lateinit var firebaseDatabase: FirebaseDatabase;
    private lateinit var view1:View;
    private lateinit var userData: UserData;

    //textViews
    private lateinit var username:TextView;
    private lateinit var nombre:TextView;
    private lateinit var apellido:TextView;
    private lateinit var correo:TextView;
    private lateinit var direccion:TextView;
    private lateinit var fecha:TextView;
    private lateinit var pais:TextView;
    private lateinit var provincia:TextView;
    private lateinit var sexo:TextView;
    private lateinit var telefono:TextView;


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
        val view = inflater.inflate(R.layout.fragment_menu, container, false);
        view1 =view;
        //initialization
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        logOutBtn = view.findViewById(R.id.logOutBtnAction);
        logOutBtn.setOnClickListener {
            logOut(view);
        }
        return view;
    }
    override fun onStart() {
        super.onStart()
        if(firebaseAuth.currentUser == null){
            Navigation.findNavController(view1).navigate(R.id.action_menuFragment_to_loginFragment);
        }
        getUserData();
        //showTextValues(view1);
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

        view1.findViewById<TextView>(R.id.menuUsername).setText(getSoloData(data,"username"));
        view1.findViewById<TextView>(R.id.menuNombre).setText(getSoloData(data,"nombre"));
        view1.findViewById<TextView>(R.id.menuApellido).setText(getSoloData(data,"apellido"));
        view1.findViewById<TextView>(R.id.menuTelefono).setText(getSoloData(data,"telefono"));
        view1.findViewById<TextView>(R.id.menuCorreo).setText(getSoloData(data,"correo"));
        view1.findViewById<TextView>(R.id.menuFecha).setText(getSoloData(data,"fecha"));
        view1.findViewById<TextView>(R.id.menuSexo).setText(getSoloData(data,"sexo"));
        view1.findViewById<TextView>(R.id.menuPais).setText(getSoloData(data,"pais"));
        view1.findViewById<TextView>(R.id.menuProvincia).setText(getSoloData(data,"provincia"));
        view1.findViewById<TextView>(R.id.menuDireccion).setText(getSoloData(data,"direccion"));

    }
    private fun getUserData(){
        val user = firebaseAuth.currentUser?.uid;

        firebaseDatabase.reference
            .child("usersDb").child(user!!).get().addOnSuccessListener(OnSuccessListener { data:DataSnapshot->
                setUsetData(data);
            });

    }

}