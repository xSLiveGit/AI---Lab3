package AI;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Created by Sergiu on 4/9/2017.
 */
public class CrossOver implements ICrossOver{
    public Integer cutPointsNumber ;
    List<Integer> cutPointsArray;
    public Integer maxValue;

    public CrossOver(Integer cutPointsNumber, ArrayList<Integer> cutPointsArray,Integer maxValue) {
        this.cutPointsNumber = cutPointsNumber;
        this.cutPointsArray = cutPointsArray;
        this.maxValue = maxValue;
        this.cutPointsArray.add(0,0);
        this.cutPointsArray.add(maxValue);
    }

    public CrossOver(Integer cutPointsNumber,Integer maxValue) {
//        this.cutPointsNumber = cutPointsNumber;
//        this.cutPointsArray = new ArrayList<>();
//        this.cutPointsArray = IntStream.of(
//                                        new Random().ints(1,maxValue)
//                                                    .distinct()
//                                                    .limit(cutPointsNumber)
//                                                    .sorted()
//                                                    .toArray())
//                                        .boxed()
//                                        .collect(Collectors.toList());
//        this.maxValue = maxValue;
//        this.cutPointsArray.add(0,0);
//        this.cutPointsArray.add(maxValue);
        reinit(cutPointsNumber,maxValue);
    }

    public void reinit(Integer cutPointsNumber,Integer maxValue){
        this.cutPointsNumber = cutPointsNumber;
        this.cutPointsArray = new ArrayList<>();
        this.cutPointsArray = IntStream.of(
                new Random().ints(1,maxValue)
                        .distinct()
                        .limit(cutPointsNumber)
                        .sorted()
                        .toArray())
                .boxed()
                .collect(Collectors.toList());
        this.maxValue = maxValue;
        this.cutPointsArray.add(0,0);
        this.cutPointsArray.add(maxValue);
    }

    public void setMaxValue(Integer value){
        this.maxValue = maxValue;
    }

    public void setCutPointsNumber(Integer cutPointsNumber){
        this.cutPointsNumber = cutPointsNumber;
        this.cutPointsArray = new ArrayList<>();
        this.cutPointsArray = IntStream.of(
                new Random().ints(1,maxValue)
                        .distinct()
                        .limit(cutPointsNumber)
                        .sorted()
                        .toArray())
                .boxed()
                .collect(Collectors.toList());
        this.cutPointsArray.add(0,0);
        this.cutPointsArray.add(maxValue);
    }

    public List<Chromosome> doCrossOver(Chromosome chromosome1,Chromosome chromosome2,Double probability){
        if(probability > 1.0d)
            probability = 1.0d;
        if(probability < 0.0d)
            probability = 0.0d;
        Double myProb = Math.random();
        List<Chromosome> chromosomeList = new ArrayList<>();
        List<Integer> son1 = new ArrayList<>();
        List<Integer> son2 = new ArrayList<>();

        if(myProb < probability){
            chromosome1.getList().forEach(son1::add);
            chromosome2.getList().forEach(son2::add);
            chromosomeList.add(new Chromosome(son1));
            chromosomeList.add(new Chromosome(son2));
            return chromosomeList;
        }

        boolean _1st = true;
        HashSet<Integer> hash1 = new HashSet<>(IntStream.range(0,chromosome1.getList().size()).map(i->i+1).boxed().collect(Collectors.toList()));
        HashSet<Integer> hash2 = new HashSet<>(IntStream.range(0,chromosome1.getList().size()).map(i->i+1).boxed().collect(Collectors.toList()));

        son1 = IntStream.range(0,chromosome1.getList().size()).map(i->0).boxed().collect(Collectors.toList());
        son2 = IntStream.range(0,chromosome2.getList().size()).map(i->0).boxed().collect(Collectors.toList());

        for(Integer i=0;i<cutPointsArray.size() -1;i++){
            for(Integer poz=cutPointsArray.get(i);poz < cutPointsArray.get(i+1);poz++){
                if(_1st){
                    if(hash1.contains(chromosome1.getGene(poz))){
                        son1.set(poz,chromosome1.getGene(poz));
                        hash1.remove(chromosome1.getGene(poz));
                    }
                    if(hash2.contains(chromosome2.getGene(poz))){
                        son2.set(poz,chromosome2.getGene(poz));
                        hash2.remove(chromosome2.getGene(poz));
                    }
                    _1st = false;
                }
                else{
                    if(hash1.contains(chromosome2.getGene(poz))){
                        son1.set(poz,chromosome2.getGene(poz));
                        hash1.remove(chromosome2.getGene(poz));
                    }
                    if(hash2.contains(chromosome1.getGene(poz))){
                        son2.set(poz,chromosome1.getGene(poz));
                        hash2.remove(chromosome1.getGene(poz));
                    }
                    _1st=true;
                }
            }
        }

        for(Integer i=0;i<son1.size();i++){
            if(son1.get(i).equals(0)){
                son1.set(i,hash1.iterator().next());
                hash1.remove(son1.get(i));
            }
            if(son2.get(i).equals(0)){
                son2.set(i,hash2.iterator().next());
                hash2.remove(son2.get(i));
            }
        }
        chromosomeList.add(new Chromosome(son1));
        chromosomeList.add(new Chromosome(son2));
        return chromosomeList;
    }
}
