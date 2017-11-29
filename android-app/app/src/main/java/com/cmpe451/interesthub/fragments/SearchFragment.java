package com.cmpe451.interesthub.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.cmpe451.interesthub.InterestHub;
import com.cmpe451.interesthub.R;
import com.cmpe451.interesthub.activities.GroupActivity;
import com.cmpe451.interesthub.adapters.CustomAdapter;
import com.cmpe451.interesthub.models.Group;
import com.cmpe451.interesthub.models.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SearchFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SearchFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SearchFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static ArrayAdapter groupAdapter;
    private static ArrayAdapter userAdapter;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private InterestHub hub;
    private HashMap<String,Group> groupHashMap;
    private HashMap<String,User> userHashMap;
    final List<Group> groupList = new ArrayList<>();
    final List<User> userList = new ArrayList<>();

    private OnFragmentInteractionListener mListener;
    List<String> groups,users;
    public SearchFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SearchFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SearchFragment newInstance(String param1, String param2) {
        SearchFragment fragment = new SearchFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        hub = (InterestHub) getActivity().getApplication();






    }

    private void setGroupAdapter(View view) {
        groups = new ArrayList<String>();
        for(int i = 0; i< groupList.size(); i++)
            groups.add(groupList.get(i).getName());

        ListView listView = view.findViewById(R.id.list_view_search_groups);
        groupAdapter= new CustomAdapter(getContext(),android.R.layout.simple_list_item_1, android.R.id.text1, groups);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int pos, long id) {

                Group selected = groupHashMap.get(parent.getAdapter().getItem(pos));
                Intent intent = new Intent(getContext(), GroupActivity.class);
                intent.putExtra("groupName", String.valueOf(selected.getName()));
                intent.putExtra("groupId", selected.getId());
                intent.putExtra("groupImg", selected.getLogo());
                startActivity(intent);


            }
        });
        listView.setAdapter(groupAdapter);



    }
    private void setUserAdapter(View view) {
        users = new ArrayList<String>();
        for(int i = 0; i< userList.size(); i++)
            users.add(userList.get(i).getUsername());
        ListView listView = view.findViewById(R.id.list_view_search_users);
        userAdapter= new CustomAdapter(getContext(),android.R.layout.simple_list_item_1, android.R.id.text1, users);
        listView.setAdapter(userAdapter);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        final View view = inflater.inflate(R.layout.fragment_search, container, false);

        hub.getApiService().getGroup().enqueue(new Callback<List<Group>>() {
            @Override
            public void onResponse(Call<List<Group>> call, Response<List<Group>> response) {
                if(response.body()!=null)
                    for(Group g: response.body())
                        groupList.add(g);
                setGroupHashMap(groupList);
                setGroupAdapter(view);
            }

            @Override
            public void onFailure(Call<List<Group>> call, Throwable t) {

            }
        });
        hub.getApiService().getUsers().enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                if(response ==null || response.body() == null) return;
                for(User u : response.body())
                    userList.add(u);
                setUserHashMap(userList);
                setUserAdapter(view);
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {

            }
        });

        Log.d("SEARCH","listview");
        return view;

    }

    private void setGroupHashMap(List<Group> list) {
        groupHashMap = new HashMap<String,Group>();
        for(Group g : list)
            groupHashMap.put(g.getName(),g);
    }

    private void setUserHashMap(List<User> list) {
        userHashMap = new HashMap<String ,User>();
        for(User g : list)
            userHashMap.put(g.getUsername(),g);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    public static void changeAdapter(String text){

        groupAdapter.getFilter().filter(text);
        userAdapter.getFilter().filter(text);
    }

}
