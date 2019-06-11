package com.example.kioskclient;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class PaymentActivity extends AppCompatActivity {

    private TextView storeName;
    private TextView menu;
    private TextView totalCost;
    private int int_total;
    private Button payBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        storeName = findViewById(R.id.payment_store_name);
        menu = findViewById(R.id.payment_menu_name);
        totalCost = findViewById(R.id.payment_cost);
        payBtn = findViewById(R.id.payment_button);


        final Intent intent = getIntent();
        storeName.setText(intent.getStringExtra("StoreName"));
        menu.setText(intent.getStringExtra("FirstMenu")+"외 " + intent.getIntExtra("menuCount",0)+"개");
        int_total = intent.getIntExtra("TotalCost",0);
        totalCost.setText(""+int_total+"원");
        payBtn.setText("결제 : "+int_total);

        payBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder alert_confirm = new AlertDialog.Builder(PaymentActivity.this);

                int data = int_total;

                if (data <= 0){
                    Toast.makeText(PaymentActivity.this, "결제할 내역이 없습니다.", Toast.LENGTH_SHORT).show();
                    return;
                }
                alert_confirm.setMessage(""+data+"원 결제하시겠습니까?");
                alert_confirm.setPositiveButton("확인", new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(PaymentActivity.this, "결제 되었습니다. 주문내역을 확인해 주세요.", Toast.LENGTH_SHORT).show();
                    }
                });
                alert_confirm.setNegativeButton("취소", null);
                AlertDialog alert = alert_confirm.create();
                alert.setTitle("결제");
                alert.show();
            }
        });

    }
}
