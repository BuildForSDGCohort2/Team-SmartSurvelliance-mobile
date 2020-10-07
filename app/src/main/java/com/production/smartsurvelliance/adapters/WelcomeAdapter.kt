package com.production.smartsurvelliance.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RadioGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.production.smartsurvelliance.R
import com.production.smartsurvelliance.helper.GettingStartedData
import kotlinx.android.synthetic.main.activity_getting_started.*
import timber.log.Timber

class WelcomeAdapter(val context: Context, val pagingCallBack: (language: String) -> Unit) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val TYPE_WELCOME = 2
    private val TYPE_LANGUAGE = 1

    override fun getItemViewType(position: Int): Int {
        if(position == 0) {
            return TYPE_LANGUAGE
        } else {
            return TYPE_WELCOME
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        if(viewType == TYPE_LANGUAGE) {
            val itemLayout = LayoutInflater.from(parent.context).inflate(
                R.layout.item_select_language,
                parent,
                false
            )
            return LanguageSelectViewHolder(itemLayout)
        } else {
            val itemLayout = LayoutInflater.from(parent.context).inflate(
                R.layout.item_welcome_slide,
                parent,
                false
            )
            return WelcomeViewHolder(itemLayout)
        }

    }



    override fun getItemCount() = 4

//    override fun onBindViewHolder(holder: WelcomeViewHolder, position: Int) {
//
//        val item = GettingStartedData.getData(context)[position]
//        holder.imageView.setImageResource(item.third)
//        holder.descriptionTextView.text = item.second
//        holder.titleTextView.text = item.first
//
//    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (position == 0) {
            (holder as LanguageSelectViewHolder).bind(context, pagingCallBack)
        } else {
            (holder as WelcomeViewHolder).bind(context, position)        }
    }


}


class WelcomeViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

    val imageView: ImageView = itemView.findViewById(R.id.slideImageView)
    val titleTextView: TextView = itemView.findViewById(R.id.textViewTitle)
    val descriptionTextView: TextView = itemView.findViewById(R.id.textViewDescription)


    fun bind(context: Context, position: Int) {
        val item = GettingStartedData.getData(context)[position - 1]
        imageView.setImageResource(item.third)
        descriptionTextView.text = item.second
        titleTextView.text = item.first
    }

}

class LanguageSelectViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

    private val languageRadioGroup: RadioGroup = itemView.findViewById(R.id.language_radio_group)

    fun bind(context: Context, pagingCallBack: (language: String) -> Unit) {

        languageRadioGroup.setOnCheckedChangeListener { radioGroup, i ->
                when (i) {
                    R.id.english_radio_button -> {
                        Timber.d("English Checked")
                        pagingCallBack("en")
                    }
                    R.id.french_radio_button -> {
                        Timber.d("French Checked")
                        pagingCallBack("fr")

                    }
                }
        }
    }

//    val imageView: ImageView = itemView.findViewById(R.id.slideImageView)
//    val titleTextView: TextView = itemView.findViewById(R.id.textViewTitle)
//    val descriptionTextView: TextView = itemView.findViewById(R.id.textViewDescription)

}