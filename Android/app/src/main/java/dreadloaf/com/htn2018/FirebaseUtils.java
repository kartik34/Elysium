package dreadloaf.com.htn2018;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import dreadloaf.com.htn2018.select.SelectInteractor;

public class FirebaseUtils {

    public interface OnMoleLoadedListener{
        void onMoleLoaded(List<Mole> moles);
    }

    public static  List<Mole> loadSavedMoles(@NonNull final OnMoleLoadedListener listener) {
        final List<Mole> moles = new ArrayList<>();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        Log.e("WHATEVER", "loading");
        db.collection("moles")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {

                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (DocumentSnapshot document : task.getResult()) {
                                Map<String, Object> data = document.getData();

                                Mole mole = new Mole((Long)data.get("id"), (String) data.get("date"), (Double) data.get("risk"), (String)data.get("risk_value"), (List<Double>) data.get("risk_history"), (List<String>) data.get("date_history"), (String)data.get("imageDir"));
                                if(!listContainsMole(moles, mole)){
                                    Log.e("FirebaseUtils", "adding mole");
                                    moles.add(mole);
                                }

                                Log.e("WHATEVER", "size: " + moles.size());
                                listener.onMoleLoaded(moles);
                            }
                        } else {
                            Log.w("SelectInteractor", "Error getting documents.", task.getException());
                        }
                    }
                });

        return moles;
    }

    private static boolean listContainsMole(List<Mole> moles, Mole mole){
        for(Mole m : moles){
            if(m.getDate().equals(mole.getDate())){
                Log.e("FirebaseUtils",m.getDate() + "==" + mole.getDate());
                return true;
            }

        }
        return false;
    }
}
