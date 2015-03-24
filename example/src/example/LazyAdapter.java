package example;

import net.londatiga.android.example.R;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class LazyAdapter extends BaseAdapter {
    
    private Activity activity;
    private String[] name;
    private String[] screenName;
    private String[] id;
    private static LayoutInflater inflater=null;
    
    public LazyAdapter(Activity a, String[] name, String[] screenName, String[] id) {
        activity = a;
        this.name=name;
        this.screenName = screenName;
        this.id = id;
        inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public int getCount() {
        return name.length;
    }

    public Object getItem(int position) {
    	return position;
        //return position;
    }

    public long getItemId(int position) {
        return position;
    }
    
    @SuppressLint("InflateParams")
	public View getView(int position, View convertView, ViewGroup parent) {
        View vi=convertView;
        if(convertView==null)
            vi = inflater.inflate(R.layout.item, null);

        TextView text1=(TextView)vi.findViewById(R.id.name);
        text1.setText(name[position]);
        TextView text2=(TextView)vi.findViewById(R.id.screen_name);
        text2.setText(screenName[position]);
        TextView text3=(TextView)vi.findViewById(R.id.id);
        text3.setText(id[position]);
        return vi;
    }
}