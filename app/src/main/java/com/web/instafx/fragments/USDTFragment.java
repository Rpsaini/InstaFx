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
//import android.widget.Toast;
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
//
//public class USDTFragment extends Fragment {
//
//    public static  MarketAdapter usdtAdapter;
//    private View  view;
//    public USDTFragment() {
//        // Required empty public constructor
//    }
//
//    public static USDTFragment newInstance(String param1, String param2) {
//
//
//        USDTFragment fragment = new USDTFragment();
//        Bundle args = new Bundle();
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
//         view=inflater.inflate(R.layout.fragment_u_s_d_t, container, false);
//
//        Toast.makeText(getActivity(),"Selected index=="+getArguments().getString("pos"),Toast.LENGTH_LONG).show();
//         try {
//             Bundle bundle=getArguments();
////             JSONArray usdtAr=new JSONArray(bundle.getString("usdtAr"));
//             JSONArray jsonArray=new JSONArray();
//             for(int x=0;x<CurrencyPagerAdapter.usdtAr.size();x++)
//             {
//                 Map<String,JSONObject> dataMap = CurrencyPagerAdapter.usdtAr.get(x);
//                 for(Map.Entry<String,JSONObject> map:dataMap.entrySet())
//                 {
//                     jsonArray.put(map.getValue());
//                 }
//             }
//             init(jsonArray);
//
//         }
//         catch (Exception e)
//         {
//             e.printStackTrace();
//         }
//
//
//
//        return view;
//    }
//
//
//    private void init(JSONArray usdatAr)
//    {
//        RecyclerView recycler_view_market =view.findViewById(R.id.recycler_view_market);
//        RelativeLayout relativeLayout =view.findViewById(R.id.rr_nodata_view);
//        if(usdatAr.length()==0)
//        {
//            relativeLayout.setVisibility(View.VISIBLE);
//            recycler_view_market.setVisibility(View.GONE);
//        }
//        else
//        {
//            relativeLayout.setVisibility(View.GONE);
//            recycler_view_market.setVisibility(View.VISIBLE);
//        }
//        usdtAdapter = new MarketAdapter(usdatAr,(MainActivity) getActivity());
//        LinearLayoutManager horizontalLayoutManagaer
//                = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
//        recycler_view_market.setLayoutManager(horizontalLayoutManagaer);
//        recycler_view_market.setItemAnimator(new DefaultItemAnimator());
//        recycler_view_market.setAdapter(usdtAdapter);
//
//    }
//}