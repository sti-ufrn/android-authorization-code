package sinfo.ufrn.br.authorizationcode;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.io.IOException;

public class TestActivity extends AppCompatActivity {

    private ProgressDialog pd;
    private TextView txtJson;
    private final String urlBase = "https://api.info.ufrn.br/";
    private final String apiKey = "API_KEY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        txtJson = (TextView) findViewById(R.id.txtJson);

        SharedPreferences preferences = this.getSharedPreferences("user_info", 0);
        String accessToken = preferences.getString(Constants.KEY_ACCESS_TOKEN, null);
        if (accessToken != null) {
            new ServiceTask().execute(accessToken);
        }
    }

    private class ServiceTask extends AsyncTask<String, Void, String> {

        protected void onPreExecute() {
            pd = ProgressDialog.show(TestActivity.this, "", "loading", true);
        }

        protected String doInBackground(String... params) {
            try {
                Get get = new Get();
                try {
                    return get.getCurso(urlBase, params[0], apiKey);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            pd.dismiss();

            if (result != null) {
                txtJson.setText(result);
            }
        }
    }
}
