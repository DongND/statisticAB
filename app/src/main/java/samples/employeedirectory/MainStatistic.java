package samples.employeedirectory;

import android.app.Activity;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Admin on 11/25/2017.
 */

public class MainStatistic extends Activity {

    private Map<String, Integer> mapDefinition;

    protected SQLiteDatabase db;

    protected List<String> listInput = new ArrayList<String>();

    public void init(){
        Map<String, Integer> mapInitial = new HashMap<String, Integer>();
        mapInitial.put("AAAA",1);
        mapInitial.put("AAAB",2);
        mapInitial.put("AABA",3);
        mapInitial.put("AABB",4);
        mapInitial.put("ABAA",5);
        mapInitial.put("ABAB",6);
        mapInitial.put("ABBA",7);
        mapInitial.put("ABBB",8);
        mapInitial.put("BAAA",9);
        mapInitial.put("BAAB",10);
        mapInitial.put("BBAA",11);
        mapInitial.put("BBAB",12);
        mapInitial.put("BBBA",13);
        mapInitial.put("BBBB",14);
        mapInitial.put("BABA",15);
        mapInitial.put("BABB",16);

        this.mapDefinition = mapInitial;
        this.listInput = getAllFromDb();

    }

    /**
     *
     * @return
     */
    public List<String> getAllFromDb(){

        Cursor cursor = db.rawQuery("SELECT ab FROM statistic",null);

        cursor.moveToFirst();

        final List<String> list = new ArrayList<String>();
        try {
            while(cursor.moveToNext()) {
                list.add(cursor.getString(cursor.getColumnIndex("ab")));
            }
        } finally {
            cursor.close();
        }
        return list;
    }

