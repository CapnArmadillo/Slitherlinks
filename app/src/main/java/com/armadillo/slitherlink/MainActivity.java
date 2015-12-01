package com.armadillo.slitherlink;

import android.app.Activity;
import android.app.FragmentManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.os.Bundle;
import android.widget.Button;
import android.widget.RadioButton;

import com.armadillo.slitherlink.altair.AltairLayoutFragment;
import com.armadillo.slitherlink.hex.HexLayoutFragment;


public class MainActivity extends Activity {
	private static final String TAG = "MainActivity";
	private RadioButton altairButton, hexButton;
	private static final String ATTACHED_FRAGMENT = "AttachedFragment";
	private String attachedFragment;

	private static final String MAIN_FRAGMENT = "layoutFragment";
	private static final String ALTAIR_FRAGMENT = "altairFragment";
	private static final String HEX_FRAGMENT = "hexFragment";

	private MainFragment layoutFragment;
	private AltairLayoutFragment altairLayoutFragment;
	private HexLayoutFragment hexLayoutFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
		altairButton =  (RadioButton) findViewById(R.id.navAltair);
		altairButton.setOnClickListener(getMenuOnClickListener());
		hexButton =  (RadioButton) findViewById(R.id.navHex);
		hexButton.setOnClickListener(getMenuOnClickListener());

        if (savedInstanceState == null) {
			attachAltairLayoutFragment();
        } else {
			attachedFragment = savedInstanceState.getString(ATTACHED_FRAGMENT);
			if (attachedFragment.equalsIgnoreCase(MAIN_FRAGMENT)) {
				attachMainFragment();
			} else if (attachedFragment.equalsIgnoreCase(ALTAIR_FRAGMENT)) {
				attachAltairLayoutFragment();
			} else if (attachedFragment.equalsIgnoreCase(HEX_FRAGMENT)) {
				attachHexLayoutFragment();
			}

		}


    }

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putString(ATTACHED_FRAGMENT, attachedFragment);
	}

	public OnClickListener getMenuOnClickListener() {
		OnClickListener listener = new OnClickListener() {
			@Override
			public void onClick(View view) {
				String text = ((Button)view).getText().toString();
				if (text.equalsIgnoreCase(getString(R.string.altair))) {
					attachAltairLayoutFragment();
//				} else if (text.equalsIgnoreCase(getString(R.string.altair))) {
//					attachAltairLayoutFragment();
				}
			}
		};

		return listener;
	}

	public void attachMainFragment() {
		attachedFragment = MAIN_FRAGMENT;
		FragmentManager fm = getFragmentManager();
		layoutFragment = (MainFragment)fm.findFragmentByTag(attachedFragment);
		if (layoutFragment == null) {
			layoutFragment = new MainFragment();
		}

		getFragmentManager().beginTransaction()
				.replace(R.id.container, layoutFragment, attachedFragment)
				.commit();
	}

	public void attachAltairLayoutFragment() {
		altairButton.setChecked(true);
		attachedFragment = ALTAIR_FRAGMENT;
		FragmentManager fm = getFragmentManager();
		altairLayoutFragment = (AltairLayoutFragment)fm.findFragmentByTag(attachedFragment);
		if (altairLayoutFragment == null) {
			altairLayoutFragment = new AltairLayoutFragment();
		}

		getFragmentManager().beginTransaction()
				.replace(R.id.container, altairLayoutFragment, attachedFragment)
				.commit();
	}

	public void attachHexLayoutFragment() {
		hexButton.setChecked(true);
		attachedFragment = HEX_FRAGMENT;
		FragmentManager fm = getFragmentManager();
		hexLayoutFragment = (HexLayoutFragment)fm.findFragmentByTag(attachedFragment);
		if (hexLayoutFragment == null) {
			hexLayoutFragment = new HexLayoutFragment();
		}

		getFragmentManager().beginTransaction()
				.replace(R.id.container, hexLayoutFragment, attachedFragment)
				.commit();
	}

}
