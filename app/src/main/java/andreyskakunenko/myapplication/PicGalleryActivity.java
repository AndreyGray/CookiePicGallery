package andreyskakunenko.myapplication;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class PicGalleryActivity extends SingleFragmentActivity{

    protected Fragment createFragment(){
        return PicGalleryFragment.newInstance();
    }
}
