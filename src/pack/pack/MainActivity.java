package pack.pack;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.MotionEvent;
import android.view.Window;
import android.view.WindowManager;

public class MainActivity extends Activity
{

  /**
   * Called when the activity is first created.
   */
  GameCanvas canvas;
  GameLogic logic;

  int screenWidth, screenHeight;

  @Override
  public void onCreate(Bundle savedInstanceState)
  {
    super.onCreate(savedInstanceState);
    setScreenMode(this);

    logic = new GameLogic(screenWidth, screenHeight);
    canvas = new GameCanvas(this, logic);
    //setContentView(R.layout.main);
    setContentView(canvas);
  }

  @Override
  public boolean onTouchEvent(MotionEvent event)
  {
    super.onTouchEvent(event);
    int action = event.getAction();
    switch (action)
    {
      case MotionEvent.ACTION_DOWN:
      {
        logic.setTouchX((int)event.getX());
        logic.setTouchY((int)event.getY());
        logic.setIsTouched(true);
        break;
      }
      case MotionEvent.ACTION_UP:
      {
        logic.setTouchX((int)event.getX());
        logic.setTouchY((int)event.getY());
        logic.setIsTouched(false);
        break;
      }
      
      case MotionEvent.ACTION_MOVE:
      {
        logic.setTouchX((int)event.getX());
        logic.setTouchY((int)event.getY());
        break;
      }
      default:
        break;
    }
    return false;
  }

  public void setScreenMode(Context context)
  {
    // Set to fullscreen
    requestWindowFeature(Window.FEATURE_NO_TITLE);
    getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN);

    // Get Screen metrics
    WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
    Display display = wm.getDefaultDisplay();
    DisplayMetrics metrics = new DisplayMetrics();
    display.getMetrics(metrics);
    screenWidth = metrics.widthPixels;
    screenHeight = metrics.heightPixels;
  }
}
