package nz.co.afleet.bit603_a3_johnmcpherson;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_home);

        Intent intent = new Intent(this, ItemDetailHostActivity.class);
        startActivity(intent);
    }
}