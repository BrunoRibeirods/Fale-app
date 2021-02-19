package dev.brunoribeiro.fale.adapters

import android.view.LayoutInflater
import android.view.TextureView
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.navigation.Navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import dev.brunoribeiro.fale.R
import dev.brunoribeiro.fale.databinding.ItemContactBinding
import dev.brunoribeiro.fale.entities.Contact

class ContactsAdapter(private val listsOfContacts: List<Contact>): RecyclerView.Adapter<ContactsAdapter.ContactViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder {
        return  ContactViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {
        val item = listsOfContacts[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int  = listsOfContacts.size


    class ContactViewHolder(val binding: ItemContactBinding): RecyclerView.ViewHolder(binding.root), View.OnClickListener{
        lateinit var itemBin: Contact
        init {
            binding.root.setOnClickListener(this)
        }

        fun bind(item: Contact){
            with(binding){
                nameContact.text = item.name
                textContact.text = item.messages?.get(0)?.text
                dateContact.text = item.messages?.get(0)?.date
                imgContact.setImageResource(R.drawable.person1)
                itemBin = item
            }
        }


        companion object {
            fun from(parent: ViewGroup): ContactViewHolder {
                val inflater = LayoutInflater.from(parent.context)
                val binding = ItemContactBinding.inflate(inflater, parent, false)
                return ContactViewHolder(binding)
            }
        }

        override fun onClick(v: View?) {
            if (v != null) {
                findNavController(v).navigate(R.id.action_homeFragment_to_contactConversationFragment, bundleOf("name" to itemBin.name))
            }
        }


    }


}