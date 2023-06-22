package com.example.sharedpreferenceskotlin058_ensayo

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.sharedpreferenceskotlin058_ensayo.databinding.ItemPreferenceBinding
class PreferencesAdapter(private val preferencesList: MutableList<Pair<String, String>>) :
    RecyclerView.Adapter<PreferencesAdapter.ViewHolder>() {

    private lateinit var binding: ItemPreferenceBinding


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemPreferenceBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val preference = preferencesList[position]
        holder.bind(preference)
    }

    override fun getItemCount(): Int {
        return preferencesList.size
    }



    inner class ViewHolder(private val binding: ItemPreferenceBinding) :
        RecyclerView.ViewHolder(binding.root) {

        private val keyTextView: TextView = binding.uno
        private val valueTextView: TextView = binding.dos

        init {
            binding.root.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                // onItemDeleteListener?.onItemDelete(position)
                }
            }
        }

        fun bind(preference: Pair<String, String>) {
            keyTextView.text = preference.first
            valueTextView.text = preference.second
        }
    }
    interface OnItemDeleteListener {
        fun onItemDelete(position: Int)
    }

    }


