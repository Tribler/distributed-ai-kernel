import org.ujmp.core.Matrix;
import org.ujmp.core.SparseMatrix;
import utils.Key;

import java.util.ArrayList;
import java.util.HashMap;

public class Machine2 {

    static double alpha = 0.05;

    public HashMap<Key, Float> getModel3() { return model3;}

    private HashMap<Key, Float> model3;

    private HashMap<String, Boolean> currUser;

    private ArrayList<String> listOfSongIds;

    //private int totalNrOfSongs;


    public HashMap<String, Boolean> getCurrUser() {
        return currUser;
    }


    /**
     * Constructor, initializes the machine.
     * @param size - size of model (how many total songs)
     * @param initValue - TBD probably 0.5 or random distribution
     * @param userRow - user's history of liked songs
     * @param songListSize
     */
    @SuppressWarnings("all")
    public Machine2(int size, float initValue, ArrayList<String> userRow, ArrayList<String> listOfSongIds) {

        this.listOfSongIds = listOfSongIds;
        this.model3 = new HashMap<>();

        this.currUser = new HashMap<>();

        for(String s: userRow){
            currUser.put(s, true);
        }
        for(String s2: listOfSongIds){
            if(!(currUser.containsKey(s2))){
                currUser.put(s2, false);
            }
        }

        //this.currUser = userRow;

        int size1 = listOfSongIds.size();
        for(int i=0; i<size1; i++){
            //if(userRow[i]){
            for(int j=i+1; j<size1; j++){

                //create row only if current user has listened to this song

                //so user listens to song 1, 5
                // matrix will be

                // s1 0.5 0.5 ... 0.5
                // s5 0.5 0.5 ... 0.5

                model3.put(new Key(listOfSongIds.get(i), listOfSongIds.get(j)), initValue);

            }
        //}
    }
    }



    private void updateMachine(Machine2 t){

        //int nrOfSongsListenedTo = t.currUser.length;

        int size1 = listOfSongIds.size();
        for(int i=0; i<size1; i++){
            //if(userRow[i]){
            for(int j=i+1; j<size1; j++){
        //for(int i=0; i< nrOfSongsListenedTo; i++) {
            //if (song1) {
                //for (int j = i; j < t.totalNrOfSongs; j++) {



                String song1 = listOfSongIds.get(i);
                String song2 = listOfSongIds.get(j);

                boolean hasSong1 = currUser.get(song1);
                boolean hasSong2 = currUser.get(song2);


                    //don't add anything for false false occurrences
                    //they are too frequent and probably not relevant


                    //if song1 == song2 == true  && hasSong1 means we don't increase for false false

                    if ((hasSong2 == hasSong1) && hasSong1) {


                            float initVal = t.model3.get(new Key(song1, song2));

                            float finalVal = initVal + (1 - initVal) * 0.05f;

                            t.model3.replace(new Key(song1, song2), finalVal);

                            //t.model[i][j] += (1 - t.model[i][j]) * 0.05f;

                    } else if(hasSong1 != hasSong2) {

                        float initVal = t.model3.get(new Key(song1,song2));

                        float finalVal = initVal - initVal * 0.05f;

                        t.model3.replace(new Key(song1, song2), finalVal);

                        //t.model[i][j] -= t.model[i][j]* 0.05f;
                    }

                }
            }
        }




    public void mergeModels(Machine2 other, int val){


        other.getModel3().forEach(
                (key, value) -> this.getModel3().merge( key, value, (v1, v2) -> (v1+ v2)/val)
        );

    }


    public void updateUM(Machine2 other) {

        updateMachine(this);
        updateMachine(other);
        this.mergeModels(other, 2);


    }


    @Override
    public String toString() {
        return this.model3.toString();
    }
}
