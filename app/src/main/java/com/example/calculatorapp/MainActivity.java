package com.example.calculatorapp;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import com.google.android.material.button.MaterialButton;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.Scriptable;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    TextView result, solution;
    MaterialButton buttonOpenBracket, buttonCloseBracket, buttonC;
    MaterialButton buttonAddition, buttonSubtraction, buttonEqual, buttonMultiply, buttonDivide;
    MaterialButton button0, button1, button2, button3, button4, button5, button6, button7, button8, button9;
    MaterialButton buttonAC, buttonPoint;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        result = findViewById(R.id.result);
        solution = findViewById(R.id.solution);

        assignId(buttonC,R.id.button_c);
        assignId(buttonOpenBracket,R.id.button_openbracket);
        assignId(buttonCloseBracket,R.id.button_closebracket);
        assignId(buttonDivide,R.id.button_divide);
        assignId(buttonMultiply,R.id.button_multiply);
        assignId(buttonAddition,R.id.button_addition);
        assignId(buttonSubtraction,R.id.button_subtraction);
        assignId(buttonEqual,R.id.button_equal);
        assignId(button0,R.id.button_0);
        assignId(button1,R.id.button_1);
        assignId(button2,R.id.button_2);
        assignId(button3,R.id.button_3);
        assignId(button4,R.id.button_4);
        assignId(button5,R.id.button_5);
        assignId(button6,R.id.button_6);
        assignId(button7,R.id.button_7);
        assignId(button8,R.id.button_8);
        assignId(button9,R.id.button_9);
        assignId(buttonAC,R.id.button_ac);
        assignId(buttonPoint,R.id.button_point);
    }

    void assignId(MaterialButton btn, int id){
        btn = findViewById(id);
        btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        MaterialButton button =(MaterialButton) v;
        String buttonText = button.getText().toString();
        String dataToCalculate = solution.getText().toString();
        if(buttonText.equals("AC")){
            //To clear everything
            solution.setText("");
            result.setText("0");
            return;
        }
        if(buttonText.equals("=")){
            solution.setText(result.getText());
            return;
        }
        if(buttonText.equals("C")){
            //To clear last character
            dataToCalculate = dataToCalculate.substring(0, dataToCalculate.length()-1);
        }else{
            dataToCalculate = dataToCalculate+buttonText;
        }
        solution.setText(dataToCalculate);

        String finalResult = getResult(dataToCalculate);
        if(!finalResult.equals("Err")){
            result.setText(finalResult);
        }
    }

    String getResult(String data){
        try{
            Context context = Context.enter();
            context.setOptimizationLevel(-1);
            Scriptable scriptable = context.initSafeStandardObjects();
            String finalResult = context.evaluateString(scriptable,data,"Javascript",1,null).toString();
            if(finalResult.endsWith(".0")){
                finalResult = finalResult.replace(".0","");
            }
            return finalResult;
        }catch (Exception e){
            return "Err";
        }
    }
}