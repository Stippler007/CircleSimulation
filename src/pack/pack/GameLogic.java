package pack.pack;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author Martin
 */
public class GameLogic
{

  public static int screenWidth, screenHeight;

  private int touchX, touchY;
  private boolean touched;

  private List<Circle> circles;
  private Paint circlePaint;

  private float timer = 0;
  private final float maxTimer = 1;

  public GameLogic(int screenWidth, int screenHeight)
  {
    GameLogic.screenHeight = screenHeight;
    GameLogic.screenWidth = screenWidth;

    touchX = 0;
    touchY = 0;
    touched = false;

    circlePaint = new Paint();
    circlePaint.setAntiAlias(true);
    circlePaint.setStyle(Paint.Style.FILL);
    circlePaint.setColor(Color.RED);

    circles = new ArrayList<Circle>();
    createTestData();
  }

  public void update(float tslf, float thisFrame)
  {
    if (touched)
    {
      Circle helpCircle = null;
      for (Circle circle : circles)
      {
        if (circle.intersects(touchX, touchY))
        {
          helpCircle = circle;
          break;
        }
      }

      if (helpCircle != null)
      {
        helpCircle.setMass(helpCircle.getMass() + 10 * tslf);
      }
      else if (timer >= maxTimer)
      {
        circles.add(new Circle(touchX, touchY, 10, circles, circlePaint));
        timer -= maxTimer;
      }
    }
    if (timer < maxTimer)
    {
      timer += tslf;
    }

    for (Circle c : circles)
    {
      c.update(tslf);
    }
  }

  public void draw(Canvas canvas)
  {
    canvas.drawColor((Color.WHITE));
    for (Circle c : circles)
    {
      c.draw(canvas);
    }
  }

  private void createTestData()
  {
    circles.add(new Circle(screenWidth / 4, screenHeight / 4, 10, circles, circlePaint));
    circles.add(new Circle(screenWidth / 3, screenHeight / 3, 10, circles, circlePaint));
    Random r = new Random();

  }

  public void setTouchX(int touchX)
  {
    this.touchX = touchX;
  }

  public void setTouchY(int touchY)
  {
    this.touchY = touchY;
  }

  public void setIsTouched(boolean isTouched)
  {
    this.touched = isTouched;
  }

  public boolean isTouched()
  {
    return touched;
  }

}
