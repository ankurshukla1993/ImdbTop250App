package autosms.ankur.com.imdbtop250;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.google.gson.Gson;
import com.lsjwzh.widget.materialloadingprogressbar.CircleProgressBar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends ActionBarActivity  implements AbsListView.OnScrollListener, ConnectivityReceiver.ConnectivityReceiverListener {

    private String TAG = "MainActivity.getData method.OnResponse()" ;
    private ListView listView;
    private Toolbar toolbar;
    private TextView floatTitle;
    private ImageView headerBg;

    private float headerHeight;
    private float minHeaderHeight;
    private float floatTitleLeftMargin;
    private float floatTitleSize;
    private float floatTitleSizeLarge;
    private CircleProgressBar progress2;
    private List<MovieObject> movieList ;
    private Gson gson ;
    private CustomListAdapter adapter ;

    private List<MovieObject> crime ;
    private List<MovieObject> action ;
    private List<MovieObject> biography ;
    private List<MovieObject> western ;
    private List<MovieObject> drama ;
    private List<MovieObject> comedy ;
    private List<MovieObject> animation ;
    private List<MovieObject> romance ;
    private List<MovieObject> adventure ;
    private List<MovieObject> horror ;
    private List<MovieObject> mystery ;
    private List<MovieObject> movielist_d ;

    private FloatingActionMenu materialDesignFAM;
    private FloatingActionButton floatingActionButton1, floatingActionButton2, floatingActionButton3, floatingActionButton4;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Remove title bar
        //this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        /*//Remove notification bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);*/

        setContentView(R.layout.activity_main);

        gson = new Gson();
        movieList = new ArrayList<MovieObject>() ;
        movielist_d = new ArrayList<MovieObject>() ;

        crime = new ArrayList<MovieObject>() ;
        action = new ArrayList<MovieObject>() ;
        biography = new ArrayList<MovieObject>() ;
        western = new ArrayList<MovieObject>() ;
        drama = new ArrayList<MovieObject>() ;
        comedy = new ArrayList<MovieObject>() ;
        animation = new ArrayList<MovieObject>() ;
        romance = new ArrayList<MovieObject>() ;
        adventure = new ArrayList<MovieObject>() ;
        horror = new ArrayList<MovieObject>() ;
        mystery = new ArrayList<MovieObject>() ;

        materialDesignFAM = (FloatingActionMenu) findViewById(R.id.material_design_android_floating_action_menu);
        floatingActionButton1 = (FloatingActionButton) findViewById(R.id.material_design_floating_action_menu_item1);
        floatingActionButton2 = (FloatingActionButton) findViewById(R.id.material_design_floating_action_menu_item2);
        floatingActionButton3 = (FloatingActionButton) findViewById(R.id.material_design_floating_action_menu_item3);
        floatingActionButton4 = (FloatingActionButton) findViewById(R.id.material_design_floating_action_menu_item4);

        adapter = new CustomListAdapter(this, movieList);
        progress2 = (CircleProgressBar) findViewById(R.id.progress2);
        progress2.setColorSchemeResources(android.R.color.holo_green_light, android.R.color.holo_orange_light, android.R.color.holo_red_light);
        progress2.setVisibility(View.VISIBLE);
        if(checkConnection())
            getData("http://imdbserver-arpitdixit.rhcloud.com/ListServer?task=d") ;
        //getData("http://192.168.1.125:8080/ImdbServer/ListServer?task=d") ;

        floatingActionButton1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                //Toast.makeText(MainActivity.this, "movieList = " + movieList.size(), Toast.LENGTH_SHORT).show();
                adapter = new CustomListAdapter(MainActivity.this,adventure);
                listView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }
        });
        floatingActionButton2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                //Toast.makeText(MainActivity.this, "movieList = " + movieList.size(), Toast.LENGTH_SHORT).show();
                adapter = new CustomListAdapter(MainActivity.this,action);
                listView.setAdapter(adapter);
                adapter.notifyDataSetChanged();

            }
        });
        floatingActionButton3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                //Toast.makeText(MainActivity.this, "movieList = " + movieList.size(), Toast.LENGTH_SHORT).show();
                adapter = new CustomListAdapter(MainActivity.this,crime);
                listView.setAdapter(adapter);
                adapter.notifyDataSetChanged();

            }
        });

        floatingActionButton4.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                //Toast.makeText(MainActivity.this, "movieList = " + movieList.size(), Toast.LENGTH_SHORT).show();
                adapter = new CustomListAdapter(MainActivity.this,movieList);
                listView.setAdapter(adapter);
                adapter.notifyDataSetChanged();

            }
        });


        initMeasure();
        initView();
        initListViewHeader();
        initListView();
        initEvent();


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Toast.makeText(MainActivity.this, "Description : \n" + movieList.get(position - 1).getDescription(), Toast.LENGTH_SHORT).show();
                /*Toast.makeText(MainActivity.this, "crime = " + crime.size() + "\n" +
                        "action = " + action.size() + "\n" +
                        "biography = " + biography.size() + "\n" +
                        "western = " + western.size() + "\n" +
                        "drama = " + drama.size() + "\n" +
                        "comedy = " + comedy.size() + "\n" +
                        "animation = " + animation.size() + "\n" +
                        "romance = " + romance.size() + "\n" +
                        "adventure = " + adventure.size() + "\n" +
                        "horror = " + horror.size() + "\n" +
                        "mystery = " + mystery.size() + "\n", Toast.LENGTH_SHORT).show();*/
            }
        });


        
    }

    private void getData(String urlJsonArry) {

        //Toast.makeText(MainActivity.this, "Progress Dialog", Toast.LENGTH_SHORT).show();

        JsonArrayRequest req = new JsonArrayRequest(urlJsonArry,
                new Response.Listener<JSONArray>() {

                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d(TAG, response.toString());

                        try {
                            // Parsing json array response
                            // loop through each json object
                            for (int i = 0; i < response.length(); i++) {

                                JSONObject movieJson = (JSONObject) response.get(i);
                                MovieObject movie = gson.fromJson(String.valueOf(movieJson), MovieObject.class);
                                String g = movie.getGenre() ;
                                switch (g){
                                    case "Crime," :
                                        movie.setGenre("Crime");
                                        crime.add(movie) ;
                                        break;
                                    case "Action," :
                                        movie.setGenre("Action");
                                        action.add(movie) ;
                                        break;
                                    case "Biography," :
                                        movie.setGenre("Biography");
                                        biography.add(movie) ;
                                        break;
                                    case "Western," :
                                        movie.setGenre("Western");
                                        western.add(movie) ;
                                        break;
                                    case "Drama," :
                                        movie.setGenre("Drama");
                                        drama.add(movie) ;
                                        break;
                                    case "Comedy," :
                                        movie.setGenre("Comedy");
                                        comedy.add(movie) ;
                                        break;
                                    case "Animation," :
                                        movie.setGenre("Animation");
                                        animation.add(movie) ;
                                        break;
                                    case "Romance," :
                                        movie.setGenre("Romance");
                                        romance.add(movie) ;
                                        break;
                                    case "Adventure," :
                                        movie.setGenre("Adventure");
                                        adventure.add(movie) ;
                                        break;
                                    case "Horror," :
                                        movie.setGenre("Horror");
                                        horror.add(movie) ;
                                        break;
                                    case "Mystery," :
                                        movie.setGenre("Mystery");
                                        mystery.add(movie) ;
                                        break;
                                }
                                movieList.add(movie) ;
                                int progress = (i/250)*100 ;
                                progress2.setProgress(progress);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(),
                                    "Error: " + e.getMessage(),
                                    Toast.LENGTH_LONG).show();
                        }
                        adapter.notifyDataSetChanged();
                        Toast.makeText(MainActivity.this, "Size = " + movieList.size(), Toast.LENGTH_SHORT).show();
                        progress2.setVisibility(View.INVISIBLE);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_SHORT).show();
                progress2.setVisibility(View.VISIBLE);
            }
        });

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(req);


    }

    private void initMeasure() {
        headerHeight = getResources().getDimension(R.dimen.header_height);
        minHeaderHeight = getResources().getDimension(R.dimen.abc_action_bar_default_height_material);
        floatTitleLeftMargin = getResources().getDimension(R.dimen.float_title_left_margin);
        floatTitleSize = getResources().getDimension(R.dimen.float_title_size);
        floatTitleSizeLarge = getResources().getDimension(R.dimen.float_title_size_large);
    }

    private void initView() {
        listView = (ListView) findViewById(R.id.lv_main);
        floatTitle = (TextView) findViewById(R.id.tv_main_title);
        toolbar = (Toolbar) findViewById(R.id.tb_main);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    private void initListView() {
        //ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.activity_list_item, android.R.id.text1, data);
        listView.setAdapter(adapter);
    }

    private void initListViewHeader() {
        View headerContainer = LayoutInflater.from(this).inflate(R.layout.header, listView, false);
        headerBg = (ImageView) headerContainer.findViewById(R.id.img_header_bg);
        headerBg.setImageResource(R.mipmap.header_bg1);
        listView.addHeaderView(headerContainer);
    }

    private void initEvent() {
        listView.setOnScrollListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {

    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

        float scrollY = getScrollY(view);


        float headerBarOffsetY = headerHeight - minHeaderHeight;//Toolbar与header高度的差值
        float offset = 1 - Math.max((headerBarOffsetY - scrollY) / headerBarOffsetY, 0f);


        toolbar.setBackgroundColor(Color.argb((int) (offset * 255), 0, 0, 0));

        headerBg.setTranslationY(scrollY / 2);



        floatTitle.setPivotX(floatTitle.getLeft() + floatTitle.getPaddingLeft());

        float titleScale = floatTitleSize / floatTitleSizeLarge;

        floatTitle.setTranslationX(floatTitleLeftMargin * offset);
        floatTitle.setTranslationY(
                (-(floatTitle.getHeight() - minHeaderHeight) +//-缩放高度差
                        floatTitle.getHeight() * (1 - titleScale))//大文字与小文字高度差
                        / 2 * offset +
                        (headerHeight - floatTitle.getHeight()) * (1 - offset));//Y轴滑动偏移

        floatTitle.setScaleX(1 - offset * (1 - titleScale));

        floatTitle.setScaleY(1 - offset * (1 - titleScale));

        
        if (scrollY > headerBarOffsetY) {
            toolbar.setTitle(getResources().getString(R.string.toolbar_title));
            floatTitle.setVisibility(View.GONE);
        } else {
            toolbar.setTitle("");
            floatTitle.setVisibility(View.VISIBLE);
        }
    }


    public float getScrollY(AbsListView view) {
        View c = view.getChildAt(0);

        if (c == null)
            return 0;

        int firstVisiblePosition = view.getFirstVisiblePosition();
        int top = c.getTop();

        float headerHeight = 0;
        if (firstVisiblePosition >= 1)
            headerHeight = this.headerHeight;

        return -top + firstVisiblePosition * c.getHeight() + headerHeight;
    }

    @Override
    protected void onResume() {
        super.onResume();

        // register connection status listener
        AppController.getInstance().setConnectivityListener(this);
    }

    @Override
    public void onNetworkConnectionChanged(boolean isConnected) {

        if(!isConnected)
            Toast.makeText(MainActivity.this, "No Internet Connection....", Toast.LENGTH_LONG).show();
        else
            getData("http://imdbserver-arpitdixit.rhcloud.com/ListServer?task=d");


    }

    private boolean checkConnection() {
        boolean isConnected = ConnectivityReceiver.isConnected();
        if(!isConnected)
            Toast.makeText(MainActivity.this, "No Internet Connection !!", Toast.LENGTH_SHORT).show();

        return isConnected ;
    }

}