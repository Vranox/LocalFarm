package com.example.localfarm.activity;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.localfarm.R;
import com.example.localfarm.adapteur.ProductItemAdapter;
import com.example.localfarm.data.Products;

import java.util.List;

/**
 * Pour utiliser le fragment, mettre ca dans le script appelant (dans le onCreate):
 *
 * <pre>{@code
 * FragmentProductList fragment = FragmentProductList.newInstance(null);
 * getSupportFragmentManager().beginTransaction()
 * .replace(R.id.ListProductOrders_ProductCommandList, fragment)
 * .commit();
 * }</pre>

 */
public class FragmentProductList extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private List<Products> mParam1;

    public FragmentProductList() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @return A new instance of fragment FragmentProductList.
     */
    public static FragmentProductList newInstance(String param1) {
        FragmentProductList fragment = new FragmentProductList();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getParcelable(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView =  inflater.inflate(R.layout.fragment_product_list, container, false);
        List<Products> productList = (mParam1!=null && mParam1.size()!=0) ? mParam1 :Products.staticList();
        // Inflate the layout for this fragment
        ProductItemAdapter adapter = new ProductItemAdapter(getActivity(),productList);
        ListView List = rootView.findViewById(R.id.FragmentProductList_list);
        List.setAdapter(adapter);
        return rootView;
    }
}