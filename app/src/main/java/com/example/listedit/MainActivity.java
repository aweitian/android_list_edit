package com.example.listedit;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ListView listView;
    private Button button;

    private JSONObject result;

    private ReceiptListAdapter listAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = findViewById(R.id.list_view);
        button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    test();
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        try {
            String data = data();
            result = new JSONObject(data);
            showList(result);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    private void showList(JSONObject result) throws JSONException {
        listView.setVisibility(View.VISIBLE);
        listAdapter = new ReceiptListAdapter(this, result.getJSONArray("DetailList"));
        ArrayAdapter<String> adapter = listAdapter;
        listView.setAdapter(adapter);
    }

    private void test() throws JSONException {
        for (int i = 0; i < result.getJSONArray("DetailList").length(); i++) {
            String string = result.getJSONArray("DetailList").getJSONObject(i).getString("RecNumber");
            System.out.println(string);
        }
    }

    private String data()
    {
        return "{\n" +
                "    \"DetailList\":[\n" +
                "        {\n" +
                "            \"INV_ITEM_Name\":\"abcc\",\n" +
                "            \"INV_ITEM_Code\":\"efg\",\n" +
                "            \"ShipNumber\":\"11\",\n" +
                "            \"RecNumber\":\"0\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"INV_ITEM_Name\":\"abcc\",\n" +
                "            \"INV_ITEM_Code\":\"efg\",\n" +
                "            \"ShipNumber\":\"11\",\n" +
                "            \"RecNumber\":\"0\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"INV_ITEM_Name\":\"abcc\",\n" +
                "            \"INV_ITEM_Code\":\"efg\",\n" +
                "            \"ShipNumber\":\"11\",\n" +
                "            \"RecNumber\":\"0\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"INV_ITEM_Name\":\"abcc\",\n" +
                "            \"INV_ITEM_Code\":\"efg\",\n" +
                "            \"ShipNumber\":\"11\",\n" +
                "            \"RecNumber\":\"0\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"INV_ITEM_Name\":\"qqq\",\n" +
                "            \"INV_ITEM_Code\":\"44nfc\",\n" +
                "            \"ShipNumber\":\"6\",\n" +
                "            \"RecNumber\":\"0\"\n" +
                "        }\n" +
                "    ]\n" +
                "}";
    }
}