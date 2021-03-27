package com.channelpartner.fragment

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import com.channelpartner.BuildConfig
import com.channelpartner.R
import com.channelpartner.activities.*
import com.channelpartner.utils.TextViewDrawableSize
import com.channelpartner.utils.utility
import com.kuber.vpn.Utils.sessionManager
import kotlinx.android.synthetic.main.fragment_account.*
import java.io.File
import java.io.FileOutputStream


class FragmentSetting : Fragment() {

    var mQRBitmap: Bitmap? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_account, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        txtMyProfile.setOnClickListener(View.OnClickListener {
            context!!.startActivity(Intent(context!!, MyProfile::class.java))
        })

        txtMyIncome.setOnClickListener(View.OnClickListener {
            context!!.startActivity(Intent(context!!, MyIncome::class.java))
        })

        txtMyStatus.setOnClickListener(View.OnClickListener {
            context!!.startActivity(Intent(context!!, Status::class.java))
        })

        txtQRCode.setOnClickListener(View.OnClickListener {
            showDialog()
        })

        txtChangePwd.setOnClickListener(View.OnClickListener {
            context!!.startActivity(Intent(context!!, ChangePassword::class.java))
        })

        txtAboutUs.setOnClickListener(View.OnClickListener {
            context!!.startActivity(Intent(context!!, AboutUs::class.java))
        })

        txtContactUs.setOnClickListener(View.OnClickListener {
            context!!.startActivity(Intent(context!!, ContactUs::class.java))
        })

        txtFAQ.setOnClickListener(View.OnClickListener {
            context!!.startActivity(Intent(context!!, FAQ::class.java))
        })

        txtLogout.setOnClickListener(View.OnClickListener {
            showAlert()
        })
    }

    private fun showDialog() {

        // custom dialog
        val dialog = Dialog(context!!)
        dialog.setContentView(R.layout.dialog_qr_code)
        dialog.setCancelable(true)
        dialog.window!!.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.MATCH_PARENT
        )
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        val imgQR = dialog.findViewById(R.id.imgQR) as ImageView
        val txtName = dialog.findViewById(R.id.txtName) as TextView
        val txtCode = dialog.findViewById(R.id.txtCode) as TextView
        val txtShare = dialog.findViewById(R.id.txtShare) as TextViewDrawableSize

        txtName.text = sessionManager.getName(context)
        txtCode.text = sessionManager.getUnique_No(context)
        mQRBitmap = utility.generateQR(sessionManager.getUnique_No(context)!!)
        if (mQRBitmap != null) {
            imgQR.setImageBitmap(mQRBitmap)
        } else {
            imgQR.setImageBitmap(null)
            mQRBitmap = null
        }

        txtShare.setOnClickListener {
            //share image
            shareImage(mQRBitmap!!)
        }

        dialog.show()

    }

    fun showAlert() {
        val builder: AlertDialog.Builder = AlertDialog.Builder(context)
        builder.setMessage("Do you really want to exit ?")
        builder.setTitle("Exit !")
        builder.setCancelable(true)
        builder
            .setPositiveButton(
                "Yes",
                DialogInterface.OnClickListener { dialog, which -> // When the user click yes button
                    sessionManager.clearData(context!!)
                    activity!!.finish()
                })
        builder
            .setNegativeButton(
                "No",
                DialogInterface.OnClickListener { dialog, which -> // If user click no
                    // then dialog box is canceled.
                    dialog.cancel()
                })
        val alertDialog: AlertDialog = builder.create()
        alertDialog.show()
    }

    private fun shareImage(bitmap: Bitmap) {
        val file_path: String =
            Environment.getExternalStorageDirectory().absolutePath + File.separator + context!!.getString(
                R.string.app_name
            )
        val dir = File(file_path)
        if (!dir.exists()) dir.mkdirs()
        val file = File(dir, "QRCode.png")
        val fOut: FileOutputStream
        try {
            fOut = FileOutputStream(file)
            bitmap.compress(Bitmap.CompressFormat.PNG, 85, fOut)
            fOut.flush()
            fOut.close()
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }

        if (file != null) {
            val photoURI = FileProvider.getUriForFile(
                context!!,
                BuildConfig.APPLICATION_ID + ".fileprovider",
                file!!
            )
            val intent = Intent()
            intent.action = Intent.ACTION_SEND
            intent.type = "image/*"
            intent.putExtra(Intent.EXTRA_STREAM, photoURI)
            startActivity(Intent.createChooser(intent, "Share Cover Image"))
        }
    }
}
