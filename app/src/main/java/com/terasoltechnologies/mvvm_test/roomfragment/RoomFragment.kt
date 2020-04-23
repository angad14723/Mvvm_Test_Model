package com.terasoltechnologies.mvvm_test.roomfragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.terasoltechnologies.mvvm_test.R
import com.terasoltechnologies.mvvm_test.database.Notes
import com.terasoltechnologies.mvvm_test.databinding.FragmentRoomBinding
import timber.log.Timber
import java.lang.reflect.Type

/**
 * A simple [Fragment] subclass.
 */
class RoomFragment : Fragment(), ResultCallback {

    companion object {

        var instance = RoomFragment
    }

    private lateinit var binding: FragmentRoomBinding

    private lateinit var noteViewModel: NoteViewModel
    private var adapter = NotesAdapter()

    //  private  var adapter: NotesAdapter?=null

    private var dataList = ArrayList<Notes>()


    // private val noteViewModel: NoteViewModel by inject()
    // private val adapter: NotesAdapter by inject()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_room, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        noteViewModel = ViewModelProvider(this).get(NoteViewModel::class.java)

        binding.lifecycleOwner = this

        binding.floatingBtn.setOnClickListener {
            findNavController().navigate(R.id.addDataFragment)
        }

        binding.recyclerView.adapter = adapter //NotesAdapter(dataList)

        noteViewModel.allNotes.observe(viewLifecycleOwner, Observer { notesList ->
            notesList?.let {
                adapter.setData(it)
            }

            //(binding.recyclerView.adapter as NotesAdapter).notifyDataSetChanged()
        })

    }


    class NotesAdapter :
        RecyclerView.Adapter<NotesAdapter.ViewHolder>() {

        private var noteList = ArrayList<Notes>()

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

            val itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.note_item, parent, false)
            return ViewHolder(itemView)
        }

        override fun getItemCount(): Int {

            return noteList.size
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val currentNote = noteList[position]
            holder.textViewTitle.text = currentNote.title
            holder.textViewDescription.text = currentNote.description
        }

        fun setData(noteList: List<Notes>) {
            this.noteList = noteList as ArrayList<Notes>
            notifyDataSetChanged()
        }

        class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            var textViewTitle: TextView = itemView.findViewById(R.id.text_view_title)
            var textViewDescription: TextView = itemView.findViewById(R.id.text_view_description)
        }
    }


    override fun setResult(notes: Notes) {

        Timber.d(notes.title, notes.description)
    }
}
