package model

class UserData {
    constructor(UserName:String, Nombre:String, Apellido:String, Telefono:String, Correo:String, Fecha:String,
                Sexo:String, Pais:String, Provincia:String, Direccion:String
    ){
        username = UserName;
        nombre=Nombre;
        apellido =Apellido;
        telefono = Telefono;
        correo = Correo;
        fecha =Fecha;
        sexo= Sexo;
        pais = Pais;
        provincia = Provincia;
        direccion = Direccion;

    }
     var username:String
        get() = this.toString()
        set(value) {
            field=value
        };

    var nombre:String
        get() = this.toString()
        set(value) {
            field=value
        };

    var apellido:String
        get() = this.toString()
        set(value) {
            field=value
        };

    var telefono:String
        get() = this.toString()
        set(value) {
            field=value
        };
    var correo:String
        get() = this.toString()
        set(value) {
            field=value
        };
    var fecha:String
        get() = this.toString()
        set(value) {
            field=value
        };
    var sexo:String
        get() = this.toString()
        set(value) {
            field=value
        };
    var pais:String
        get() = this.toString()
        set(value) {
            field=value
        };
    var provincia:String
        get() = this.toString()
        set(value) {
            field=value
        };
    var direccion:String
        get() = this.toString()
        set(value) {
            field=value
        };

}