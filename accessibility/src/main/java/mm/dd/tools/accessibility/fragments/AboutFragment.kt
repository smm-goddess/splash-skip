package mm.dd.tools.accessibility.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import mm.dd.Tools.accessibility.R

class AboutFragment : Fragment() {

    companion object {
        fun newInstance(): AboutFragment {
            return AboutFragment()
        }
    }

    private var content:View? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        if(content == null){
            content = inflater.inflate(R.layout.layout_about, null)
        }
        return content
    }

}