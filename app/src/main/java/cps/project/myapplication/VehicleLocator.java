package cps.project.myapplication;

import android.location.Location;

import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by vedahari on 12/6/2015.
 */

/*
*  This class indicates the current location and
*  speed information. Saves current position to a db file.*
* */

public class VehicleLocator {
public enum VEHICLE_STATE{STOPPED,MOVING};
    VEHICLE_STATE vehicle_state;
    Location oCurrentLocation;
    long lCurrentStopLength;
    private long lHaltStartTime;

    public VehicleLocator() {
        this.vehicle_state = VEHICLE_STATE.STOPPED;
    }

    private void vUpdateCurrentStopLengthToDB(long lStopLength) throws IOException {
        if(lStopLength>4)
        {
            //Write this to file
            //TODO: The db file should be part of the apk file & should be secured common path
            String filename= "D:/Courses/CPS/Project/Project3_OnlineAlgo/files/fileTest.txt";
            FileWriter fw =  null;
            try
            {
                fw = new FileWriter(filename,true);
                fw.write(Long.toString(lStopLength));//appends the string to the file
            }
            catch(IOException ioe)
            {
                System.err.println("IOException: " + ioe.getMessage());
            }

            finally {
                if (fw!=null) {
                    //TODO:Ensure there is no input output exception. Decide how often to close,
                    fw.close();
                }
            }
        }
        else{
            System.out.println("Not storing the time in DB as it is less than 5 seconds!");
        }
    }
    private void vHandleVehicleSpeed(float fVehicleSpeed)
    {
        switch(vehicle_state)
        {
            case STOPPED: {
                if(fVehicleSpeed>0)
                {
                    //Vehicle has started moving. Hence store the stopped duration.
                    lCurrentStopLength = (System.currentTimeMillis()/1000)-lHaltStartTime;
                    try{
                        vUpdateCurrentStopLengthToDB(lCurrentStopLength);
                    }
                    catch (IOException ioe)
                    {
                        System.err.println("IOException: " + ioe.getMessage());
                    }
                }
            }
            break;
            case MOVING:
            {
                if(fVehicleSpeed==0)
                {
                    lHaltStartTime = System.currentTimeMillis()/1000;
                }
            }
            break;
        }

    }
    /*
    * This function should update whether the vehicle is moving or not.
    * */
    public void vUpdateVehicleState(Location oLocation)
    {
        if(oLocation.hasSpeed())
        {
            if(oLocation.getSpeed()==0.0)
            {
                //Assuming that the vehicle is in zero speed now.
                vehicle_state = VEHICLE_STATE.STOPPED;
            }
            else
            {
                vehicle_state = VEHICLE_STATE.MOVING;
            }

        }
        else
        {
            //Should implement my own logic to calculate speed.
        }

    }
    //TODO:Update the vehicle speed based on the location information received
    private void vCalculateSpeed(){}


    public void vSetVehiclePosition(Location oLocation){
        vHandleVehicleSpeed(oLocation.getSpeed());
        vUpdateVehicleState(oLocation);
        this.oCurrentLocation = oLocation;
    }
    public Location oGetVehicleLocation(){
        return oCurrentLocation;
    }
}
