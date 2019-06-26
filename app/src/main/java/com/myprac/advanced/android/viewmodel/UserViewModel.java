package com.myprac.advanced.android.viewmodel;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import com.myprac.advanced.android.BR;
import com.myprac.advanced.android.model.User;

public class UserViewModel extends BaseObservable {
    private final User user;

    public UserViewModel(){
        user = new User();
    }

    @Bindable
    public String getUserEmail() {
        return user.getEmail();
    }

    @Bindable
    public String getUserName(){
        return user.getName();
    }

    public void setUserName(String name) {
        this.user.setName(name);
        notifyPropertyChanged(BR.userName);
    }

    public void setUserEmail(String email){
        this.user.setEmail(email);
        notifyPropertyChanged(BR.userEmail);
    }
}
