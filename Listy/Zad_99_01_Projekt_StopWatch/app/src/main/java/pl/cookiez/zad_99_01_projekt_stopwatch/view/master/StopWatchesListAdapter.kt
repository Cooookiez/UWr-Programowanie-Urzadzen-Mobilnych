package pl.cookiez.zad_99_01_projekt_stopwatch.view.master

import android.app.Application
import android.media.Image
import android.opengl.Visibility
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.contentValuesOf
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import pl.cookiez.zad_99_01_projekt_stopwatch.R
import pl.cookiez.zad_99_01_projekt_stopwatch.databinding.StopWatchesListSingleItemBinding
import pl.cookiez.zad_99_01_projekt_stopwatch.model.StopWatch
import pl.cookiez.zad_99_01_projekt_stopwatch.model.local.StopWatchDAO
import pl.cookiez.zad_99_01_projekt_stopwatch.model.local.StopWatchRoom
import pl.cookiez.zad_99_01_projekt_stopwatch.util.nanoTime2strTimeHMS
import pl.cookiez.zad_99_01_projekt_stopwatch.viewmodel.StopWatchesListViewModel
import kotlin.coroutines.coroutineContext

class StopWatchesListAdapter(
    private val stopWatchesList: ArrayList<StopWatch>
) : RecyclerView.Adapter<StopWatchesListAdapter.StopWatchViewHolder>(),
    StopWatchesListClickListener {

    inner class StopWatchViewHolder(var binding: StopWatchesListSingleItemBinding)
        : RecyclerView.ViewHolder(binding.root)

    var handlingStarted: Boolean = false
    var handling: Boolean = true

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
        holder.binding.strTime = "99:99 - $position"
        holder.binding.stopwatchControlsPlay.visibility =
            if (stopWatchesList[position].stopWatchIsCounting == true) View.GONE else View.VISIBLE
        holder.binding.stopwatchControlsPause.visibility =
            if (stopWatchesList[position].stopWatchIsCounting == true) View.VISIBLE else View.GONE

        if (!handlingStarted) {
            handlingStarted = true
            handling = true
            tickTime(holder, position)
        }
    }

    private fun tickTime(
        holder: StopWatchesListAdapter.StopWatchViewHolder,
        position: Int,
        tickDelay: Long = 1000) {
        if (handling) {
            Handler(Looper.getMainLooper())
                .postDelayed({ tickTime(holder, position, tickDelay) }, tickDelay)
            // on tick this code
            if (stopWatchesList.size > 0) {
                Log.d("zaq1 – i", "($position/${stopWatchesList.size-1})")
                Log.d("zaq1 – i", "Count: ${stopWatchesList[position].stopWatchIsCounting}")
                if (stopWatchesList[position].stopWatchIsCounting == true) {
                    // counting
                    holder.binding.stopwatchControlsPlay.visibility = View.GONE
                    holder.binding.stopwatchControlsPause.visibility = View.VISIBLE

                    val timeNano =
                        stopWatchesList[position].timeSavedFromPreviousCounting!! +
                                (System.nanoTime() - stopWatchesList[position].timeStart!!)
                    val strTime = nanoTime2strTimeHMS(timeNano)

                    holder.binding.strTime = strTime
                    Log.d("zaq1 – holder", "val: ${holder.binding.stopwatchCurTime.text}")
                } else {
                    // no counting
                    holder.binding.stopwatchControlsPlay.visibility = View.VISIBLE
                    holder.binding.stopwatchControlsPause.visibility = View.GONE
                }
            }
        }
    }

    override fun getItemCount(): Int = stopWatchesList.size

    override fun onMoreClicked(view: View) {
        Toast.makeText(view.context, "MORE", Toast.LENGTH_SHORT).show()

        val uuidString =
            ((view.parent as View).findViewById<View>(R.id.stopwatch_uuid) as TextView)
            .text.toString()
        val action = StopWatchesListDirections
            .actionStopWatchesListToStopWatchDetail(uuidString.toLong())
        Navigation.findNavController(view).navigate(action)
    }

    override fun onPlayPauseClicked(view: View) {
        Toast.makeText(view.context, "PLAY / PAUSE", Toast.LENGTH_SHORT).show()

        val stopWatchListViewModel: StopWatchesListViewModel =
            StopWatchesListViewModel(application = Application())

        val itemView = view.parent.parent as View

        val stopwatchControlsPlay = itemView.findViewById<ImageButton>(R.id.stopwatch_controls_play)
        val stopwatchControlsPause = itemView.findViewById<ImageButton>(R.id.stopwatch_controls_pause)

        val uuid: Long =
            (itemView.findViewById<View>(R.id.stopwatch_uuid) as TextView)
                .text.toString().toLong()
        val position: Int =
            (itemView.findViewById<View>(R.id.stopwatch_position) as TextView)
                .text.toString().toInt()
        Log.d("zaq1", "uuid: $uuid")
        Log.d("zaq1", "position: $position")

        if (stopWatchesList[position].stopWatchIsCounting == true) {
            // is counting / go pause
            Log.d("zaq1", "is counting")
            stopwatchControlsPlay.visibility = View.GONE
            stopwatchControlsPause.visibility = View.VISIBLE

            val timeNano =
                stopWatchesList[position].timeSavedFromPreviousCounting!! +
                        (System.nanoTime() - stopWatchesList[position].timeStart!!)
            stopWatchesList[position].timeSavedFromPreviousCounting = timeNano
            stopWatchesList[position].timeStart = 0L
            stopWatchesList[position].stopWatchIsCounting = false
            stopWatchListViewModel.updateStopWatch(stopWatchesList[position])
        } else {
            // is pause / go counting
            Log.d("zaq1", "isn't counting")
            stopwatchControlsPlay.visibility = View.VISIBLE
            stopwatchControlsPause.visibility = View.GONE

            stopWatchesList[position].timeStart = System.nanoTime()
            stopWatchesList[position].stopWatchIsCounting = true
            stopWatchListViewModel.updateStopWatch(stopWatchesList[position])
        }
    }

}