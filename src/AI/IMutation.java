package AI;

/**
 * Created by Sergiu on 4/9/2017.
 */
public interface IMutation {
    Chromosome doMutation(Chromosome chromosome, Double probability);
}
