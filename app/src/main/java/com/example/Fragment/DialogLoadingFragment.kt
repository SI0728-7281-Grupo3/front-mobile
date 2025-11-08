package com.example.Fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.ImageView
import androidx.fragment.app.DialogFragment
import com.example.restyle_mobile.R

class DialogLoadingFragment : DialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate el layout para el diálogo de carga
        val view = inflater.inflate(R.layout.fragment_dialog_loading, container, false)

        // Encontrar el ImageView y aplicar la animación
        val logoImageView: ImageView = view.findViewById(R.id.loading_image)
        val floatingAnimation = AnimationUtils.loadAnimation(context, R.anim.floating_animation)
        logoImageView.startAnimation(floatingAnimation)

        return view
    }

    override fun onStart() {
        super.onStart()
        // Hacer que el diálogo ocupe todo el ancho
        //dialog?.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
    }

    companion object {
        const val TAG = "LoadingDialogFragment"

        fun newInstance(): DialogLoadingFragment {
            return DialogLoadingFragment()
        }
    }
}