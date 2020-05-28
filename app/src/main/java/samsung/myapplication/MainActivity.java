package samsung.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    String[] building = {"경상관","교육관", "문화관", "미래관", "백마관", "베어드홀","벤처중소기업센터", "안익태기념관", "전산관", "정보과학관", "조만식기념관", "중앙도서관", "진리관","학생회관", "한경직기념관", "형남공학관"};
    String[] time = {"1교시 (09:00~10:15)","2교시 (10:30~11:45)","3교시 (12:00~13:15)","4교시 (13:30~14:45)","5교시 (15:00~16:15)","6교시 (16:30~17:45)","7교시 (18:00~19:15)","8교시 (19:30~20:45)","9교시 (21:00~22:15)"};
    public static final int REQUEST_CODE_MENU = 101;

    String[] send = new String[2];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final TextView textView1 = (TextView) findViewById(R.id.textView2);
        final Spinner spinner1 = (Spinner) findViewById(R.id.spinner1);

        ArrayAdapter<String> adapter1 = new ArrayAdapter(this, android.R.layout.simple_spinner_item, building);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner1.setAdapter(adapter1);

        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                 send[0] = spinner1.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                textView1.setText("건물을 고르세요");
            }
        });

        final TextView textView2 = (TextView) findViewById(R.id.textView3);
        final Spinner spinner2 = (Spinner) findViewById(R.id.spinner2);

        ArrayAdapter<String> adapter2 = new ArrayAdapter(this, android.R.layout.simple_spinner_item, time);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner2.setAdapter(adapter2);

        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                send[1] = spinner2.getSelectedItem().toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                textView2.setText("건물을 고르세요");
            }
        });
    }
    public void onStartbuttonClicked (View v)
    {
        Intent intent = new Intent(getApplicationContext(), MapsActivity.class);

        intent.putExtra("building",send[0]);
        intent.putExtra("time",send[1]);
        startActivityForResult(intent,REQUEST_CODE_MENU);

    }
}
