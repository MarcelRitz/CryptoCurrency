package dev.cytronix.cryptocurrency.ui.fragment;

import android.app.Fragment;
import android.content.Context;

import dev.cytronix.cryptocurrency.ui.activity.BaseActivity;

public class BaseFragment extends Fragment {

    protected BaseActivity baseActivity;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        baseActivity = (BaseActivity) getActivity();
    }
}
