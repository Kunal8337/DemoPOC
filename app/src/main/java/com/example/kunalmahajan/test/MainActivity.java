package com.example.kunalmahajan.test;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observable;
import rx.Observer;
import rx.Scheduler;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.schedulers.Schedulers;
import rx.schedulers.Schedulers;
import rx.schedulers.Schedulers;
import rx.schedulers.Schedulers;
import rx.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {


    @BindView(R.id.edtDataSource)
    EditText edtDataSource;
    @BindView(R.id.btnClick)
    Button btnClick;
    @BindView(R.id.txtHello)
    TextView txtHello;

    StringBuffer stringBuffer;
    Observable<StringBuffer> database;
    Observer<StringBuffer> observer;
    Subscription subscription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        stringBuffer = new StringBuffer();

        database = Observable      //Observable. This will emit the data
                .just(stringBuffer);    //Operator


    }

    private void setUpObserver() {

         observer = new Observer<StringBuffer>() {
            @Override
            public void onCompleted() {
                Log.d("test", "Completed.......");
            }

            @Override
            public void onError(Throwable e) {
                Log.d("test", "Some Error Occured......");
            }

            @Override
            public void onNext(StringBuffer s) {
                Log.d("test", "Data :: " + s);

                txtHello.setText(s);

            }
        };

        subscription = database.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    @OnClick(R.id.btnClick)
    public void onClick() {
        String s = edtDataSource.getText().toString();
        if (!s.isEmpty()){
            stringBuffer.append(s);
            edtDataSource.setText("");
            setUpObserver();
            Toast.makeText(this, "added", Toast.LENGTH_LONG).show();
        }else {
            Toast.makeText(this, "Please Enter Some Text and then retry.", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //if (!subscription.isUnsubscribed()) {
        subscription.unsubscribe();
        Log.d("test", "Subscription is released......");
        //}
    }
}
