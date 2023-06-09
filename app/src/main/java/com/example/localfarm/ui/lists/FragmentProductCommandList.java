package com.example.localfarm.ui.lists;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.localfarm.R;
import com.example.localfarm.adapteur.ProductCommandItemAdapter;
import com.example.localfarm.models.command.ProductOrder;

import java.util.ArrayList;
import java.util.List;

/**
 * Pour utiliser le fragment, mettre ca dans le script appelant (dans le onCreate):
 *
 * <pre>{@code
 * FragmentProductCommandList fragment = FragmentProductCommandList.newInstance(null);
 * getSupportFragmentManager().beginTransaction()
 * .replace(R.id.ListProductOrders_ProductCommandList, fragment)
 * .commit();
 * }</pre>

 */
public class FragmentProductCommandList extends Fragment {

    private static final String PRODUCT_COMMAND_LIST = "param1";

    private List<ProductOrder> mParam1;
    //Ne pas completer
    public FragmentProductCommandList() {}

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @return A new instance of fragment FragmentProductList.
     */
    public static FragmentProductCommandList newInstance(List<ProductOrder> param1) {
        FragmentProductCommandList fragment = new FragmentProductCommandList();
        Bundle args = new Bundle();
        args.putParcelableArrayList(PRODUCT_COMMAND_LIST, new ArrayList<>(param1));
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getParcelableArrayList(PRODUCT_COMMAND_LIST);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView =  inflater.inflate(R.layout.fragment_product_command_list, container, false);
        System.out.println(mParam1!=null?"list size = "+mParam1.size():"mParam1 is null");
        List<ProductOrder> productList = (mParam1!=null && mParam1.size()!=0) ? mParam1 : new ArrayList<>();
        // Inflate the layout for this fragment
        ProductCommandItemAdapter adapter = new ProductCommandItemAdapter(getActivity(),productList);
        ListView List = rootView.findViewById(R.id.FragmentProductList_productList);
        List.setAdapter(adapter);
        return rootView;
    }

    @Override
    public void onDestroy() {
        System.out.println("FragmentProductCommandList destroyed "+mParam1!=null?"of size: "+mParam1.size():"without list");
        super.onDestroy();
    }
}