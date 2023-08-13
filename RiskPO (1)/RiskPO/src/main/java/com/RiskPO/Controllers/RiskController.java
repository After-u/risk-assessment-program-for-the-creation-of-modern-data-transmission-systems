package com.RiskPO.Controllers;

import com.RiskPO.ImitateModels.MultiChannelFourPhaseSystemSimulator;
import com.RiskPO.Logic.*;
import com.RiskPO.Models.TypeOfModel;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.DataInput;
import java.util.ArrayList;
import java.util.List;

@Controller
public class RiskController {
    @GetMapping("/RiskCalc")
    public String RiscMain(Model model) {
        List<TypeOfModel> models = new ArrayList<>();
        models.add(new TypeOfModel("MultiChannelFour", 1));
        models.add(new TypeOfModel("TwoPhaseModel", 2)); // добавляем новую модель
        models.add(new TypeOfModel("OnePhaseModel", 3)); // добавляем новую модель
        model.addAttribute("typesofModels", models);

        Integer chosenid = 1;
        model.addAttribute("chosenid", chosenid);
        return "RiskCalc";
    }

    @GetMapping("/process-model")
    public String processModel(Model model, @RequestParam("model") Integer idModel) {
        switch (idModel) {
            case 1:
                return "models/fourPhasemodel";

            case 2:
                return "models/twoPhasemodel";
            case 3:
                return "models/onePhasemodel";

            default:
                return "error";
        }
    }

    @GetMapping("/two-phase-simulate")
    public String twophasesimulate(Model model,
                                   @RequestParam("simulationTime") Double simulationTime,
                                   @RequestParam("transDelay") Double transDelay,
                                   @RequestParam("propagandDelay") Double propagandDelay,
                                   @RequestParam("processingDelay")Double processingDelay,
                                   @RequestParam("packetLostRate") Double packetsLostRate,
                                   @RequestParam("experimentCount") Integer experimentCount,
                                   @RequestParam("probabilityLost") Integer probabilityLost,
                                   @RequestParam("probabilityResived") Double probabilityResived
    ) throws InterruptedException {
        MegasimulationforTwoPhase megasimulationforTwoPhase = new MegasimulationforTwoPhase(simulationTime,packetsLostRate,transDelay,propagandDelay,processingDelay);

        int[] arrayOfResived;
        arrayOfResived = new int[experimentCount];

        int[] arrayOfLost;
        arrayOfLost = new int[experimentCount];

        megasimulationforTwoPhase.gettingSampleforPackets(arrayOfResived,arrayOfLost);

        for (int i=0; i < experimentCount; i++){
            System.out.println(arrayOfResived[i]);
        }
        for (int i=0; i < experimentCount; i++){
            System.out.println(arrayOfLost[i]);
        }

        Logic logic = new Logic();
        List<Double> dataLost = logic.fromInttoDouble(arrayOfLost);
        // замените на свою выборку
        DensityEstimator density = new DensityEstimator(dataLost);
        ProbabilityCalculator calculator = new ProbabilityCalculator(density);

        double lostBorder = probabilityLost; // значение, для которого нам нужно найти вероятность
        double probabilityLostresult = calculator.probability(lostBorder);
        if (probabilityLostresult > 1) probabilityLostresult = 1;
        if (probabilityLostresult < 0.0001) probabilityLostresult = 0.01;

        Logic logic1 = new Logic();
        List<Double> dataResived = logic.fromInttoDouble(arrayOfResived);

        DensityEstimator densityEstimator = new DensityEstimator(dataResived);
        ProbabilityCalculator calculator1 = new ProbabilityCalculator(densityEstimator);

        double resivedBorder = probabilityResived;
        double probabilityResivedResult = calculator1.probabilityDown(resivedBorder);
        if (probabilityResivedResult > 1) probabilityResivedResult = 1;
        if (probabilityResivedResult < 0.01) probabilityResivedResult = 0.01;

        model.addAttribute("probabilityResivedResult", probabilityResivedResult);
        model.addAttribute("probabilityLostResult", probabilityLostresult);
        model.addAttribute("dataResived", arrayOfResived);
        model.addAttribute("dataLost", arrayOfLost);

        return "modelsa/twoPhaseSimulateResult";
    }
    @GetMapping("/four-phase-simulate")
    public String fourphasesimulate(Model model,
                                    @RequestParam("NUMBER_OF_CHANNELS") Integer numberOfChannels,
                                    @RequestParam("SERVICE_RATE") Double serviceRate,
                                    @RequestParam("SIMULATION_TIME") Integer simulationTime,
                                    @RequestParam("MAX_QUEUE_SIZE") Integer maxQueueSize,
                                    @RequestParam("NUMBER_OF_EXPERIMENTS") Integer numberOfExperiments,
                                    @RequestParam("ProbabilityLost") Integer probabilityLost,
                                    @RequestParam("ProbabilityResived") Integer probabilityResived
    ) {
        MegasimulationforFourPhase megasimulationforFourPhase = new MegasimulationforFourPhase(
                numberOfChannels,
                serviceRate,
                simulationTime,
                maxQueueSize);

        MultiChannelFourPhaseSystemSimulator multiChannelFourPhaseSystemSimulator = new MultiChannelFourPhaseSystemSimulator(
                numberOfChannels,
                serviceRate,
                simulationTime,
                maxQueueSize);
        double stressResistance = multiChannelFourPhaseSystemSimulator.calculateStressResistance(simulationTime);

        int[] arrayOfResived;
        arrayOfResived = new int[numberOfExperiments];

        int[] arrayOfLost;
        arrayOfLost = new int[numberOfExperiments];

        megasimulationforFourPhase.gettingSampleforPacketsServiced(arrayOfResived, arrayOfLost);

        Logic logic = new Logic();
        List<Double> dataLost = logic.fromInttoDouble(arrayOfLost);
        // замените на свою выборку
        DensityEstimator density = new DensityEstimator(dataLost);
        ProbabilityCalculator calculator = new ProbabilityCalculator(density);

        double lostBorder = probabilityLost; // значение, для которого нам нужно найти вероятность
        double probabilityLostresult = calculator.probability(lostBorder);
        if (probabilityLostresult > 1) probabilityLostresult = 1;
        if (probabilityLostresult < 0.0001) probabilityLostresult = 0.01;


        Logic logic1 = new Logic();
        List<Double> dataResived = logic.fromInttoDouble(arrayOfLost);
        // замените на свою выборку
        DensityEstimator density1 = new DensityEstimator(dataResived);
        ProbabilityCalculator calculator1 = new ProbabilityCalculator(density1);

        double resivedBorder = probabilityResived; // значение, для которого нам нужно найти вероятность
        double probabilityResivedResylt = calculator.probabilityDown(resivedBorder);
        if (probabilityResivedResylt > 1) probabilityResivedResylt = 1;
        if (probabilityResivedResylt < 0.01) probabilityResivedResylt = 0.01;

        model.addAttribute("stressResist", stressResistance);
        model.addAttribute("probabilityResivedResult", probabilityResivedResylt);
        model.addAttribute("probabilityLostResult", probabilityLostresult);
        model.addAttribute("data", arrayOfResived);
        model.addAttribute("data1", arrayOfLost);

        return "modelsa/fourPhaseSimulateResult";
    }

