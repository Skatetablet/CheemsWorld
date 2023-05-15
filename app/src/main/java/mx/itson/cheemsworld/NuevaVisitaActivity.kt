package mx.itson.cheemsworld

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import mx.itson.cheemsworld.entidades.Visita
import mx.itson.cheemsworld.utilerias.RetrofitUtil
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NuevaVisitaActivity : AppCompatActivity(),View.OnClickListener {
    var txtLugar : EditText? = null
    var txtMotivo : EditText? = null
    var txtResponsable : EditText? = null
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nueva_visita)
        val btnGuardar = findViewById<Button>(R.id.btnGuardar)
        btnGuardar.setOnClickListener(this)
        txtLugar = findViewById<EditText>(R.id.txtLugar)
        txtMotivo = findViewById<EditText>(R.id.txtMotivo)
        txtResponsable = findViewById<EditText>(R.id.txtResponsable)

        val btnCancelar = findViewById<Button>(R.id.btnCancelar)
        btnCancelar.setOnClickListener{val intent = Intent(this, MainActivity::class.java)
            startActivity(intent) }

    }

    override fun onClick(v: View?) {
        if (v?.id == R.id.btnGuardar){
            val visita = Visita()
            val datos = intent.extras
            visita.lugar = txtLugar?.text.toString()
            visita.motivo = txtMotivo?.text.toString()
            visita.responsable = txtResponsable?.text.toString()
            visita.latitud = datos?.getDouble("latitud")
            visita.longitud = datos?.getDouble("longitud")

            val call = RetrofitUtil.getApi().postVisita(visita)
            call.enqueue(object : Callback<Void> {
                override fun onResponse(call: Call<Void>, response: Response<Void>) {
                    if (response.isSuccessful) {
                        val intent = Intent(applicationContext, MainActivity::class.java)
                        startActivity(intent)
                    } else {

                    }
                }

                override fun onFailure(call: Call<Void>, t: Throwable) {

                }
            })


        }

    }
}


