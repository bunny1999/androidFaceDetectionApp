package in.irotech.facesimledetector;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.ml.vision.FirebaseVision;
import com.google.firebase.ml.vision.common.FirebaseVisionImage;
import com.google.firebase.ml.vision.face.FirebaseVisionFace;
import com.google.firebase.ml.vision.face.FirebaseVisionFaceDetector;
import com.google.firebase.ml.vision.face.FirebaseVisionFaceDetectorOptions;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    Button button;
    FirebaseVisionImage image;
    FirebaseVisionFaceDetector detector;

    //unique as other apps also request, so request should not miss matches
    public static final int REQUEST_CODE=121;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button=findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if(intent.resolveActivity(getPackageManager())!=null){
                    startActivityForResult(intent,REQUEST_CODE);
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==REQUEST_CODE && resultCode==RESULT_OK){
            Bundle allImageData=data.getExtras();
            Bitmap bitmap=(Bitmap) allImageData.get("data");

            detectFaces(bitmap);
        }
    }

    private void detectFaces(Bitmap bitmap) {
        FirebaseVisionFaceDetectorOptions options=new FirebaseVisionFaceDetectorOptions.Builder()
                .setModeType(FirebaseVisionFaceDetectorOptions.ACCURATE_MODE)
                .setLandmarkType(FirebaseVisionFaceDetectorOptions.ALL_LANDMARKS)
                .setClassificationType(FirebaseVisionFaceDetectorOptions.ALL_CLASSIFICATIONS)
                .setTrackingEnabled(true)
                .setMinFaceSize(0.15f)
                .build();

        try {
            //seted image
            image=FirebaseVisionImage.fromBitmap(bitmap);
            //seted all options for detection
            detector= FirebaseVision.getInstance().getVisionFaceDetector(options);
        } catch (Exception e) {
            e.printStackTrace();
        }

        //pushForDetection
        detector.detectInImage(image).addOnSuccessListener(new OnSuccessListener<List<FirebaseVisionFace>>() {
            @Override
            public void onSuccess(List<FirebaseVisionFace> firebaseVisionFaces) {
                //giving list of faces
                String detailsOfFaces="";
                int i=1;
                for(FirebaseVisionFace faces:firebaseVisionFaces){
                    detailsOfFaces=detailsOfFaces.concat("\nImage:"+i).concat("\nSmile:"+faces.getSmilingProbability()*100+"%");
                    i++;
                }
                if(firebaseVisionFaces.size()==0){
                    Toast.makeText(MainActivity.this,"NO FACE",Toast.LENGTH_SHORT).show();
                }else{

                    //seting data to intermidiate
                    Bundle bundle=new Bundle();
                    //so that it  will use comman title
                    bundle.putString(AppStarterAndStateMaintainer.TEXT_DILOG,detailsOfFaces);

                    DialogFragment dialogFragment=new DilogBOX();
                    dialogFragment.setArguments(bundle);
                    dialogFragment.setCancelable(true);

                    //formality
                    dialogFragment.show(getSupportFragmentManager(),AppStarterAndStateMaintainer.DILOG_BOX);
                }
            }
        });
        //on faliure listner
    }

}
