import org.jfree.ui.RefineryUtilities;
import utils.Key;
import utils.XYSeriesDemo;

import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.Array;
import java.util.*;
import java.util.stream.Collectors;

public class Main {
    public static void main(String []args) throws FileNotFoundException {


        String[][] songHistory2 = {
                {"a", "b", "d", "f"},
                {"a", "b", "f"},
                {"a", "b", "d", "f"},
                {"c", "d", "f"},
                {"c", "e"},
                {"c", "d", "e"},
                {"a", "b", "d", "f"},
                {"c", "d", "f"}
        };

        ArrayList<ArrayList<String>> exampleList = new ArrayList<>();

        for(String[] li: songHistory2){
            exampleList.add(new ArrayList<>(Arrays.asList(li)));
        }


        String[] allSongs = {"a", "b", "c", "d", "e", "f"};
        ArrayList<String> allSongsExampleList = new ArrayList<>(Arrays.asList(allSongs));




        Entity2 entity = new Entity2(exampleList, allSongsExampleList);
        HashMap<Key, Float> exampleModel  = entity.run();





        System.out.println(exampleModel);

        for (int i=1; i<allSongs.length; i++){
            Key k = new Key("a", allSongs[i]);
            System.out.println("similarity to a, " + k + " : " + exampleModel.get(k));
        }


        Map<String, ArrayList<String>> trainMap = new HashMap<>();
        Map<String, Integer> allSongTrainMap = new HashMap<>();

        File fileMap = new File("train_triplets.txt");
        for(int i = 0; i < 100; ++i)
        {
            String s = choose(fileMap);

            String[] fields = s.split("\t", -1);

            if(!trainMap.containsKey(fields[0])) {
                trainMap.put(fields[0], new ArrayList<String>());
            }
            trainMap.get(fields[0]).add(fields[1]);
            allSongTrainMap.put(fields[1], 0);

        }

        ArrayList<ArrayList<String>> trainList = new ArrayList<>(trainMap.values());

        System.out.println(trainMap);



        ArrayList<String> allSongsTrainList = new ArrayList<>(allSongTrainMap.keySet());
        Entity2 entity2 = new Entity2(trainList, allSongsTrainList);
        HashMap<Key, Float> avgModel  = entity2.run();

        PriorityQueue<Map.Entry<Key, Float>> pq = new PriorityQueue<>(
                (a,b) -> a.getValue().equals(b.getValue()) ? b.getKey().compareTo(a.getKey()) : -(a.getValue().compareTo(b.getValue()))
        );

        for(Map.Entry<Key, Float> entry : avgModel.entrySet()){
            pq.offer(entry);
        }

        System.out.println(pq.poll());

        int M = 5; ////nr of first M songs to recommend

        PriorityQueue<Map.Entry<Key, Float>> firstM = new PriorityQueue<>(
                (a,b) -> a.getValue().equals(b.getValue()) ? b.getKey().compareTo(a.getKey()) : -(a.getValue().compareTo(b.getValue()))
        );



       File keyLookup = new File("unique_tracks.txt");

       for(int i=0 ; i < M && !(pq.isEmpty()); i++) {
           Map.Entry<Key, Float> currEntry = pq.poll();
           String songString1 = currEntry.getKey().getX();
           String songString2 = currEntry.getKey().getY();

           String song1 = lookupKey(songString1, keyLookup);

           System.out.println(song1);
           String song2 = lookupKey(songString2, keyLookup);
           System.out.println(song2);
           firstM.offer(new AbstractMap.SimpleEntry<>(new Key(song1, song2), currEntry.getValue()));
       }

       System.out.println(firstM);
//
//
//        }

    }

    public static String choose(File f) throws FileNotFoundException
    {
        String result = null;
        Random rand = new Random();
        int n = 0;
        for(Scanner sc = new Scanner(f); sc.hasNext() && n < 10000; )
        {
            ++n;
            String line = sc.nextLine();
            if(rand.nextInt(n) == 0)
                result = line;
        }

        return result;
    }

    public static String lookupKey(String toFind, File f) throws FileNotFoundException
    {

        Scanner sc = new Scanner(f);
        sc.useDelimiter("<SEP>");

        String scannerLine = "";

        String foundLine = "";
        int flag = 1;
        while (sc.hasNextLine() && flag == 1) {
            scannerLine = sc.nextLine();
            if(scannerLine.contains(toFind)){
                foundLine =  scannerLine;
                flag = 0;
            }
        }

        String[] fields = foundLine.split("<SEP>", -1);
        return fields[2] +";" +fields[3];
    }



}
