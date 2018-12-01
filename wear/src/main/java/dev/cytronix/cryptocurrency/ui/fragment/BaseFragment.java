package dev.cytronix.cryptocurrency.ui.fragment;

import android.app.Fragment;
import android.content.Context;
import androidx.annotation.StringRes;
import android.widget.Toast;

import dev.cytronix.cryptocurrency.ui.activity.BaseActivity;

public class BaseFragment extends Fragment {

    protected BaseActivity baseActivity;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        baseActivity = (BaseActivity) getActivity();
    }

    @SuppressWarnings("SameParameterValue")
    protected void showToast(@StringRes int resId) {
        Toast.makeText(getContext(), resId, Toast.LENGTH_LONG).show();
    }
}
