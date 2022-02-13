package com.example.loginapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
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
    //private lateinit var userinfocontainer:<anoni>


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
        //firebaseDatabase = FirebaseDatabase.getInstance();
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
        return data.child(keyName).value.toString();
    }
    private fun setUsetData(data:DataSnapshot){

        userData = UserData();
        userData.setusername(getSoloData(data,"username"));
        userData.setnombre(getSoloData(data,"nombre"));
        userData.setapellido(getSoloData(data,"apellido"));
        userData.settelefono(getSoloData(data,"telefono"));
        userData.setcorreo(getSoloData(data,"correo"));
        userData.setfecha(getSoloData(data,"fecha"));
        userData.setsexo(getSoloData(data,"sexo"));
        userData.setpais(getSoloData(data,"pais"));
        userData.setprovincia(getSoloData(data,"provincia"));
        userData.setdireccion(getSoloData(data,"direccion"));


        //userData.username = data.child("username").value.toString();
        Toast.makeText(activity,"la data es ${userData.username()}", Toast.LENGTH_SHORT).show();

    }
    private fun getUserData(){
        val user = firebaseAuth.currentUser;
        var nameUser:String ="";
        if(user != null){
            nameUser=user.email!!.replace("@gmail.com","");
        }
        firebaseDatabase.reference
            .child("message").child(nameUser).get().addOnSuccessListener(OnSuccessListener { data:DataSnapshot->
                setUsetData(data);
            });

    }
}