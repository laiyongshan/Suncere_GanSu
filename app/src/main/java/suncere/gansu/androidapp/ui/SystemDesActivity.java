package suncere.gansu.androidapp.ui;

import android.os.Bundle;
import android.view.View;

import suncere.gansu.androidapp.R;

public class SystemDesActivity extends BaseActivity
{
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.system_des_act);
	}
	
	public void  On_btnBack_Click(View sender)
	{
		this.finish();
	}
	
}
