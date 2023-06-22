package com.example.sharedpreferenceskotlin058_ensayo
import com.google.gson.Gson
import android.content.Context
import android.content.SharedPreferences
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.reflect.TypeToken


/********************** CLASE PREFERENCES*********************************************
 *
 * En este ejemplo, utilizamos la clase pair de Kotlin para almacenar los elementos
 * con datos pair. La clase pais es una estructura de datos que toma dos valores de tipos diferentes y los agrupa en un solo objeto.
En el método saveList, convertimos la lista de elementos dobles en formato
JSON utilizando la biblioteca Gson y luego la guardamos en las SharedPreferences con una clave específica.

En el método getList, recuperamos la lista de elementos
double desde las SharedPreferences, la convertimos nuevamente al tipo List<Triple<String, Int, Boolean>> utilizando Gson y la devolvemos.
 */

class MyPreferences(context: Context) {
    // nombre del archivo  que se guardara
    private val PREFS_NAME = "my_preferences"
    // nombre de la lista que se generara con preferences
    private val KEY_LIST = "my_list"

    private val sharedPreferences: SharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    private val gson = Gson()


    // recibir una lista pair , y liuego lo trasforma en formato en gson
    fun saveList(list: List<Pair<String, String>>) {
        val listJson = gson.toJson(list)
        sharedPreferences.edit().putString(KEY_LIST, listJson).apply()
    }


    //Recupera la lista de preferencias del almacenamiento compartido.
    //Si la lista existe, la convierte desde JSON a una lista de pares de cadenas
    // utilizando la biblioteca Gson y la devuelve.
    fun getList(): List<Pair<String, String>> {
        val listJson = sharedPreferences.getString(KEY_LIST, null)
        if (listJson != null) {
            val type = object : TypeToken<List<Pair<String, String>>>() {}.type
            return gson.fromJson(listJson, type)
        }
        return emptyList()
    }

   // Recibe un par de cadenas (Pair<String, String>) y agrega el elemento al final de la lista de preferencias existente obtenida mediante getList().
   // Luego, guarda la lista actualizada en el almacenamiento compartido utilizando saveList().

    fun addItem2(item: Pair<String, String>) {
        val list = getList().toMutableList()
        val newItem = item
        list.add(newItem)
        saveList(list)
    }



   // Recibe una clave (key) y un valor (value) como cadenas.
  // Crea un par de cadenas (Pair) con la clave y el valor y llama a addItem(item: Pair<String, String>)
   // para agregarlo a la lista de preferencias.
    fun addItem(key: String, value: String) {
        val item = Pair(key, value)
        addItem2(item)
    }



    //Recibe una instancia de RecyclerView y muestra la lista de preferencias en el RecyclerView.
    //Obtiene la lista de preferencias utilizando getList().
    //Si la lista no está vacía, crea una instancia de PreferencesAdapter
    // pasando la lista de preferencias como datos y establece el adaptador en el RecyclerView.
    fun showPreferences(recyclerView: RecyclerView) {
        val myPreferences = MyPreferences(recyclerView.context)
        val preferencesList = myPreferences.getList()

        if (preferencesList.isNotEmpty()) {
            val adapter = PreferencesAdapter(preferencesList.toMutableList())
            recyclerView.adapter = adapter
        } else {

        }
    }

    // METODO QUE BORRA TODO LO DE LAS PREFERENCIAS para la clave keylist

    fun clearList() {
        sharedPreferences.edit().remove(KEY_LIST).apply()
    }

    // para borrar desde el recycler
    fun removeItem(position: Int) {
        val list = getList().toMutableList()

        if (position in 0 until list.size) {
            list.removeAt(position)
     //       notifyItemRemoved(position)
        }
}}

