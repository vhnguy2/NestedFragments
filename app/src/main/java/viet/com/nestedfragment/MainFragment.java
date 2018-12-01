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
    ViewPager.PageTransformer pt = new MyPageTransformer();
    viewPager.setPageTransformer(false, pt);

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

  private class MyPageTransformer implements ViewPager.PageTransformer {

    private final float MIN_X_DELTA = 100f;
    private final float MIN_ALPHA_DELTA = 0.5f;

    @Override
    public void transformPage(@NonNull View view, float position) {
      int pageWidth = view.getWidth();

      if (position < -1) { // [-Infinity,-1)
        // This page is way off-screen to the left.
        view.setAlpha(0f);
      } else if (position <= 0) { // [-1,0]
        // Fade the page out.
        float alpha = MIN_ALPHA_DELTA + (1 - MIN_ALPHA_DELTA) * (1 - Math.abs(position));
        view.setAlpha(alpha);
        // Scale the page down (between MIN_SCALE and 1)
        float xDelta = MIN_X_DELTA + (1 - MIN_X_DELTA) * (1 - Math.abs(position));
        // Counteract the default slide transition
        view.setTranslationX((pageWidth * -position) - xDelta);
      } else if (position <= 1) { // (0,1]
        // Use the default slide transition when moving to the left page
        view.setAlpha(1f);
        view.setTranslationX(0f);
        view.setScaleX(1f);
        view.setScaleY(1f);
        view.bringToFront();
      } else { // (1,+Infinity]
        // This page is way off-screen to the right.
        view.setAlpha(0f);
      }
    }
  }
}
