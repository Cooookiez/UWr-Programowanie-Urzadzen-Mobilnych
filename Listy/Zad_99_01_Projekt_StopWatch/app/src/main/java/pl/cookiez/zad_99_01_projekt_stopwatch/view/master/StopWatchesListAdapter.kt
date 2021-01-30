package pl.cookiez.zad_99_01_projekt_stopwatch.view.master

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import pl.cookiez.zad_99_01_projekt_stopwatch.R
import pl.cookiez.zad_99_01_projekt_stopwatch.databinding.StopWatchesListSingleItemBinding
import pl.cookiez.zad_99_01_projekt_stopwatch.model.StopWatch
import kotlin.coroutines.coroutineContext

class StopWatchesListAdapter(
    private val stopWatchesList: ArrayList<StopWatch>
) : RecyclerView.Adapter<StopWatchesListAdapter.StopWatchViewHolder>(),
    StopWatchesListClickListener {

    inner class StopWatchViewHolder(var binding: StopWatchesListSingleItemBinding)
        : RecyclerView.ViewHolder(binding.root)

    fun updateList(newList: List<StopWatch>) {
        stopWatchesList.clear()
        stopWatchesList.addAll(newList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): StopWatchesListAdapter.StopWatchViewHolder {
        return StopWatchViewHolder(
            StopWatchesListSingleItemBinding.inflate(
                LayoutInflater.from(parent.context)
            )
        )
    }

    override fun onBindViewHolder(
        holder: StopWatchesListAdapter.StopWatchViewHolder,
        position: Int
    ) {
        holder.binding.stopwatch = stopWatchesList[position]
        holder.binding.listener = this
    }

    override fun getItemCount(): Int = stopWatchesList.size

    override fun onMoreClicked(view: View) {
        Toast.makeText(view.context, "MORE", Toast.LENGTH_SHORT).show()

        val uuidString =
            ((view.parent as View).findViewById<View>(R.id.stopwatch_uuid) as TextView)
            .text.toString()
        Log.d("zaq1 – list adapter", "uuid: $uuidString")
        val action = StopWatchesListDirections
            .actionStopWatchesListToStopWatchDetail(uuidString.toLong())
        Navigation.findNavController(view).navigate(action)
    }

    override fun onPlayPauseClicked(view: View) {
        Toast.makeText(view.context, "PLAY / PAUSE", Toast.LENGTH_SHORT).show()
    }
}