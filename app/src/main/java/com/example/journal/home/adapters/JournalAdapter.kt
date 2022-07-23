package com.example.journal.home.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.journal.databinding.JournalBinding

class JournalAdapter(private var fragment: Fragment) : RecyclerView.Adapter<JournalAdapter.ViewHolder>() {
    private val journals = listOf("How", "Hello", "Are", "You", "I", "Am", "Fine")
    class ViewHolder (view: JournalBinding): RecyclerView.ViewHolder(view.root){
        val journalName = view.journalName
        val card = view.journalCard
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = JournalBinding.inflate(LayoutInflater.from(fragment.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val journal = journals[position]
        holder.journalName.text = journal

        holder.card.setOnClickListener {
            Log.i("card view clicked", "card view")
        }
    }

    override fun getItemCount(): Int {
        return journals.size
    }
}