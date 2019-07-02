package andreyskakunenko.myapplication;

import android.Manifest;
import android.content.Context;
import android.app.AlertDialog;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import andreyskakunenko.myapplication.Helper.SaveImageHelper;

public class FullScreenPhoto extends AppCompatActivity {

    private static final int PERMISSION_REQUEST_CODE = 888;
    TextView licenseTextView, ownerTextView;
    ImageView mFullImageView;
    String urlPic;
    Context mContext = FullScreenPhoto.this;
    AlertDialog mDialog;

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode)
        {
            case PERMISSION_REQUEST_CODE:
            {
                if(grantResults.length > 0 & grantResults[0]==PackageManager.PERMISSION_GRANTED){
                    Toast.makeText(mContext,"Permission Granted",Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(mContext, "Permission Denied", Toast.LENGTH_SHORT).show();
                }

            }
            break;
        }
    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_full_picture,menu);
        return  true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_save){

            if(ActivityCompat.checkSelfPermission
                    (mContext,Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(mContext,"You should grant permission",Toast.LENGTH_SHORT).show();
                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},PERMISSION_REQUEST_CODE);
            }else {
                Picasso.get()
                        .load(urlPic)
                        .into(new SaveImageHelper(
                                mContext,
                                mDialog,
                                getBaseContext().getContentResolver(),
                                "lorem_pic",
                                "LoremFickr"));
                Toast.makeText(this, "Saved successfully", Toast.LENGTH_LONG).show();
            }
        }
        return true;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_screen_photo);

        if(ActivityCompat.checkSelfPermission
                (this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
        }


        licenseTextView = findViewById(R.id.license_pic);
        ownerTextView = findViewById(R.id.owner_pic);
        mFullImageView = findViewById(R.id.full_screen_pic);
        Bundle mBundle = getIntent().getExtras();
        if(mBundle != null){
            urlPic = mBundle.getString("file");
            licenseTextView.setText(mBundle.getString("license"));
            ownerTextView.setText(mBundle.getString("owner"));
            Picasso.get()
                    .load(urlPic)
                    .into(mFullImageView);
        }

    }
}
