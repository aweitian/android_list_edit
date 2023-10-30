package com.example.listedit;

import android.app.Activity;
import android.graphics.Color;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class ReceiptListAdapter extends ArrayAdapter<String>  {
    private int selectedEditTextPosition = -1;
    public int lastPosition = -1;
    private final Activity context;

    private Set<String> hash;
    private HashMap<String, Float> hashRcv = new HashMap<>();

    private JSONArray detailList;


//    private TextWatcher mTextWatcher = new TextWatcher() {
//        @Override
//        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
//
//        @Override
//        public void onTextChanged(CharSequence s, int start, int before, int count) {
//            int position = selectedEditTextPosition;
//            if (selectedEditTextPosition != -1) {
//                Log.w("MyAdapter", "onTextCha " + selectedEditTextPosition);
////                ItemTest itemTest = getItem(selectedEditTextPosition);
//////                itemTest.setText(s.toString());
////                MyAdapter.this.a = s.toString();
//                //listData.set(selectedEditTextPosition,s.toString());
//                Log.d("ddddd", "onTextChanged: "+s.toString());
//
//                try {
//                    JSONArray detailList = result.getJSONArray("DetailList");
//                    String INV_ITEM_Code = detailList.getJSONObject(position).getString("INV_ITEM_Code");
//                    String shipNumber = detailList.getJSONObject(position).getString("ShipNumber");
////                    rcvNumber.setText(hashRcv.containsKey(INV_ITEM_Code) ? hashRcv.get(INV_ITEM_Code) + "" : detailList.getJSONObject(position).getString("ShipNumber"));
////                    rcvNumber.setHint(detailList.getJSONObject(position).getString("RecNumber"));
////                    rcvNumber.setSelection(rcvNumber.length());
//                    float cur = 0;
////                    String s = rcvNumber.getText() + "";
//                    if (s.length() > 0) {
//                        cur = Float.parseFloat(s + "");
//                    }
//
//                    float max = Float.parseFloat(shipNumber);
//                    if (cur > max) {
////                        rcvNumber.setText(max + "");
//                        cur = max;
//                    }
//                    hashRcv.put(INV_ITEM_Code, cur);
//                    detailList.getJSONObject(position).put("RecNumber",cur);
//                    //Toast.makeText(context,"lost focus",Toast.LENGTH_SHORT).show();
//                    Log.d("LOST FOCUS", hash.toString());
//                } catch (Exception e) {
//                }
//            }
//        }
//
//        @Override
//        public void afterTextChanged(Editable editable) {}
//    };
//    @Override
//    public void onClick(View v) {
//        if (v.getId() == R.id.item_root) {
//            int position = (int) v.getTag(R.id.item_root);
//            Toast.makeText(getContext(), "点击第 " + position + " 个item", Toast.LENGTH_SHORT).show();
//        }
//    }
    //这个监测TEXT失去焦点
//    @Override
//    public void onFocusChange(View view, boolean b) {
//        EditText editText = (EditText) view;
//        if (b) {
//            editText.addTextChangedListener(mTextWatcher);
//        } else {
//            editText.removeTextChangedListener(mTextWatcher);
//        }
//    }

//    @Override
//    public boolean onTouch(View view, MotionEvent motionEvent) {
//        if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
//            EditText editText = (EditText) view;
//            lastPosition = selectedEditTextPosition = (int) editText.getTag();
//            Log.w("MyAdapter", "clicked position: " + selectedEditTextPosition);
//        }
//        return false;
//    }

    //https://www.freesion.com/article/88301234238/
    public ReceiptListAdapter(Activity context, JSONArray data){
        super(context, R.layout.list_item_receipt,getTitle(data));
        this.context = context;
        detailList = data;
        this.hash = new HashSet<>();
    }

    private static String[] getTitle(JSONArray data){
        String[] d = new String[data.length()];
        for (int i = 0; i < data.length(); i++) {
            d[i] = "";
//            try {
//                d[i] = data.getJSONObject(i).getString("");
//            } catch (JSONException e) {
//                throw new RuntimeException(e);
//            }
        }
        return d;
    }

    public View getView(int position, View rowView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        if(rowView == null){
            rowView = inflater.inflate(R.layout.list_item_receipt, null, true);
        }

        TextView name = rowView.findViewById(R.id.name);
        TextView id = rowView.findViewById(R.id.id);
        TextView shipNumber = rowView.findViewById(R.id.tv_shipNum);
        EditText rcvNumber = rowView.findViewById(R.id.tv_rcvNum);
        rcvNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                //detailList.getJSONObject(position).getString("ShipNumber")
                try {
                    detailList.getJSONObject(position).put("RecNumber",s.toString());
                } catch (JSONException e) {
                    //throw new RuntimeException(e);
                }
            }
        });
//        rcvNumber.setOnTouchListener(this); // 正确写法
//        rcvNumber.setOnFocusChangeListener(this);
        rcvNumber.setTag(position);
//        vh.editText.requestFocus();
//        if (selectedEditTextPosition != -1 && position == selectedEditTextPosition) { // 保证每个时刻只有一个EditText能获取到焦点
//            rcvNumber.requestFocus();
//        } else {
//            rcvNumber.clearFocus();
//        }

        String INV_ITEM_Code = "";
        try {

            name.setText(detailList.getJSONObject(position).getString("INV_ITEM_Name"));
            INV_ITEM_Code = detailList.getJSONObject(position).getString("INV_ITEM_Code");
            id.setText("物料编号:".concat(INV_ITEM_Code));
            shipNumber.setText("发货数量:".concat(detailList.getJSONObject(position).getString("ShipNumber")));
            rcvNumber.setText(detailList.getJSONObject(position).getString("RecNumber"));
            rcvNumber.setHint(detailList.getJSONObject(position).getString("RecNumber"));
            rcvNumber.setSelection(rcvNumber.length());
            rowView.setTag(R.id.item_root, position);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (hash.contains(INV_ITEM_Code)) {
            rowView.setBackgroundColor(context.getResources().getColor(R.color.pass));
            name.setTextColor(context.getResources().getColor(R.color.white));
            id.setTextColor(context.getResources().getColor(R.color.white));
            shipNumber.setTextColor(context.getResources().getColor(R.color.white));
            rcvNumber.setTextColor(context.getResources().getColor(R.color.white));
//            rcvNumber.setText(hashRcv.get(INV_ITEM_Code) + "");
        } else {
            rowView.setBackgroundColor(context.getResources().getColor(R.color.white));
            name.setTextColor(context.getResources().getColor(R.color.listTitle));
            id.setTextColor(context.getResources().getColor(R.color.listItem));
            shipNumber.setTextColor(context.getResources().getColor(R.color.listItem));
            rcvNumber.setTextColor(context.getResources().getColor(R.color.listItem));
        }
        //滚动到某行
//        if (Receipt.lastMaterialCode.equals(INV_ITEM_Code)) {
//            rowView.setSelected(true);
//        }

        return rowView;
    }
}
