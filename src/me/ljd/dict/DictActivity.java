package me.ljd.dict;

import java.util.Map;

import android.app.Activity;
import android.os.Bundle;
import android.provider.SyncStateContract.Constants;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import me.ljd.dict.util.AsycHttp;
import me.ljd.dict.util.AsycHttp.OnResonseListener;
import me.ljd.dict.util.Contants;
import me.ljd.dict.util.Parser;
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
		initView();
	}
	public void initView() {
		mTxtShowResult=(TextView) this.findViewById(R.id.txt_show_result);
		mImgVoiceQuery = (ImageView) this.findViewById(R.id.img_voice_query);
		mEditInput = (EditText)this.findViewById(R.id.edt_key_word);
		mTxtStartQuery = (TextView) this.findViewById(R.id.txt_start_query);
		
		mTxtStartQuery.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// 获取内容
				String input = mEditInput.getText().toString().trim();
				Map<String,String> params = Contants.getParams(input);
				AsycHttp.getInstance().requestForHttp(Contants.PATH_BASE, params,monResonseListener);
			}
		});
	}
	OnResonseListener monResonseListener = new OnResonseListener() {

		@Override
		public void onSuccess(String result) {
			mTxtShowResult.setText(Parser.ParserData(result).toString());
		}

		@Override
		public void onFailed(String error) {
			Toast.makeText(DictActivity.this, "数据错误："+error, Toast.LENGTH_LONG).show();
		}
		
	};
}
