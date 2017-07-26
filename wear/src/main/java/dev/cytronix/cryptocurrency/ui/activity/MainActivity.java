package dev.cytronix.cryptocurrency.ui.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.wearable.view.WatchViewStub;
import android.widget.TextView;

import dev.cytronix.cryptocurrency.R;
import dev.cytronix.data.Data;

public class MainActivity extends Activity {

    private WatchViewStub watchViewStub;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        watchViewStub = (WatchViewStub) findViewById(R.id.watch_view_stub);
        watchViewStub.setOnLayoutInflatedListener(new WatchViewStub.OnLayoutInflatedListener() {
            @Override
            public void onLayoutInflated(WatchViewStub stub) {
                TextView textView = (TextView) watchViewStub.findViewById(R.id.textview_main_text);
                textView.setText("Result="+new Data().getTime());
            }
        });
    }
}
