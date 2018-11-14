package viet.com.nestedfragment;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

  private MainFragment mainFragment;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    if (savedInstanceState == null) {
      mainFragment = new MainFragment();
      getSupportFragmentManager()
          .beginTransaction()
          .replace(R.id.fragment_container, mainFragment)
          .commit();
    } else {
      mainFragment = (MainFragment) getSupportFragmentManager().getFragments().get(0);
    }
  }

  /**
   * Only Activity has this special callback method
   * Fragment doesn't have any onBackPressed callback
   *
   * Logic:
   * Each time when the back button is pressed, this Activity will propagate the call to the
   * container Fragment and that Fragment will propagate the call to its each tab Fragment
   * those Fragments will propagate this method call to their child Fragments and
   * eventually all the propagated calls will get back to this initial method
   *
   * If the container Fragment or any of its Tab Fragments and/or Tab child Fragments couldn't
   * handle the onBackPressed propagated call then this Activity will handle the callback itself
   */
  @Override
  public void onBackPressed() {

    if (!mainFragment.onBackPressed()) {
      // container Fragment or its associates couldn't handle the back pressed task
      // delegating the task to super class
      super.onBackPressed();

    } else {
      // carousel handled the back pressed task
      // do not call super
    }
  }
}
