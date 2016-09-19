package cps.project.myapplication;

/**
 * Created by vedahari on 12/6/2015.
 */
//TODO: select a possible idling time to suggest the probability
import android.os.Environment;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

class OnAireFunction{
    //declaration of lagrangian variables
    double v1,v2;
    ArrayList<Double> L1,L0;
    ArrayList<Double> P;
    public void setIdlingTimeProbability(ArrayList<Double> IdlingProb)
    {
        P = IdlingProb;
    }
    public void setLagrangianMultiplierL1(ArrayList<Double> LagArr1)
    {
        L1 = LagArr1;
    }

    public void setLagrangianMultiplierL0(ArrayList<Double> LagArr0)
    {
        L0 = LagArr0;
    }

    public void setLagrangianMultiplierV1(double fV1)
    {
        v1 = fV1;
    }

    public void setLagrangianMultiplierV2(double fV2)
    {
        v2 = fV2;
    }
    @Override
    public String toString() {
        String sL0 = new String();
        String sL1 = new String();
        String sP = new String();

        for(int j=0;j<L0.size();j++)
        {
            sL0=sL0+L0.get(j)+' ';
            sL1=sL1+L1.get(j)+' ';
            sP=sP+P.get(j)+' ';
        }
        sP=sP+P.get(P.size()-1);
        String sFuncAsString = new String();
        sFuncAsString = v1+" "+v2+" ";
        sFuncAsString = sP + sL0 + sL1 + sFuncAsString ;
        //System.out.println("f(P,L0,L1,v1,v2) = "+sFuncAsString);
        return sFuncAsString;
    }
    public String toStringDbg(){
        return "OnAireFunction [v1=" + v1 + ", v2=" + v2 + ", L1=" + L1 + ", L0=" + L0 + ", p=" + P + "]";
    }


}
/*
 * Onaire algorithm:
 * Motivation: Provide the user with randomly selected stop length and its probability
 *
 *
 * Get the stop lengths for a stop
 * Calculate the probability for each number
 */
public class OnAire {
    final static public int B = 200;
    private HashMap<Integer,Integer> StoplenFreqMap;
    private int totalStops;
    private double qB, uB;
    LPSolver oLPSolver=new LPSolver();
    public OnAire()
    {
        StoplenFreqMap = new HashMap<Integer,Integer>();
    }

    public void vInitialize()
    {

    }

    public void vExecute()
    {
        System.out.println("Currently nothing is done!");
        vReadStopLengthArrayFromFile();
        vCalculateStatisticalParameters();
        vFormObjectiveFunction();
        vFormConstraintFuntion();
        vAddTotalProbabilityConstraint();
        vAddProbabilityRangeConstraint();
        vAddProbabilityRangeConstraintMin();
        vSolveFormedEquation();

    }


    public void vReadStopLengthArrayFromFile(){
        //Read from the file and get all the stop length
        Scanner scanner;
        try {
            //TODO:Use Internal storage to specify the text file location
            String fileName = "fileAtlanta.txt";
            if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState()))
            {
                File Root = Environment.getExternalStorageDirectory();
                System.out.println("The external path is : " + Root.getAbsolutePath());
            }
            else
            {
                System.out.println("The external storage is not available.");
            }
            scanner = new Scanner( new File("D:/Courses/CPS/Project/Project3_OnlineAlgo/files/fileAtlanta.txt"));

            int current_input;
            int count =0;
            while(scanner.hasNextInt()){
                current_input = scanner.nextInt();
                totalStops++;
                if (StoplenFreqMap.containsKey(current_input))
                {
                    count = StoplenFreqMap.get(current_input).intValue()+1;
                    StoplenFreqMap.put(current_input, count);
                }
                else
                {
                    StoplenFreqMap.put(current_input,1);
                }
            }
            System.out.println(totalStops+"  "+ StoplenFreqMap.size());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

    public void vCalculateStopLengthDis(){
    }

