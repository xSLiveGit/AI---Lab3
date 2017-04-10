package AI;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sergiu on 4/9/2017.
 */
public class Chromosome {
    private List<Integer> arrayList;
    private Double fitness;

    public Chromosome setFitness(Double fitness) {
        this.fitness = fitness;
        return this;
    }

    public Double getFitness() {

        return fitness;
    }

    public Chromosome(List<Integer> arrayList, Double fitness) {

        this.arrayList = arrayList;
        this.fitness = fitness;
    }

    public Chromosome(List<Integer> arrayList) {
        this.arrayList = arrayList;
    }

    public Chromosome(Chromosome c) {
        this.arrayList = new ArrayList<>(c.arrayList);
    }

    public Integer getGene(Integer index){
        return arrayList.get(index);
    }

    public void setGene(Integer index,Integer value){
        this.arrayList.set(index,value);
    }

    public List<Integer> getList(){
        return arrayList;
    }

    public Integer getSize(){
        return arrayList.size();
    }
}
