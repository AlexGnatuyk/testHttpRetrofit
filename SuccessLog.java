package com.sancho.clienttest;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.POST;
import retrofit.RestAdapter;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;

import com.androidquery.AQuery;


public class SuccessLog extends ActionBarActivity {

    TextView text;
    TextView uInfo;
    SharedPreferences sPref;
    SharedPreferences sToken;
    user [] users;
    String jwtToken;

    public class user {
        private String id;
        private String username;
        private String email;
        private String group;
        private String sex;
        private String avatar;
        private String firstname;
        private String secondname;

        public user(String id, String username, String email, String group,String sex,String avatar, String firstname, String secondname){
            this.id = id;
            this.username = username;
            this.email = email;
            this.group = group;
            this.sex = sex;
            this.avatar = avatar;
            this.firstname = firstname;
            this.secondname = secondname;
        }

        public user(){}

        public String getId() {return id;}
        public void setId(String id) {this.id = id;}
        public String getUsername() {return username;}
        public void setUsername(String username) {this.username = username;}
        public String getEmail() {return email;}
        public void setEmail(String email) {this.email = email;}
        public String getGroup(){return group;}
        public void setGroup(String group) {this.group = group;}
        public String getSex() {return sex;}
        public void setSex(String sex) {this.sex = sex;}
        public String getAvatar() {return avatar;}
        public void setAvatar(String avatar) {this.avatar = avatar;}
        public String getFirstname() {return firstname;}
        public void setFirstname(String firstname) {this.firstname = firstname;}
        public String getSecondname() {return secondname;}
        public void setSecondname(String secondname) {this.secondname = secondname;}



    }

    public interface Api{
        public static final String URL ="http://178.62.42.66/api/v1";
        static final String USERS = "/users/";


        @GET(USERS)
       user [] users(@Header("Authorization") String jwtToken );

    }

    private void getUsers(String jwtToken){
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(Api.URL)
                .build();
        Api api = restAdapter.create(Api.class);
        users = api.users(jwtToken);
        //api.users(jwtToken);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.success_log);
        AQuery aq = new AQuery(this);
        aq.id(R.id.image1).image("http://178.62.42.66/static/images/avatars/default_avatar.png");
        text = (TextView) findViewById(R.id.textView5);
        uInfo = (TextView) findViewById(R.id.textView6);

        sPref = getSharedPreferences("MyPref",MODE_PRIVATE);
        String savedText = sPref.getString("token","");
        jwtToken = "JWT "+savedText;


        Runnable runnable = new Runnable() {
            public void run() {

                getUsers(jwtToken);
            }
        };
        Thread thread = new Thread(runnable);
        thread.start();


       // user [] users;
        uInfo.setText(users[0].getUsername()); //users[0].getUsername();
        text.setText(savedText);


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_success_log, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
