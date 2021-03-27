package com.channelpartner.utils

import android.annotation.SuppressLint
import android.app.Activity
import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.content.ContentUris
import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.Color
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.DocumentsContract
import android.provider.MediaStore
import android.provider.Settings
import android.text.TextUtils
import android.util.Patterns
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.annotation.NonNull
import androidx.core.content.FileProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DecodeFormat
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.signature.ObjectKey
import com.channelpartner.BuildConfig
import com.channelpartner.Constants.Companion.IMAGE_URL
import com.channelpartner.R
import com.channelpartner.utils.FileUtils.isDownloadsDocument
import com.channelpartner.utils.FileUtils.isExternalStorageDocument
import com.channelpartner.utils.FileUtils.isMediaDocument
import com.google.zxing.BarcodeFormat
import com.google.zxing.EncodeHintType
import com.google.zxing.MultiFormatWriter
import com.google.zxing.WriterException
import com.google.zxing.common.BitMatrix
import com.google.zxing.common.CharacterSetECI
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
import java.io.IOException
import java.net.URISyntaxException
import java.text.SimpleDateFormat
import java.util.*
import java.util.regex.Matcher
import java.util.regex.Pattern


object utility {

    //Our constants
    val OPERATION_CAPTURE_PHOTO = 1
    val OPERATION_CHOOSE_PHOTO = 2
    val OPERATION_CHOOSE_MULTIPLE_PHOTO = 3
    var source: File? = null


    fun isValidEmail(target: CharSequence?): Boolean {
        return if (TextUtils.isEmpty(target)) {
            false
        } else {
            Patterns.EMAIL_ADDRESS.matcher(target).matches()
        }
    }

    fun getDeviceID(context: Context): String {
        return Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
    }

    fun capturePhoto(context: Activity) {
        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        if (cameraIntent.resolveActivity(context.packageManager) != null) {
            source = getPictureFile(context)
            if (source != null) {
                val photoURI = FileProvider.getUriForFile(
                    context,
                    BuildConfig.APPLICATION_ID + ".fileprovider",
                    source!!
                )
                cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                context.startActivityForResult(cameraIntent, OPERATION_CAPTURE_PHOTO)
            }
        } else {
            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            context.startActivityForResult(intent, OPERATION_CAPTURE_PHOTO)
        }
    }

