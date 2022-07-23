package com.example.journal.home.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.journal.databinding.FragmentHomePageBinding
import com.example.journal.home.adapters.JournalAdapter
import com.example.journal.home.viewmodels.HomePageViewModel

class HomePage : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    private lateinit var homePageBinding: FragmentHomePageBinding
    private val homePageViewModel: HomePageViewModel by viewModels()
    private lateinit var journalAdapter: JournalAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        homePageBinding = FragmentHomePageBinding.inflate(inflater, container, false)
        return homePageBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        homePageBinding.journalRecyclerView.layoutManager =
            LinearLayoutManager(requireActivity())

        journalAdapter = JournalAdapter(this@HomePage)
        homePageBinding.journalRecyclerView.adapter = journalAdapter
    }

}