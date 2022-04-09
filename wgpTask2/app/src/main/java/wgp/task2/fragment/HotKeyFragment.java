package wgp.task2.fragment;

import static wgp.task2.MainActivity.fragmentList;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.GridView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import java.util.ArrayList;

import wgp.task2.MainActivity;
import wgp.task2.R;

public class HotKeyFragment extends Fragment {
    FrameLayout frameLayout;
    Fragment homeFragment;
//    ArrayList<Fragment> fragmentList;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.hotkey_fragment, null);
        frameLayout = view.findViewById(R.id.hotkey_fragment_container);
        System.out.println("onCreateView");
        showHomeFragment();
        return view;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        System.out.println("onAttach");
    }

    @Override
    public void onHiddenChanged(boolean hidden) { // hide changed show 调用这个方法
        super.onHiddenChanged(hidden);
        showHomeFragment();
    }

    public void showHomeFragment() {
        System.out.println("showHomeFragment now");
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        if (homeFragment == null) {
            homeFragment = new HotKeyHomeFragment();
            fragmentList.add(homeFragment);
            transaction.add(R.id.fragmentContainer, homeFragment);
        }
        hideAllFragments(transaction);
        transaction.show(homeFragment);
        transaction.commit();
    }
    public void hideAllFragments(FragmentTransaction transaction) {
        for (Fragment frag : fragmentList) {
            if (frag != null) {
                transaction.hide(frag);
            }
        }
    }
}
