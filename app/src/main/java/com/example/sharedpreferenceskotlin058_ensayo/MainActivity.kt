package com.example.sharedpreferenceskotlin058_ensayo

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.sharedpreferenceskotlin058_ensayo.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var mBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mBinding.root)

        mBinding.btnSave.setOnClickListener(this)
        mBinding.btnClear.setOnClickListener(this)
        mBinding.btnShow.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btnSave -> {
                val password = mBinding.inPassword.text.toString()
                val userId = mBinding.inUserId.text.toString()
                save(password, userId)
                mostrarDatos()
            }
            R.id.btnClear -> {
                showClearConfirmationDialog()
                mostrarDatos()

            }
            R.id.btnShow -> {
                mostrarDatos()
            }
        }
    }

    fun notificarRecyclerView() {
        //OBJETO RECYCLER
        val recycler = mBinding.recycler
        // LLAMO MIS CLASE PREFERENCIAS
        val myPreferences = MyPreferences(this)
        // CREO UNA LISTA MUTABLE RECIBE DOS STRING COMO CADENA
        val mutablePreferences = mutableListOf<Pair<String, String>>()
        // RECUPERA LOS DATOS
        myPreferences.showPreferences(recycler)
        // LE ENTREGO LOS DATOS QUE ALMACENE COMO CADENA
        val adapter = PreferencesAdapter(mutablePreferences)
        //PARA LUEGO NOTIFICAR AL RECYCLER
        recycler.adapter = adapter
        adapter.notifyDataSetChanged()
    }

    private fun save(password: String, userId: String) {
        val preferences = MyPreferences(applicationContext)

        if (password.isEmpty() || userId.isEmpty()) {
            Toast.makeText(
                applicationContext,
                "Ingrese todos los datos",
                Toast.LENGTH_LONG
            ).show()
            return
        }

        preferences.addItem(password, userId)

        Toast.makeText(
            applicationContext,
            "DATOS GUARDADOS CORRECTAMENTE",
            Toast.LENGTH_LONG
        ).apply {
            setGravity(Gravity.CENTER, 0, 0)
            show()
        }

        mBinding.inPassword.setText("")
        mBinding.inUserId.setText("")
        mostrarDatos()
        notificarRecyclerView()
    }

    private fun showClearConfirmationDialog() {
        val alertDialog = AlertDialog.Builder(this)
            .setMessage("¿Estás seguro de que quieres eliminar los datos?")
            .setPositiveButton("Sí") { dialog, _ ->
                val preferences = MyPreferences(this)
                dialog.dismiss()
                Toast.makeText(this, "Datos eliminados correctamente", Toast.LENGTH_SHORT).show()

                // elimina de la lista
                preferences.clearList()
                mostrarDatos()
                notificarRecyclerView()
            }
            .setNegativeButton("No") { dialog, _ ->
                dialog.dismiss()
            }
            .create()
        alertDialog.show()
    }

    private fun mostrarDatos() {
        val myPreferences = MyPreferences(this)
        val recycler = mBinding.recycler

        val layoutManager = LinearLayoutManager(this)
        recycler.layoutManager = layoutManager

        myPreferences.showPreferences(recycler)

        val lastIndex = layoutManager.itemCount - 1

        if (lastIndex == -1) {
            Toast.makeText(
                applicationContext,
                "NO HAY DATOS",
                Toast.LENGTH_LONG
            ).apply {
                setGravity(Gravity.CENTER, 0, 0)
                show()
            }
        } else if (lastIndex >= 0) {
            layoutManager.scrollToPosition(lastIndex)
        }
    }
}