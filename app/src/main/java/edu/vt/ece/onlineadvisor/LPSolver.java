package edu.vt.ece.onlineadvisor;

/**
 * Created by vedahari on 12/6/2015.
 */

import java.util.ArrayList;

import lpsolve.*;


class Constraint{
    String sConstraintFunction;
    int iRelation;
    double rhs;
    public Constraint(String sConstraintFunction, int iRelation, double rhs) {
        super();
        this.sConstraintFunction = sConstraintFunction;
        this.iRelation = iRelation;
        this.rhs = rhs;
    }
}

public class LPSolver {
    private String sObjFunction;
    private ArrayList<Constraint> sConstraintList = new ArrayList<Constraint>();
    final int LESSER_THAN = 1;
    final int GREATER_THAN = 2;
    final int EQUAL_TO = 3;

    public void vSetObjective(String sFunction)
    {
        this.sObjFunction = sFunction;
    }
    public void vAddConstraint(String sFunction, int relationOperator, double rhs_value)
    {
        Constraint oConstraint = new Constraint(sFunction,relationOperator,rhs_value);
        sConstraintList.add(oConstraint);
    }
    public void vSolveLinearProgram()
    {
        try {
            // Create a problem with 3B variables and 0 constraints
            LpSolve solver = LpSolve.makeLp(0, 3*OnAire.B);
            // add constraints
            System.out.println("Solving the model!");
            for(int i=0;i<sConstraintList.size();i++)
            {
                //System.out.println("Constraint "+i+" "+sConstraintList.get(i).sConstraintFunction);
                solver.strAddConstraint(sConstraintList.get(i).sConstraintFunction,
                        sConstraintList.get(i).iRelation, sConstraintList.get(i).rhs);
            }
            // set objective function
            //System.out.println("Objective Function is : "+sObjFunction);
            solver.strSetObjFn(sObjFunction);
            // solve the problem
            solver.solve();
            // print solution
            //System.out.println("Value of objective function: " + solver.getObjective());
            double[] var = solver.getPtrVariables();
            //Currently the maximum value of the prob dist is taken as time for vechicle stoptime
            int x_det_max = 0;
            for (int i=1;i<OnAire.B;i++)
            {
                if (var[i]>var[x_det_max])
                    x_det_max = i;
            }
            OnAire.x_det = x_det_max;
//            for (int i = 0; i < var.length; i++) {
//                System.out.println("Value of var[" + i + "] = " + var[i]);
//            }
            //solver.printLp();

            // delete the problem and free memory
            solver.deleteLp();
        }
        catch (LpSolveException e) {
            e.printStackTrace();
        }

    }

}
