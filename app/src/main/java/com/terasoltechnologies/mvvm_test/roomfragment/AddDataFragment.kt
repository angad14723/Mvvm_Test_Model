package com.terasoltechnologies.mvvm_test.roomfragment

import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController

import com.terasoltechnologies.mvvm_test.R
import com.terasoltechnologies.mvvm_test.database.Notes
import com.terasoltechnologies.mvvm_test.databinding.FragmentAddDataBinding

/**
 * A simple [Fragment] subclass.
 */
class AddDataFragment : Fragment() {

    private lateinit var binding: FragmentAddDataBinding
    private lateinit var viewModel: NoteViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_add_data, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this).get(NoteViewModel::class.java)
        binding.lifecycleOwner = this

        binding.submitBtn.setOnClickListener {

            when {
                TextUtils.isEmpty(binding.titleEdt.text.toString()) -> {
                    Toast.makeText(requireContext(), "Input Title..", Toast.LENGTH_SHORT).show()
                }
                TextUtils.isEmpty(binding.subTitleEdt.text.toString()) -> {
                    Toast.makeText(requireContext(), "Input Description..", Toast.LENGTH_SHORT).show()
                }
                else -> {
                    val title = binding.titleEdt.text.toString()
                    val subTitle = binding.subTitleEdt.text.toString()


                    val notes = Notes(title, subTitle)
                    viewModel.insert(notes)

                    findNavController().navigateUp()
                }
            }
        }
    }
}
