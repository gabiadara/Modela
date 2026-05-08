package com.modela.app.ui.filter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.modela.app.databinding.FragmentFiltersBinding

class FilterFragment : BottomSheetDialogFragment() {

    private var _binding: FragmentFiltersBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFiltersBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnClose.setOnClickListener {
            dismiss()
        }

        binding.btnApply.setOnClickListener {
            dismiss()
        }

        binding.btnClear.setOnClickListener {

        }
        val sortOptions = listOf(
            "Relevância",
            "Mais recentes",
            "Mais populares",
            "Maior idade",
            "Menor idade",
            "Maior altura",
            "Menor altura"
        )

        val adapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_dropdown_item_1line,
            sortOptions
        )

        binding.actSort.setAdapter(adapter)
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}