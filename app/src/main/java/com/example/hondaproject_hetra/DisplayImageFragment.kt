package com.example.hondaproject_hetra

import android.graphics.Bitmap
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.widget.AppCompatButton
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.hondaproject_hetra.Model.Barang
import com.example.hondaproject_hetra.Model.Image
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.surendramaran.yolov8tflite.Adapter.AdapterImage
import java.time.LocalDate
import java.time.format.DateTimeFormatter

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [DisplayImageFragment.newInstance] factory method to
 * create an instance of this fragment.
 */


class DisplayImageFragment : Fragment() {
    private lateinit var barang : Barang
    private lateinit var recyclerView : RecyclerView
    private lateinit var ImageAdapter : AdapterImage

    public  var tanggal : String = ""
    public var DaftarGambar = ArrayList<Bitmap>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    private fun Init(view : View){
        barang = ViewModelProvider(requireActivity()).get(Barang::class.java)

        recyclerView = view.findViewById(R.id.recyclerView)
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(requireContext(),RecyclerView.HORIZONTAL,false)
        Log.i("Gambar" , DaftarGambar.count().toString())
        val imageList: ArrayList<Image> = DaftarGambar.map { Image(it) } as ArrayList<Image>

        ImageAdapter = AdapterImage(imageList) { selectedImage ->
            Log.i("Gambar" , selectedImage.toString())
          // view.viewFinder.setImageBitmap(selectedImage.foodImage)
        }
        val image = view.findViewById<ImageView>(R.id.ImageDisplay)
        image.setImageBitmap(DaftarGambar.get(DaftarGambar.lastIndex))
        recyclerView.adapter = ImageAdapter
        ImageAdapter.notifyDataSetChanged()


    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_display_image, container, false)

        Init(view)

        return view
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment DisplayImageFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            DisplayImageFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val bottomNav = requireActivity().findViewById<BottomNavigationView>(R.id.NavButton)
        bottomNav.visibility = View.GONE
        val Botton_Information = requireActivity().findViewById<LinearLayout>(R.id.Information_Check)
        Botton_Information.visibility = View.GONE


        val ButtonLanjut = requireActivity().findViewById<AppCompatButton>(R.id.Lanjut)
        ButtonLanjut.setOnClickListener {
            val Botton_LayerCheck = requireActivity().findViewById<LinearLayout>(R.id.Information_Check)
            Botton_LayerCheck.visibility = View.VISIBLE
            val Botton_Check = requireActivity().findViewById<LinearLayout>(R.id.Layer_Button_Check)
            Botton_Check.visibility = View.GONE

            val TextDateow = requireActivity().findViewById<TextView>(R.id.CheckDate)
            val currentDate = LocalDate.now()
            val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
            val formattedDate = currentDate.format(formatter)
            val TextKondisi = requireActivity().findViewById<TextView>(R.id.kondisi)
            val IconKondisi = requireActivity().findViewById<ImageView>(R.id.iconkondisi)

            val textPlanned = requireActivity().findViewById<TextView>(R.id.datepalanned)

            textPlanned.setText(barang.tanggal)
            IconKondisi.setColorFilter(ContextCompat.getColor(requireContext(),R.color.Green))
            TextKondisi.setText("Ok")
            TextKondisi.setTextColor(ContextCompat.getColor(requireContext(),R.color.Green))
            TextDateow.setText(formattedDate)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        val bottomNav = requireActivity().findViewById<BottomNavigationView>(R.id.NavButton)
        bottomNav.visibility = View.VISIBLE
    }
}