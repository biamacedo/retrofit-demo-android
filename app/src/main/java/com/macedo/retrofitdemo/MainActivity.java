package com.macedo.retrofitdemo;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.macedo.retrofitdemo.controller.GitHubService;
import com.macedo.retrofitdemo.model.GitModel;

import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

public class MainActivity extends AppCompatActivity {

    public Button click;
    public TextView tv;
    public EditText edit_user;
    public ProgressBar pbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/

        click = (Button) findViewById(R.id.button);
        tv = (TextView) findViewById(R.id.tv);
        edit_user = (EditText) findViewById(R.id.edit);
        pbar = (ProgressBar) findViewById(R.id.pb);
        pbar.setVisibility(View.INVISIBLE);

        click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user = edit_user.getText().toString();
                Log.d("Retrofit", user);
                pbar.setVisibility(View.VISIBLE);

                //Retrofit section start from here...                                 //create an adapter for retrofit with base url
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(GitHubService.BASE_URI)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                GitHubService service = retrofit.create(GitHubService.class);                            //creating a service for adapter with our GET class

                //Now ,we need to call for response
                //Retrofit using gson for JSON-POJO conversion

                Call<GitModel> call = service.getFeed(user);
                call.enqueue(new Callback<GitModel>() {
                    @Override
                    public void onResponse(Response<GitModel> response, Retrofit retrofit) {
                        Log.d("Retrofit", response.message());
                        if (response.body() != null) {
                            GitModel gitModel = response.body();

                            String name;
                            String location;
                            String email;
                            String blog;
                            String company;
                            String hireable;
                            String publicRepos;
                            String followers;
                            String following;
                            String bio;
                            if(gitModel.getName()!= null){name = gitModel.getName();} else {name = "";}
                            if(gitModel.getLocation()!= null){location = gitModel.getLocation();} else {location = "";}
                            if(gitModel.getEmail()!= null){email = gitModel.getEmail();} else {email = "";}
                            if(gitModel.getBlog()!= null){blog = gitModel.getBlog();} else {blog = "";}
                            if(gitModel.getCompany()!= null){company = gitModel.getCompany();} else {company = "";}
                            if(gitModel.getHireable()!= null){
                                if (gitModel.getHireable()) {hireable = "Yes";} else {hireable = "No";}
                            } else {hireable = "";}
                            if(gitModel.getPublicRepos()!= null){publicRepos = gitModel.getPublicRepos().toString();} else {publicRepos = "";}
                            if(gitModel.getFollowers()!= null){followers = gitModel.getFollowers().toString();} else {followers = "";}
                            if(gitModel.getFollowing()!= null){following = gitModel.getFollowing().toString();} else {following = "";}
                            if(gitModel.getBio()!= null){bio = gitModel.getBio().toString();} else {bio = "";}

                            Resources res = getResources();
                            String userText = String.format(res.getString(R.string.user_info),
                                    name, location, email, blog, company, hireable, publicRepos, followers, following, bio);
                            tv.setText(userText);

                        } else {
                            tv.setText(R.string.user_not_found);
                        }
                        pbar.setVisibility(View.INVISIBLE);
                    }

                    @Override
                    public void onFailure(Throwable t) {
                        tv.setText(t.getMessage());
                        pbar.setVisibility(View.INVISIBLE);
                    }
                });
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        /*if (id == R.id.action_settings) {
            return true;
        }*/

        return super.onOptionsItemSelected(item);
    }
}
