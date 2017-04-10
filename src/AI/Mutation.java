package AI;

import java.util.Random;

/**
 * Created by Sergiu on 4/9/2017.
 */
public class Mutation implements IMutation {
    @Override
    public Chromosome doMutation(Chromosome chromosome, Double probability) {
        double myprobability = Math.random();
        if(myprobability > probability)
            return chromosome;
        Random random = new Random();
        Integer poz1 = random.nextInt(chromosome.getSize()-1) + 1;
        Integer poz2 = random.nextInt(chromosome.getSize()-1) + 1;
        Chromosome newChromosome = new Chromosome(chromosome);
        Integer aux = newChromosome.getGene(poz1);
        newChromosome.setGene(poz1,chromosome.getGene(poz2));
        newChromosome.setGene(poz2,aux);
        return newChromosome;
    }
}
