package viet.com.nestedfragment;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class B1Fragment extends Fragment {
  private static int COUNTER = 1;
  private static final String EXTRA_KEY_COUNT = "extra_key_count";

  private String localCount;
  private TextView textView;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    if (savedInstanceState == null) {
      localCount = "B " + COUNTER++;
    } else {
      localCount = savedInstanceState.getString(EXTRA_KEY_COUNT);
    }
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
    View rootView = inflater.inflate(R.layout.fragment_layout, parent, false);
    rootView.setBackgroundColor(getResources().getColor(android.R.color.holo_red_dark));

    textView = rootView.findViewById(R.id.value);
    textView.setText(localCount);

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
}
