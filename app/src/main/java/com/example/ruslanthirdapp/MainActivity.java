package com.example.ruslanthirdapp;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    double currentResult = 0;
    String operation = "";
    boolean isNewOp = true;
    StringBuilder currentExpression = new StringBuilder();

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

        Button num0 = findViewById(R.id.num0);
        Button num1 = findViewById(R.id.num1);
        Button num2 = findViewById(R.id.num2);
        Button num3 = findViewById(R.id.num3);
        Button num4 = findViewById(R.id.num4);
        Button num5 = findViewById(R.id.num5);
        Button num6 = findViewById(R.id.num6);
        Button num7 = findViewById(R.id.num7);
        Button num8 = findViewById(R.id.num8);
        Button num9 = findViewById(R.id.num9);

        Button del = findViewById(R.id.del);
        Button ac = findViewById(R.id.ac);
        Button divide = findViewById(R.id.div);
        Button square_root = findViewById(R.id.square_root);
        Button multiply = findViewById(R.id.multiply);
        Button subtraction = findViewById(R.id.subtraction);
        Button sum = findViewById(R.id.sum);
        Button equal = findViewById(R.id.equal);
        Button dot = findViewById(R.id.dot);
        Button sign_change = findViewById(R.id.sign_change);

        TextView screen = findViewById(R.id.screen);

        ArrayList<Button> nums = new ArrayList<>();
        nums.add(num0);
        nums.add(num1);
        nums.add(num2);
        nums.add(num3);
        nums.add(num4);
        nums.add(num5);
        nums.add(num6);
        nums.add(num7);
        nums.add(num8);
        nums.add(num9);


        for (Button b : nums) {
            b.setOnClickListener(view -> {
                if (isNewOp) {
                    screen.setText(b.getText().toString());
                    isNewOp = false;
                } else {
                    screen.setText(screen.getText().toString() + b.getText().toString());
                }
                currentExpression.append(b.getText().toString());
            });
        }

        ArrayList<Button> operations = new ArrayList<>();
        operations.add(del);
        operations.add(ac);
        operations.add(square_root);
        operations.add(subtraction);
        operations.add(multiply);
        operations.add(divide);
        operations.add(equal);
        operations.add(dot);
        operations.add(sign_change);
        operations.add(sum);


        for (Button b : operations) {
            b.setOnClickListener(view -> {
                String input = screen.getText().toString();
                if (!input.isEmpty()) {
                    double secondNum = Double.parseDouble(input);
                    if (!operation.isEmpty()) {
                        currentResult = calculate(currentResult, secondNum, operation);
                    } else {
                        currentResult = secondNum;
                    }
                    operation = b.getText().toString();

                    currentExpression.append(operation);
                    screen.setText(currentExpression.toString());

                    isNewOp = true;
                }
            });
        }

        // Del button (delete last character)
        del.setOnClickListener(view -> {
            String num = screen.getText().toString();
            if (num.length() > 1) {
                screen.setText(num.substring(0, num.length() - 1));
                currentExpression.setLength(currentExpression.length() - 1);
            } else {
                screen.setText("0");
                currentExpression.setLength(0);
            }
        });

        // Dot button
        dot.setOnClickListener(view -> {
            if (!screen.getText().toString().contains(".")) {
                screen.setText(screen.getText().toString() + ".");
                currentExpression.append(".");
            }
        });

        // Equal button (calculate and show result)
        equal.setOnClickListener(view -> {
            String input = screen.getText().toString();
            if (!input.isEmpty()) {
                double secondNum = Double.parseDouble(input);
                currentResult = calculate(currentResult, secondNum, operation);
                screen.setText(String.valueOf(currentResult));

                currentExpression.setLength(0);
                currentExpression.append(currentResult);
                operation = "";
                isNewOp = true;
            }
        });

        // AC button (clear everything)
        ac.setOnClickListener(view -> {
            currentResult = 0;
            operation = "";
            screen.setText("0");
            currentExpression.setLength(0);
            isNewOp = true;
        });

        // Sign change button
        sign_change.setOnClickListener(view -> {
            String input = screen.getText().toString();
            if (!input.isEmpty()) {
                double currentNum = Double.parseDouble(input);
                currentNum = -currentNum; // Change the sign
                screen.setText(String.valueOf(currentNum));

                if (currentExpression.length() > 0) {
                    currentExpression.setLength(0); // Reset and update the expression
                    currentExpression.append(currentNum);
                }
            }
        });

        // Square root button
        square_root.setOnClickListener(view -> {
            String input = screen.getText().toString();
            if (!input.isEmpty()) {
                double currentNum = Double.parseDouble(input);
                currentNum = Math.sqrt(currentNum);
                screen.setText(String.valueOf(currentNum));

                currentExpression.setLength(0);
                currentExpression.append(currentNum);
            }
        });
    }

    // Calculation logic
    private double calculate(double firstNum, double secondNum, String operation) {
        switch (operation) {
            case "/":
                return firstNum / secondNum;
            case "*":
                return firstNum * secondNum;
            case "-":
                return firstNum - secondNum;
            case "+":
                return firstNum + secondNum;
            default:
                return secondNum;
        }
    }
}
