package com.example.uicomponents

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_left.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [LeftFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class LeftFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private val exampleList = ArrayList<ExampleObject>()

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
        addExampleData()
        val view = inflater.inflate(R.layout.fragment_left, container, false)
        val rvLeftFragment = view.findViewById<RecyclerView>(R.id.rvLeftFragment)
        val exampleAdapter = RecyclerViewLeftFragmentAdapter(exampleList)
        rvLeftFragment.apply {
            adapter = exampleAdapter
            layoutManager = LinearLayoutManager(activity)
        }
        return view
    }

    private fun addExampleData() {
        val img1 = "https://media.istockphoto.com/vectors/2019ncov-coronovirus-alert-dangerous-virus-epidemic-chinese-pneumonia-vector-id1265247958"
        val img2 = "https://media.istockphoto.com/vectors/the-new-normal-related-flat-style-banner-design-with-icons-vector-vector-id1257424769"
        val img3 = "https://media.istockphoto.com/vectors/uncertainty-in-business-due-covid19-pandemic-concept-vector-id1258944264"
        val img4 = "https://media.istockphoto.com/vectors/health-worker-points-a-temperature-gun-to-passengers-screening-for-vector-id1260169325"
        val img5 = "https://media.istockphoto.com/illustrations/faces-of-various-people-wearing-masks-illustration-id1257530912"
        val img6 = "https://media.istockphoto.com/vectors/quarantine-vector-id1255951361"
        val img7 = "https://media.istockphoto.com/vectors/firefighters-in-covid19-pandemic-flat-isolated-vector-illustration-vector-id1253986851"
        val img8 = "https://media.istockphoto.com/vectors/social-distancing-w-masks-imperial-vector-id1257428548"
        val img9 = "https://media.istockphoto.com/vectors/hospital-staff-pattern-doctors-and-nurses-in-protective-suits-and-vector-id1251354378"
        val img10 = "https://media.istockphoto.com/vectors/stay-home-card-girl-self-isolation-in-room-with-cup-of-coffee-vector-id1251354243"

        val textContent = "Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged."

        exampleList.apply {
            add(ExampleObject(img1, textContent, false))
            add(ExampleObject(img2, textContent, true))
            add(ExampleObject(img3, textContent, false))
            add(ExampleObject(img4, textContent, false))
            add(ExampleObject(img5, textContent, false))
            add(ExampleObject(img6, textContent, true))
            add(ExampleObject(img7, textContent, false))
            add(ExampleObject(img8, textContent, false))
            add(ExampleObject(img9, textContent, false))
            add(ExampleObject(img10, textContent, false))
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment LeftFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            LeftFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
