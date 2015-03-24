package example;

import java.util.ArrayList;
import java.util.List;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import net.londatiga.android.example.R;
import twitter.Twitter;
import twitter.TwitterRequest;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class UserActivity extends BaseActivity {
	private Twitter mTwitter;
	ListView list;
    LazyAdapter adapter;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_user);
		list=(ListView)findViewById(android.R.id.list);
		mTwitter = new Twitter(this, MainActivity.CONSUMER_KEY, MainActivity.CONSUMER_SECRET, MainActivity.CALLBACK_URL);
		
		((Button) findViewById(R.id.btn_logout)).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				mTwitter.clearSession();
				
				clearCredential();
				
				startActivity(new Intent(getActivity(), MainActivity.class));
				
				finish();
				showToast("Logged out of the app.");
			}
		});
		updateStatus("Hi");

	}

	private void updateStatus(String status) {
		final ProgressDialog progressDlg = new ProgressDialog(this);
		
		progressDlg.setMessage("Fetching Followers List...");
		progressDlg.setCancelable(false);
		
		progressDlg.show();
		
		TwitterRequest request 		= new TwitterRequest(mTwitter.getConsumer(), mTwitter.getAccessToken());
		
		String updateStatusUrl		= "https://api.twitter.com/1.1/followers/list.json";
		
		List<NameValuePair> params 	= new ArrayList<NameValuePair>(1);
		
		params.add(new BasicNameValuePair("name", "Naukri"));
		
		request.createRequest("GET", updateStatusUrl, params, new TwitterRequest.RequestListener() {
			@SuppressLint("NewApi")
			@Override
			public void onSuccess(String response) {
				progressDlg.dismiss();
				int length =0;
				try {
					int i=0;
					JSONObject jsonObject = new JSONObject(response);
					JSONArray jsonArray = jsonObject.getJSONArray("users");
					length = jsonArray.length();
					String []name = new String[length];
					String []screenName = new String[length];
					String []id = new String[length];
					for(i=0;i<length;i++){
						JSONObject object = jsonArray.getJSONObject(i);
						name[i] = object.getString("name");
						screenName[i] = object.getString("screen_name");
						id[i] = object.getString("id");
					}
					setAdapter(name, screenName, id);
				} catch (JSONException e) {
					e.printStackTrace();
				}
				
				showToast("Twitter Followers list.");
			}
			
			@Override
			public void onError(String error) {
				showToast(error);
				progressDlg.dismiss();
			}
		});
	}
	private void setAdapter(String [] name, String [] screenName, String [] id) {
		adapter=new LazyAdapter(this, name, screenName, id);
        list.setAdapter(adapter);
	}
}