package com.myprac.advanced.android.activity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.myprac.advanced.android.databinding.ContentSecondBinding;
import com.myprac.advanced.android.viewmodel.UserViewModel;
import com.myprac.advanced.android.R;

public class SecondActivity extends AppCompatActivity {

    private EditText ed1;
    private EditText ed2;
    private ContentSecondBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View rootView = getLayoutInflater().inflate(R.layout.content_second, null, false);
        binding = DataBindingUtil.bind(rootView);
        setContentView(rootView);
        binding.setUserModel(new UserViewModel());
        binding.executePendingBindings();

        ed1 = findViewById(R.id.et1_et);
        ed1.addTextChangedListener(new MyTextWatcher1());
        ed2 = findViewById(R.id.et2_et);
        ed2.addTextChangedListener(new MyTextWatcher2());
    }

    public void btnClick(View view){
        binding.getUserModel().setUserEmail(ed1.getText().toString());
        binding.getUserModel().setUserName(ed2.getText().toString());
    }

    class MyTextWatcher1 implements TextWatcher{

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            binding.getUserModel().setUserEmail(String.valueOf(s));
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    }

    class MyTextWatcher2 implements TextWatcher{

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            binding.getUserModel().setUserName(String.valueOf(s));
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    }
}
