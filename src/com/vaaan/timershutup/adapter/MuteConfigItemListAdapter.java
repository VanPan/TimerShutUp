package com.vaaan.timershutup.adapter;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.vaaan.timershutup.MainActivity;
import com.vaaan.timershutup.R;
import com.vaaan.timershutup.entity.MuteConfigItem;

public class MuteConfigItemListAdapter extends BaseAdapter {

	private MainActivity mainActivity;
	private LayoutInflater listContainer;           //视图容器   
	
	public MuteConfigItemListAdapter(MainActivity mainActivity){
		this.mainActivity=mainActivity;
		listContainer = LayoutInflater.from(mainActivity);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return this.mainActivity.getConfigList().size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	private final class ListItemView{
		public EditText etTime;
		public Spinner spType;
		public Button btnDelete;
	}
	
	private final class ETextWatcher implements TextWatcher{
		private MuteConfigItem mci;
		
		public ETextWatcher(MuteConfigItem mci){
			this.mci=mci;
		}
		
		@Override
		public void onTextChanged(CharSequence s, int start, int before, int count) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void beforeTextChanged(CharSequence s, int start, int count,
				int after) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void afterTextChanged(Editable s) {
			try {
				mci.setFireTime(sdf.parse(s.toString()));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	private final class MuteTypeSpinnerItenSelectedListener implements OnItemSelectedListener{
		private MuteConfigItem mci;
		
		public MuteTypeSpinnerItenSelectedListener(MuteConfigItem mci){
			this.mci=mci;
		}

		@Override
		public void onItemSelected(AdapterView<?> arg0, View arg1, int position,
				long arg3) {
			mci.setMute(position==0);
		}

		@Override
		public void onNothingSelected(AdapterView<?> arg0) {
			// TODO Auto-generated method stub
			
		}
		
	}
	
	private final class DeleteButtonClickListener implements OnClickListener{
		
		private MuteConfigItem mci;
		private MainActivity mainActivity;
		
		public DeleteButtonClickListener(MuteConfigItem mci, MainActivity mainActivity){
			this.mci=mci;
			this.mainActivity=mainActivity;
		}
		
		@Override
		public void onClick(View v) {
			this.mainActivity.getConfigList().remove(mci);
			this.mainActivity.RefreshDataList();
		}
		
	}
	
	private static SimpleDateFormat sdf=new SimpleDateFormat("HH:mm");
	

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		//final int selectID = position;   
		//自定义视图   
		ListItemView  listItemView = null;   
        if (convertView == null) {   
            listItemView = new ListItemView();    
            //获取list_item布局文件的视图   
            convertView = listContainer.inflate(R.layout.list_item, null);   
            //获取控件对象   
            listItemView.etTime = (EditText)convertView.findViewById(R.id.listItemEt);   
            listItemView.spType = (Spinner)convertView.findViewById(R.id.listItemSp);   
            listItemView.btnDelete = (Button)convertView.findViewById(R.id.listItemBtnDelete);   
            //设置控件集到convertView   
            convertView.setTag(listItemView);   
        }else {   
            listItemView = (ListItemView)convertView.getTag();   
        }  
        MuteConfigItem mci=mainActivity.getConfigList().get(position);
        listItemView.etTime.setText(sdf.format(mci.getFireTime()));
        listItemView.spType.setSelection(mci.isMute()?0:1);
        listItemView.etTime.addTextChangedListener(new ETextWatcher(mci));
        listItemView.spType.setOnItemSelectedListener(new MuteTypeSpinnerItenSelectedListener(mci));
        listItemView.btnDelete.setOnClickListener(new DeleteButtonClickListener(mci, mainActivity));
        return convertView;   
	}

}