    public void vCalculateStopLengthProb(){
    }
    public void vCalculateStatisticalParameters()
    {
        int q_total=0;
        int expectation = 0;
        for(Map.Entry<Integer, Integer> entry: StoplenFreqMap.entrySet())
        {
            if(entry.getKey().intValue()>=B)
            {
                //calculate qB
                q_total += entry.getValue().intValue();
            }
            else
            {
                //calculate uB
                expectation += entry.getKey()*entry.getValue();
            }
        }
        qB = (double)q_total/totalStops;
        uB = (double)expectation/totalStops;
        System.out.println("qB is "+qB);
        System.out.println("uB is "+uB);
    }
    public void vCalculateQb(){}
    public void vCalculateUb(){}
    public void vCalculateCostMatrix(){}
    public void vFormObjectiveFunction(){
        OnAireFunction objectiveFunction = new OnAireFunction();
        objectiveFunction.setLagrangianMultiplierV1((double)(1-qB));
        objectiveFunction.setLagrangianMultiplierV2((double)uB);

        //Calculate cost matrix and set the Idling Time probability coefficients
        ArrayList<Double> arrCostCoeff = new ArrayList<Double>();
        ArrayList<Double> arrLagranVarL1 = new ArrayList<Double>();
        ArrayList<Double> arrLagranVarL0 = new ArrayList<Double>();
        for(int j=1;j<B;j++){
            arrCostCoeff.add((double)(qB*(B+j-1)));
            arrLagranVarL0.add((double) 0.0);
            arrLagranVarL1.add((double)(1-qB));
        }
        arrCostCoeff.add(qB*(B+B-1));
        objectiveFunction.setLagrangianMultiplierL0(arrLagranVarL0);
        objectiveFunction.setLagrangianMultiplierL1(arrLagranVarL1);
        objectiveFunction.setIdlingTimeProbability(arrCostCoeff);
        System.out.println("ObjectiveFunction:: "+objectiveFunction.toString());
//	System.out.println(objectiveFunction.toStringDbg());
        oLPSolver.vSetObjective(objectiveFunction.toString());

    }

    public void vFormConstraintFuntion(){
        OnAireFunction oConstraintFunction = new OnAireFunction();

        for(int j=1;j<=B-1;j++)
        {
            oConstraintFunction.setLagrangianMultiplierV1(-1.0);
            oConstraintFunction.setLagrangianMultiplierV2(-j);
            ArrayList<Double> arrCostCoeff = new ArrayList<Double>();
            ArrayList<Double> arrLagranVarL1 = new ArrayList<Double>();
            ArrayList<Double> arrLagranVarL0 = new ArrayList<Double>();
            for(int i=1;i<=B; i++)
            {
                if(i<=j)
                {
                    arrCostCoeff.add((double) (B+i-1));
                }
                else
                {
                    arrCostCoeff.add((double)j);
                }
                if(i!=B)
                {
                    if(i==j)
                    {
                        arrLagranVarL0.add((double)1.0);
                        arrLagranVarL1.add((double)-1.0);
                    }
                    else
                    {
                        arrLagranVarL0.add((double)0.0);
                        arrLagranVarL1.add((double)0.0);
                    }
                }

                oConstraintFunction.setLagrangianMultiplierL0(arrLagranVarL0);
                oConstraintFunction.setLagrangianMultiplierL1(arrLagranVarL1);
                oConstraintFunction.setIdlingTimeProbability(arrCostCoeff);
            }
            oLPSolver.vAddConstraint(oConstraintFunction.toString(), oLPSolver.EQUAL_TO, 0);
        }
    }

    public void vAddTotalProbabilityConstraint()
    {
        OnAireFunction oConstraintFunction = new OnAireFunction();
        oConstraintFunction.setLagrangianMultiplierV1((double)0.0);
        oConstraintFunction.setLagrangianMultiplierV2((double)0.0);

        //Calculate cost matrix and set the Idling Time probability coefficients
        ArrayList<Double> arrCostCoeff = new ArrayList<Double>();
        ArrayList<Double> arrLagranVarL1 = new ArrayList<Double>();
        ArrayList<Double> arrLagranVarL0 = new ArrayList<Double>();
        for(int j=1;j<B;j++){
            arrCostCoeff.add((double)1.0);
            arrLagranVarL0.add((double) 0.0);
            arrLagranVarL1.add((double)0.0);
        }
        arrCostCoeff.add((double)1.0);
        oConstraintFunction.setLagrangianMultiplierL0(arrLagranVarL0);
        oConstraintFunction.setLagrangianMultiplierL1(arrLagranVarL1);
        oConstraintFunction.setIdlingTimeProbability(arrCostCoeff);
//	System.out.println(objectiveFunction.toString());
//	System.out.println(objectiveFunction.toStringDbg());
        oLPSolver.vAddConstraint(oConstraintFunction.toString(),oLPSolver.EQUAL_TO,1.0);
        //System.out.println(oConstraintFunction.toString().length());

    }

