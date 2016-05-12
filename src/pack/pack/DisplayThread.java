/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pack.pack;

import android.content.Context;
import android.graphics.Canvas;
import android.view.SurfaceHolder;

/**
 * @author Martin
 */
public class DisplayThread extends Thread
{
  private final SurfaceHolder surfaceHolder;
  private final GameLogic gameLogic;
  
  private boolean isRunning;
  final long DELAY = 1;

  public DisplayThread(SurfaceHolder surfaceHolder, Context context, GameLogic gameLogic)
  {
    this.surfaceHolder = surfaceHolder;
    this.gameLogic = gameLogic;
    isRunning = true;
  }

  @Override
  public void run()
  {
    Canvas drawingCanvas;
    long lastFrame = System.currentTimeMillis();
    
    //Looping until the boolean is false
    while (isRunning)
    {
      long thisFrame = System.currentTimeMillis();
      float tslf = (float) (thisFrame - lastFrame) / 1000;
      lastFrame = thisFrame;
      
      // Update gameLogic
      gameLogic.update(tslf, thisFrame);
      
      // lock the canvas
      drawingCanvas = surfaceHolder.lockCanvas(null);

      if (drawingCanvas != null)
      {
        synchronized (surfaceHolder)
        {
          gameLogic.draw(drawingCanvas);
        }

        //unlocking the Canvas
        surfaceHolder.unlockCanvasAndPost(drawingCanvas);
      }

      try
      {
        Thread.sleep(DELAY);
      } catch (InterruptedException ex)
      {
        //TODO: Log
      }
    }
  }

  public void SetIsRunning(boolean isRunning)
  {
    this.isRunning = isRunning;
  }

  public boolean IsRunning()
  {
    return isRunning;
  }
}
