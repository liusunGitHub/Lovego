package com.guosun.lovego.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.guosun.lovego.R;
import com.guosun.lovego.util.ULog;

public class ContactsFragment extends Fragment {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		ULog.e("lovego", this.getClass().getName() + " onCreate");
	}

	@Override
	public void onResume() {
		super.onResume();
		ULog.e("lovego", this.getClass().getName() + " onResume");
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		ULog.e("lovego", this.getClass().getName() + " onCreateView");
		return inflater.inflate(R.layout.activity_handler, container, false);
	}

	@Override
	public void onPause() {
		super.onPause();
		ULog.e("lovego", this.getClass().getName() + " onPause");
	}

	@Override
	public void onStop() {
		super.onStop();
		ULog.e("lovego", this.getClass().getName() + " onStop");
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		ULog.e("lovego", this.getClass().getName() + " onDestroy");
	}
	
	@Override
	public void onDestroyView() {
		super.onDestroyView();
		ULog.e("lovego", this.getClass().getName() + " onDestroyView");
	}
}
