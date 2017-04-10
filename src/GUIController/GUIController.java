package GUIController;

import AI.*;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Sergiu on 4/10/2017.
 */
public class GUIController {
    ICrossOver crossOver = new CrossOver(1,2);
    IMutation mutation = new Mutation();
    @FXML
    TextField textField_MutationProbability;
    @FXML
    TextField textField_CrossOverProbability;
    @FXML
    TextField textField_CutNr;
    @FXML
    TextField textField_PopulationDimension;
    @FXML
    TextField textField_NrOfGeneration;

    @FXML
    TextField textField_W;
    @FXML
    TextField textField_D;
    @FXML
    TextField textField_Solution;
    @FXML
    Pane graphicPane;
    public GUIController() {
    }

    public void initDesgin(){
        drawPane(new ArrayList<>());

    }
    @FXML
    private void searchHandler(){
        List<List<Double>> w = getMatrixFromLine(textField_W.getText());
        List<List<Double>> d = getMatrixFromLine(textField_D.getText());
        crossOver.reinit(Integer.parseInt(textField_CutNr.getText()),w.size());
        AE ae = new AE(null,crossOver,mutation);
        Chromosome chromosome = ae.search(
                w,
                d,
                Integer.parseInt(textField_PopulationDimension.getText()),
                w.size(),
                Integer.parseInt(textField_NrOfGeneration.getText()),
                Double.parseDouble(textField_CrossOverProbability.getText()),
                Double.parseDouble(textField_MutationProbability.getText()));
        String txt = (String.join(" ",chromosome.getList().stream().map(el->el.toString()+" ").collect(Collectors.toList())));
        txt+=" Fitness: " + chromosome.getFitness().toString();
        System.out.println(txt);
        textField_Solution.setText(txt);
        System.out.println("Generatiile");
//        ae.getAllGenerations().forEach(el->{
//            el.forEach(el2->System.out.print(el2.getFitness() + " "));
//            System.out.println("");
//        });
        drawPane(ae.getAllGenerations());
    }

    //forma 1:2:3!4:5:6!7:8:9
    private List<List<Double>> getMatrixFromLine(String lineToParse){
        List<List<Double>> matrix = new ArrayList<>();
        for(String line: lineToParse.split("!")){
            matrix.add(new ArrayList<>());
            Integer poz = matrix.size()-1;
            for(String number : line.split(":")){
                matrix.get(poz).add(Double.parseDouble(number));
            }
        }
        return matrix;
    }

    private void drawPane(List<List<Chromosome>> generations){
        final NumberAxis xAxis = new NumberAxis();
        final NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel("ChromosomeIndex");
        final LineChart<Number,Number> lineChart =
                new LineChart<Number,Number>(xAxis,yAxis);

        lineChart.setTitle("Evolutions of egenerations");

        Integer popIndex = 0;

        for(List<Chromosome> generation : generations){
            Integer index = 0;
            XYChart.Series generationChart = new XYChart.Series();
            generationChart.setName("Gen: " + popIndex.toString());
            for(Chromosome chromosome : generation){
                generationChart.getData().add(new XYChart.Data(index,chromosome.getFitness()));
                index++;
            }
            popIndex++;
            lineChart.getData().add(generationChart);
        }


        lineChart.setMinWidth(823);
        graphicPane.getChildren().clear();
        graphicPane.getChildren().add(lineChart);
//        graphicPane.getChildren().addAll(stage);

    }

}
