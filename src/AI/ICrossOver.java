package AI;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Created by Sergiu on 4/9/2017.
 */
public interface ICrossOver {
    List<Chromosome> doCrossOver(Chromosome chromosome1, Chromosome chromosome2, Double probability);
    public void reinit(Integer cutPointsNumber,Integer maxValue);
    public void setMaxValue(Integer value);
    public void setCutPointsNumber(Integer cutPointsNumber);
}