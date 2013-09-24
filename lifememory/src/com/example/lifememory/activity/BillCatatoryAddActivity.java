package com.example.lifememory.activity;

import com.example.lifememory.R;
import com.example.lifememory.activity.model.BillCatagoryItem;
import com.example.lifememory.adapter.BillCatagoryGridViewAdapter;
import com.example.lifememory.db.service.BillCatagoryService;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class BillCatatoryAddActivity extends Activity {
	private static int[] imageIds = new int[]{
		R.drawable.canyin, R.drawable.changyong, R.drawable.gouwu, R.drawable.jiaotong,
		R.drawable.jujia, R.drawable.renqing, R.drawable.touzi, R.drawable.yijiao, R.drawable.yule,
		R.drawable.baokanshuji, R.drawable.baoxian, R.drawable.baoxianfei, R.drawable.baoyangweihu,
		R.drawable.chajiukafei, R.drawable.chekuanchedai, R.drawable.chexian, R.drawable.cishanjuankuan,
		R.drawable.dache, R.drawable.daifukuan, R.drawable.dianqi, R.drawable.dianying, R.drawable.fakuanpeichang,
		R.drawable.fangwudaikuan, R.drawable.gongjiaoditie, R.drawable.guoluguoqiao, R.drawable.hangkong, 
		R.drawable.huazhuanghufu,  R.drawable.jiajiaobuxi, R.drawable.jiayou,
		R.drawable.jiazhengfuwu, R.drawable.kalaok, R.drawable.kuaidiyouji, R.drawable.kuandai, R.drawable.lijin,
		R.drawable.lingshi, R.drawable.lipin, R.drawable.luyoudujia, R.drawable.meirongmeifa, R.drawable.peixunkaoshi,
		R.drawable.qiuyimaiyao, R.drawable.riyongbaihuo, R.drawable.sheying, R.drawable.shoujihuafei,
		R.drawable.shoushi, R.drawable.shuhuayishu, R.drawable.shuidianranqi, R.drawable.shuifei, R.drawable.shumachanpin,
		R.drawable.tingchefei, R.drawable.waihui, R.drawable.wancan, R.drawable.wangyoudianwan, R.drawable.wanju,
		R.drawable.wucan, R.drawable.wuye, R.drawable.xiaofeidaikuan, R.drawable.xiyuzuyu, R.drawable.xuezajiaocai,
		R.drawable.yanjiu, R.drawable.yexiao, R.drawable.yifuxiemao, R.drawable.yingeryongpin, R.drawable.yinliaoshuiguo,
		R.drawable.youyanjiangcu, R.drawable.yundongjianshen, R.drawable.zaocan, R.drawable.zhengquanqihuo, R.drawable.zhusuzufang,
		R.drawable.d_ggx, R.drawable.d_hlqt, R.drawable.d_hsfz, R.drawable.d_jsbh, R.drawable.d_jsh, R.drawable.d_jsqh,
		R.drawable.d_jssh, R.drawable.d_lh, R.drawable.d_mc, R.drawable.d_nf, R.drawable.d_pjsp, R.drawable.d_sfjj,
		R.drawable.d_shyp, R.drawable.d_tlrjq, R.drawable.d_xtxt, R.drawable.d_zs, R.drawable.icon_bbyp_nbnp,
		R.drawable.icon_bbyp_ykxm, R.drawable.icon_jjwy_wygl, R.drawable.icon_jjwy_yxby, R.drawable.icon_lyyp_hwzb,
		R.drawable.icon_qtzx_ywds, R.drawable.icon_transfer_in, R.drawable.icon_transfer_out, R.drawable.icon_yfsp,
		R.drawable.icon_yfsp_xmbb, R.drawable.icon_yfsp_yfkz, R.drawable.icon_ylbj_bjf, R.drawable.icon_ylbj_zlf,
		R.drawable.icon_zysr_jjsr, R.drawable.icon_zysr_jzsr, R.drawable.xianjin
	};
	private GridView gridView;
	private BillCatagoryGridViewAdapter adapter;
	private int selectedImageId;
	private ImageView imageView;
	private EditText nameEt;
	private TextView titleTv;
	private String title;
	private int parentId = 0;
	private BillCatagoryItem item;
	private BillCatagoryService dbService;
	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 0:
				//添加成功
				BillCatatoryAddActivity.this.finish();
				overridePendingTransition(R.anim.activity_steady, R.anim.activity_down);
				break;
			case 1:
				Toast.makeText(BillCatatoryAddActivity.this, "类别名称不能重复哦", 0).show();
				break;
			}
		};
	};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.bill_catagory_add_layout);
		
		dbService = new BillCatagoryService(this);
		
		findViews();
		gridViewAdapter();
		setListeners();
		initDatas();
	}
	
	private void setListeners() {
		gridView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				selectedImageId = imageIds[position];
				imageView.setImageResource(selectedImageId);
				adapter.setSelected(position);
			}
		});
	}

	private void gridViewAdapter() {
		adapter = new BillCatagoryGridViewAdapter(BillCatatoryAddActivity.this, imageIds);
		gridView.setAdapter(adapter);
	}

	private void findViews() {
		gridView = (GridView) this.findViewById(R.id.gridView);
		imageView = (ImageView) this.findViewById(R.id.imageView);
		selectedImageId = imageIds[0];
		imageView.setImageResource(selectedImageId);
		nameEt = (EditText) this.findViewById(R.id.name_txt);
		titleTv = (TextView) this.findViewById(R.id.title);
	}
	
	private void initDatas() {
		title = this.getIntent().getStringExtra("title");
		parentId = this.getIntent().getIntExtra("parentId", 0);
		titleTv.setText(title);
	}
	
	public void btnClick(View view) {
		switch (view.getId()) {
		case R.id.back:
			back();
			break;
		case R.id.save:
			
			if(nameEt.getText().toString() == null || "".equals(nameEt.getText().toString())) {
				Toast.makeText(BillCatatoryAddActivity.this, "请填写类别名称", 0).show();
			}else {
				item = new BillCatagoryItem();
				item.setName(nameEt.getText().toString());
				item.setParentId(parentId);
				item.setImageId(selectedImageId);
				
				new AddCatagoryThread().start();
				
			}
			
			break;
		}
	}
	
	private class AddCatagoryThread extends Thread {
		@Override
		public void run() {
			boolean result = dbService.addCatagory(item);
			if(result) {
				handler.sendEmptyMessage(0);
			}else 
				handler.sendEmptyMessage(1);
		}
	}
	
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		dbService.closeDB();
	}
	
	private void back() {
		BillCatatoryAddActivity.this.finish();
		overridePendingTransition(R.anim.activity_steady, R.anim.activity_down);
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		switch (keyCode) {
		case KeyEvent.KEYCODE_BACK:
			back();
			break;
		}
		return true;
	}
}
