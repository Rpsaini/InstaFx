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
//
//public class BTCFragment extends Fragment {
//
//    private View view;
//    public static MarketAdapter btcAdapter;
//
//    public BTCFragment() {
//        // Required empty public constructor
//    }
//
//
//    public static BTCFragment newInstance(String param1, String param2) {
//        BTCFragment fragment = new BTCFragment();
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
//    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        view= inflater.inflate(R.layout.fragment_b_t_c, container, false);
//        try {
//            Bundle bundle=getArguments();
//           // JSONArray btctAr=new JSONArray(bundle.getString("btcAr"));
//
//            JSONArray jsonArray=new JSONArray();
//            for(int x = 0; x< CurrencyPagerAdapter.btcAr.size(); x++)
//            {
//                Map<String,JSONObject> dataMap = CurrencyPagerAdapter.btcAr.get(x);
//                for(Map.Entry<String,JSONObject> map:dataMap.entrySet())
//                {
//                    jsonArray.put(map.getValue());
//                }
//            }
//            init(jsonArray);
//
//
//        }
//        catch (Exception e)
//        {
//            e.printStackTrace();
//        }
//       return view;
//    }
//
//    private void init(JSONArray dataObj)
//    {
//
//
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
//
//
//        btcAdapter = new MarketAdapter(dataObj,(MainActivity) getActivity());
//        LinearLayoutManager horizontalLayoutManagaer
//                = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
//        recycler_view_market.setLayoutManager(horizontalLayoutManagaer);
//        recycler_view_market.setItemAnimator(new DefaultItemAnimator());
//        recycler_view_market.setAdapter(btcAdapter);
//
//    }
//
//}