    @Throws(IOException::class)
    private fun getPictureFile(context: Activity): File? {
        val timeStamp = SimpleDateFormat("yyyyMMddHHmmss").format(Date())
        val pictureFile: String =
            context.getString(R.string.app_name).replace(" ", "").toString() + timeStamp
        val storageDir = File(Environment.getExternalStorageDirectory().absolutePath + File.separator + context.getString(R.string.app_name))
        if (!storageDir.exists()) {
            storageDir.mkdirs()
        }
//        val storageDir1: File? = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        var image: File? = null
        try {
            image = File.createTempFile(pictureFile, ".jpg", storageDir)
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return image
    }

    fun openGallery1(context: Activity) {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "image/*"
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
        intent.addCategory(Intent.CATEGORY_OPENABLE)
        context.startActivityForResult(intent, OPERATION_CHOOSE_MULTIPLE_PHOTO)
    }

    fun openGallery(context: Activity) {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "image/*"
        context.startActivityForResult(intent, OPERATION_CHOOSE_PHOTO)
    }

    @SuppressLint("NewApi")
    @Throws(URISyntaxException::class)
    fun getPath(context: Activity, uri: Uri): String? {
        var uri = uri
        val needToCheckUri = Build.VERSION.SDK_INT >= 19
        var selection: String? = null
        var selectionArgs: Array<String>? = null
        // Uri is different in versions after KITKAT (Android 4.4), we need to
        // deal with different Uris.
        if (needToCheckUri && DocumentsContract.isDocumentUri(
                context.getApplicationContext(),
                uri
            )
        ) {
            if (isExternalStorageDocument(uri)) {
                val docId = DocumentsContract.getDocumentId(uri)
                val split = docId.split(":".toRegex()).toTypedArray()
                return Environment.getExternalStorageDirectory()
                    .toString() + "/" + split[1]
            } else if (isDownloadsDocument(uri)) {
                val id = DocumentsContract.getDocumentId(uri)
                uri = ContentUris.withAppendedId(
                    Uri.parse("content://downloads/public_downloads"),
                    java.lang.Long.valueOf(id)
                )
            } else if (isMediaDocument(uri)) {
                val docId = DocumentsContract.getDocumentId(uri)
                val split = docId.split(":".toRegex()).toTypedArray()
                val type = split[0]
                if ("image" == type) {
                    uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                } else if ("video" == type) {
                    uri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI
                } else if ("audio" == type) {
                    uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
                }
                selection = "_id=?"
                selectionArgs = arrayOf(split[1])
            }
        }
        if ("content".equals(uri.scheme, ignoreCase = true)) {
            val projection =
                arrayOf(MediaStore.Images.Media.DATA)
            var cursor: Cursor? = null
            try {
                cursor = context.contentResolver
                    .query(uri, projection, selection, selectionArgs, null)
                val column_index: Int = cursor!!.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
                if (cursor!!.moveToFirst()) {
                    return cursor!!.getString(column_index)
                }
            } catch (e: Exception) {
            }
        } else if ("file".equals(uri.scheme, ignoreCase = true)) {
            return uri.path
        }
        return null
    }

    @NonNull
    fun prepareFilePart(
        context: Activity,
        partName: String,
        fileUri: Uri
    ): MultipartBody.Part? {
        val file: File? = FileUtils.getFile(context, fileUri)
        val requestFile: RequestBody =
            RequestBody.create(MediaType.parse(FileUtils.MIME_TYPE_IMAGE), file)
        return MultipartBody.Part.createFormData(partName, file!!.name, requestFile)
    }

    fun loadImage(
        activity: Activity,
        type: Boolean,
        int: Int,
        image: String,
        imageView: ImageView
    ) {
        var requestOptions = RequestOptions()
        requestOptions = requestOptions.format(DecodeFormat.PREFER_ARGB_8888)
        requestOptions = requestOptions.diskCacheStrategy(DiskCacheStrategy.NONE)
        requestOptions = requestOptions.skipMemoryCache(true)
        requestOptions = requestOptions.signature(ObjectKey(System.currentTimeMillis().toString()))
        if (int == 1)
            requestOptions = RequestOptions.circleCropTransform()

        if (type)
            Glide.with(activity).load(IMAGE_URL + image).apply(requestOptions).placeholder(R.drawable.ic_gallery).into(imageView)
        else
            Glide.with(activity).load(image).apply(requestOptions).placeholder(R.drawable.ic_gallery).into(imageView)
    }

    fun loadImage(activity: Activity, int: Int, image: Uri, imageView: ImageView) {
        var requestOptions = RequestOptions()
        requestOptions = requestOptions.format(DecodeFormat.PREFER_ARGB_8888)
        requestOptions = requestOptions.diskCacheStrategy(DiskCacheStrategy.NONE)
        requestOptions = requestOptions.skipMemoryCache(true)
        requestOptions = requestOptions.signature(ObjectKey(System.currentTimeMillis().toString()))
        if (int == 1)
            requestOptions = RequestOptions.circleCropTransform()

        Glide.with(activity).load(image).apply(requestOptions).placeholder(R.drawable.ic_gallery).into(imageView)
    }

    fun isValidPan(pan: String?): Boolean {
        val mPattern: Pattern = Pattern.compile("[A-Z]{5}[0-9]{4}[A-Z]{1}")
        val mMatcher: Matcher = mPattern.matcher(pan)
        return mMatcher.matches()
    }

    fun isValidIFSC(pan: String?): Boolean {
        val mPattern: Pattern = Pattern.compile("^[A-Za-z]{4}0[A-Z0-9]{6}\$")
        val mMatcher: Matcher = mPattern.matcher(pan)
        return mMatcher.matches()
    }

//    fun scan(activity: Activity){
//        val intent = Intent(activity, ScanActivity::class.java)
//        activity.startActivityForResult(intent, REQUEST_CODE)
//    }

    @Throws(WriterException::class)
    fun generateQR(value: String): Bitmap? {
        val bitMatrix: BitMatrix
        try {
            val hints = EnumMap<EncodeHintType, Any>(EncodeHintType::class.java)
            hints[EncodeHintType.ERROR_CORRECTION] = ErrorCorrectionLevel.H
            hints[EncodeHintType.CHARACTER_SET] = CharacterSetECI.UTF8

            bitMatrix = MultiFormatWriter().encode(
                value,
                BarcodeFormat.QR_CODE,
                500,
                500,
                hints
            )
        } catch (Illegalargumentexception: IllegalArgumentException) {
            return null
        }

        val bitMatrixWidth = bitMatrix.width

        val bitMatrixHeight = bitMatrix.height

        val pixels = IntArray(bitMatrixWidth * bitMatrixHeight)

        for (y in 0 until bitMatrixHeight) {
            val offset = y * bitMatrixWidth

            for (x in 0 until bitMatrixWidth) {

                pixels[offset + x] = if (bitMatrix.get(x, y))
                    Color.BLACK
                else
                    Color.WHITE
            }
        }
        val bitmap = Bitmap.createBitmap(bitMatrixWidth, bitMatrixHeight, Bitmap.Config.ARGB_4444)
        bitmap.setPixels(pixels, 0, 500, 0, 0, bitMatrixWidth, bitMatrixHeight)

        return bitmap
    }

    var calendar = Calendar.getInstance();
    fun DateDialog(context: Activity, editText: EditText) {
        val date = OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
            // TODO Auto-generated method stub
            var calendar = Calendar.getInstance();
            calendar.set(Calendar.YEAR, year)
            calendar.set(Calendar.MONTH, monthOfYear)
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            val myFormat = "MM-dd-yyyy"
            val sdf = SimpleDateFormat(myFormat, Locale.US)
            editText.setText(sdf.format(calendar.getTime()))
            editText.setEnabled(true)
            //updateLabel();
            val userAge: Calendar =
                GregorianCalendar(year, monthOfYear, dayOfMonth)
            val minAdultAge: Calendar = GregorianCalendar()
            minAdultAge.add(Calendar.YEAR, -18)
            if (minAdultAge.before(userAge)) {
                Toast.makeText(context, "Select valid Date", Toast.LENGTH_LONG).show()
            }
            //updateLabel();
        }
        val calendar1 = Calendar.getInstance()
        calendar1.add(Calendar.YEAR, -18)
        val dpDialog = DatePickerDialog(
            context, date,
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )
        dpDialog.datePicker.maxDate = calendar1.timeInMillis
        dpDialog.show()
    }

