package me.ljd.dict;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import me.ljd.voicedcit.R;

public class DictActivity extends Activity {
	private ImageView mImgVoiceQuery;//语音查询
	private EditText mEditInput;//关键字
	private TextView mTxtStartQuery;//手动执行查询	
	private TextView mTxtShowResult;//结构显示
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}
}
