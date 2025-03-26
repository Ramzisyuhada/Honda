package com.example.hondaproject_hetra

import android.app.DatePickerDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import com.example.hondaproject_hetra.Model.Barang
import java.util.Calendar

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ScanFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ScanFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var PartType : Spinner
    private lateinit var PartName : Spinner
    private lateinit var ButtonScan : Button
    private lateinit var ButtonLanjut : Button
     val DisplayImage = DisplayImageFragment()
    private lateinit var Tanggal : EditText
    private lateinit var barang: Barang

    private fun Init(view: View){
        ButtonScan = view.findViewById(R.id.SubmitScan)
        PartType = view.findViewById(R.id.PartType)
        PartName = view.findViewById(R.id.PartName)
        Tanggal = view.findViewById(R.id.Tanggal)
        val ItemType = listOf("Sensor")
        val ItemName = listOf("DHT11")

        val Adapter_Type = ArrayAdapter(requireContext(),
            androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,ItemType)
        Adapter_Type.setDropDownViewResource(androidx.appcompat.R.layout.support_simple_spinner_dropdown_item)
        val Adapter_Name = ArrayAdapter(requireContext(),
            androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,ItemName)
        Adapter_Type.setDropDownViewResource(androidx.appcompat.R.layout.support_simple_spinner_dropdown_item)

        PartType.adapter = Adapter_Type
        PartName.adapter = Adapter_Name

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Init(view)

        barang = ViewModelProvider(requireActivity()).get(Barang::class.java)

        PartType.onItemSelectedListener = object  : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                val selectedItem = p0?.getItemAtPosition(p2).toString()
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }

        PartName.onItemSelectedListener = object  : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                val selectedItem = p0?.getItemAtPosition(p2).toString()
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }

        ButtonScan.setOnClickListener {
            val dialogView = layoutInflater.inflate(R.layout.costum_dialogbox,null)

            val dialog = AlertDialog.Builder(requireContext())
                .setView(dialogView)
                .create()

            val btnClose = dialogView.findViewById<Button>(R.id.DialogBtnClose)
                    ButtonLanjut = dialogView.findViewById(R.id.Lanjut)
                    ButtonLanjut.setOnClickListener {
                            val fragmentCamera = CameraFragment()
                        parentFragmentManager.beginTransaction().replace(R.id.Frame,fragmentCamera)
                            .addToBackStack(null)
                            .commit()

                        dialog.dismiss()

                    }


            btnClose.setOnClickListener {
                dialog.dismiss()
            }

            dialog.show()
        }
        Tanggal.setOnClickListener {

            val calendar = Calendar.getInstance()
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)

            val datePickerDialog = DatePickerDialog(
                requireContext(),
                { _, selectedYear, selectedMonth, selectedDay ->
                    val selectedDate = "$selectedDay-${selectedMonth + 1}-$selectedYear"
                    val bundle = Bundle()
                    bundle.putString("tanggal", selectedDate)
                    barang.tanggal = selectedDate
                  //  DisplayImage.tanggal = selectedDate.toString()
                    Tanggal.setText(selectedDate)
                },
                year,
                month,
                day
            )
            datePickerDialog.show()

        }

    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_scan, container, false)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ScanFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ScanFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}