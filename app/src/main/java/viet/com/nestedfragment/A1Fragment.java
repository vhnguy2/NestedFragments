package viet.com.nestedfragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class A1Fragment extends Fragment implements OnBackPressedListener {

  private static int COUNTER = 1;
  private static final String EXTRA_KEY_COUNT = "extra_key_count";

  private String localCount;
  private TextView textView;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    if (savedInstanceState == null) {
      localCount = "A " + COUNTER++;
    } else {
      localCount = savedInstanceState.getString(EXTRA_KEY_COUNT);
    }
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
    View rootView = inflater.inflate(R.layout.fragment_layout, parent, false);

    textView = rootView.findViewById(R.id.value);
    textView.setText(localCount);
    textView.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        addFragment();
      }
    });

    return rootView;
  }

  @Override
  public void onDestroyView() {
    super.onDestroyView();
  }

  @Override
  public void onSaveInstanceState(Bundle outState) {
    super.onSaveInstanceState(outState);

    outState.putString(EXTRA_KEY_COUNT, localCount);
  }

  private void addFragment() {
    A1Fragment child = new A1Fragment();
    getChildFragmentManager()
        .beginTransaction()
        .addToBackStack(null)
        .replace(R.id.root, child)
        .commit();
  }

  @Override
  public boolean onBackPressed() {
    int childCount = getChildFragmentManager().getBackStackEntryCount();

    if (childCount == 0) {
      // it has no child Fragment
      // can not handle the onBackPressed task by itself
      return false;

    } else {
      // get the child Fragment
      FragmentManager childFragmentManager = getChildFragmentManager();
      OnBackPressedListener childFragment = (OnBackPressedListener) childFragmentManager.getFragments().get(0);

      // propagate onBackPressed method call to the child Fragment
      if (!childFragment.onBackPressed()) {
        // child Fragment was unable to handle the task
        // It could happen when the child Fragment is last last leaf of a chain
        // removing the child Fragment from stack
        childFragmentManager.popBackStackImmediate();
      }

      // either this Fragment or its child handled the task
      // either way we are successful and done here
      return true;
    }
  }
}
