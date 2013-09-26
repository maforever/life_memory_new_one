package com.example.lifememory.activity;

import java.util.List;

import com.example.lifememory.R;
import com.example.lifememory.activity.model.BillAccountItem;
import com.example.lifememory.adapter.BillReimbursementSelectAccountDialogAdapter;
import com.example.lifememory.db.service.BillAccountService;
import com.example.lifememory.utils.ConstantUtil;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class BillReimbursementInputAccountSelectDialog extends Activity {
	List<BillAccountItem> accountItems;
	BillAccountService accountService;
	BillReimbursementSelectAccountDialogAdapter adapter;
	Intent intent;
	int currentSelectedIndex;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.bill_reimbursement_input_account_select_dialog);
		
		accountService = new BillAccountService(this);
		this.currentSelectedIndex = this.getIntent().getIntExtra("currentSelectedIndex", 0);
		ListView listView = (ListView) this.findViewById(R.id.listView);
		accountItems = accountService.getAllAccounts();
		adapter = new BillReimbursementSelectAccountDialogAdapter(this, accountItems);
		listView.setAdapter(adapter);
		adapter.showSelectedTag(currentSelectedIndex);
		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				adapter.showSelectedTag(position);
				intent = new Intent();
				intent.putExtra("name", accountItems.get(position).getName());
				intent.putExtra("accountId", accountItems.get(position).getIdx());
				intent.putExtra("currentSelectedIndex", position);
				BillReimbursementInputAccountSelectDialog.this.setResult(ConstantUtil.SELECT_ACCOUNT_FINISHED, intent);
				BillReimbursementInputAccountSelectDialog.this.finish();
			}
		});
	}
	
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		accountService.closeDB();
		accountService = null;
	}
}
