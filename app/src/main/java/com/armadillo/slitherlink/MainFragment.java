package com.armadillo.slitherlink;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.armadillo.slitherlink.R;
import com.armadillo.common.DLog;

/**
 * Created by john.pushnik on 10/14/14.
 */
public class MainFragment extends Fragment {
	private static final String TAG = "PlaceholderFragment";

	public MainFragment() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		DLog.i();
		View rootView = inflater.inflate(R.layout.fragment_main, container, false);

		return rootView;
	}

}

