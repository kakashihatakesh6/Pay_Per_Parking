package com.example.payparking.fragment

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.payparking.R
import com.example.payparking.adapter.DashboardRecyclerAdapter

class DashboardFragment : Fragment() {

    lateinit var recylerDashoboard: RecyclerView

    lateinit var layoutManager: LinearLayoutManager

    val bookList = arrayListOf(
        "Book 1",
        "Cook1",
        "Dook52"
    )

    lateinit var recyclerAdapter: DashboardRecyclerAdapter

    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_dashboard, container, false)

        recylerDashoboard = view.findViewById(R.id.recyclerDashboard)

        layoutManager = LinearLayoutManager(activity)

        recyclerAdapter = DashboardRecyclerAdapter(activity as Context, bookList)

        recylerDashoboard.adapter = recyclerAdapter

        recylerDashoboard.layoutManager = layoutManager

        return view
    }

}