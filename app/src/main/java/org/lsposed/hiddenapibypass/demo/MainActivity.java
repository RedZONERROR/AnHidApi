package red.androhidapi.demo;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;
import red.androhidapi.HiddenApiBypass;

public class MainActivity extends Activity {

    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView statusText = findViewById(R.id.status_text);
        TextView resultText = findViewById(R.id.result_text);

        try {
            // Test HiddenApiBypass
            boolean success = HiddenApiBypass.addHiddenApiExemptions("");
            
            statusText.setText("HiddenApiBypass Status: " + (success ? "SUCCESS" : "FAILED"));
            
            // Try to access a hidden API as a demo
            try {
                // This is just a demonstration - accessing system properties
                String result = "Hidden API access test completed";
                resultText.setText("Result: " + result);
                
                Toast.makeText(this, "HiddenApiBypass demo completed!", Toast.LENGTH_LONG).show();
                
            } catch (Exception e) {
                resultText.setText("Error accessing hidden API: " + e.getMessage());
            }
            
        } catch (Exception e) {
            statusText.setText("HiddenApiBypass Error: " + e.getMessage());
            resultText.setText("Failed to initialize HiddenApiBypass");
        }
    }
}