    @GetMapping("/one")
    public String onePhaseSimulate(Model model,
                                   @RequestParam("NUMBER_OF_CHANNELS") Integer numberOfChannels,
                                   @RequestParam("SERVICE_RATE") Double serviceRate,
                                   @RequestParam("SIMULATION_TIME") Integer simulationTime,
                                   @RequestParam("MAX_QUEUE_SIZE") Integer maxQueueSize,
                                   @RequestParam("NUMBER_OF_EXPERIMENTS") Integer numberOfExperiments,
                                   @RequestParam("ProbabilityLost") Integer probabilityLost,
                                   @RequestParam("ProbabilityResived") Integer probabilityResived
    ) throws InterruptedException {
        MegasimulationOnePhase megasimulationOnePhase = new MegasimulationOnePhase(
                numberOfChannels,
                serviceRate,
                simulationTime,
                maxQueueSize);
        int[] arrayOfResived;
        arrayOfResived = new int[numberOfExperiments];

        int[] arrayOfLost;
        arrayOfLost = new int[numberOfExperiments];


            megasimulationOnePhase.gettingSampleforPackets(arrayOfResived,arrayOfLost);


        Logic logic = new Logic();
        List<Double> dataLost = logic.fromInttoDouble(arrayOfLost);
        // замените на свою выборку
        DensityEstimator density = new DensityEstimator(dataLost);
        ProbabilityCalculator calculator = new ProbabilityCalculator(density);

        double lostBorder = probabilityLost; // значение, для которого нам нужно найти вероятность
        double probabilityLostresult = calculator.probability(lostBorder);
        if (probabilityLostresult > 1) probabilityLostresult = 1;
        if (probabilityLostresult < 0.0001) probabilityLostresult = 0.01;


        Logic logic1 = new Logic();
        List<Double> dataResived = logic.fromInttoDouble(arrayOfLost);
        // замените на свою выборку
        DensityEstimator density1 = new DensityEstimator(dataResived);
        ProbabilityCalculator calculator1 = new ProbabilityCalculator(density1);

        double resivedBorder = probabilityResived; // значение, для которого нам нужно найти вероятность
        double probabilityResivedResylt = calculator.probabilityDown(resivedBorder);
        if (probabilityResivedResylt > 1) probabilityResivedResylt = 1;
        if (probabilityResivedResylt < 0.01) probabilityResivedResylt = 0.01;

        model.addAttribute("probabilityResivedResult", probabilityResivedResylt);
        model.addAttribute("probabilityLostResult", probabilityLostresult);
        model.addAttribute("data", arrayOfResived);
        model.addAttribute("data1", arrayOfLost);

        return "modelsa/onePhaseSimulateResult";
    }
}
