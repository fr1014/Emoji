package com.example.emoji;

import androidx.annotation.IdRes;
import androidx.annotation.IntegerRes;
import androidx.annotation.LayoutRes;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

/**
 * 创建时间:2020/6/23
 * 作者:fr
 * 邮箱:1546352238@qq.com
 */
public class NavigationViewModel extends ViewModel {

    private MutableLiveData<Integer> fragmentResLiveData;

    public MutableLiveData<Integer> getFragmentResLiveData() {
        if (fragmentResLiveData == null){
            fragmentResLiveData = new MutableLiveData<>();
            select(R.id.navigation_home);
        }
        return fragmentResLiveData;
    }

    public void select(@IdRes int layoutRes) {
        fragmentResLiveData.setValue(layoutRes);
    }
}
