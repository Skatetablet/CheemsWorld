package mx.itson.cheemsworld

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class VerVisitaActivity : AppCompatActivity() {

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ver_visita)


        val datos = intent.extras
        val lugar = datos?.getString("lugar")
        val txtLugar  = findViewById<TextView>(R.id.lugar)
        txtLugar.setText(lugar)

        val motivo = datos?.getString("motivo")
        val txtMotivo  = findViewById<TextView>(R.id.motivo)
        txtMotivo.setText(motivo)

        val responsable = datos?.getString("responsable")
        val txtResponsable  = findViewById<TextView>(R.id.responsable)
        txtResponsable.setText(responsable)




        val btnCancelar = findViewById<Button>(R.id.btnCancelar)
        btnCancelar.setOnClickListener{val intent = Intent(this, MainActivity::class.java)
            startActivity(intent) }






    }


}