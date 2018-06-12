package mm.dd.tools.accessibility

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_setting.*
import mm.dd.Tools.accessibility.R
import mm.dd.tools.accessibility.fragments.AboutFragment
import mm.dd.tools.accessibility.fragments.AppListFragment

class SettingActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)
        initTab()
        initViewPager()
    }

    private fun initViewPager() {
        val fragments = arrayListOf<Fragment>()
        fragments.add(AppListFragment.newInstance(AppListFragment.TYPE_ADAPTATION))
        fragments.add(AppListFragment.newInstance(AppListFragment.TYPE_NORMAL))
        fragments.add(AboutFragment.newInstance())
        settingPager.adapter = SkipPagerAdapter(fragments, supportFragmentManager)
    }

    private fun initTab() {
        settingTab.run {
            setupWithViewPager(settingPager)
        }

    }

    class SkipPagerAdapter(private val fragments: List<Fragment>, fm: FragmentManager) : FragmentPagerAdapter(fm) {
        private val pageTitles = arrayListOf("已适配", "未适配", "关于")
        override fun getItem(position: Int): Fragment {
            return fragments[position]
        }

        override fun getCount(): Int {
            return 3
        }

        override fun getPageTitle(position: Int): CharSequence? {
            return pageTitles[position]
        }
    }

}