//package com.web.whatashot.fragments;
//
//import android.os.Bundle;
//
//import androidx.fragment.app.Fragment;
//import androidx.recyclerview.widget.DefaultItemAnimator;
//import androidx.recyclerview.widget.LinearLayoutManager;
//import androidx.recyclerview.widget.RecyclerView;
//
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.RelativeLayout;
//
//import com.web.whatashot.MainActivity;
//import com.web.whatashot.R;
//import com.web.whatashot.adapters.CurrencyPagerAdapter;
//import com.web.whatashot.adapters.MarketAdapter;
//
//import org.json.JSONArray;
//import org.json.JSONObject;
//
//import java.util.Map;
//
//public class INRFragment extends Fragment {
//
//   private View view;
//   public static  MarketAdapter inrAdapter;
//    public INRFragment() {
//        // Required empty public constructor
//    }
//
//    public static INRFragment newInstance(String param1, String param2) {
//        INRFragment fragment = new INRFragment();
//
//        return fragment;
//    }
//
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//
//    }
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        // Inflate the layout for this fragment
//        view=inflater.inflate(R.layout.fragment_i_n_r, container, false);
//        try {
//            Bundle bundle=getArguments();
//          //  JSONArray inrtAr=new JSONArray(bundle.getString("inrAr"));
//            //init(inrtAr);
//            JSONArray jsonArray=new JSONArray();
//            for(int x = 0; x< CurrencyPagerAdapter.inrAr.size(); x++)
//            {
//                Map<String,JSONObject> dataMap = CurrencyPagerAdapter.inrAr.get(x);
//                for(Map.Entry<String,JSONObject> map:dataMap.entrySet())
//                {
//                    jsonArray.put(map.getValue());
//                }
//            }
//            init(jsonArray);
//        }
//        catch (Exception e)
//        {
//            e.printStackTrace();
//        }
//
//
//        return view;
//    }
//
//    private void init(JSONArray dataObj)
//    {
//        RecyclerView recycler_view_market =view.findViewById(R.id.recycler_view_market);
//        RelativeLayout relativeLayout =view.findViewById(R.id.rr_nodata_view);
//        if(dataObj.length()==0)
//        {
//            relativeLayout.setVisibility(View.VISIBLE);
//            recycler_view_market.setVisibility(View.GONE);
//        }
//        else
//        {
//            relativeLayout.setVisibility(View.GONE);
//            recycler_view_market.setVisibility(View.VISIBLE);
//        }
//        inrAdapter = new MarketAdapter(dataObj,(MainActivity)getActivity());
//        LinearLayoutManager horizontalLayoutManagaer
//                = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
//        recycler_view_market.setLayoutManager(horizontalLayoutManagaer);
//        recycler_view_market.setItemAnimator(new DefaultItemAnimator());
//        recycler_view_market.setAdapter(inrAdapter);
//
//    }
//}