    public void vAddProbabilityRangeConstraint()
    {
        OnAireFunction oConstraintFunction = new OnAireFunction();
        for (int i=1;i<B+1;i++)
        {
            oConstraintFunction.setLagrangianMultiplierV1((double)0.0);
            oConstraintFunction.setLagrangianMultiplierV2((double)0.0);
            //Calculate cost matrix and set the Idling Time probability coefficients
            ArrayList<Double> arrCostCoeff = new ArrayList<Double>();
            ArrayList<Double> arrLagranVarL1 = new ArrayList<Double>();
            ArrayList<Double> arrLagranVarL0 = new ArrayList<Double>();
            for(int j=1;j<B;j++)
            {
                if(i==j)
                {
                    arrCostCoeff.add((double)1.0);
                }
                else{
                    arrCostCoeff.add((double)0.0);
                }
                arrLagranVarL0.add((double) 0.0);
                arrLagranVarL1.add((double)0.0);
            }
            if(i==B)
            {
                arrCostCoeff.add((double)1.0);
            }
            else{
                arrCostCoeff.add((double)0.0);
            }
            oConstraintFunction.setLagrangianMultiplierL0(arrLagranVarL0);
            oConstraintFunction.setLagrangianMultiplierL1(arrLagranVarL1);
            oConstraintFunction.setIdlingTimeProbability(arrCostCoeff);
//	System.out.println(objectiveFunction.toString());
//	System.out.println(objectiveFunction.toStringDbg());
            oLPSolver.vAddConstraint(oConstraintFunction.toString(),oLPSolver.LESSER_THAN,1);
            System.out.println(oConstraintFunction.toString());
        }
    }


    public void vAddProbabilityRangeConstraintMin()
    {
        OnAireFunction oConstraintFunction = new OnAireFunction();
        for (int i=1;i<B+1;i++)
        {
            oConstraintFunction.setLagrangianMultiplierV1((double)0.0);
            oConstraintFunction.setLagrangianMultiplierV2((double)0.0);
            //Calculate cost matrix and set the Idling Time probability coefficients
            ArrayList<Double> arrCostCoeff = new ArrayList<Double>();
            ArrayList<Double> arrLagranVarL1 = new ArrayList<Double>();
            ArrayList<Double> arrLagranVarL0 = new ArrayList<Double>();
            for(int j=1;j<B;j++)
            {
                if(i==j)
                {
                    arrCostCoeff.add((double)1.0);
                }
                else{
                    arrCostCoeff.add((double)0.0);
                }
                arrLagranVarL0.add((double) 0.0);
                arrLagranVarL1.add((double)0.0);
            }
            if(i==B)
            {
                arrCostCoeff.add((double)1.0);
            }
            else{
                arrCostCoeff.add((double)0.0);
            }
            oConstraintFunction.setLagrangianMultiplierL0(arrLagranVarL0);
            oConstraintFunction.setLagrangianMultiplierL1(arrLagranVarL1);
            oConstraintFunction.setIdlingTimeProbability(arrCostCoeff);
//		System.out.println(objectiveFunction.toString());
//		System.out.println(objectiveFunction.toStringDbg());
            oLPSolver.vAddConstraint(oConstraintFunction.toString(),oLPSolver.GREATER_THAN,0);
            //System.out.println(oConstraintFunction.toString().length());
        }

    }

    public void vSolveFormedEquation()
    {
        oLPSolver.vSolveLinearProgram();
    }

}
