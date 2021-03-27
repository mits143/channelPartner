package com.channelpartner.fragment

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.channelpartner.R
import com.channelpartner.activities.*
import com.channelpartner.utils.utility
import com.channelpartner.utils.utility.loadImage
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.kuber.vpn.Utils.sessionManager
import kotlinx.android.synthetic.main.activity_kyc.*
import kotlinx.android.synthetic.main.activity_my_profile.*
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_home.imgProfile

class FragmentHome : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        txtName.text = sessionManager.getName(context)

        llClassBook.setOnClickListener(View.OnClickListener {
            showDialog()
        })

        llAutoHub.setOnClickListener(View.OnClickListener {
            context!!.startActivity(Intent(context, GarageRegistration::class.java))
        })

        llOTGSChannelPartner.setOnClickListener(View.OnClickListener {
            context!!.startActivity(Intent(context, OTGSChannelPartner::class.java))
        })

        llOTGSSpNetwork.setOnClickListener(View.OnClickListener {
            context!!.startActivity(Intent(context, OTGS_SP_Network::class.java))
        })

        llClassBookNetwork.setOnClickListener(View.OnClickListener {
            context!!.startActivity(Intent(context, ClassbookNetwork::class.java))
        })

        llChannelPartner.setOnClickListener(View.OnClickListener {
            context!!.startActivity(Intent(context, CPRegistration::class.java))
        })
    }

    private fun showDialog() {
        val dialogView: View = layoutInflater.inflate(R.layout.dialog_class, null)
        val dialog = BottomSheetDialog(context!!)
        dialog.setCanceledOnTouchOutside(true)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setContentView(dialogView)
        val txtClass = dialogView.findViewById(R.id.txtClass) as TextView
        val txtTeacher = dialogView.findViewById(R.id.txtTeacher) as TextView
        val txtStudent = dialogView.findViewById(R.id.txtStudent) as TextView
        val txtCareerExpert = dialogView.findViewById(R.id.txtCareerExpert) as TextView
//
        txtClass.setOnClickListener {
            context!!.startActivity(Intent(context, ClassRegistration::class.java).putExtra("type", "class"))
            dialog.dismiss()
        }

        txtTeacher.setOnClickListener {
            context!!.startActivity(Intent(context, ClassRegistration::class.java).putExtra("type", "teacher"))
            dialog.dismiss()
        }

        txtStudent.setOnClickListener {
            context!!.startActivity(Intent(context, ClassRegistration::class.java).putExtra("type", "student"))
            dialog.dismiss()
        }

        txtCareerExpert.setOnClickListener {
            context!!.startActivity(Intent(context, ClassRegistration::class.java).putExtra("type", "expert"))
            dialog.dismiss()
        }
        dialog.show()

    }

    override fun onResume() {
        super.onResume()
        loadImage(
            context!! as Activity,
            true,
            1,
            sessionManager.getPofile_photo(context)!!,
            imgProfile
        )
    }
}
