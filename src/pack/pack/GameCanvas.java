/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pack.pack;

import android.content.Context;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 *
 * @author Martin
 */
public class GameCanvas extends SurfaceView implements SurfaceHolder.Callback
{
    SurfaceHolder surfaceHolder;
    GameLogic gameLogic;
    DisplayThread displayThread;
    Context context;
    
    public GameCanvas(Context context, GameLogic gameLogic) 
    {
        super(context);
        this.gameLogic = gameLogic;
        InitializeCanvas(context);
    }
    
    private void InitializeCanvas(Context context)
    {
        surfaceHolder = getHolder();
        surfaceHolder.addCallback(this);
        this.context = context;
        
        // TODO: Add display Thread
        displayThread = new DisplayThread(surfaceHolder, context, gameLogic);
        setFocusable(true);
    }
    
    public void surfaceCreated(SurfaceHolder arg0) 
    {
        if(!displayThread.IsRunning())
        {
            displayThread = new DisplayThread(getHolder(), context, gameLogic);
            displayThread.start();
        }
        else
        {
            displayThread.start();
        }
    }

    public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3) 
    {
        // TODO: react to surface changes
    }

    public void surfaceDestroyed(SurfaceHolder arg0) 
    {
        //Stop the display thread
        displayThread.SetIsRunning(false);	
       
        boolean retry = true;
        while (retry) 
        {
            try 
            {
                displayThread.join();
                retry = false;
            } 
            catch (InterruptedException e) {}
        }
    }
}
