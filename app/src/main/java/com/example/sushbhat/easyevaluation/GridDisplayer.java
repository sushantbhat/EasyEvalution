package com.example.sushbhat.easyevaluation;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class GridDisplayer extends AppCompatActivity {

    EditText[][] marksArray;
    TextView[] totalArray;
    TextView pageTotalDisp;
    Button btnPageTotal, btnGrandTotal;
    boolean newPage = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grid_displayer);
        marksArray = new EditText[7][11];
        totalArray = new TextView[8];
        pageTotalDisp = (TextView) findViewById(R.id.pageTotalRes);
        btnPageTotal = (Button) findViewById(R.id.btnPageTotal);
        btnGrandTotal = (Button) findViewById(R.id.btnGrandTotal);
        btnPageTotal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clearEditTextFocus();
                hideKeyboard(view);
                setPageTotal();
            }
        });
        btnGrandTotal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateMarks();
            }
        });
        initMarks();
        initControls();
        updateMarks();
    }



    private void clearEditTextFocus(){
        for(int i=0; i<7; i++) {
            for (int j = 1; j < 11; j++) {
                marksArray[i][j].clearFocus();
            }
        }
    }

    private void hideKeyboard(View view) {
        InputMethodManager inputMethodManager =(InputMethodManager)getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    private void setPageTotal(){
        if(!pageTotalDisp.getText().toString().equals("")) {
            String numArray[] = pageTotalDisp.getText().toString().split("\\+");
            double pageTotal = 0;
            for (String num : numArray) {
                pageTotal += Double.parseDouble(num);
            }
            pageTotalDisp.setText(new Double(pageTotal).toString());
            newPage = true;
        }
        updateMarks();
    }

    private double getTotal(int rowNo){
        double total = 0;
        for(int j=1; j<11 ; j++){
            if(marksArray[rowNo][j].getText().toString().equals("") || (marksArray[rowNo][j].getText().toString() == null)){}
            else
                total += Double.parseDouble(marksArray[rowNo][j].getText().toString());
        }
        return total;
    }

    private void updateMarks(){
        for(int i=0 ; i<7 ; i++){
            totalArray[i].setText(new Double(getTotal(i)).toString());
        }
        setGrandTotal();
    }

    private void setGrandTotal(){
        double total = 0;
        for(int i=0 ; i<7; i++){
            if(! (totalArray[i].getText().toString().isEmpty() && totalArray[i].getText().toString().equals(" ")))
                total += Double.parseDouble(totalArray[i].getText().toString());
        }
        totalArray[7].setText(new Double(total).toString());
    }

    private void updatePageTotal(String num){
        if(pageTotalDisp.getText().toString().equals("") || newPage == true){
            pageTotalDisp.setText(num);
            newPage = false;
        }
        else{
            pageTotalDisp.setText(pageTotalDisp.getText().toString() + " + " + num);
        }
    }

    private void initControls(){
        for(int i=0; i<7; i++) {
            for(int j=1; j<11; j++) {
                final int a = i, b = j;
                String editTextId = "editText" + new Integer(i*10 + j).toString();
                String nextId = "editText" + new Integer(i*10 + j + 1).toString();
                int resID = getResources().getIdentifier(editTextId, "id", getPackageName());
                int nextResId = getResources().getIdentifier(nextId, "id", getPackageName());
                marksArray[i][j] = ((EditText) findViewById(resID));

                marksArray[i][j].setNextFocusDownId(nextResId);
                marksArray[i][j].setOnFocusChangeListener(new View.OnFocusChangeListener() {
                    @Override
                    public void onFocusChange(View view, boolean hasFocus) {
                        if(hasFocus){
                            marksArray[a][b].setText("");
                        }
                        if(!hasFocus){
                            if(marksArray[a][b].getText().toString().equals("")){
                                marksArray[a][b].setText("0");
                            }
                            updateMarks();
                            updatePageTotal(marksArray[a][b].getText().toString());
                        }
                    }
                });

            }
        }
    }



    @Override
    public void onBackPressed(){
        updateMarks();
    }

    private void initMarks(){
        for(int i=0; i<8 ; i++){
            String textViewId = "T" + new Integer(i+1).toString();
            int resID = getResources().getIdentifier(textViewId, "id", getPackageName());
            totalArray[i] = ((TextView) findViewById(resID));
            totalArray[i].setText("0");
        }


    }
}
