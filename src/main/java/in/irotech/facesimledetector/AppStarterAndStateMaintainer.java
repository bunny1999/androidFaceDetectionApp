package in.irotech.facesimledetector;

import android.app.Application;

import com.google.firebase.FirebaseApp;

public class AppStarterAndStateMaintainer extends Application {

    //app start from here due to intiating with this Appliction class so to maintain app to stay connected to firebase

    //will work as intermidate title to get data from bundle passed
    public final static String TEXT_DILOG="TEXT-DILOG";

    public final static String DILOG_BOX="DILOG-BOX";

    @Override
    public void onCreate() {
        super.onCreate();
        FirebaseApp.initializeApp(this);
    }
}
