package com.example.calculator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.Scriptable;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    // Textview
    TextView resultTv, queryTv;

    // Calculator buttons
    Button buttonBackSpace;
    Button buttonDivide, buttonMultiply, buttonMinus, buttonPlus, buttonPercentage, buttonEquals;
    Button button0, button1, button2, button3, button4, button5, button6, button7, button8, button9;
    Button buttonC, buttonDot;

    String res;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // assigning ids to both views
        resultTv = findViewById(R.id.result_tv);

        queryTv = findViewById(R.id.query_tv);

        // assigning ids to buttons
        assignId(buttonC, R.id.button1);
        assignId(buttonDivide, R.id.button2);
        assignId(buttonMultiply, R.id.button3);
        assignId(buttonBackSpace, R.id.button4);
        assignId(button7, R.id.button5);
        assignId(button8, R.id.button6);
        assignId(button9, R.id.button7);
        assignId(buttonMinus, R.id.button8);
        assignId(button4, R.id.button9);
        assignId(button5, R.id.button10);
        assignId(button6, R.id.button11);
        assignId(buttonPlus, R.id.button12);
        assignId(button1, R.id.button13);
        assignId(button2, R.id.button14);
        assignId(button3, R.id.button15);
        assignId(buttonEquals, R.id.button16);
        assignId(buttonPercentage, R.id.button17);
        assignId(button0, R.id.button18);
        assignId(buttonDot, R.id.button19);

    }

    // function to initialize button with ids
    public void assignId(Button btn, int id){
        btn = findViewById(id);

        // same function call set on every button when clicked
        btn.setOnClickListener(this);


    }

    @Override
    public void onClick(View view) {
//      we will get whatever button is clicked
        Button button = (Button) view;

        // from this button we will extract the text of the button
        String buttonText = button.getText().toString();

        // set the text to the query text view
//        queryTv.setText(buttonText);
        // so that we can know our buttons are working properly

        String dataToCalculate = queryTv.getText().toString();

        // All clear button
        if(buttonText.equals("C")){
            resultTv.setText("");
            queryTv.setText("");
            return;
        }
        // Backspace button
        else if(buttonText.equals("⌫")){
            if((dataToCalculate.length())>0){
                queryTv.setText(dataToCalculate.substring(0, dataToCalculate.length()-1));

                // if query ends with a number then keep calculating result from function otherwise clear the result text view and return
//                if(dataToCalculate.endsWith("[0-9]")){
//                if(Character.isDigit(dataToCalculate.charAt(dataToCalculate.length()-1))){
                    // continue
//                    String finalResult = getResult(dataToCalculate);
//
//                    if(!finalResult.equals("Err")){
//                        resultTv.setText(finalResult);
//                    }

                    if(queryTv.getText().toString().isEmpty()){
                        resultTv.setText("");
                    }

                    // new data to calculate
                    dataToCalculate = dataToCalculate.substring(0, dataToCalculate.length()-1);
//                    return;
                }
                else{
                    return;
                }
            }
//            else{
//                resultTv.setText("");
//                return;
//            }

        // equals to button
        else if(buttonText.equals("=")){
            if(!(resultTv.getText().toString().equals(""))) {
                res = resultTv.getText().toString();
                resultTv.setText("");
                queryTv.setText(res);


            }
            return;

        }
        // if any other button is pressed
        else{

            // if any number button is pressed and the query text view is not empty(means on empty field number entered), and the result text view last character is a digit and has the result calculated text then clear the query text view
            System.out.println(res);
            System.out.println(queryTv.getText().toString());
//            System.out.println(queryTv.getText().equals(res));

            if(!queryTv.getText().toString().isEmpty()) {
                if (Character.isDigit(buttonText.charAt(0))) {
                    if (queryTv.getText().equals(res)) {
                        queryTv.setText("");
                        dataToCalculate = "";
                        res = "";
                    }
                }
            }

            // concatenate already present query in query text view with the button text in query text view
            dataToCalculate = dataToCalculate+buttonText;

            queryTv.setText(dataToCalculate);

        }



//        if(!queryTv.getText().toString().equals("")){

        // if query ends with a number then keep calculating result from function otherwise clear the result text view and return
//        if(dataToCalculate.endsWith("[0-9]")){
        if(!dataToCalculate.isEmpty()) {
            if (Character.isDigit(dataToCalculate.charAt(dataToCalculate.length() - 1))) {
                String finalResult = getResult(dataToCalculate);

                if (!finalResult.equals("Err")) {
                    resultTv.setText(finalResult);
                }
            } else {
                resultTv.setText("");
            }
        }

//        }
    }

    // to calculate result of the query
    String getResult(String data){
        try{
            Context context = Context.enter();
            context.setOptimizationLevel(-1);
            Scriptable scriptable = context.initStandardObjects();
            String finalResult = context.evaluateString(scriptable, data, "Javascript", 1, null).toString();

            if(finalResult.endsWith(".0")){
                finalResult = finalResult.replace(".0", "");
            }

            return finalResult;
        }
        catch(Exception e){
            return "Err";
        }
    }
}