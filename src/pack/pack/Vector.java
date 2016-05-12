package pack.pack;

/**
 * @author Christian
 */
public class Vector
{
  public float x=0;
  public float y=0;

  @Override
  public String toString()
  {
    return String.format("[Vector;x:%.3f;y:%.3f]", x,y);
  }
  
}
