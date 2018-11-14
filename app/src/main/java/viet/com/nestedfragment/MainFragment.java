package viet.com.nestedfragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

public class MainFragment extends Fragment {

  private ViewPager viewPager;
  private ViewPagerAdapter adapter;

  private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
      = new BottomNavigationView.OnNavigationItemSelectedListener() {

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
      switch (item.getItemId()) {
        case R.id.navigation_home:
          viewPager.setCurrentItem(0);
          return true;
        case R.id.navigation_dashboard:
          viewPager.setCurrentItem(1);
          return true;
        case R.id.navigation_notifications:
          viewPager.setCurrentItem(2);
          return true;
      }
      return false;
    }
  };

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
    View rootView =inflater.inflate(R.layout.fragment_main, parent, false);

    viewPager = rootView.findViewById(R.id.viewpager);
    BottomNavigationView navigation = rootView.findViewById(R.id.navigation);
    navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

    adapter = new ViewPagerAdapter(getResources(), getChildFragmentManager());
    viewPager.setAdapter(adapter);

    return rootView;
  }

  /**
   * Retrieve the currently visible Tab Fragment and propagate the onBackPressed callback
   *
   * @return true = if this fragment and/or one of its associates Fragment can handle the backPress
   */
  public boolean onBackPressed() {
    // currently visible tab Fragment
    OnBackPressedListener currentFragment =
        (OnBackPressedListener) adapter.getRegisteredFragment(viewPager.getCurrentItem());

    if (currentFragment != null) {
      // lets see if the currentFragment or any of its childFragment can handle onBackPressed
      return currentFragment.onBackPressed();
    }

    // this Fragment couldn't handle the onBackPressed call
    return false;
  }
}
