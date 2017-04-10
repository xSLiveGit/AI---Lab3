package AI;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Created by Sergiu on 4/9/2017.
 */
public class AE {
    private  List<Chromosome> chromosomeSet;
    ICrossOver crossOver;
    IMutation mutation;
    List<List<Chromosome>> allGenerations;
    public void solve(Integer capacity,Integer size){
        chromosomeSet = initializeSet(capacity,size);
    }

    public  List<Chromosome> initializeSet(Integer capacity,Integer chromosomeDimension){
        chromosomeSet = new ArrayList<>(capacity);
        IntStream.rangeClosed(1,capacity).forEach(el->chromosomeSet.add(generateRandomChromosome(chromosomeDimension)));
        return chromosomeSet;
    }

    public  Chromosome generateRandomChromosome(Integer chromosomeDimension){
        Chromosome chr =  new Chromosome(IntStream.rangeClosed(1,chromosomeDimension).boxed().collect(Collectors.toList()));
        Collections.shuffle(chr.getList());
        return chr;
    }

    public AE(List<Chromosome> chromosomeSet, ICrossOver crossOver, IMutation mutation) {
        this.chromosomeSet = chromosomeSet;
        this.crossOver = crossOver;
        this.mutation = mutation;
    }

    public List<List<Chromosome>> getAllGenerations() {
        return allGenerations;
    }

    public Chromosome search(
            List<List<Double>> w,
            List<List<Double>> d,
            Integer numbersOfChromosomesInPopulation,
            Integer chromosomeDimension,
            Integer generations,
            Double crossOverProbability,
            Double mutationProbability
            ){
        allGenerations = new ArrayList<>();
        chromosomeSet = initializeSet(numbersOfChromosomesInPopulation,chromosomeDimension);
        evalGeneration(w,d,chromosomeSet);
        Chromosome bestChr = chromosomeSet.get(0);
        for(Chromosome chromosome : chromosomeSet){
            System.out.print(chromosome.getFitness()+" ");
            if(chromosome.getFitness().compareTo(bestChr.getFitness()) < 0){
                bestChr = chromosome;
            }
        }
        Integer currentGenerationIndex = 1;
        allGenerations.add(new ArrayList<Chromosome>(chromosomeSet));
        while(currentGenerationIndex < generations){
            List<Chromosome> currentGeneration = new ArrayList<>();
            Chromosome localBestChromosome = null;
            do{
                //selection
                Chromosome chr1 = selectChromosome(chromosomeSet);
                Chromosome chr2 = selectChromosome(chromosomeSet);
                //crossover
                List<Chromosome> chrList = crossOver.doCrossOver(chr1,chr2,crossOverProbability);
                chrList.get(0).setFitness(evalChromosome(w,d,chrList.get(0)));
                chrList.get(1).setFitness(evalChromosome(w,d,chrList.get(1)));
                if(chrList.get(0).getFitness().compareTo(bestChr.getFitness()) < 0){
                    localBestChromosome = chrList.get(0);
                }
                if(chrList.get(1).getFitness().compareTo(bestChr.getFitness()) < 0){
                    localBestChromosome = chrList.get(1);
                }
                //mutation
                chr1 = mutation.doMutation(chr1,mutationProbability);
                chr2 = mutation.doMutation(chr2,mutationProbability);
                //evaluation
                chr1.setFitness(evalChromosome(w,d,chr1));
                chr2.setFitness(evalChromosome(w,d,chr2));
                currentGeneration.add(chr1);
                currentGeneration.add(chr2);
            }while(currentGeneration.size() < chromosomeSet.size());
            currentGenerationIndex++;
            System.out.println("new generation:");
            if(null == localBestChromosome)
                localBestChromosome = currentGeneration.get(0);
            for(Chromosome chromosome : currentGeneration){
                System.out.print(chromosome.getFitness()+" ");
                if(chromosome.getFitness().compareTo(localBestChromosome.getFitness()) < 0){
                    localBestChromosome = chromosome;
                }
            }
            allGenerations.add(new ArrayList<Chromosome>(currentGeneration));
            if(bestChr.getFitness().compareTo(localBestChromosome.getFitness()) > 0)
                bestChr = localBestChromosome;
            System.out.print("Local: " + localBestChromosome.getFitness());
            System.out.println();
            chromosomeSet = currentGeneration;
        }
        System.out.print("Gens:\n");
//        allGenerations.forEach(el->{
//            el.forEach(el2->System.out.print(el2.getFitness() + " "));
//            System.out.println("");
//        });
        return bestChr;
    }

    public void evalGeneration(List<List<Double>> w,
                               List<List<Double>> d,
                               List<Chromosome> list){
        list.forEach(el->el.setFitness(evalChromosome(w, d,el)));
    }

    public Double evalChromosome(
            List<List<Double>> w,
            List<List<Double>> d,
            Chromosome chr){
        Double sum = 0.0d;
        for(Integer a=0;a<w.size();a++){
            for(Integer b=0;b<d.size();b++){
                sum = sum
                        +
                        w.get(a).get(b)
                                *
                                d.get(chr.getGene(a)-1).get(chr.getGene(b)-1);
            }
        }
        return sum;
    }

    public Chromosome selectChromosome(List<Chromosome> chromosomesSet){
        Collections.shuffle(chromosomesSet);
        List<Chromosome> chr =   chromosomesSet.subList(0,Math.min(1,chromosomesSet.size())).stream().sorted(Comparator.comparing(Chromosome::getFitness)).collect(Collectors.toList());
//        assert (chr.get(0).getFitness() < chr.get(1).getFitness());
        return chr.get(0);
    }
}
