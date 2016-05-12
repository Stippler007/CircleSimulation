package pack.pack;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import java.util.List;

/**
 * @author Christian
 */
public class Circle
{

  private float epsilon = 1f;
  private float x, y, mass;
  private float maxMass=69;
  private Vector moveVector;
  private float speed = 100;
  
  private Paint drawingPaint;
  private List<Circle> circles;

  public Circle(float x, float y, float mass, List<Circle> circles, Paint paint)
  {
    this.x = x;
    this.y = y;
    this.mass = mass;
    this.circles = circles;
    this.drawingPaint = paint;
    moveVector = new Vector();
  }

  public void update(float tslf)
  {
    float centerX = 0;
    float centerY = 0;

    for (Circle c : circles)
    {
      centerX += c.x;
      centerY += c.y;
    }

    centerX /= circles.size();
    centerY /= circles.size();

    Vector v = getDirectonVector(centerX, centerY);
    moveVector.x += v.x * speed * tslf;
    moveVector.y += v.y * speed * tslf;

    if (x - getRadius() < 0 && moveVector.x < 0)
    {
      moveVector.x *= -1;
    } else if (x + getRadius() > GameLogic.screenWidth && moveVector.x > 0)
    {
      moveVector.x *= -1;
    }

    if (y - getRadius() < 0 && moveVector.y < 0)
    {
      moveVector.y *= -1;
    } else if (y + getRadius() > GameLogic.screenHeight && moveVector.y > 0)
    {
      moveVector.y *= -1;
    }

    collisionResponse();

    x += moveVector.x * tslf;
    y += moveVector.y * tslf;
  }

  public Vector getDirectonVector(float x, float y)
  {
    Vector v = new Vector();
    v.x = x - this.x;
    v.y = y - this.y;

    float help = (float) Math.sqrt(v.x * v.x + v.y * v.y);

    if (help != 0)
    {
      v.x /= help;
      v.y /= help;
    }

    return v;
  }

  private void collisionResponse()
  {
    for (Circle c2 : circles)
    {
      Circle c1 = this;
      if (c2 != this && c1.intersects(c2))
      {
        float normalX = c2.x - c1.x;
        float normalY = c2.y - c1.y;
        float length = (float) Math.sqrt(normalX * normalX + normalY * normalY);
        normalX /= length;
        normalY /= length;

        float overlapplength = c2.getRadius() + c1.getRadius() - length;

        float masses = c1.mass + c2.mass;
        c1.x += -normalX * overlapplength * c2.mass / masses;
        c1.y += -normalY * overlapplength * c2.mass / masses;
        c2.x += -normalX * overlapplength * c1.mass / masses;
        c2.y += +normalY * overlapplength * c1.mass / masses;

        float f = (-(1 + epsilon) * ((c2.moveVector.x - c1.moveVector.x)
                * normalX + (c2.moveVector.y - c1.moveVector.y) * normalY)) / (1 / c1.mass + 1 / c2.mass);
        float oldspeedXc1 = c1.moveVector.x;
        float oldspeedYc1 = c1.moveVector.y;
        float oldspeedXc2 = c2.moveVector.x;
        float oldspeedYc2 = c2.moveVector.y;

        c1.moveVector.x = oldspeedXc1 - f / c1.mass * normalX;
        c1.moveVector.y = oldspeedYc1 - f / c1.mass * normalY;

        c2.moveVector.x = oldspeedXc2 + f / c2.mass * normalX;
        c2.moveVector.y = oldspeedYc2 + f / c2.mass * normalY;
      }
    }
  }

  public void draw(Canvas canvas)
  {
    float radius = mass;
    drawingPaint.setColor(Color.RED);
    canvas.drawCircle(x, y, radius, drawingPaint);
  }

  public void setMass(float mass)
  {
    this.mass=mass>=maxMass?maxMass:mass;
  }
  
  public float getX()
  {
    return x;
  }

  public float getY()
  {
    return y;
  }

  public float getMass()
  {
    return mass;
  }

  public float getSpeedX()
  {
    return moveVector.x;
  }

  public float getSpeedY()
  {
    return moveVector.y;
  }

  public float getRadius()
  {
    return mass;
  }

  public boolean intersects(Circle c)
  {
    float absX = x - c.x;
    float absY = y - c.y;
    double abs = absX * absX + absY * absY;
    return abs <= (this.getRadius() + c.getRadius()) * (this.getRadius() + c.getRadius());
  }
  
  public boolean intersects(float x,float y)
  {
    float absX = this.x - x;
    float absY = this.y - y;
    double abs = Math.sqrt(absX * absX + absY * absY);
    return abs <= (this.getRadius());
  }

  @Override
  public String toString()
  {
    return super.toString()+" CircleX: "+x+" CircleY: "+y;
  }
  
  
}