    fun deviceId(context: Context): String {
        return Settings.Secure.getString(
            context.contentResolver,
            Settings.Secure.ANDROID_ID
        )
    }

    private var classCalendar = Calendar.getInstance();
    fun dialogClassDate(context: Activity, editText: EditText) {
        val date = DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
            // TODO Auto-generated method stub
            var calendar = Calendar.getInstance();
            calendar.set(Calendar.YEAR, year)
            calendar.set(Calendar.MONTH, monthOfYear)
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            val myFormat = "MM-dd-yyyy"
            val sdf = SimpleDateFormat(myFormat, Locale.US)
            editText.setText(sdf.format(calendar.time))
            editText.isEnabled = true
        }
        val dpDialog = DatePickerDialog(
            context, date,
            classCalendar.get(Calendar.YEAR),
            classCalendar.get(Calendar.MONTH),
            classCalendar.get(Calendar.DAY_OF_MONTH)
        )
        dpDialog.datePicker.maxDate = classCalendar.timeInMillis
        dpDialog.show()
    }

    private var studentCalendar = Calendar.getInstance();
    fun dialogStudentDOB(context: Activity, editText: EditText) {
        val date = DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
            // TODO Auto-generated method stub
            var calendar = Calendar.getInstance();
            calendar.set(Calendar.YEAR, year)
            calendar.set(Calendar.MONTH, monthOfYear)
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            val myFormat = "MM-dd-yyyy"
            val sdf = SimpleDateFormat(myFormat, Locale.US)
            editText.setText(sdf.format(calendar.time))
            editText.isEnabled = true
            //updateLabel();
            val userAge: Calendar =
                GregorianCalendar(year, monthOfYear, dayOfMonth)
            val minAdultAge: Calendar = GregorianCalendar()
            minAdultAge.add(Calendar.YEAR, -9)
            if (minAdultAge.before(userAge)) {
                Toast.makeText(context, "Select valid Date", Toast.LENGTH_LONG).show()
            }
            //updateLabel();
        }
        val calendar1 = Calendar.getInstance()
        calendar1.add(Calendar.YEAR, -9)
        val dpDialog = DatePickerDialog(
            context, date,
            studentCalendar.get(Calendar.YEAR),
            studentCalendar.get(Calendar.MONTH),
            studentCalendar.get(Calendar.DAY_OF_MONTH)
        )
        dpDialog.datePicker.maxDate = calendar1.timeInMillis
        dpDialog.show()
    }

    private var teacherCalendar = Calendar.getInstance();
    fun dialogTeacherDate(context: Activity, editText: EditText) {
        val date = DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
            // TODO Auto-generated method stub
            var calendar = Calendar.getInstance();
            calendar.set(Calendar.YEAR, year)
            calendar.set(Calendar.MONTH, monthOfYear)
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            val myFormat = "MM-dd-yyyy"
            val sdf = SimpleDateFormat(myFormat, Locale.US)
            editText.setText(sdf.format(calendar.time))
            editText.isEnabled = true
            //updateLabel();
            val userAge: Calendar =
                GregorianCalendar(year, monthOfYear, dayOfMonth)
            val minAdultAge: Calendar = GregorianCalendar()
            minAdultAge.add(Calendar.YEAR, -18)
            if (minAdultAge.before(userAge)) {
                Toast.makeText(context, "Select valid Date", Toast.LENGTH_LONG).show()
            }
            //updateLabel();
        }
        val calendar1 = Calendar.getInstance()
        calendar1.add(Calendar.YEAR, -18)
        val dpDialog = DatePickerDialog(
            context, date,
            teacherCalendar.get(Calendar.YEAR),
            teacherCalendar.get(Calendar.MONTH),
            teacherCalendar.get(Calendar.DAY_OF_MONTH)
        )
        dpDialog.datePicker.maxDate = calendar1.timeInMillis
        dpDialog.show()
    }
}