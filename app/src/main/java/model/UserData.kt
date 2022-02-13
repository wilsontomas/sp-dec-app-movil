package model

class UserData {

    private lateinit var _username:String;
    public fun username():String{
        return this._username;
    }
    public fun setusername(value:String){
        this._username=value;
    }

    private lateinit var _nombre:String;
    public fun nombre():String{
        return this._nombre;
    }
    public fun setnombre(value:String){
        this._nombre=value;
    }

    private lateinit var _apellido:String;
    public fun apellido():String{
        return this._apellido;
    }
    public fun setapellido(value:String){
        this._apellido=value;
    }

    private lateinit var _telefono:String;
    public fun telefono():String{
        return this._telefono;
    }
    public fun settelefono(value:String){
        this._telefono=value;
    }
    private lateinit var _correo:String;
    public fun correo():String{
        return this._correo;
    }
    public fun setcorreo(value:String){
        this._correo=value;
    }
    private lateinit var _fecha:String;
    public fun fecha():String{
        return this._fecha;
    }
    public  fun setfecha(value:String){
        this._fecha=value;
    }
    private lateinit var _sexo:String;
    public fun sexo():String{
        return this._sexo;
    }
    public fun setsexo(value:String){
        this._sexo=value;
    }
    private lateinit var _pais:String;
    public fun pais():String{
        return this._pais;
    }
    public fun setpais(value:String){
        this._pais=value;
    }

    private lateinit var _provincia:String
    public fun provincia():String{
        return this._provincia;
    }
    public fun setprovincia(value:String){
        this._provincia=value;
    }
    private lateinit var _direccion:String
    public fun direccion():String{
        return this._direccion;
    }
    public fun setdireccion(value:String){
        this._direccion=value;
    }

}