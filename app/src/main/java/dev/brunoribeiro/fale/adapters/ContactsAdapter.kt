package dev.brunoribeiro.fale.adapters

import android.view.LayoutInflater
import android.view.TextureView
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import dev.brunoribeiro.fale.R
import dev.brunoribeiro.fale.entities.Contact

class ContactsAdapter(private val listsOfContacts: List<Contact>): RecyclerView.Adapter<ContactsAdapter.ContactViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder {
        val itemview = LayoutInflater.from(parent.context).inflate(R.layout.item_contact, parent, false)

        return ContactViewHolder(itemview)
    }

    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {
        val current = listsOfContacts[position]

        holder.nameContact.text = current.name
        holder.lastTextContact.text = current.messages[listsOfContacts.size - 1].text
        holder.dateContact.text = current.messages[listsOfContacts.size - 1].date
        holder.imgContact.setImageResource(R.drawable.person1)

    }

    override fun getItemCount(): Int  = listsOfContacts.size


    class ContactViewHolder(view: View): RecyclerView.ViewHolder(view){
        val nameContact = view.findViewById<TextView>(R.id.name_contact)
        val lastTextContact = view.findViewById<TextView>(R.id.text_contact)
        val imgContact = view.findViewById<ImageView>(R.id.img_contact)
        val dateContact = view.findViewById<TextView>(R.id.date_contact)
    }
}