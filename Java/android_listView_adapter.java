public class listviewAdapter extends BaseAdapter
{
    public List<MainActivity.Person> list;
    Activity activity;

    public listviewAdapter(Activity activity, List<MainActivity.Person> list) {
        super();
        this.activity = activity;
        this.list = list;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return 0;
    }

    private class ViewHolder {
        TextView txtFirst;
        TextView txtSecond;
        TextView txtThird;
        TextView txtFourth;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub

        // TODO Auto-generated method stub
        ViewHolder holder;
        LayoutInflater inflater =  activity.getLayoutInflater();

        if (convertView == null)
        {
            convertView = inflater.inflate(R.layout.listview_row, null);
            holder = new ViewHolder();
            holder.txtFirst = (TextView) convertView.findViewById(R.id.FirstText);
            holder.txtSecond = (TextView) convertView.findViewById(R.id.SecondText);
            holder.txtThird = (TextView) convertView.findViewById(R.id.ThirdText);
            holder.txtFourth = (TextView) convertView.findViewById(R.id.FourthText);
            convertView.setTag(holder);
        }
        else
        {
            holder = (ViewHolder) convertView.getTag();
        }

        MainActivity.Person var = list.get(position);
        String group = var.group ; //big one
        String service = var.service;
        String value = var.value;
        String updated_at = var.updated;

        Log.i("Show_data", group + " >> " + service + " >> " + value + " >> " + updated_at);
        holder.txtFirst.setText(group);
        holder.txtSecond.setText(service);
        holder.txtThird.setText(value);
        holder.txtFourth.setText(updated_at);


        return convertView;
    }

}



/// on another page or anywhere
///
///
	lview = (ListView) findViewById(R.id.listView);
	listviewAdapter adapter = new listviewAdapter(activity, personList);
	lview.setAdapter(adapter);
