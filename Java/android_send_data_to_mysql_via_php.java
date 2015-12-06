       public class connectDB extends AsyncTask<String,Void,String> {
        private Context context;
        boolean flag = false;

        public connectDB(Context context) {
            this.context = context;
        }

        protected void onPreExecute() {

        }

        @Override
        protected String doInBackground(String... arg0) {
            try {
                String service_group = (String) arg0[0];
                String service = (String) arg0[1];
                String code = (String) arg0[2];
                String value = (String)arg0[3];

                String idLogin = (String)readAndWrite();

		//changes > server ip address, folder name and php file.
		//name of the parameter

                String link = "http://192.168.43.61/BRACathon_ServerSide/db_insert.php"; 
                String data = URLEncoder.encode("service_group", "UTF-8") + "=" + URLEncoder.encode(service_group, "UTF-8");
                data += "&" + URLEncoder.encode("service", "UTF-8") + "=" + URLEncoder.encode(service, "UTF-8");
                data += "&" + URLEncoder.encode("login_idlogin", "UTF-8") + "="
                        + URLEncoder.encode(idLogin, "UTF-8");
                data += "&" + URLEncoder.encode("code", "UTF-8") + "="
                        + URLEncoder.encode(code, "UTF-8");
                data += "&" + URLEncoder.encode("amount_var", "UTF-8") + "="
                        + URLEncoder.encode(value, "UTF-8");

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
                    flag = true;
                    break;
                }
                return sb.toString();
            } catch (Exception e) {
                return new String("Exception: " + e.getMessage());
            }
        }

        @Override
        protected void onPostExecute(String result) {
            if(flag)
                Toast.makeText(getApplicationContext(), "Data Sent", Toast.LENGTH_LONG).show();
            else
                Toast.makeText(getApplicationContext(), "Data Not Sent", Toast.LENGTH_LONG).show();
        }

        private String readAndWrite() //to write and read data from SharedPreference;
        {
            SharedPreferences settings;
            String temp = "";
            try {
                settings = getSharedPreferences("BRACathon", 0);
                temp = settings.getString("index", "");
            } catch (NullPointerException np) {
                np.printStackTrace();
            }
            return temp;
        }

    }
