List<Person> personList = new ArrayList<>();
public class getInformation extends AsyncTask<String,Void,String> {

        private Context context;
        private TextView tv;
        String value = "";
        Activity activity;

        public getInformation(Context context, Activity activity) {
            this.context = context;
            this.activity = activity;
//            this.tv = tv;
        }

        protected void onPreExecute(){

        }


        @Override
        protected String doInBackground(String... arg0) {
            try{
                String username = (String)arg0[0];

//changes need in server ip, server address, php fle name
                String link="http://192.168.43.61/BRACathon_ServerSide/db_get.php";

                String data = URLEncoder.encode("login_idlogin", "UTF-8") + "="
                        + URLEncoder.encode(username, "UTF-8");

                URL url = new URL(link);
                URLConnection conn = url.openConnection();

                conn.setDoOutput(true);
                OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());

                wr.write(data);
                wr.flush();

                BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));

                StringBuilder sb = new StringBuilder();
                String line = null;

                // Read Server Response
                while ((line = reader.readLine()) != null) {
                    sb.append(line);
                    break;
                }
                value = sb.toString();
                value = value.substring(1); //This line controlled a bug for us, you may remove
                return sb.toString();
            }
            catch(Exception e){
                return new String("Exception: " + e.getMessage());
            }
        }

        @Override
        protected void onPostExecute(String result){
            Toast.makeText(context, "Data Fetched", Toast.LENGTH_LONG).show();
//changes >>

            JSONArray jsonArr;
            JSONObject jsb;
            try {
                jsonArr = new JSONArray(value);
                for(int i=0;i<jsonArr.length();i++){
                    JSONObject c = jsonArr.getJSONObject(i);
                    String service_group = c.getString("sgroup");
                    String service = c.getString("service");
                    String value = c.getString("value");
                    String updated_at = c.getString("updated_at");

                    Person p = new Person(service_group, service, value, updated_at);

                    personList.add(p);
                }

                listviewAdapter adapter = new listviewAdapter(activity, personList);
                lview.setAdapter(adapter);
            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }


//CHANGES might need here
public class Person {
        public String group;
        public String service;
        public String value;
        public String updated;
        public Person(String g, String s, String v, String u) {
            group = g;
            service = s;
            value = v;
            updated = u;
        }
    }
