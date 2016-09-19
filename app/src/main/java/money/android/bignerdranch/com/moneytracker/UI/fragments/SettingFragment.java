package money.android.bignerdranch.com.moneytracker.UI.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import money.android.bignerdranch.com.moneytracker.R;

/**
 * Created by User on 19.09.2016.
 */
public class SettingFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       View rootView = inflater.inflate(R.layout.setting_fragment, container, false);
        return rootView;
    }
}
