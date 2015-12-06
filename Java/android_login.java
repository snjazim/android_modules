/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity {

    // UI references.
    private AutoCompleteTextView mEmailView;
    
    private EditText mPasswordView; //CHANGES WILL BE HERE
    
    String index;
    final Intent intent = new Intent(this, Index.class); //CHANGES WILL BE HERE
    boolean flag = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mEmailView = (AutoCompleteTextView) findViewById(R.id.phone); //CHANGES WILL BE HERE

        mPasswordView = (EditText) findViewById(R.id.password); //CHANGES WILL BE HERE
        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.id.phone || id == EditorInfo.IME_NULL) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });

        Button mEmailSignInButton = (Button) findViewById(R.id.email_sign_in_button); //CHANGES WILL BE HERE


        mEmailSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });
    }

    private void attemptLogin() {
        if (mAuthTask != null) {
            return;
        }

        // Reset errors.
        mEmailView.setError(null);
        mPasswordView.setError(null);

        // Store values at the time of the login attempt.
        String email = mEmailView.getText().toString();
        String password = mPasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            mPasswordView.setError("Password can't be empty");
            focusView = mPasswordView;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            mEmailView.setError(getString(R.string.error_field_required));
            focusView = mEmailView;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            new UserLoginTask(getApplicationContext(), this).execute(email, password);
        }
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 4;
    }

    /**
     * Represents an asynchronous login/registration task used to authenticate
     * the user.
     */
    public class UserLoginTask extends AsyncTask<String,Void,String>{

        private String mEmail;
        private String mPassword;
        private Context context;
        private Activity act;

        UserLoginTask(Context context, Activity act) {
            this.context = context;
            this.act = act;
        }

        @Override
        protected String doInBackground(String... arg0) {
            // TODO: attempt authentication against a network service.

            mEmail = (String)arg0[0];
            mPassword = (String)arg0[1];



            try {
                String link = "http://192.168.43.61/LOCALHOST_FOLDER/PHP_FILE.PHP"; //CHANGES WILL BE HERE 
				//<CHANGE THIS IP WITH YOUR LAPTIO IP ADDRESS, SERVER ADDRESS AND PHP FILE

                String data = URLEncoder.encode("mEmail", "UTF-8") + "=" +
                        URLEncoder.encode(mEmail, "UTF-8"); //CHANGES WILL BE HERE, HOW PHP IS GOING TO ACCEPT IT VIA POST
                data += "&" + URLEncoder.encode("mPassword", "UTF-8") + "=" + URLEncoder.encode(mPassword, "UTF-8"); //CHANGES WILL BE HERE , HOW PHP IS GOING TO ACCEPT IT VIA POST
                
		URL url = new URL(link);
                URLConnection conn = url.openConnection();

                conn.setDoOutput(true);
                OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());

                wr.write(data);
                wr.flush();

                BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));

                StringBuilder sb = new StringBuilder();
                String line = null;
                String value = "";
                // Read Server Response
                while ((line = reader.readLine()) != null) {
                    sb.append(line);
                    value = line;
                    break;
                }

                value = value.substring(1);

                JSONArray jsonArr;
                JSONObject jsb;
                List<Person> personList = new ArrayList<>(); 
                   jsonArr = new JSONArray(value);
                    for (int i = 0; i < jsonArr.length(); i++) {
                        JSONObject c = jsonArr.getJSONObject(i);
                        index = c.getString("idlogin"); //CHANGES WILL BE HERE, YOU MIGHT NOT NEED THIS OR DON'T WANT TO RECIEVE ANYTHING.

                        Person p = new Person(value);

                        personList.add(p);
                    }

                return sb.toString();
            } catch (Exception e) {
                return new String("Exception: " + e.getMessage());
            }
        }

        @Override
        protected void onPostExecute(String result) {

            result = result.substring(1);
            if(result.charAt(0) == '[' && result.charAt(1) == ']')
            {
                Toast.makeText(context, "Login unsuccessful", Toast.LENGTH_LONG).show();
            }
            else
            {
                Toast.makeText(context, "Login Successful", Toast.LENGTH_LONG).show();
                write(index);
//                startActivity(intent);
                act.startActivity(new Intent(act, Index.class));

            }
        }

        private boolean write(String val) 
        {
            SharedPreferences settings;
            settings = getSharedPreferences("ANYTHING", 0);//CHANGES WILL BE HERE 
            SharedPreferences.Editor editor = settings.edit();
            editor.putString("index", index);//CHANGES WILL BE HERE 

            // Commit the edits!
            boolean bool = editor.commit();
            if(bool)
            {
                return true;
            }
            else
            {
                return false;
            }

        }
    }

    public class Person {
        public String idlogin;
        public Person(String val) {
            idlogin = val;
        }
    }
}


