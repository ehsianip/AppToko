package com.pcs.apptoko

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.pcs.apptoko.LoginActivity.Companion.sessionManager

class AboutFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        val view = inflater.inflate(R.layout.fragment_about, container, false)

        val btnLogout = view.findViewById(R.id.btnLogout) as Button

        btnLogout.setOnClickListener {
            sessionManager.clearSession()

            val moveIntent = Intent(activity, LoginActivity::class.java)
            startActivity(moveIntent)
            activity?.finish()
        }

        return view
    }

}