    /**
     *
     * @param db
     * @param value
     * @return
     */
    public boolean writeToDb(SQLiteDatabase db, String value){
        String sql = "insert into statistic (ab) values('"+value+"');";
        try {
            db.execSQL(sql);
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        db = (new DatabaseHelper(this)).getWritableDatabase();
        db.execSQL("CREATE TABLE IF NOT EXISTS statistic (_id INTEGER PRIMARY KEY AUTOINCREMENT, ab VARCHAR(1))");
        init();

        // When click to buttonB
        final Button buttonA = (Button) findViewById(R.id.buttonA);
        final Button buttonB = (Button) findViewById(R.id.buttonB);
        final TextView lanVuaNhap = (TextView) findViewById(R.id.lanGanNhat);
        final TextView lan2 = (TextView) findViewById(R.id.lan2);
        final TextView lan3 = (TextView) findViewById(R.id.lan3);

        final TextView nextA = (TextView) findViewById(R.id.nextA);
        final TextView nextB = (TextView) findViewById(R.id.nextB);
        final TextView per1A = (TextView) findViewById(R.id.per1A);
        final TextView per1B = (TextView) findViewById(R.id.per1B);
        final TextView per2A = (TextView) findViewById(R.id.per2A);
        final TextView per2B = (TextView) findViewById(R.id.per2B);
        final TextView per3A = (TextView) findViewById(R.id.per3A);
        final TextView per3B = (TextView) findViewById(R.id.per3B);

        final TextView header2 = (TextView) findViewById(R.id.header2);

        final TextView header3 = (TextView) findViewById(R.id.header3);

        //Display state when init
        String lanGanNhat = StatisticApp.buildTextDisplay(listInput);
        lanVuaNhap.setText("         " + lanGanNhat);

        String lan2Display = "";
        if (listInput.size() >=5){
            lan2Display = StatisticApp.buildTextDisplay(StatisticApp.getSublist(listInput,0, listInput.size()-1));
        }
        lan2.setText(lan2Display);

        String lan3Display = "";
        if (listInput.size() >= 6){
            lan3Display = StatisticApp.buildTextDisplay(StatisticApp.getSublist(listInput,0, listInput.size()-2));
        }
        lan3.setText(lan3Display);

        Map<String, Long> mapResult = StatisticApp.calculatePercentage(listInput);

        if (mapResult != null){
            per1A.setText(String.valueOf(mapResult.get("1A")) + " %");
            per1B.setText(String.valueOf(mapResult.get("1B")) + " %");
            per2A.setText(String.valueOf(mapResult.get("2A")) + " %");
            per2B.setText(String.valueOf(mapResult.get("2B")) + " %");
            per3A.setText(String.valueOf(mapResult.get("3A")) + " %");
            per3B.setText(String.valueOf(mapResult.get("3B")) + " %");

            String nA = StatisticApp.getLastInput(listInput, 3)+ "A";
            String nB = StatisticApp.getLastInput(listInput, 3) + "B";
            nextA.setText( mapDefinition.get(nA) + " ( " + nA +" )");
            nextB.setText( mapDefinition.get(nB) + " ( " + nB +" )");
        }


        // Event handle
        buttonA.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                String text = buttonA.getText().toString().trim();

                if (writeToDb(db, text)){
                    listInput.add(text);
                }

                String lanGanNhat = StatisticApp.buildTextDisplay(listInput);
                lanVuaNhap.setText("         " +lanGanNhat);

                String lan2Display = "";
                if (listInput.size() >=5){
                    lan2Display = StatisticApp.buildTextDisplay(StatisticApp.getSublist(listInput,0, listInput.size()-1));
                }
                lan2.setText(lan2Display);

                String lan3Display = "";
                if (listInput.size() >= 6){
                    lan3Display = StatisticApp.buildTextDisplay(StatisticApp.getSublist(listInput,0, listInput.size()-2));
                }
                lan3.setText(lan3Display);


                Map<String, Long> mapResult = StatisticApp.calculatePercentage(listInput);

                if (mapResult != null){
                    per1A.setText(String.valueOf(mapResult.get("1A")) + " %");
                    per1B.setText(String.valueOf(mapResult.get("1B")) + " %");
                    per2A.setText(String.valueOf(mapResult.get("2A")) + " %");
                    per2B.setText(String.valueOf(mapResult.get("2B")) + " %");
                    per3A.setText(String.valueOf(mapResult.get("3A")) + " %");
                    per3B.setText(String.valueOf(mapResult.get("3B")) + " %");

                    String nA = StatisticApp.getLastInput(listInput, 3)+ "A";
                    String nB = StatisticApp.getLastInput(listInput, 3) + "B";
                    nextA.setText( mapDefinition.get(nA) + " ( " + nA +" )");
                    nextB.setText( mapDefinition.get(nB) + " ( " + nB +" )");
                }


            }
        });

        buttonB.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                String text = buttonB.getText().toString().trim();

                if (writeToDb(db, text)){
                    listInput.add(text);
                }

                String lanGanNhat = StatisticApp.buildTextDisplay(listInput);
                lanVuaNhap.setText("         " + lanGanNhat);

                String lan2Display = "";
                if (listInput.size() >=5){
                    lan2Display = StatisticApp.buildTextDisplay(StatisticApp.getSublist(listInput, 0, listInput.size()-1));
                }
                lan2.setText(lan2Display);

                String lan3Display = "";
                if (listInput.size() >= 6){
                    lan3Display = StatisticApp.buildTextDisplay(StatisticApp.getSublist(listInput, 0, listInput.size()-2));
                }
                lan3.setText(lan3Display);

                Map<String, Long> mapResult = StatisticApp.calculatePercentage(listInput);

                if (mapResult != null){
                    per1A.setText(String.valueOf(mapResult.get("1A")) + " %");
                    per1B.setText(String.valueOf(mapResult.get("1B")) + " %");
                    per2A.setText(String.valueOf(mapResult.get("2A")) + " %");
                    per2B.setText(String.valueOf(mapResult.get("2B")) + " %");
                    per3A.setText(String.valueOf(mapResult.get("3A")) + " %");
                    per3B.setText(String.valueOf(mapResult.get("3B")) + " %");

                    String nA = StatisticApp.getLastInput(listInput, 3)+ "A";
                    String nB = StatisticApp.getLastInput(listInput, 3) + "B";
                    nextA.setText( mapDefinition.get(nA) + " ( " + nA +" )");
                    nextB.setText( mapDefinition.get(nB) + " ( " + nB +" )");
                }
            }
        });


    }

}
