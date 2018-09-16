package dreadloaf.com.htn2018.select;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dreadloaf.com.htn2018.Mole;

public class SelectInteractor {

    interface OnCompleteLoadListener{
        void onComplete(List<Mole> moles);
    }




}
