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

    FirebaseFirestore db;

    void loadSavedMoles(OnCompleteLoadListener listener) {

        final List<Mole> moles = new ArrayList<>();
        db = FirebaseFirestore.getInstance();
        db.collection("users")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {

                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (DocumentSnapshot document : task.getResult()) {
                                Map<String, Object> data = document.getData();
                                Mole mole = new Mole((Integer) data.get("id"), (String) data.get("date"), (Float) data.get("risk"), (Float[]) data.get("risk_history"), (String[]) data.get("date_history"));
                                moles.add(mole);
                            }
                        } else {
                            Log.w("SelectInteractor", "Error getting documents.", task.getException());
                        }
                    }
                });
        listener.onComplete(moles);
    }

    void saveMoles(Mole[] moles){

        db = FirebaseFirestore.getInstance();

        for(Mole mole : moles){
            HashMap<String, Object> moleEntry = new HashMap<String, Object>();
            moleEntry.put("id", mole.getNum());
            moleEntry.put("date", mole.getDate());
            moleEntry.put("risk", mole.getRiskPercent());
            moleEntry.put("risk_history", mole.getRiskHistory());
            moleEntry.put("date_history", mole.getDateHistory());



            db.collection("moles")
                    .add(moleEntry);
        }
    }
}
