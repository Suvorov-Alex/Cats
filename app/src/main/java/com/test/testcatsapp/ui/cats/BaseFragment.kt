package com.test.testcatsapp.ui.cats

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.findNavController

class BaseFragment : Fragment() {

    private val navController: NavController
        get() = requireActivity().findNavController(navHostId)

    private var layoutResId: Int = -1
    private var navHostId: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            layoutResId = it.getInt(LAYOUT_ID)
            navHostId = it.getInt(NAV_HOST_ID)
        } ?: return
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if (layoutResId == NO_ID) {
            return null
        } else {
            return inflater.inflate(layoutResId, container, false)
        }
    }

    fun popBackStack(): Boolean =
        navController.popBackStack()

    fun popToRoot(): Boolean =
        navController.popBackStack(navController.graph.startDestination, false)

    companion object {
        private const val NO_ID = -1

        private const val LAYOUT_ID = "layout_key"
        private const val NAV_HOST_ID = "nav_host_key"

        fun newInstance(layoutRes: Int, navHostId: Int) = BaseFragment().apply {
            arguments = Bundle().apply {
                putInt(LAYOUT_ID, layoutRes)
                putInt(NAV_HOST_ID, navHostId)
            }
        }
    }
}