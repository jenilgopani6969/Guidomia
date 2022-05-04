package com.worldimage.belldemo.presentation

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.worldimage.belldemo.R
import com.worldimage.belldemo.domain.model.Car

private var previousExpandedPosition = -1
private var mExpandedPosition = -1

class CarListAdapter:
    RecyclerView.Adapter<CarListAdapter.ViewHolder>(), Filterable {

    lateinit var carList: List<Car>
    lateinit var carListFiltered: List<Car>

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        var modelTxt : TextView = itemView.findViewById(R.id.recycle_model)
        var priceTxt : TextView = itemView.findViewById(R.id.recycle_price)
        var ratingStar : RatingBar = itemView.findViewById(R.id.recycle_star)
        var carImage : ImageView = itemView.findViewById(R.id.recycle_image)
        var expendableLayout : LinearLayout = itemView.findViewById(R.id.recycle_expandable_layout)
        var linearLayout : LinearLayout = itemView.findViewById(R.id.recycle_linearLayout)
        var linPros : LinearLayout = itemView.findViewById(R.id.lin_Pros)
        var linCons : LinearLayout = itemView.findViewById(R.id.lin_Cons)
        var linProsMain : LinearLayout = itemView.findViewById(R.id.lin_ProsMain)
        var linConsMain : LinearLayout = itemView.findViewById(R.id.lin_ConsMain)
        var linearLayoutProsCons : LayoutInflater = LayoutInflater.from(itemView.context).context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view : View = LayoutInflater.from(parent.context).inflate(R.layout.item_carlist,parent,false)
        return ViewHolder(view)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun addData(list: List<Car>) {
        carList = list as ArrayList<Car>
        carListFiltered = carList
        notifyDataSetChanged()
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, @SuppressLint("RecyclerView") position: Int) {
        val carList : Car = carListFiltered[position]

        holder.modelTxt.text = carList.model

        //list price
        val convPrice: Int = (carList.marketPrice.toInt()/1000)
        holder.priceTxt.text = "${convPrice}k"

        holder.ratingStar.rating = carList.rating.toFloat()

        //listPros
        if (carList.prosList.count() == 0) {
            holder.linProsMain.visibility = View.GONE
        } else {
            holder.linPros.removeAllViews()
            carList.prosList.forEach {
                val view: View = holder.linearLayoutProsCons.inflate(R.layout.listview_item, holder.linPros, false)
                val title : TextView = view.findViewById(R.id.list_name)
                title.text = it
                holder.linPros.addView(view)
            }
        }


        //listCons
        if (carList.consList.count() == 0) {
            holder.linConsMain.visibility = View.GONE
        } else {
            holder.linCons.removeAllViews()
            carList.consList.forEach {
                val view: View = holder.linearLayoutProsCons.inflate(R.layout.listview_item, holder.linCons, false)
                val title : TextView = view.findViewById(R.id.list_name)
                title.text = it
                holder.linCons.addView(view)
            }
        }


        //setImage
        when(carList.make) {
            "Land Rover" -> holder.carImage.setImageResource(R.drawable.range_rover)
            "Alpine" -> holder.carImage.setImageResource(R.drawable.alpine_roadster)
            "BMW" -> holder.carImage.setImageResource(R.drawable.bmw_330i)
            "Mercedes Benz" -> holder.carImage.setImageResource(R.drawable.mercedez_benz)
        }

        //Expandable logic
        val isExpandable: Boolean = (position == mExpandedPosition)
        holder.expendableLayout.visibility = if (isExpandable) View.VISIBLE else View.GONE
        holder.linearLayout.isActivated = isExpandable

        if (isExpandable) previousExpandedPosition = position

        holder.linearLayout.setOnClickListener {
            mExpandedPosition = if (isExpandable) -1 else position
            notifyItemChanged(previousExpandedPosition)
            notifyItemChanged(position)
        }


    }

    override fun getItemCount(): Int {
        return carListFiltered.size
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charString = constraint?.toString() ?: ""
                carListFiltered = if (charString.isEmpty()) carList else {
                    val filteredList = ArrayList<Car>()
                    carList
                        .filter {
                            it.make.lowercase().startsWith(charString.lowercase()) ||
                                    it.model.lowercase().startsWith(charString.lowercase())
                        }
                        .forEach { filteredList.add(it) }
                    filteredList

                }
                return FilterResults().apply { values = carListFiltered }
            }

            @SuppressLint("NotifyDataSetChanged")
            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {

                carListFiltered = if (results?.values == null)
                    ArrayList()
                else
                    results.values as List<Car>
                notifyDataSetChanged()
            }
        }
    }
}
