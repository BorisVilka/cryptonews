package org.cryptonews.main.ui.coin;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;

import org.cryptonews.main.databinding.FragmentCoinBinding;
import org.cryptonews.main.ui.activities.MainActivity;
import org.cryptonews.main.ui.list_utils.ListItem;


public class CoinFragment extends Fragment {

    private FragmentCoinBinding binding;
    private ListItem item;
    private int position;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        item = (ListItem) getArguments().getSerializable("Coin");
        position = getArguments().getInt("position");
        ((MainActivity)getActivity()).setTitle(item.getCoin().getName());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentCoinBinding.inflate(inflater);
        binding.setItem(item);
        Picasso.get().load(item.getInfo().getLogo()).resize(150,150).into(binding.logo);
         return binding.getRoot();
    }

    public static CoinFragment getInstance(Bundle args) {
        CoinFragment fragment = new CoinFragment();
        fragment.setArguments(args);
        return fragment;
    }
}