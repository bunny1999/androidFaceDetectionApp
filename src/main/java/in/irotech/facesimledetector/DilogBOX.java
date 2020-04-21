package in.irotech.facesimledetector;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class DilogBOX extends DialogFragment {

    Button ok_btn;
    TextView textView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        String resultStr="";

        View view=inflater.inflate(R.layout.fragment_dilog,container,false);
        ok_btn=view.findViewById(R.id.result_ok_button);
        textView=view.findViewById(R.id.result_text_view);

        Bundle bundle=getArguments();
        resultStr=bundle.getString(AppStarterAndStateMaintainer.TEXT_DILOG);

        textView.setText(resultStr);

        ok_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        return view;
    }
}
